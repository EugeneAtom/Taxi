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
 * A directed weighted pseudograph. A directed weighted pseudograph is a non-simple directed graph
 * in which both graph loops and multiple edges are permitted, and edges have weights.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 */
public class DirectedWeightedPseudograph<V, E>
    extends DirectedPseudograph<V, E>
    implements DirectedGraph<V, E>, WeightedGraph<V, E>
{
    private static final long serialVersionUID = -4775269773843490859L;

    /**
     * Creates a new directed weighted pseudograph.
     *
     * @param edgeClass class on which to base factory for edges
     */
    public DirectedWeightedPseudograph(Class<? extends E> edgeClass)
    {
        this(new ClassBasedEdgeFactory<>(edgeClass));
    }

    /**
     * Creates a new directed weighted pseudograph with the specified edge factory.
     *
     * @param ef the edge factory of the new graph.
     */
    public DirectedWeightedPseudograph(EdgeFactory<V, E> ef)
    {
        super(ef, true);
    }

    /**
     * Create a builder for this kind of graph.
     * 
     * @param edgeClass class on which to base factory for edges
     * @param <V> the graph vertex type
     * @param <E> the graph edge type
     * @return a builder for this kind of graph
     */
    public static <V,
        E> GraphBuilder<V, E, ? extends DirectedWeightedPseudograph<V, E>> createBuilder(
            Class<? extends E> edgeClass)
    {
        return new GraphBuilder<>(new DirectedWeightedPseudograph<>(edgeClass));
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
        E> GraphBuilder<V, E, ? extends DirectedWeightedPseudograph<V, E>> createBuilder(
            EdgeFactory<V, E> ef)
    {
        return new GraphBuilder<>(new DirectedWeightedPseudograph<>(ef));
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
    public static <V, E> DirectedWeightedGraphBuilderBase<V, E,
        ? extends DirectedWeightedPseudograph<V, E>, ?> builder(Class<? extends E> edgeClass)
    {
        return new DirectedWeightedGraphBuilder<>(new DirectedWeightedPseudograph<>(edgeClass));
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
    public static <V, E> DirectedWeightedGraphBuilderBase<V, E,
        ? extends DirectedWeightedPseudograph<V, E>, ?> builder(EdgeFactory<V, E> ef)
    {
        return new DirectedWeightedGraphBuilder<>(new DirectedWeightedPseudograph<>(ef));
    }
}

// End DirectedWeightedPseudograph.java
