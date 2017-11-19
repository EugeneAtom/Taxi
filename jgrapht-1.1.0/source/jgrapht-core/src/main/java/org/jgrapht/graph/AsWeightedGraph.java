/*
 * (C) Copyright 2007-2017, by Lucas J Scharenbroich and Contributors.
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

import java.io.*;
import java.util.*;

import org.jgrapht.*;

/**
 * A weighted view of a graph.
 * 
 * <p>
 * A weighted view of the backing graph specified in the constructor. This graph allows modules to
 * apply algorithms designed for weighted graphs to an unweighted graph by providing an explicit
 * edge weight mapping. The implementation also allows for "masking" weights for a subset of the
 * edges in an existing weighted graph.
 *
 * <p>
 * Query operations on this graph "read through" to the backing graph. Vertex addition/removal and
 * edge addition/removal are all supported (and immediately reflected in the backing graph). Setting
 * an edge weight will pass the operation to the backing graph as well if the backing graph
 * implements the WeightedGraph interface. Setting an edge weight will modify the weight map in
 * order to maintain a consistent graph.
 *
 * <p>
 * Note that edges returned by this graph's accessors are really just the edges of the underlying
 * graph.
 *
 * <p>
 * This graph does <i>not</i> pass the hashCode and equals operations through to the backing graph,
 * but relies on <tt>Object</tt>'s <tt>equals</tt> and <tt>hashCode</tt> methods. This graph will be
 * serializable if the backing graph is serializable.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Lucas J. Scharenbroich
 * @since Sep 10, 2007
 */
public class AsWeightedGraph<V, E>
    extends GraphDelegator<V, E>
    implements WeightedGraph<V, E>, Serializable
{
    private static final long serialVersionUID = 6408608293216853184L;

    private final Map<E, Double> weightMap;

    /**
     * Constructor for AsWeightedGraph.
     *
     * @param g the backing graph over which a weighted view is to be created.
     * @param weightMap A mapping of edges to weights. If an edge is not present in the weight map,
     *        the edge weight for the underlying graph is returned. Note that a live reference to
     *        this map is retained, so if the caller changes the map after construction, the changes
     *        will affect the AsWeightedGraph instance as well.
     */
    public AsWeightedGraph(Graph<V, E> g, Map<E, Double> weightMap)
    {
        super(g);
        this.weightMap = Objects.requireNonNull(weightMap, "Weight map cannot be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEdgeWeight(E e, double weight)
    {
        if (super.getType().isWeighted()) {
            super.setEdgeWeight(e, weight);
        }

        // Always modify the weight map. It would be a terrible violation
        // of the use contract to silently ignore changes to the weights.
        weightMap.put(e, weight);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEdgeWeight(E e)
    {
        double weight;

        // Always return the value from the weight map first and
        // only pass the call through as a backup
        if (weightMap.containsKey(e)) {
            weight = weightMap.get(e);
        } else {
            weight = super.getEdgeWeight(e);
        }

        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphType getType()
    {
        return super.getType().asWeighted();
    }

}

// End AsWeightedGraph.java
