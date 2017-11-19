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
package org.jgrapht.graph;

import org.jgrapht.*;

/**
 * A directed weighted graph which is also {@link org.jgrapht.ListenableGraph}.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @see DefaultListenableGraph
 * @deprecated In favor of {@link DefaultListenableGraph}.
 */
@Deprecated
public class ListenableDirectedWeightedGraph<V, E>
    extends ListenableDirectedGraph<V, E>
    implements DirectedGraph<V, E>, WeightedGraph<V, E>
{
    private static final long serialVersionUID = 3977582476627621938L;

    /**
     * Creates a new listenable directed weighted graph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public ListenableDirectedWeightedGraph(Class<? extends E> edgeClass)
    {
        this(new DefaultDirectedWeightedGraph<>(edgeClass));
    }

    /**
     * Creates a new listenable directed weighted graph.
     *
     * @param base the backing graph.
     */
    public ListenableDirectedWeightedGraph(DirectedGraph<V, E> base)
    {
        super(base);
    }
}

// End ListenableDirectedWeightedGraph.java
