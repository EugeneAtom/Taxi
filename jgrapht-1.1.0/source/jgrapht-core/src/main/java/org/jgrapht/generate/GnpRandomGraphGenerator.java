/*
 * (C) Copyright 2016-2017, by Dimitrios Michail and Contributors.
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
 * Create a random graph based on the G(n, p) Erdős–Rényi model. See the Wikipedia article for
 * details and references about <a href="https://en.wikipedia.org/wiki/Random_graph">Random
 * Graphs</a> and the
 * <a href="https://en.wikipedia.org/wiki/Erd%C5%91s%E2%80%93R%C3%A9nyi_model">Erdős–Rényi model</a>
 * .
 * 
 * <p>
 * In the G(n, p) model, a graph is constructed by connecting nodes randomly. Each edge is included
 * in the graph with probability p independent from every other edge. The complexity of the
 * generator is O(n^2) where n is the number of vertices.
 * 
 * <p>
 * For the G(n, M) model please see {@link GnmRandomGraphGenerator}.
 *
 * @author Dimitrios Michail
 * @since September 2016
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 * @see GnmRandomGraphGenerator
 */
public class GnpRandomGraphGenerator<V, E>
    implements GraphGenerator<V, E, V>
{
    private static final boolean DEFAULT_ALLOW_LOOPS = false;

    private final Random rng;
    private final int n;
    private final double p;
    private final boolean loops;

    /**
     * Create a new G(n, p) random graph generator. The generator does not create self-loops.
     * 
     * @param n the number of nodes
     * @param p the edge probability
     */
    public GnpRandomGraphGenerator(int n, double p)
    {
        this(n, p, new Random(), DEFAULT_ALLOW_LOOPS);
    }

    /**
     * Create a new G(n, p) random graph generator. The generator does not create self-loops.
     * 
     * @param n the number of nodes
     * @param p the edge probability
     * @param seed seed for the random number generator
     */
    public GnpRandomGraphGenerator(int n, double p, long seed)
    {
        this(n, p, new Random(seed), DEFAULT_ALLOW_LOOPS);
    }

    /**
     * Create a new G(n, p) random graph generator.
     * 
     * @param n the number of nodes
     * @param p the edge probability
     * @param seed seed for the random number generator
     * @param loops whether the generated graph may create loops
     */
    public GnpRandomGraphGenerator(int n, double p, long seed, boolean loops)
    {
        this(n, p, new Random(seed), loops);
    }

    /**
     * Create a new G(n, p) random graph generator.
     * 
     * @param n the number of nodes
     * @param p the edge probability
     * @param rng the random number generator to use
     * @param loops whether the generated graph may create loops
     */
    public GnpRandomGraphGenerator(int n, double p, Random rng, boolean loops)
    {
        if (n < 0) {
            throw new IllegalArgumentException("number of vertices must be non-negative");
        }
        this.n = n;
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("not valid probability of edge existence");
        }
        this.p = p;
        this.rng = rng;
        this.loops = loops;
    }

    /**
     * Generates a random graph based on the G(n, p) model.
     * 
     * @param target the target graph
     * @param vertexFactory the vertex factory
     * @param resultMap not used by this generator, can be null
     */
    @Override
    public void generateGraph(
        Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, V> resultMap)
    {
        // special case
        if (n == 0) {
            return;
        }

        // check whether to also create loops
        boolean createLoops = loops;
        if (createLoops) {
            if (target instanceof AbstractBaseGraph<?, ?>) {
                AbstractBaseGraph<V, E> abg = (AbstractBaseGraph<V, E>) target;
                if (!abg.isAllowingLoops()) {
                    throw new IllegalArgumentException(
                        "Provided graph does not support self-loops");
                }
            } else {
                createLoops = false;
            }
        }

        // create vertices
        int previousVertexSetSize = target.vertexSet().size();
        Map<Integer, V> vertices = new HashMap<>(n);
        for (int i = 0; i < n; i++) {
            V v = vertexFactory.createVertex();
            target.addVertex(v);
            vertices.put(i, v);
        }

        if (target.vertexSet().size() != previousVertexSetSize + n) {
            throw new IllegalArgumentException(
                "Vertex factory did not produce " + n + " distinct vertices.");
        }

        // check if graph is directed
        boolean isDirected = target.getType().isDirected();

        // create edges
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                V s = vertices.get(i);
                V t = vertices.get(j);

                if (i == j) {
                    if (!createLoops) {
                        // no self-loops
                        continue;
                    }
                }

                // s->t
                if (rng.nextDouble() < p) {
                    target.addEdge(s, t);
                }

                if (isDirected) {
                    // t->s
                    if (rng.nextDouble() < p) {
                        target.addEdge(t, s);
                    }
                }
            }
        }

    }

}

// End GnpRandomGraphGenerator.java
