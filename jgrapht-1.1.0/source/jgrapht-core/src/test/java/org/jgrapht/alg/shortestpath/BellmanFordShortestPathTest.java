/*
 * (C) Copyright 2006-2017, by John V Sichi and Contributors.
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
package org.jgrapht.alg.shortestpath;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.*;
import org.jgrapht.graph.*;

/**
 * .
 *
 * @author John V. Sichi
 */
public class BellmanFordShortestPathTest
    extends ShortestPathTestCase
{
    // ~ Methods ----------------------------------------------------------------

    public void testUndirected()
    {
        SingleSourcePaths<String, DefaultWeightedEdge> tree;
        Graph<String, DefaultWeightedEdge> g = create();

        tree = new BellmanFordShortestPath<>(g).getPaths(V3);

        // find best path
        assertEquals(
            Arrays.asList(new DefaultWeightedEdge[] { e13, e12, e24, e45 }),
            tree.getPath(V5).getEdgeList());
        assertEquals(3.0, tree.getPath(V1).getWeight(), 1e-9);
        assertEquals(5.0, tree.getPath(V2).getWeight(), 1e-9);
        assertEquals(0.0, tree.getPath(V3).getWeight(), 1e-9);
        assertEquals(10.0, tree.getPath(V4).getWeight(), 1e-9);
        assertEquals(15.0, tree.getPath(V5).getWeight(), 1e-9);
    }

    @Override
    protected List<DefaultWeightedEdge> findPathBetween(
        Graph<String, DefaultWeightedEdge> g, String src, String dest)
    {
        return new BellmanFordShortestPath<>(g).getPaths(src).getPath(dest).getEdgeList();
    }

    public void testWithNegativeEdges()
    {
        Graph<String, DefaultWeightedEdge> g = createWithBias(true);

        List<DefaultWeightedEdge> path;

        path = findPathBetween(g, V1, V4);
        assertEquals(Arrays.asList(new DefaultWeightedEdge[] { e13, e34 }), path);

        path = findPathBetween(g, V1, V5);
        assertEquals(Arrays.asList(new DefaultWeightedEdge[] { e15 }), path);
    }

    public void testNoPath()
    {
        DirectedWeightedPseudograph<String, DefaultWeightedEdge> g =
            new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
        g.addVertex("a");
        g.addVertex("b");

        BellmanFordShortestPath<String, DefaultWeightedEdge> alg = new BellmanFordShortestPath<>(g);
        SingleSourcePaths<String, DefaultWeightedEdge> paths = alg.getPaths("a");
        assertEquals(paths.getWeight("b"), Double.POSITIVE_INFINITY);
        assertNull(paths.getPath("b"));
    }

    public void testWikipediaExampleBellmanFord()
    {
        DirectedWeightedPseudograph<String, DefaultWeightedEdge> g =
            new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
        g.addVertex("w");
        g.addVertex("y");
        g.addVertex("x");
        g.addVertex("z");
        g.addVertex("s");
        g.setEdgeWeight(g.addEdge("w", "z"), 2);
        g.setEdgeWeight(g.addEdge("y", "w"), 4);
        g.setEdgeWeight(g.addEdge("x", "w"), 6);
        g.setEdgeWeight(g.addEdge("x", "y"), 3);
        g.setEdgeWeight(g.addEdge("z", "x"), -7);
        g.setEdgeWeight(g.addEdge("y", "z"), 5);
        g.setEdgeWeight(g.addEdge("z", "y"), -3);
        g.setEdgeWeight(g.addEdge("s", "w"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "y"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "x"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "z"), 0.0);

        BellmanFordShortestPath<String, DefaultWeightedEdge> alg = new BellmanFordShortestPath<>(g);
        SingleSourcePaths<String, DefaultWeightedEdge> paths = alg.getPaths("s");
        assertEquals(0d, paths.getPath("s").getWeight(), 1e-9);
        assertEquals(-1d, paths.getPath("w").getWeight(), 1e-9);
        assertEquals(-4d, paths.getPath("y").getWeight(), 1e-9);
        assertEquals(-7d, paths.getPath("x").getWeight(), 1e-9);
        assertEquals(0d, paths.getPath("z").getWeight(), 1e-9);
    }

    public void testNegativeCycleDetection()
    {
        DirectedWeightedPseudograph<String, DefaultWeightedEdge> g =
            new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
        g.addVertex("w");
        g.addVertex("y");
        g.addVertex("x");
        g.addVertex("z");
        g.addVertex("s");
        g.setEdgeWeight(g.addEdge("w", "z"), 2);
        g.setEdgeWeight(g.addEdge("y", "w"), 4);
        g.setEdgeWeight(g.addEdge("x", "w"), 6);
        g.setEdgeWeight(g.addEdge("x", "y"), 3);
        g.setEdgeWeight(g.addEdge("z", "x"), -7);
        g.setEdgeWeight(g.addEdge("y", "z"), 3);
        g.setEdgeWeight(g.addEdge("z", "y"), -3);
        g.setEdgeWeight(g.addEdge("s", "w"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "y"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "x"), 0.0);
        g.setEdgeWeight(g.addEdge("s", "z"), 0.0);

        try {
            new BellmanFordShortestPath<>(g).getPaths("s");
            fail("Negative-weight cycle not detected");
        } catch (RuntimeException e) {
            assertEquals("Graph contains a negative-weight cycle", e.getMessage());
        }
    }

    public void testNegativeEdgeUndirectedGraph()
    {
        WeightedPseudograph<String, DefaultWeightedEdge> g =
            new WeightedPseudograph<>(DefaultWeightedEdge.class);
        g.addVertex("w");
        g.addVertex("y");
        g.addVertex("x");
        g.setEdgeWeight(g.addEdge("w", "y"), 1);
        g.setEdgeWeight(g.addEdge("y", "x"), 1);
        g.setEdgeWeight(g.addEdge("y", "x"), -1);
        try {
            new BellmanFordShortestPath<>(g).getPaths("w");
            fail("Negative-weight cycle not detected");
        } catch (RuntimeException e) {
            assertEquals("Graph contains a negative-weight cycle", e.getMessage());
        }
    }

}

// End BellmanFordShortestPathTest.java
