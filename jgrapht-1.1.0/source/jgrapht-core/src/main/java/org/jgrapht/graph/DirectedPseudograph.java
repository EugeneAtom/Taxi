/*
 * (C) Copyright 2004-2017, by Christian Hammer and Contributors.
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
import org.jgrapht.graph.builder.*;

/**
 * A directed pseudograph. A directed pseudograph is a non-simple directed graph in which both graph
 * loops and multiple edges are permitted. If you're unsure about pseudographs, see:
 * <a href="http://mathworld.wolfram.com/Pseudograph.html">
 * http://mathworld.wolfram.com/Pseudograph.html</a>.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 */
public class DirectedPseudograph<V, E>
    extends AbstractBaseGraph<V, E>
    implements DirectedGraph<V, E>
{
    private static final long serialVersionUID = -7461248851245878913L;

    /**
     * Creates a new directed pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public DirectedPseudograph(Class<? extends E> edgeClass)
    {
        this(new ClassBasedEdgeFactory<>(edgeClass));
    }

    /**
     * Creates a new directed pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public DirectedPseudograph(EdgeFactory<V, E> ef)
    {
        this(ef, false);
    }

    /**
     * Creates a new directed pseudograph with the specified edge factory.
     *
     * @param weighted if true the graph supports edge weights
     * @param ef the edge factory of the new graph.
     */
    public DirectedPseudograph(EdgeFactory<V, E> ef, boolean weighted)
    {
        super(ef, true, true, true, weighted);
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends DirectedPseudograph<V, E>> createBuilder(
        Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new DirectedPseudograph<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param ef the edge factory of the new graph
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends DirectedPseudograph<V, E>> createBuilder(
        EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new DirectedPseudograph<>(ef));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     * @deprecated In favor of {@link #createBuilder(Class)}.
     */
    @Deprecated
    public static <V,
        E> DirectedGraphBuilderBase<V, E, ? extends DirectedPseudograph<V, E>, ?> builder(
            Class<? extends E> edgeClass)
    {
        return new DirectedGraphBuilder<>(new DirectedPseudograph<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param ef the edge factory of the new graph
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     * @deprecated In favor of {@link #createBuilder(EdgeFactory)}.
     */
    @Deprecated
    public static <V,
        E> DirectedGraphBuilderBase<V, E, ? extends DirectedPseudograph<V, E>, ?> builder(
            EdgeFactory<V, E> ef)
    {
        return new DirectedGraphBuilder<>(new DirectedPseudograph<>(ef));
    }
}

// End DirectedPseudograph.java
