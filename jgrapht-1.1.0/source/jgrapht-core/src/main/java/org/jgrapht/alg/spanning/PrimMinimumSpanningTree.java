/*
 * (C) Copyright 2013-2017, by Alexey Kudinkin and Contributors.
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
package org.jgrapht.alg.spanning;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.*;

/**
 * An implementation of <a href="http://en.wikipedia.org/wiki/Prim's_algorithm"> Prim's
 * algorithm</a> that finds a minimum spanning tree/forest subject to connectivity of the supplied
 * weighted undirected graph. The algorithm was developed by Czech mathematician V. Jarník and later
 * independently by computer scientist Robert C. Prim and rediscovered by E. Dijkstra.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Alexey Kudinkin
 * @since Mar 5, 2013
 */
public class PrimMinimumSpanningTree<V, E>
    implements SpanningTreeAlgorithm<E>
{
    private final Graph<V, E> g;

    /**
     * Construct a new instance of the algorithm.
     * 
     * @param graph the input graph
     */
    public PrimMinimumSpanningTree(Graph<V, E> graph)
    {
        this.g = Objects.requireNonNull(graph, "Graph cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpanningTree<E> getSpanningTree()
    {
        Set<E> minimumSpanningTreeEdgeSet = new HashSet<>(g.vertexSet().size());
        double spanningTreeWeight = 0d;

        Set<V> unspanned = new HashSet<>(g.vertexSet());

        while (!unspanned.isEmpty()) {
            Iterator<V> ri = unspanned.iterator();

            V root = ri.next();

            ri.remove();

            // Edges crossing the cut C = (S, V \ S), where S is set of
            // already spanned vertices

            PriorityQueue<E> dangling = new PriorityQueue<>(
                g.edgeSet().size(),
                (lop, rop) -> Double.valueOf(g.getEdgeWeight(lop)).compareTo(g.getEdgeWeight(rop)));

            dangling.addAll(g.edgesOf(root));

            for (E next; (next = dangling.poll()) != null;) {
                V s, t = unspanned.contains(s = g.getEdgeSource(next)) ? s : g.getEdgeTarget(next);

                // Decayed edges aren't removed from priority-queue so that
                // having them just ignored being encountered through min-max
                // traversal
                if (!unspanned.contains(t)) {
                    continue;
                }

                minimumSpanningTreeEdgeSet.add(next);
                spanningTreeWeight += g.getEdgeWeight(next);

                unspanned.remove(t);

                for (E e : g.edgesOf(t)) {
                    if (unspanned.contains(
                        g.getEdgeSource(e).equals(t) ? g.getEdgeTarget(e) : g.getEdgeSource(e)))
                    {
                        dangling.add(e);
                    }
                }
            }
        }

        return new SpanningTreeImpl<>(minimumSpanningTreeEdgeSet, spanningTreeWeight);
    }
}

// End PrimMinimumSpanningTree.java
