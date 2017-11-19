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
 * A simple graph. A simple graph is an undirected graph for which at most one edge connects any two
 * vertices, and loops are not permitted. If you're unsure about simple graphs, see:
 * <a href="http://mathworld.wolfram.com/SimpleGraph.html">
 * http://mathworld.wolfram.com/SimpleGraph.html</a>.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 */
public class SimpleGraph<V, E>
    extends AbstractBaseGraph<V, E>
    implements UndirectedGraph<V, E>
{
    private static final long serialVersionUID = 4607246833824317836L;

    /**
     * Creates a new simple graph with the specified edge factory.
     *
     * @param weighted if true the graph supports edge weights
     * @param ef the edge factory of the new graph.
     */
    public SimpleGraph(EdgeFactory<V, E> ef, boolean weighted)
    {
        super(ef, false, false, false, weighted);
    }

    /**
     * Creates a new simple graph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public SimpleGraph(EdgeFactory<V, E> ef)
    {
        this(ef, false);
    }

    /**
     * Creates a new simple graph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public SimpleGraph(Class<? extends E> edgeClass)
    {
        this(new ClassBasedEdgeFactory<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends SimpleGraph<V, E>> createBuilder(
        Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new SimpleGraph<>(edgeClass));
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
        E> GraphBuilder<V, E, ? extends SimpleGraph<V, E>> createBuilder(EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new SimpleGraph<>(ef));
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
    public static <V, E> UndirectedGraphBuilderBase<V, E, ? extends SimpleGraph<V, E>, ?> builder(
        Class<? extends E> edgeClass)
    {
        return new UndirectedGraphBuilder<>(new SimpleGraph<>(edgeClass));
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
    public static <V, E> UndirectedGraphBuilderBase<V, E, ? extends SimpleGraph<V, E>, ?> builder(
        EdgeFactory<V, E> ef)
    {
        return new UndirectedGraphBuilder<>(new SimpleGraph<>(ef));
    }
}

// End SimpleGraph.java
