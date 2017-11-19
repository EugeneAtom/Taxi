/*
 * (C) Copyright 2010-2017, by Michael Behrisch and Contributors.
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
package org.jgrapht.experimental;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;

import junit.framework.*;

/**
 * .
 *
 * @author Michael Behrisch
 */
public class ColoringTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testBacktrackColoring()
    {
        Graph<Object, DefaultEdge> completeGraph = new SimpleGraph<>(DefaultEdge.class);
        CompleteGraphGenerator<Object, DefaultEdge> completeGraphGenerator =
            new CompleteGraphGenerator<>(6);
        completeGraphGenerator
            .generateGraph(completeGraph, new ClassBasedVertexFactory<>(Object.class), null);
        BrownBacktrackColoring<Object, DefaultEdge> colorer =
            new BrownBacktrackColoring<>(completeGraph);
        assertEquals(new Integer(6), colorer.getResult(null));
    }
}

// End ColoringTest.java
