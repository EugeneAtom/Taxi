/*
 * (C) Copyright 2007-2017, by France Telecom and Contributors.
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
package org.jgrapht.alg;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;

import junit.framework.*;

/**
 * @since July 5, 2007
 */
public class BlockCutpointGraphTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    public void testBiconnected()
    {
        BiconnectedGraph graph = new BiconnectedGraph();

        BlockCutpointGraph<String, DefaultEdge> blockCutpointGraph =
            new BlockCutpointGraph<>(graph);
        testGetBlock(blockCutpointGraph);

        assertEquals(0, blockCutpointGraph.getCutpoints().size());
        int nbBiconnectedComponents =
            blockCutpointGraph.vertexSet().size() - blockCutpointGraph.getCutpoints().size();
        assertEquals(1, nbBiconnectedComponents);
    }

    public <V> void testGetBlock(BlockCutpointGraph<V, DefaultEdge> blockCutpointGraph)
    {
        for (Graph<V, DefaultEdge> component : blockCutpointGraph.vertexSet()) {
            if (!component.edgeSet().isEmpty()) {
                for (V vertex : component.vertexSet()) {
                    if (!blockCutpointGraph.getCutpoints().contains(vertex)) {
                        assertEquals(component, blockCutpointGraph.getBlock(vertex));
                    }
                }
            } else {
                assertTrue(
                    blockCutpointGraph
                        .getCutpoints().contains(component.vertexSet().iterator().next()));
            }
        }
    }

    public void testLinearGraph()
    {
        testLinearGraph(3);
        testLinearGraph(5);
    }

    public void testLinearGraph(int nbVertices)
    {
        Graph<Object, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        LinearGraphGenerator<Object, DefaultEdge> generator =
            new LinearGraphGenerator<>(nbVertices);
        generator.generateGraph(graph, new ClassBasedVertexFactory<>(Object.class), null);

        BlockCutpointGraph<Object, DefaultEdge> blockCutpointGraph =
            new BlockCutpointGraph<>(graph);
        testGetBlock(blockCutpointGraph);

        assertEquals(nbVertices - 2, blockCutpointGraph.getCutpoints().size());
        int nbBiconnectedComponents =
            blockCutpointGraph.vertexSet().size() - blockCutpointGraph.getCutpoints().size();
        assertEquals(nbVertices - 1, nbBiconnectedComponents);
    }

    public void testNotBiconnected()
    {
        Graph<String, DefaultEdge> graph = new NotBiconnectedGraph();

        BlockCutpointGraph<String, DefaultEdge> blockCutpointGraph =
            new BlockCutpointGraph<>(graph);
        testGetBlock(blockCutpointGraph);

        assertEquals(2, blockCutpointGraph.getCutpoints().size());
        int nbBiconnectedComponents =
            blockCutpointGraph.vertexSet().size() - blockCutpointGraph.getCutpoints().size();
        assertEquals(3, nbBiconnectedComponents);
    }
}

// End BlockCutpointGraphTest.java
