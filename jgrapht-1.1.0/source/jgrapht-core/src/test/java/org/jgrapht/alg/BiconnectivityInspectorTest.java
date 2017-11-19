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
public class BiconnectivityInspectorTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    public void testBiconnected()
    {
        BiconnectedGraph graph = new BiconnectedGraph();

        BiconnectivityInspector<String, DefaultEdge> inspector =
            new BiconnectivityInspector<>(graph);

        assertTrue(inspector.isBiconnected());
        assertEquals(0, inspector.getCutpoints().size());
        assertEquals(1, inspector.getBiconnectedVertexComponents().size());
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

        BiconnectivityInspector<Object, DefaultEdge> inspector =
            new BiconnectivityInspector<>(graph);

        assertEquals(nbVertices - 2, inspector.getCutpoints().size());
        assertEquals(nbVertices - 1, inspector.getBiconnectedVertexComponents().size());
    }

    public void testNotBiconnected()
    {
        NotBiconnectedGraph graph = new NotBiconnectedGraph();

        BiconnectivityInspector<String, DefaultEdge> inspector =
            new BiconnectivityInspector<>(graph);

        assertEquals(2, inspector.getCutpoints().size());
        assertEquals(3, inspector.getBiconnectedVertexComponents().size());
    }
}

// End BiconnectivityInspectorTest.java
