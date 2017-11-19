/*
 * (C) Copyright 2015-2017, by Andrew Chen and Contributors.
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
import org.jgrapht.graph.builder.*;

public class GraphBuilderTest
    extends EnhancedTestCase
{
    // ~ Instance fields --------------------------------------------------------

    private String v1 = "v1";
    private String v2 = "v2";
    private String v3 = "v3";
    private String v4 = "v4";
    private String v5 = "v5";
    private String v6 = "v6";
    private String v7 = "v7";
    private String v8 = "v8";

    // ~ Methods ----------------------------------------------------------------

    public void testAddVertex()
    {
        Graph<String, DefaultEdge> g =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addVertex(v1).addVertices(v2, v3).build();

        assertEquals(3, g.vertexSet().size());
        assertEquals(0, g.edgeSet().size());
        assertTrue(g.vertexSet().containsAll(Arrays.asList(v1, v2, v3)));
    }

    @Deprecated
    public void testAddVertexOldInterfaces()
    {
        Graph<String,
            DefaultEdge> g = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addVertex(v1).addVertices(v2, v3).build();

        assertEquals(3, g.vertexSet().size());
        assertEquals(0, g.edgeSet().size());
        assertTrue(g.vertexSet().containsAll(Arrays.asList(v1, v2, v3)));
    }

    public void testAddEdge()
    {
        DefaultWeightedEdge e1 = new DefaultWeightedEdge();
        DefaultWeightedEdge e2 = new DefaultWeightedEdge();

        Graph<String,
            DefaultWeightedEdge> g = new GraphBuilder<>(
                new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(
                    DefaultWeightedEdge.class))
                        .addEdge(v1, v2).addEdgeChain(v3, v4, v5, v6).addEdge(v7, v8, 10.0)
                        .addEdge(v1, v7, e1).addEdge(v1, v8, e2, 42.0).buildAsUnmodifiable();

        assertEquals(8, g.vertexSet().size());
        assertEquals(7, g.edgeSet().size());
        assertTrue(g.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8)));
        assertTrue(g.containsEdge(v1, v2));
        assertTrue(g.containsEdge(v3, v4));
        assertTrue(g.containsEdge(v4, v5));
        assertTrue(g.containsEdge(v5, v6));
        assertTrue(g.containsEdge(v7, v8));
        assertTrue(g.containsEdge(v1, v7));
        assertTrue(g.containsEdge(v1, v8));
        assertEquals(e1, g.getEdge(v1, v7));
        assertEquals(e2, g.getEdge(v1, v8));
        assertEquals(10.0, g.getEdgeWeight(g.getEdge(v7, v8)));
        assertEquals(42.0, g.getEdgeWeight(g.getEdge(v1, v8)));
    }

    @Deprecated
    public void testAddEdgeOldInterfaces()
    {
        DefaultWeightedEdge e1 = new DefaultWeightedEdge();
        DefaultWeightedEdge e2 = new DefaultWeightedEdge();

        UnmodifiableGraph<String,
            DefaultWeightedEdge> g = new DirectedWeightedGraphBuilder<>(
                new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(
                    DefaultWeightedEdge.class))
                        .addEdge(v1, v2).addEdgeChain(v3, v4, v5, v6).addEdge(v7, v8, 10.0)
                        .addEdge(v1, v7, e1).addEdge(v1, v8, e2, 42.0).buildUnmodifiable();

        assertEquals(8, g.vertexSet().size());
        assertEquals(7, g.edgeSet().size());
        assertTrue(g.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v4, v5, v6, v7, v8)));
        assertTrue(g.containsEdge(v1, v2));
        assertTrue(g.containsEdge(v3, v4));
        assertTrue(g.containsEdge(v4, v5));
        assertTrue(g.containsEdge(v5, v6));
        assertTrue(g.containsEdge(v7, v8));
        assertTrue(g.containsEdge(v1, v7));
        assertTrue(g.containsEdge(v1, v8));
        assertEquals(e1, g.getEdge(v1, v7));
        assertEquals(e2, g.getEdge(v1, v8));
        assertEquals(10.0, g.getEdgeWeight(g.getEdge(v7, v8)));
        assertEquals(42.0, g.getEdgeWeight(g.getEdge(v1, v8)));
    }

    public void testAddGraph()
    {
        Graph<String,
            DefaultEdge> g1 = DefaultDirectedGraph
                .<String, DefaultEdge> createBuilder(DefaultEdge.class).addVertex(v1)
                .addEdge(v2, v3).buildAsUnmodifiable();

        Graph<String, DefaultEdge> g2 =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addGraph(g1).addEdge(v1, v4).build();

        assertEquals(4, g2.vertexSet().size());
        assertEquals(2, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v3)));
        assertTrue(g2.containsEdge(v2, v3));
        assertTrue(g2.containsEdge(v1, v4));
    }

    @Deprecated
    public void testAddGraphOldInterfaces()
    {
        Graph<String,
            DefaultEdge> g1 = DefaultDirectedGraph
                .<String, DefaultEdge> builder(DefaultEdge.class).addVertex(v1).addEdge(v2, v3)
                .buildUnmodifiable();

        Graph<String,
            DefaultEdge> g2 = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addGraph(g1).addEdge(v1, v4).build();

        assertEquals(4, g2.vertexSet().size());
        assertEquals(2, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v3)));
        assertTrue(g2.containsEdge(v2, v3));
        assertTrue(g2.containsEdge(v1, v4));
    }

    public void testRemoveVertex()
    {
        Graph<String, DefaultEdge> g1 =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addEdge(v1, v3).addEdgeChain(v2, v3, v4, v5).buildAsUnmodifiable();

        Graph<String, DefaultEdge> g2 =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addGraph(g1).removeVertex(v2).removeVertices(v4, v5).build();

        assertEquals(2, g2.vertexSet().size());
        assertEquals(1, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v3)));
        assertTrue(g2.containsEdge(v1, v3));
    }

    @Deprecated
    public void testRemoveVertexOldInterfaces()
    {
        Graph<String,
            DefaultEdge> g1 = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addEdge(v1, v3).addEdgeChain(v2, v3, v4, v5).buildUnmodifiable();

        Graph<String,
            DefaultEdge> g2 = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addGraph(g1).removeVertex(v2).removeVertices(v4, v5).build();

        assertEquals(2, g2.vertexSet().size());
        assertEquals(1, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v3)));
        assertTrue(g2.containsEdge(v1, v3));
    }

    public void testRemoveEdge()
    {
        DefaultEdge e = new DefaultEdge();

        Graph<String, DefaultEdge> g1 =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addEdgeChain(v1, v2, v3, v4).addEdge(v1, v4, e).buildAsUnmodifiable();

        Graph<String, DefaultEdge> g2 =
            new GraphBuilder<>(new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                .addGraph(g1).removeEdge(v2, v3).removeEdge(e).build();

        assertEquals(4, g2.vertexSet().size());
        assertEquals(2, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v4)));
        assertTrue(g2.containsEdge(v1, v2));
        assertTrue(g2.containsEdge(v3, v4));
    }

    @Deprecated
    public void testRemoveEdgeOldInterfaces()
    {
        DefaultEdge e = new DefaultEdge();

        Graph<String,
            DefaultEdge> g1 = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addEdgeChain(v1, v2, v3, v4).addEdge(v1, v4, e).buildUnmodifiable();

        Graph<String,
            DefaultEdge> g2 = new DirectedGraphBuilder<>(
                new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class))
                    .addGraph(g1).removeEdge(v2, v3).removeEdge(e).build();

        assertEquals(4, g2.vertexSet().size());
        assertEquals(2, g2.edgeSet().size());
        assertTrue(g2.vertexSet().containsAll(Arrays.asList(v1, v2, v3, v4)));
        assertTrue(g2.containsEdge(v1, v2));
        assertTrue(g2.containsEdge(v3, v4));
    }

    public void testAddVertexPseudograph()
    {
        Pseudograph<String, DefaultEdge> g = Pseudograph
            .<String, DefaultEdge> createBuilder(DefaultEdge.class).addVertex(v1).build();
        assertEquals(1, g.vertexSet().size());
        assertEquals(0, g.edgeSet().size());
        assertTrue(g.vertexSet().containsAll(Arrays.asList(v1)));
    }

}

// End GraphBuilderTest.java
