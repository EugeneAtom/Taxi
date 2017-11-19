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
 * A directed graph which is also {@link org.jgrapht.ListenableGraph}.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @see DefaultListenableGraph
 * @deprecated In favor of {@link DefaultListenableGraph}.
 */
@Deprecated
public class ListenableDirectedGraph<V, E>
    extends DefaultListenableGraph<V, E>
    implements DirectedGraph<V, E>
{
    private static final long serialVersionUID = 3257571698126368824L;

    /**
     * Creates a new listenable directed graph.
     *
     * @param edgeClass class on which to base factory for edges
     * @deprecated Use {@link ListenableDirectedGraph#ListenableDirectedGraph(DirectedGraph)}
     *             instead.
     */
    @Deprecated
    public ListenableDirectedGraph(Class<? extends E> edgeClass)
    {
        this(new DefaultDirectedGraph<>(edgeClass));
    }

    /**
     * Creates a new listenable directed graph.
     *
     * @param base the backing graph.
     */
    public ListenableDirectedGraph(DirectedGraph<V, E> base)
    {
        super(base);
    }
}

// End ListenableDirectedGraph.java
