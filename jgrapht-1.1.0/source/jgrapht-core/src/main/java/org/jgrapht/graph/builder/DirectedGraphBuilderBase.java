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
import org.jgrapht.graph.*;

/**
 * Base class for {@link DirectedGraphBuilder} for extending.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * @param <G> type of the resulting graph
 * @param <B> type of this builder
 * @deprecated In favor of {@link GraphBuilder}.
 */
@Deprecated
public abstract class DirectedGraphBuilderBase<V, E, G extends DirectedGraph<V, E>,
    B extends DirectedGraphBuilderBase<V, E, G, B>>
    extends AbstractGraphBuilder<V, E, G, B>
{
    /**
     * Creates a builder based on {@code baseGraph}. {@code baseGraph} must be mutable.
     *
     * @param baseGraph the graph object to base building on
     */
    public DirectedGraphBuilderBase(G baseGraph)
    {
        super(baseGraph);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnmodifiableDirectedGraph<V, E> buildUnmodifiable()
    {
        return new UnmodifiableDirectedGraph<>(this.graph);
    }
}

// End DirectedGraphBuilderBase.java
