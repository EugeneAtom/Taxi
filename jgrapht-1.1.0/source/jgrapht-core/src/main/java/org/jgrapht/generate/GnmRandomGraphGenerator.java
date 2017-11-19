/*
 * (C) Copyright 2005-2017, by Assaf Lehr, Dimitrios Michail and Contributors.
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
package org.jgrapht.generate;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;

/**
 * Create a random graph based on the G(n, M) Erdős–Rényi model. See the Wikipedia article for
 * details and references about <a href="https://en.wikipedia.org/wiki/Random_graph">Random
 * Graphs</a> and the
 * <a href="https://en.wikipedia.org/wiki/Erd%C5%91s%E2%80%93R%C3%A9nyi_model">Erdős–Rényi model</a>
 * .
 * 
 * <p>
 * In the G(n, M) model, a graph is chosen uniformly at random from the collection of all graphs
 * which have n nodes and M edges. For example, in the G(3, 2) model, each of the three possible
 * graphs on three vertices and two edges are included with probability 1/3.
 * 
 * <p>
 * The implementation creates the vertices and then randomly chooses an edge and tries to add it. If
 * the add fails for any reason (an edge already exists and multiple edges are not allowed) it will
 * just choose another and try again. The performance therefore varies significantly based on the
 * probability of successfully constructing an acceptable edge.
 * 
 * <p>
 * The implementation tries to guess the number of allowed edges based on the following. If
 * self-loops or multiple edges are allowed and requested, the maximum number of edges is
 * {@link Integer#MAX_VALUE}. Otherwise the maximum for undirected graphs with n vertices is
 * n(n-1)/2 while for directed n(n-1). If the graph type cannot be determined (for example using
 * adapter classes or user-created custom graph types) the generator assumes the graph is undirected
 * and therefore uses n(n-1)/2 as the maximum number of edges. If the user requests self-loops
 * and/or multiple edges and the graph type cannot be determined, the corresponding feature is
 * silently ignored.
 * 
 * <p>
 * For the G(n, p) model please see {@link GnpRandomGraphGenerator}.
 *
 * @author Assaf Lehr
 * @author Dimitrios Michail
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 * @see GnpRandomGraphGenerator
 */
