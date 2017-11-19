/*
 * (C) Copyright 2016-2017, by Assaf Mizrachi and Contributors.
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

package org.jgrapht.traverse;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;

/**
 * Tests for the {@link RandomWalkIterator} class.
 * 
 * @author Assaf Mizrachi
 *
 */
public class RandomWalkIteratorTest
    extends EnhancedTestCase
{

    /**
     * Tests empty graph
     */
    public void testEmptyGraph()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        assertFalse(iter.hasNext());
    }

    /**
     * Tests single node graph
     */
    public void testSingleNode()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex("123");
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        assertTrue(iter.hasNext());
        assertEquals("123", iter.next());
        assertFalse(iter.hasNext());
    }

    /**
     * Tests iterator does not have more elements after reaching sink vertex.
     */
    public void testSink()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        int graphSize = 10;
        LinearGraphGenerator<String, DefaultEdge> graphGenerator =
            new LinearGraphGenerator<>(graphSize);
        graphGenerator.generateGraph(graph, new VertexFactory<String>()
        {
            private int index = 1;

            @Override
            public String createVertex()
            {
                return String.valueOf(index++);
            }
        }, null);
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        for (int i = 0; i < graphSize; i++) {
            assertTrue(iter.hasNext());
            assertNotNull(iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Tests iterator is exhausted after maxSteps
     */
    public void testExhausted()
    {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        RingGraphGenerator<String, DefaultEdge> graphGenerator = new RingGraphGenerator<>(10);
        graphGenerator.generateGraph(graph, new VertexFactory<String>()
        {
            private int index = 1;

            @Override
            public String createVertex()
            {
                return String.valueOf(index++);
            }
        }, null);

        int maxSteps = 4;
        Iterator<String> iter = new RandomWalkIterator<>(graph, "1", false, maxSteps);
        for (int i = 0; i < maxSteps; i++) {
            assertTrue(iter.hasNext());
            assertNotNull(iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Test deterministic walk using directed ring graph.
     */
    public void testDeterministic()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        int ringSize = 5;
        RingGraphGenerator<String, DefaultEdge> graphGenerator = new RingGraphGenerator<>(ringSize);
        graphGenerator.generateGraph(graph, new VertexFactory<String>()
        {
            private int index = 0;

            @Override
            public String createVertex()
            {
                return String.valueOf(index++);
            }
        }, null);
        Iterator<String> iter = new RandomWalkIterator<>(graph, "0", false, 20);
        int step = 0;
        while (iter.hasNext()) {
            step++;
            assertEquals(String.valueOf(step % ringSize), iter.next());
        }
    }

}
