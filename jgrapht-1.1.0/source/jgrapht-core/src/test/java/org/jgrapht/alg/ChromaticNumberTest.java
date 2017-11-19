/*
 * (C) Copyright 2008-2017, by Andrew Newell and Contributors.
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

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;

import junit.framework.*;

/**
 * .
 *
 * @author Andrew Newell
 */
@Deprecated
public class ChromaticNumberTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testChromaticNumber()
    {
        UndirectedGraph<Object, DefaultEdge> completeGraph = new SimpleGraph<>(DefaultEdge.class);
        CompleteGraphGenerator<Object, DefaultEdge> completeGenerator =
            new CompleteGraphGenerator<>(7);
        completeGenerator
            .generateGraph(completeGraph, new ClassBasedVertexFactory<>(Object.class), null);

        // A complete graph has a chromatic number equal to its order
        assertEquals(7, ChromaticNumber.findGreedyChromaticNumber(completeGraph));
        Map<Integer, Set<Object>> coloring = ChromaticNumber.findGreedyColoredGroups(completeGraph);
        assertEquals(7, coloring.keySet().size());

        UndirectedGraph<Object, DefaultEdge> linearGraph = new SimpleGraph<>(DefaultEdge.class);
        LinearGraphGenerator<Object, DefaultEdge> linearGenerator = new LinearGraphGenerator<>(50);
        linearGenerator
            .generateGraph(linearGraph, new ClassBasedVertexFactory<>(Object.class), null);

        // A linear graph is a tree, and a greedy algorithm for chromatic number
        // can always find a 2-coloring
        assertEquals(2, ChromaticNumber.findGreedyChromaticNumber(linearGraph));
    }
}

// End ChromaticNumberTest.java
