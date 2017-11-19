/*
 * (C) Copyright 2015-2017, by Graeme Ahokas and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * This program and the accompanying materials are dual-licensed under
 * either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation, or (at your option) any
 * later version.
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */
package org.jgrapht.alg.matching;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.*;

/**
 * This class finds a maximum weight matching of a simple undirected weighted bipartite graph. The
 * algorithm runs in O(V|E|^2). The algorithm is described in The LEDA Platform of Combinatorial and
 * Geometric Computing, Cambridge University Press, 1999.
 * https://people.mpi-inf.mpg.de/~mehlhorn/LEDAbook.html Note: the input graph must be bipartite
 * with positive integer edge weights
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Graeme Ahokas
 */
public class MaximumWeightBipartiteMatching<V, E>
    implements MatchingAlgorithm<V, E>
{
    private final Graph<V, E> graph;
    private Set<V> partition1;
    private Set<V> partition2;

    private Map<V, Long> vertexWeights;
    private Map<V, Boolean> hasVertexBeenProcessed;
    private Map<E, Boolean> isEdgeMatched;

    private Set<E> bipartiteMatching;

    /**
     * Construct a new instance of the algorithm. Supported graphs are simple undirected weighted
     * bipartite with positive integer edge weights.
     * 
     * @param graph the input graph
     * @param partition1 the first partition of the vertex set
     * @param partition2 the second partition of the vertex set
     * @throws IllegalArgumentException if the graph is not undirected
     */
    public MaximumWeightBipartiteMatching(Graph<V, E> graph, Set<V> partition1, Set<V> partition2)
    {
        this.graph = GraphTests.requireUndirected(graph);
        if (partition1 == null) {
            throw new IllegalArgumentException("Partition 1 cannot be null");
        }
        this.partition1 = partition1;
        if (partition2 == null) {
            throw new IllegalArgumentException("Partition 2 cannot be null");
        }
        this.partition2 = partition2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Matching<V, E> getMatching()
    {
        if (!GraphTests.isSimple(graph)) {
            throw new IllegalArgumentException("Only simple graphs supported");
        }
        if (!GraphTests.isBipartitePartition(graph, partition1, partition2)) {
            throw new IllegalArgumentException("Graph partition is not bipartite");
        }
        this.vertexWeights = new HashMap<>();
        this.hasVertexBeenProcessed = new HashMap<>();
        this.isEdgeMatched = new HashMap<>();

        initializeVerticesAndEdges();

        this.bipartiteMatching = maximumWeightBipartiteMatching();
        double weight = 0d;
        for (E edge : bipartiteMatching) {
            weight += graph.getEdgeWeight(edge);
        }
        return new MatchingImpl<>(graph, bipartiteMatching, weight);
    }

    private void initializeVerticesAndEdges()
    {
        for (V vertex : graph.vertexSet()) {
            if (isTargetVertex(vertex)) {
                hasVertexBeenProcessed.put(vertex, true);
                setVertexWeight(vertex, (long) 0);
            } else {
                hasVertexBeenProcessed.put(vertex, false);
                setVertexWeight(vertex, maximumWeightOfEdgeIncidentToVertex(vertex));
            }
        }

        for (E edge : graph.edgeSet()) {
            isEdgeMatched.put(edge, false);
        }
    }

    private long maximumWeightOfEdgeIncidentToVertex(V vertex)
    {
        long maxWeight = 0;
        for (E edge : graph.edgesOf(vertex)) {
            if (graph.getEdgeWeight(edge) > maxWeight) {
                maxWeight = (long) graph.getEdgeWeight(edge);
            }
        }
        return maxWeight;
    }

    private boolean isSourceVertex(V vertex)
    {
        return partition1.contains(vertex);
    }

    private boolean isTargetVertex(V vertex)
    {
        return partition2.contains(vertex);
    }

    private long vertexWeight(V vertex)
    {
        return vertexWeights.get(vertex);
    }

    private void setVertexWeight(V vertex, Long weight)
    {
        vertexWeights.put(vertex, weight);
    }

    private long reducedWeight(E edge)
    {
        return (long) (vertexWeight(graph.getEdgeSource(edge))
            + vertexWeight(graph.getEdgeTarget(edge)) - graph.getEdgeWeight(edge));
    }

    private boolean isVertexMatched(V vertex, Set<E> matchings)
    {
        for (E edge : matchings) {
            if (graph.getEdgeSource(edge).equals(vertex)
                || graph.getEdgeTarget(edge).equals(vertex))
            {
                return true;
            }
        }
        return false;
    }

    private void addPathToMatchings(List<E> path, Set<E> matchings)
    {
        for (int i = 0; i < path.size(); i++) {
            E edge = path.get(i);
            if ((i % 2) == 0) {
                isEdgeMatched.put(edge, true);
                matchings.add(edge);
            } else {
                isEdgeMatched.put(edge, false);
                matchings.remove(edge);
            }
        }
    }

    private void adjustVertexWeights(Map<V, List<E>> reachableVertices)
    {
        long alpha = Long.MAX_VALUE;
        for (V vertex : reachableVertices.keySet()) {
            if (isSourceVertex(vertex) && (vertexWeights.get(vertex) < alpha)) {
                alpha = vertexWeights.get(vertex);
            }
        }

        long beta = Long.MAX_VALUE;
        for (V vertex : reachableVertices.keySet()) {
            if (isTargetVertex(vertex)) {
                continue;
            }
            for (E edge : graph.edgesOf(vertex)) {
                if (hasVertexBeenProcessed.get(Graphs.getOppositeVertex(graph, edge, vertex))
                    && !reachableVertices
                        .keySet().contains(Graphs.getOppositeVertex(graph, edge, vertex))
                    && (reducedWeight(edge) < beta))
                {
                    beta = reducedWeight(edge);
                }
            }
        }

        assert ((alpha > 0) && (beta > 0));

        long minValue = Math.min(alpha, beta);

        for (V vertex : reachableVertices.keySet()) {
            if (isSourceVertex(vertex)) {
                vertexWeights.put(vertex, vertexWeights.get(vertex) - minValue);
            } else {
                vertexWeights.put(vertex, vertexWeights.get(vertex) + minValue);
            }
        }
    }

    private Map<V, List<E>> verticesReachableByTightAlternatingEdgesFromVertex(V vertex)
    {
        Map<V, List<E>> pathsToVertices = new HashMap<>();
        pathsToVertices.put(vertex, new ArrayList<>());
        findPathsToVerticesFromVertices(Collections.singletonList(vertex), false, pathsToVertices);
        return pathsToVertices;
    }

    private void findPathsToVerticesFromVertices(
        List<V> verticesToProcess, boolean needMatchedEdge, Map<V, List<E>> pathsToVertices)
    {
        if (verticesToProcess.size() == 0) {
            return;
        }
        List<V> nextVerticesToProcess = new ArrayList<>();
        for (V vertex : verticesToProcess) {
            for (E edge : graph.edgesOf(vertex)) {
                V adjacentVertex = Graphs.getOppositeVertex(graph, edge, vertex);
                if (hasVertexBeenProcessed.get(adjacentVertex) && (reducedWeight(edge) == 0)
                    && !pathsToVertices.keySet().contains(adjacentVertex))
                {
                    if ((needMatchedEdge && isEdgeMatched.get(edge))
                        || (!needMatchedEdge && !isEdgeMatched.get(edge)))
                    {
                        nextVerticesToProcess.add(adjacentVertex);
                        List<E> pathToAdjacentVertex = new ArrayList<>(pathsToVertices.get(vertex));
                        pathToAdjacentVertex.add(edge);
                        pathsToVertices.put(adjacentVertex, pathToAdjacentVertex);
                    }
                }
            }
        }
        findPathsToVerticesFromVertices(nextVerticesToProcess, !needMatchedEdge, pathsToVertices);
    }

    private Set<E> maximumWeightBipartiteMatching()
    {
        Set<E> matchings = new HashSet<>();
        for (V vertex : partition1) {
            hasVertexBeenProcessed.put(vertex, true);
            while (true) {
                Map<V, List<E>> reachableVertices =
                    verticesReachableByTightAlternatingEdgesFromVertex(vertex);
                boolean successful = false;
                for (V reachableVertex : reachableVertices.keySet()) {
                    if (isSourceVertex(reachableVertex) && (vertexWeight(reachableVertex) == 0)) {
                        addPathToMatchings(reachableVertices.get(reachableVertex), matchings);
                        successful = true;
                        break;
                    }
                    if (isTargetVertex(reachableVertex)
                        && !isVertexMatched(reachableVertex, matchings))
                    {
                        addPathToMatchings(reachableVertices.get(reachableVertex), matchings);
                        successful = true;
                        break;
                    }
                }
                if (successful) {
                    break;
                }
                adjustVertexWeights(reachableVertices);
            }
        }
        return matchings;
    }
}

// End MaximumWeightBipartiteMatching.java
