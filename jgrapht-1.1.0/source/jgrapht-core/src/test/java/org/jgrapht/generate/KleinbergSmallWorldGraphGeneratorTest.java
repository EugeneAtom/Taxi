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
 * @author Dimitrios Michail
 */
public class KleinbergSmallWorldGraphGeneratorTest
{
    @Test
    public void testBadParameters()
    {
        try {
            new KleinbergSmallWorldGraphGenerator<>(-1, 1, 1, 1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new KleinbergSmallWorldGraphGenerator<>(5, 0, 1, 1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new KleinbergSmallWorldGraphGenerator<>(5, 9, 1, 1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new KleinbergSmallWorldGraphGenerator<>(5, 1, -1, 1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }

        try {
            new KleinbergSmallWorldGraphGenerator<>(5, 1, 1, -1);
            fail("Bad parameter");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testUndirected()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new KleinbergSmallWorldGraphGenerator<>(5, 2, 3, 2, seed);
        Graph<Integer, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(25, g.vertexSet().size());
    }

    @Test
    public void testDirected()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new KleinbergSmallWorldGraphGenerator<>(5, 2, 3, 2, seed);
        Graph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(25, g.vertexSet().size());
    }

    @Test
    public void testDirectedWithUniform()
    {
        final long seed = 5;

        GraphGenerator<Integer, DefaultEdge, Integer> gen =
            new KleinbergSmallWorldGraphGenerator<>(5, 2, 3, 0, seed);
        Graph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);
        gen.generateGraph(g, new IntegerVertexFactory(), null);

        assertEquals(25, g.vertexSet().size());
    }

}
