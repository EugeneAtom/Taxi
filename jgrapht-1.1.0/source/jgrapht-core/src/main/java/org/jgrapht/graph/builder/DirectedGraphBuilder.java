/*
 * (C) Copyright 2015-2017, by Andrew Chen and Contributors.
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
package org.jgrapht.graph.builder;

import org.jgrapht.*;

/**
 * A builder class for {@link Graph}. If you want to extend this class, see
 * {@link DirectedGraphBuilderBase}.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * @param <G> type of the resulting graph
 * @deprecated In favor of {@link GraphBuilder}.
 */
@Deprecated
public final class DirectedGraphBuilder<V, E, G extends DirectedGraph<V, E>>
    extends DirectedGraphBuilderBase<V, E, G, DirectedGraphBuilder<V, E, G>>
{
    /**
     * Creates a builder based on {@code baseGraph}. {@code baseGraph} must be mutable.
     *
     * <p>
     * The recommended way to use this constructor is: {@code new
     * DirectedGraphBuilder<...>(new YourGraph<...>(...))}.
     *
     * <p>
     * NOTE: {@code baseGraph} should not be an existing graph. If you want to add an existing graph
     * to the graph being built, you should use the {@link #addVertex(Object)} method.
     *
     * @param baseGraph the graph object to base building on
     */
    public DirectedGraphBuilder(G baseGraph)
    {
        super(baseGraph);
    }

    @Override
    protected DirectedGraphBuilder<V, E, G> self()
    {
        return this;
    }
}

// End DirectedGraphBuilder.java
