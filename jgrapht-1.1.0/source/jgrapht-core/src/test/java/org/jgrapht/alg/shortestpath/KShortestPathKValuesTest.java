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
package org.jgrapht.alg.shortestpath;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.util.*;

import junit.framework.*;

/**
 * @since July 5, 2007
 */
public class KShortestPathKValuesTest
    extends TestCase
{
    // ~ Methods ----------------------------------------------------------------

    /**
     * @param k
     * @param n
     *
     * @return A(n,k).
     */
    public static long permutation(int n, int k)
    {
        if (k <= n) {
            return MathUtil.factorial(n) / MathUtil.factorial(n - k);
        } else {
            return 0;
        }
    }

    public void testMaxSizeValueCompleteGraph6()
    {
        KShortestPathCompleteGraph6 graph = new KShortestPathCompleteGraph6();

        for (int maxSize = 1; maxSize <= calculateNbElementaryPathsForCompleteGraph(6); maxSize++) {
            KShortestPaths<String, DefaultWeightedEdge> finder =
                new KShortestPaths<>(graph, maxSize);

            assertEquals(finder.getPaths("vS", "v1").size(), maxSize);
            assertEquals(finder.getPaths("vS", "v2").size(), maxSize);
            assertEquals(finder.getPaths("vS", "v3").size(), maxSize);
            assertEquals(finder.getPaths("vS", "v4").size(), maxSize);
            assertEquals(finder.getPaths("vS", "v5").size(), maxSize);
        }
    }

    public void testNbReturnedPaths()
    {
        KShortestPathCompleteGraph4 kSPCompleteGraph4 = new KShortestPathCompleteGraph4();
        verifyNbPathsForAllPairsOfVertices(kSPCompleteGraph4);

        KShortestPathCompleteGraph5 kSPCompleteGraph5 = new KShortestPathCompleteGraph5();
        verifyNbPathsForAllPairsOfVertices(kSPCompleteGraph5);

        KShortestPathCompleteGraph6 kSPCompleteGraph6 = new KShortestPathCompleteGraph6();
        verifyNbPathsForAllPairsOfVertices(kSPCompleteGraph6);
    }

    /**
     * Compute the total number of paths between every pair of vertices in a complete graph with
     * <code>n</code> vertices.
     *
     * @param n
     *
     * @return
     */
    private long calculateNbElementaryPathsForCompleteGraph(int n)
    {
        long nbPaths = 0;
        for (int k = 1; k <= (n - 1); k++) {
            nbPaths = nbPaths + permutation(n - 2, k - 1);
        }
        return nbPaths;
    }

    private void verifyNbPathsForAllPairsOfVertices(Graph<String, DefaultWeightedEdge> graph)
    {
        long nbPaths = calculateNbElementaryPathsForCompleteGraph(graph.vertexSet().size());
        int maxSize = Integer.MAX_VALUE;

        for (String sourceVertex : graph.vertexSet()) {
            KShortestPaths<String, DefaultWeightedEdge> finder =
                new KShortestPaths<>(graph, maxSize);
            for (String targetVertex : graph.vertexSet()) {
                if (targetVertex != sourceVertex) {
                    assertEquals(finder.getPaths(sourceVertex, targetVertex).size(), nbPaths);
                }
            }
        }
    }
}

// End KShortestPathKValuesTest.java
