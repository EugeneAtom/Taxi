/*
 * (C) Copyright 2013-2017, by Alexey Kudinkin and Contributors.
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
package org.jgrapht.generate;

import java.util.*;

import org.jgrapht.*;

/**
 * An interface for generating graph structures having edges weighted with real values.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * @param <T> type for returning implementation-specific mappings from String roles to graph
 *        elements
 *
 * @author Alexey Kudinkin
 * @since Aug 1, 2013
 * @deprecated Not needed since {@link WeightedGraph} is deprecated.
 */
@Deprecated
public abstract class WeightedGraphGeneratorAdapter<V, E, T>
    implements GraphGenerator<V, E, T>
{
    protected double[][] weights;

    /**
     * Generate a weighted graph structure. The topology of the generated graph is dependent on the
     * implementation. For graphs in which not all vertices share the same automorphism equivalence
     * class, the generator may produce a labeling indicating the roles played by generated
     * elements. This is the purpose of the resultMap parameter. For example, a generator for a
     * wheel graph would designate a hub vertex. Role names used as keys in resultMap should be
     * declared as public static final Strings by implementation classes.
     *
     * @param target receives the generated edges and vertices; if this is non-empty on entry, the
     *        result will be a disconnected graph since generated elements will not be connected to
     *        existing elements
     * @param vertexFactory called to produce new vertices
     * @param resultMap if non-null, receives implementation-specific mappings from String roles to
     *        graph elements (or collections of graph elements)
     * @deprecated Not needed since {@link WeightedGraph} is deprecated.
     */
    @Deprecated
    public abstract void generateGraph(
        WeightedGraph<V, E> target, VertexFactory<V> vertexFactory, Map<String, T> resultMap);

    /**
     * Set the weights of the generator.
     * 
     * @param weights the weights
     * @return the generator
     */
    public WeightedGraphGeneratorAdapter<V, E, T> weights(double[][] weights)
    {
        this.weights = weights;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generateGraph(
        Graph<V, E> target, VertexFactory<V> vertexFactory, Map<String, T> resultMap)
    {
        generateGraph((WeightedGraph<V, E>) target, vertexFactory, resultMap);
    }
}

// End WeightedGraphGeneratorAdapter.java
