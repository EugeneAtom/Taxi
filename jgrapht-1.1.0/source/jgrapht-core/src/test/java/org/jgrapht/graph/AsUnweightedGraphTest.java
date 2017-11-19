/*
 * (C) Copyright 2007-2017, by John V Sichi and Contributors.
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

import org.jgrapht.*;

/**
 * A unit test for the AsUnweighted[Directed]Graph views.
 *
 * @author John V. Sichi
 */
public class AsUnweightedGraphTest
    extends EnhancedTestCase
{
    // ~ Static fields/initializers ---------------------------------------------

    private static final String v1 = "v1";
    private static final String v2 = "v2";
    private static final String v3 = "v3";

    // ~ Constructors -----------------------------------------------------------

    /**
     * @see junit.framework.TestCase#TestCase(java.lang.String)
     */
    public AsUnweightedGraphTest(String name)
    {
        super(name);
    }

    // ~ Methods ----------------------------------------------------------------

    /**
     * .
     */
    public void testDirected()
    {
        DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> directed =
            new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        constructWeighted(directed);

        Graph<String, DefaultWeightedEdge> unweighted = new AsUnweightedGraph<>(directed);
        checkView(directed, unweighted);
    }

    /**
     * .
     */
    public void testWeightedGraph()
    {
        Graph<String, DefaultWeightedEdge> undirected =
            new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        constructWeighted(undirected);

        AsUnweightedGraph<String, DefaultWeightedEdge> unweighted =
            new AsUnweightedGraph<>(undirected);
        checkView(undirected, unweighted);
    }

    private void constructWeighted(Graph<String, DefaultWeightedEdge> weighted)
    {
        weighted.addVertex(v1);
        weighted.addVertex(v2);
        weighted.addVertex(v3);
        Graphs.addEdge(weighted, v1, v2, 3.0);
        assertEquals(3.0, weighted.getEdgeWeight(weighted.getEdge(v1, v2)));
    }

    private void checkView(
        Graph<String, DefaultWeightedEdge> weighted, Graph<String, DefaultWeightedEdge> unweighted)
    {
        assertEquals(
            Graph.DEFAULT_EDGE_WEIGHT, unweighted.getEdgeWeight(unweighted.getEdge(v1, v2)));

        Graphs.addEdge(weighted, v2, v3, 5.0);
        assertEquals(
            Graph.DEFAULT_EDGE_WEIGHT, unweighted.getEdgeWeight(unweighted.getEdge(v2, v3)));

        unweighted.addEdge(v3, v1);
        assertEquals(
            Graph.DEFAULT_EDGE_WEIGHT, unweighted.getEdgeWeight(unweighted.getEdge(v3, v1)));
        assertEquals(Graph.DEFAULT_EDGE_WEIGHT, weighted.getEdgeWeight(weighted.getEdge(v3, v1)));
    }
}

// End AsUnweightedGraphTest.java