public class GnmRandomGraphGenerator<V, E>
    implements GraphGenerator<V, E, V>
{
    private static final boolean DEFAULT_ALLOW_LOOPS = false;
    private static final boolean DEFAULT_ALLOW_MULTIPLE_EDGES = false;

    private final Random rng;
    private final int n;
    private final int m;
    private final boolean loops;
    private final boolean multipleEdges;

    /**
     * Create a new G(n, M) random graph generator. The generator does not create self-loops or
     * multiple edges between the same two vertices.
     * 
     * @param n the number of nodes
     * @param m the number of edges
     */
    public GnmRandomGraphGenerator(int n, int m)
    {
        this(n, m, new Random(), DEFAULT_ALLOW_LOOPS, DEFAULT_ALLOW_MULTIPLE_EDGES);
    }

    /**
     * Create a new G(n, M) random graph generator. The generator does not create self-loops or
     * multiple edges between the same two vertices.
     * 
     * @param n the number of nodes
     * @param m the number of edges
     * @param seed seed for the random number generator
     */
    public GnmRandomGraphGenerator(int n, int m, long seed)
    {
        this(n, m, new Random(seed), DEFAULT_ALLOW_LOOPS, DEFAULT_ALLOW_MULTIPLE_EDGES);
    }

    /**
     * Create a new G(n, M) random graph generator
     * 
     * @param n the number of nodes
     * @param m the number of edges
     * @param seed seed for the random number generator
     * @param loops whether the generated graph may contain loops
     * @param multipleEdges whether the generated graph many contain multiple edges between the same
     *        two vertices
     */
    public GnmRandomGraphGenerator(int n, int m, long seed, boolean loops, boolean multipleEdges)
    {
        this(n, m, new Random(seed), loops, multipleEdges);
    }

    /**
     * Create a new G(n, M) random graph generator
     * 
     * @param n the number of nodes
     * @param m the number of edges
     * @param rng the random number generator
     * @param loops whether the generated graph may contain loops
     * @param multipleEdges whether the generated graph many contain multiple edges between the same
     *        two vertices
     */
    public GnmRandomGraphGenerator(int n, int m, Random rng, boolean loops, boolean multipleEdges)
    {
        if (n < 0) {
            throw new IllegalArgumentException("number of vertices must be non-negative");
        }
        this.n = n;
        if (m < 0) {
            throw new IllegalArgumentException("number of edges must be non-negative");
        }
        this.m = m;
        this.rng = rng;
        this.loops = loops;
        this.multipleEdges = multipleEdges;
    }

    /**
     * Generates a random graph based on the G(n, M) model
     * 
     * @param target the target graph
     * @param vertexFactory the vertex factory
     * @param resultMap not used by this generator, can be null
     * 
     * @throws IllegalArgumentException if the number of edges, passed in the constructor, cannot be
     *         created on a graph of the concrete type with the specified number of vertices
     * @throws IllegalArgumentException if the graph does not support a requested feature such as
     *         self-loops or multiple edges
     */
    @Override
    public void generateGraph(
        Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap)
    {
        // special case
        if (n == 0) {
            return;
        }

        // check whether to create loops
        boolean createLoops = loops;
        if (createLoops) {
            if (target instanceof AbstractBaseGraph<?, ?>) {
                AbstractBaseGraph<V, E> abg = (AbstractBaseGraph<V, E>) target;
                if (!abg.isAllowingLoops()) {
                    throw new IllegalArgumentException(
                        "Provided graph does not support self-loops");
                }
            } else {
                // cannot guess here, so disable loops
                createLoops = false;
            }
        }

        // check whether to create multiple edges
        boolean createMultipleEdges = multipleEdges;
        if (createMultipleEdges) {
            if (target instanceof AbstractBaseGraph<?, ?>) {
                AbstractBaseGraph<V, E> abg = (AbstractBaseGraph<V, E>) target;
                if (!abg.isAllowingMultipleEdges()) {
                    throw new IllegalArgumentException(
                        "Provided graph does not support multiple edges between the same vertices");
                }
            } else {
                // cannot guess here, so disable multiple edges
                createMultipleEdges = false;
            }
        }

        // compute maximum allowed edges
        if (m > computeMaximumAllowedEdges(
            n, target.getType().isDirected(), createLoops, createMultipleEdges))
        {
            throw new IllegalArgumentException(
                "number of edges is not valid for the graph type " + "\n-> invalid number of edges="
                    + m + " for:" + " graph type=" + target.getClass() + ", number of vertices="
                    + n);
        }

        // create vertices
        Map<Integer, V> vertices = new HashMap<>(n);
        int previousVertexSetSize = target.vertexSet().size();
        for (int i = 0; i < n; i++) {
            V currVertex = vertexFactory.createVertex();
            target.addVertex(currVertex);
            vertices.put(i, currVertex);
        }

        if (target.vertexSet().size() != previousVertexSetSize + n) {
            throw new IllegalArgumentException(
                "Vertex factory did not produce " + n + " distinct vertices.");
        }

        // create edges
        int edgesCounter = 0;
        while (edgesCounter < m) {
            V s = vertices.get(rng.nextInt(n));
            V t = vertices.get(rng.nextInt(n));

            // check whether to add the edge
            boolean addEdge = false;
            if (s.equals(t)) { // self-loop
                if (createLoops) {
                    addEdge = true;
                }
            } else {
                if (createMultipleEdges) {
                    addEdge = true;
                } else {
                    if (!target.containsEdge(s, t)) {
                        addEdge = true;
                    }
                }
            }

            // if yes, add it
            if (addEdge) {
                try {
                    E resultEdge = target.addEdge(s, t);
                    if (resultEdge != null) {
                        edgesCounter++;
                    }
                } catch (IllegalArgumentException e) {
                    // do nothing, just ignore the edge
                }
            }
        }
    }

    /**
     * Return the number of allowed edges based on the graph type.
     * 
     * @param n number of nodes
     * @param isDirected whether the graph is directed or not
     * @param createLoops if loops are allowed
     * @param createMultipleEdges if multiple edges are allowed
     * @return the number of maximum edges
     */
    static <V, E> int computeMaximumAllowedEdges(
        int n, boolean isDirected, boolean createLoops, boolean createMultipleEdges)
    {
        if (n == 0) {
            return 0;
        }

        int maxAllowedEdges;
        try {
            if (isDirected) {
                maxAllowedEdges = Math.multiplyExact(n, n - 1);
            } else {
                // assume undirected
                if (n % 2 == 0) {
                    maxAllowedEdges = Math.multiplyExact(n / 2, n - 1);
                } else {
                    maxAllowedEdges = Math.multiplyExact(n, (n - 1) / 2);
                }
            }

            if (createLoops) {
                if (createMultipleEdges) {
                    return Integer.MAX_VALUE;
                } else {
                    if (isDirected) {
                        maxAllowedEdges = Math.addExact(maxAllowedEdges, Math.multiplyExact(2, n));
                    } else {
                        // assume undirected
                        maxAllowedEdges = Math.addExact(maxAllowedEdges, n);
                    }
                }
            } else {
                if (createMultipleEdges) {
                    if (n > 1) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
        } catch (ArithmeticException e) {
            return Integer.MAX_VALUE;
        }
        return maxAllowedEdges;
    }

}

// End GnmRandomGraphGenerator.java
