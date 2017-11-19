/*
 * (C) Copyright 2017-2017, by Dimitrios Michail and Contributors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jgrapht.*;
import org.jgrapht.alg.util.*;
import org.jgrapht.graph.*;
import org.junit.*;

/**
 * Tests for {@link LinearizedChordDiagramGraphGenerator}.
 * 
 * @author Dimitrios Michail
 */
public class LinearizedChordDiagramGraphGeneratorTest
{
    @Test
    public void testBadParameters()
    {
        try {
            new LinearizedChordDiagramGraphGenerator<>(0, 10);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new LinearizedChordDiagramGraphGenerator<>(-1, 10);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new LinearizedChordDiagramGraphGenerator<>(5, 0);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new LinearizedChordDiagramGraphGenerator<>(5, -1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiGraph()
    {
        final long seed = 5;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(10, 2, seed);
        Graph<Integer, DefaultEdge> g = new Multigraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimpleGraph()
    {
        final long seed = 5;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(10, 2, seed);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectedMultiGraph()
    {
        final long seed = 5;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(10, 2, seed);
        Graph<Integer, DefaultEdge> g = new DirectedMultigraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDirectedSimpleGraph()
    {
        final long seed = 5;
        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(10, 2, seed);
        Graph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);
    }

    @Test
    public void testUndirected()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(20, 1, seed);
        Graph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(20, g.vertexSet().size());
    }

    @Test
    public void testUndirectedTwoEdges()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(20, 2, seed);
        Graph<Integer, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(20, g.vertexSet().size());
    }

    @Test
    public void testDirected()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new LinearizedChordDiagramGraphGenerator<>(20, 1, seed);
        Graph<Integer, DefaultEdge> g = new DirectedPseudograph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(20, g.vertexSet().size());
    }

}
