/*
 * (C) Copyright 2003-2017, by Barak Naveh and Contributors.
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

import org.jgrapht.generate.*;
import org.jgrapht.graph.*;

import junit.framework.*;

/**
 * .
 *
 * @author Andrew Newell
 * @deprecated Since {@link HamiltonianCycle} is deprecated.
 */
@Deprecated
public class HamiltonianCycleTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    // ~ Methods
    // ----------------------------------------------------------------

    /**
     * .
     */
    public void testHamiltonianCycle()
    {
        SimpleWeightedGraph<Object, DefaultWeightedEdge> completeGraph =
            new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        CompleteGraphGenerator<Object, DefaultWeightedEdge> completeGraphGenerator =
            new CompleteGraphGenerator<>(6);
        completeGraphGenerator
            .generateGraph(completeGraph, new ClassBasedVertexFactory<>(Object.class), null);

        assertTrue(
            HamiltonianCycle.getApproximateOptimalForCompleteGraph(completeGraph).size() == 6);

        List<Object> vertices = new LinkedList<>(completeGraph.vertexSet());
        completeGraph.removeEdge(completeGraph.getEdge(vertices.get(0), vertices.get(1)));

        assertTrue(HamiltonianCycle.getApproximateOptimalForCompleteGraph(completeGraph) == null);
    }
}

// End HamiltonianCycleTest.java
