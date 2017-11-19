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
 * A directed graph. A default directed graph is a non-simple directed graph in which multiple edges
 * between any two vertices are <i>not</i> permitted, but loops are.
 *
 * <p>
 * prefixed 'Default' to avoid name collision with the DirectedGraph interface.
 * </p>
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 */
public class DefaultDirectedGraph<V, E>
    extends AbstractBaseGraph<V, E>
    implements DirectedGraph<V, E>
{
    private static final long serialVersionUID = -2066644490824847621L;

    /**
     * Creates a new directed graph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public DefaultDirectedGraph(Class<? extends E> edgeClass)
    {
        this(new ClassBasedEdgeFactory<>(edgeClass));
    }

    /**
     * Creates a new directed graph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public DefaultDirectedGraph(EdgeFactory<V, E> ef)
    {
        this(ef, false);
    }

    /**
     * Creates a new directed graph with the specified edge factory.
     *
     * @param weighted if true the graph supports edge weights
     * @param ef the edge factory of the new graph.
     */
    public DefaultDirectedGraph(EdgeFactory<V, E> ef, boolean weighted)
    {
        super(ef, true, false, true, weighted);
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends DefaultDirectedGraph<V, E>> createBuilder(
        Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new DefaultDirectedGraph<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param ef the edge factory of the new graph
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends DefaultDirectedGraph<V, E>> createBuilder(
        EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new DefaultDirectedGraph<>(ef));
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
        E> DirectedGraphBuilderBase<V, E, ? extends DefaultDirectedGraph<V, E>, ?> builder(
            Class<? extends E> edgeClass)
    {
        return new DirectedGraphBuilder<>(new DefaultDirectedGraph<>(edgeClass));
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
        E> DirectedGraphBuilderBase<V, E, ? extends DefaultDirectedGraph<V, E>, ?> builder(
            EdgeFactory<V, E> ef)
    {
        return new DirectedGraphBuilder<>(new DefaultDirectedGraph<>(ef));
    }
}

// End DefaultDirectedGraph.java
