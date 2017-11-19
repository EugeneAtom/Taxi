/*
 * (C) Copyright 2007-2017, by Lucas J Scharenbroich and Contributors.
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
package org.jgrapht.graph;

import java.util.*;

import org.jgrapht.*;

/**
 * A unit test for the AsWeightedGraph view and the AsDirectedWeightedGraph view.
 *
 * @author Lucas J. Scharenbroich
 * @author Joris Kinable
 */
public class AsWeightedGraphTest
    extends EnhancedTestCase
{
    // ~ Instance fields --------------------------------------------------------

    public Graph<String, DefaultWeightedEdge> weightedGraph;
    public Graph<String, DefaultEdge> unweightedGraph;

    public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> directedWeightedGraph;
    public Graph<String, DefaultEdge> directedUnweightedGraph;

    public SimpleWeightedGraph<String, DefaultWeightedEdge> undirectedWeightedGraph;
    public Graph<String, DefaultEdge> undirectedUnweightedGraph;

    // ~ Methods ----------------------------------------------------------------

    @Override
    public void setUp()
    {
        // Create a weighted, undirected graph
        weightedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        this.createdWeightedGraph(weightedGraph);

        // Create an undirected graph without weights
        unweightedGraph = new SimpleGraph<>(DefaultEdge.class);
        this.createdUnweightedGraph(unweightedGraph);

        // Create another weighted, undirected graph
        undirectedWeightedGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        this.createdWeightedGraph(undirectedWeightedGraph);

        // Create another undirected graph without weights
        undirectedUnweightedGraph = new SimpleGraph<>(DefaultEdge.class);
        this.createdUnweightedGraph(undirectedUnweightedGraph);

        // Create a weighted, directed graph
        directedWeightedGraph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        this.createdWeightedGraph(directedWeightedGraph);

        // Create a directed graph without weights
        directedUnweightedGraph = new SimpleDirectedGraph<>(DefaultEdge.class);
        this.createdUnweightedGraph(directedUnweightedGraph);
    }

    private void createdWeightedGraph(Graph<String, DefaultWeightedEdge> graph)
    {
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");

        graph.setEdgeWeight(graph.addEdge("v1", "v2"), 1.);
        graph.setEdgeWeight(graph.addEdge("v2", "v3"), 2.);
        graph.setEdgeWeight(graph.addEdge("v3", "v1"), 3.);
    }

    private void createdUnweightedGraph(Graph<String, DefaultEdge> graph)
    {
        graph.addVertex("v1");
        graph.addVertex("v2");
        graph.addVertex("v3");

        graph.addEdge("v1", "v2");
        graph.addEdge("v2", "v3");
        graph.addEdge("v3", "v1");
    }

    /*** Unweighted graphs ***/

    public void testUnweightedGraphs()
    {
        this.testUnweightedGraph(unweightedGraph);
        this.testUnweightedGraph(undirectedUnweightedGraph);
        this.testUnweightedGraph(directedUnweightedGraph);
    }

    public void testUnweightedGraph(Graph<String, DefaultEdge> graph)
    {
        DefaultEdge e1 = graph.getEdge("v1", "v2");
        DefaultEdge e2 = graph.getEdge("v2", "v3");
        DefaultEdge e3 = graph.getEdge("v3", "v1");

        Map<DefaultEdge, Double> weightMap = new HashMap<>();
        weightMap.put(e1, 9.0);

        assertEquals(graph.getEdgeWeight(e1), Graph.DEFAULT_EDGE_WEIGHT);

        Graph<String, DefaultEdge> graphView;
        graphView = new AsWeightedGraph<>(graph, weightMap);

        assertEquals(graphView.getEdgeWeight(e1), 9.0);
        assertEquals(graphView.getEdgeWeight(e2), Graph.DEFAULT_EDGE_WEIGHT);
        assertEquals(graphView.getEdgeWeight(e3), Graph.DEFAULT_EDGE_WEIGHT);

        graphView.setEdgeWeight(e2, 5.0);
        assertEquals(graphView.getEdgeWeight(e2), 5.0);
        assertEquals(graph.getEdgeWeight(e2), Graph.DEFAULT_EDGE_WEIGHT);
    }

    /*** Weighted graphs ***/

    public void testWeightedGraphs()
    {
        this.testWeightedGraph(weightedGraph);
        this.testWeightedGraph(undirectedWeightedGraph);
        this.testWeightedGraph(directedWeightedGraph);
    }

    public void testWeightedGraph(Graph<String, DefaultWeightedEdge> graph)
    {
        DefaultWeightedEdge e1 = graph.getEdge("v1", "v2");
        DefaultWeightedEdge e2 = graph.getEdge("v2", "v3");
        DefaultWeightedEdge e3 = graph.getEdge("v3", "v1");

        Map<DefaultWeightedEdge, Double> weightMap = new HashMap<>();
        weightMap.put(e1, 9.0);
        weightMap.put(e3, 8.0);

        Graph<String, DefaultWeightedEdge> graphView;
        graphView = new AsWeightedGraph<>(graph, weightMap);

        assertEquals(graphView.getEdgeWeight(e1), 9.0);
        assertEquals(graphView.getEdgeWeight(e2), 2.0);
        assertEquals(graphView.getEdgeWeight(e3), 8.0);

        graphView.setEdgeWeight(e2, 5.0);
        assertEquals(graphView.getEdgeWeight(e2), 5.0);
        assertEquals(graph.getEdgeWeight(e2), 5.0);

        try {
            graphView.getEdgeWeight(null);
            // should not get here
            assertFalse();
        } catch (NullPointerException ex) {
            // expected, swallow
        }
    }
}

// End AsWeightedGraphTest.java
