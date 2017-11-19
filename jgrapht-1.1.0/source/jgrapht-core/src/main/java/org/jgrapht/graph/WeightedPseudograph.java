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
 * A weighted pseudograph. A weighted pseudograph is a non-simple undirected graph in which both
 * graph loops and multiple edges are permitted. The edges of a weighted pseudograph have weights.
 * If you're unsure about pseudographs, see:
 * <a href="http://mathworld.wolfram.com/Pseudograph.html">
 * http://mathworld.wolfram.com/Pseudograph.html</a>.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 */
public class WeightedPseudograph<V, E>
    extends Pseudograph<V, E>
    implements UndirectedGraph<V, E>, WeightedGraph<V, E>
{
    private static final long serialVersionUID = 3037964528481084240L;

    /**
     * Creates a new weighted pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public WeightedPseudograph(EdgeFactory<V, E> ef)
    {
        super(ef, true);
    }

    /**
     * Creates a new weighted pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public WeightedPseudograph(Class<? extends E> edgeClass)
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
    public static <V, E> GraphBuilder<V, E, ? extends WeightedPseudograph<V, E>> createBuilder(
        Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new WeightedPseudograph<>(edgeClass));
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param ef the edge factory of the new graph
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V, E> GraphBuilder<V, E, ? extends WeightedPseudograph<V, E>> createBuilder(
        EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new WeightedPseudograph<>(ef));
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
        E> UndirectedWeightedGraphBuilderBase<V, E, ? extends WeightedPseudograph<V, E>, ?> builder(
            Class<? extends E> edgeClass)
    {
        return new UndirectedWeightedGraphBuilder<>(new WeightedPseudograph<>(edgeClass));
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
        E> UndirectedWeightedGraphBuilderBase<V, E, ? extends WeightedPseudograph<V, E>, ?> builder(
            EdgeFactory<V, E> ef)
    {
        return new UndirectedWeightedGraphBuilder<>(new WeightedPseudograph<>(ef));
    }
}

// End WeightedPseudograph.java
