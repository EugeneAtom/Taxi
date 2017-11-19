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
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.util.*;
import org.jgrapht.graph.*;

/**
 * Allows the <a href="http://mathworld.wolfram.com/ChromaticNumber.html"> chromatic number</a> of a
 * graph to be calculated. This is the minimal number of colors needed to color each vertex such
 * that no two adjacent vertices share the same color. This algorithm will not find the true
 * chromatic number, since this is an NP-complete problem. So, a greedy algorithm will find an
 * approximate chromatic number.
 *
 * @author Andrew Newell
 * @since Dec 21, 2008
 * @deprecated Use the classes implementing {@link VertexColoringAlgorithm} instead.
 */
@Deprecated
public abstract class ChromaticNumber
{
    /**
     * Finds the number of colors required for a greedy coloring of the graph.
     *
     * @param g an undirected graph to find the chromatic number of
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     *
     * @return integer the approximate chromatic number from the greedy algorithm
     */
    public static <V, E> int findGreedyChromaticNumber(UndirectedGraph<V, E> g)
    {
        Map<Integer, Set<V>> coloredGroups = findGreedyColoredGroups(g);
        return coloredGroups.keySet().size();
    }

    /**
     * Finds a greedy coloring of the graph.
     * 
     * @param g an undirected graph for which to find the coloring
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * 
     * @return a greedy coloring
     */
    public static <V, E> Map<Integer, Set<V>> findGreedyColoredGroups(UndirectedGraph<V, E> g)
    {
        // A copy of the graph is made, so that elements of the graph may be
        // removed to carry out the algorithm
        UndirectedGraph<V, E> sg = new UndirectedSubgraph<>(g, null, null);

        // The Vertices will be sorted in decreasing order by degree, so that
        // higher degree vertices have priority to be colored first
        VertexDegreeComparator<V, E> comp = new VertexDegreeComparator<>(sg);
        List<V> sortedVertices = new LinkedList<>(sg.vertexSet());
        Collections.sort(sortedVertices, comp);
        Collections.reverse(sortedVertices);

        int color;

        // create a map which will hold color as key and Set<V> as value
        Map<Integer, Set<V>> coloredGroups = new HashMap<>();

        // We'll attempt to color each vertex with a single color each
        // iteration, and these vertices will be removed from the graph at the
        // end of each iteration
        for (color = 0; sg.vertexSet().size() > 0; color++) {
            // This set will contain vertices that are colored with the
            // current color of this iteration
            Set<V> currentColor = new HashSet<>();
            for (Iterator<V> iter = sortedVertices.iterator(); iter.hasNext();) {
                V v = iter.next();

                // Add new vertices to be colored as long as they are not
                // adjacent with any other vertex that has already been colored
                // with the current color
                boolean flag = true;
                for (V temp : currentColor) {
                    if (sg.containsEdge(temp, v)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    currentColor.add(v);
                    iter.remove();
                }
            }

            // Add all these vertices as a group for this color
            coloredGroups.put(color, currentColor);

            // Remove vertices from the graph and then repeat the process for
            // the next iteration
            sg.removeAllVertices(currentColor);
        }
        return coloredGroups;
    }
}

// End ChromaticNumber.java
