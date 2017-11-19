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
 * Base class for {@link DirectedWeightedGraphBuilder} for extending.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * @param <G> type of the resulting graph
 * @param <B> type of this builder
 * @deprecated In favor of {@link GraphBuilder}.
 */
@Deprecated
public abstract class DirectedWeightedGraphBuilderBase<V, E, G extends DirectedGraph<V, E>,
    B extends DirectedWeightedGraphBuilderBase<V, E, G, B>>
    extends DirectedGraphBuilderBase<V, E, G, B>
{
    /**
     * Creates a builder based on {@code baseGraph}. {@code baseGraph} must be mutable.
     *
     * @param baseGraph the graph object to base building on
     */
    public DirectedWeightedGraphBuilderBase(G baseGraph)
    {
        super(baseGraph);
    }

    /**
     * Adds an weighted edge to the graph being built. The source and target vertices are added to
     * the graph, if not already included.
     *
     * @param source source vertex of the edge.
     * @param target target vertex of the edge.
     * @param weight weight of the edge.
     *
     * @return this builder object
     *
     * @see Graphs#addEdgeWithVertices(Graph, Object, Object, double)
     */
    public B addEdge(V source, V target, double weight)
    {
        Graphs.addEdgeWithVertices(this.graph, source, target, weight);
        return this.self();
    }

    /**
     * Adds the specified weighted edge to the graph being built. The source and target vertices are
     * added to the graph, if not already included.
     *
     * @param source source vertex of the edge.
     * @param target target vertex of the edge.
     * @param edge edge to be added to this graph.
     * @param weight weight of the edge.
     *
     * @return this builder object
     *
     * @see Graph#addEdge(Object, Object, Object)
     * @see Graph#setEdgeWeight(Object, double)
     */
    public B addEdge(V source, V target, E edge, double weight)
    {
        this.graph.addEdge(source, target, edge);
        this.graph.setEdgeWeight(edge, weight);
        return this.self();
    }
}

// End DirectedWeightedGraphBuilderBase.java
