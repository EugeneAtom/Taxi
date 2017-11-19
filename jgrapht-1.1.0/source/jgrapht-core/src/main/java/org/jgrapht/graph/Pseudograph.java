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
import org.jgrapht.graph.builder.*;

/**
 * A pseudograph. A pseudograph is a non-simple undirected graph in which both graph loops and
 * multiple edges are permitted. If you're unsure about pseudographs, see:
 * <a href="http://mathworld.wolfram.com/Pseudograph.html">
 * http://mathworld.wolfram.com/Pseudograph.html</a>.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class Pseudograph<V, E>
    extends AbstractBaseGraph<V, E>
    implements UndirectedGraph<V, E>
{
    private static final long serialVersionUID = -7574564204896552581L;

    /**
     * Creates a new pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public Pseudograph(Class<? extends E> edgeClass)
    {
        this(new ClassBasedEdgeFactory<>(edgeClass));
    }

    /**
     * Creates a new pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public Pseudograph(EdgeFactory<V, E> ef)
    {
        this(ef, false);
    }

    /**
     * Creates a new pseudograph with the specified edge factory.
     *
     * @param weighted if true the graph supports edge weights
     * @param ef the edge factory of the new graph.
     */
    public Pseudograph(EdgeFactory<V, E> ef, boolean weighted)
    {
        super(ef, false, true, true, weighted);
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends Pseudograph<V, E>> createBuilder(
        Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new Pseudograph<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param ef the edge factory of the new graph
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V,
        E> GraphBuilder<V, E, ? extends Pseudograph<V, E>> createBuilder(EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new Pseudograph<>(ef));
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
    public static <V, E> UndirectedGraphBuilderBase<V, E, ? extends Pseudograph<V, E>, ?> builder(
        Class<? extends E> edgeClass)
    {
        return new UndirectedGraphBuilder<>(new Pseudograph<>(edgeClass));
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
    public static <V, E> UndirectedGraphBuilderBase<V, E, ? extends Pseudograph<V, E>, ?> builder(
        EdgeFactory<V, E> ef)
    {
        return new UndirectedGraphBuilder<>(new Pseudograph<>(ef));
    }
}

// End Pseudograph.java
