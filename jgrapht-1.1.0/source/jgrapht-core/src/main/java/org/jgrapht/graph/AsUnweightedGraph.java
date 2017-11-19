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

import org.jgrapht.*;

/**
 * An unweighted view of a graph.
 * 
 * <p>
 * An unweighted view of the backing weighted graph specified in the constructor. This graph allows
 * modules to apply algorithms designed for unweighted graphs to a weighted graph by simply ignoring
 * edge weights. Query operations on this graph "read through" to the backing graph. Vertex
 * addition/removal and edge addition/removal are all supported (and immediately reflected in the
 * backing graph).
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
 * @since Sep 7, 2007
 */
public class AsUnweightedGraph<V, E>
    extends GraphDelegator<V, E>
    implements Serializable
{
    private static final long serialVersionUID = 7175505077601824663L;

    /**
     * Constructor
     *
     * @param g the backing graph over which an unweighted view is to be created.
     */
    public AsUnweightedGraph(Graph<V, E> g)
    {
        super(g);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getEdgeWeight(E e)
    {
        if (e == null) {
            throw new NullPointerException();
        } else {
            return Graph.DEFAULT_EDGE_WEIGHT;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setEdgeWeight(E e, double weight)
    {
        throw new UnsupportedOperationException("Graph is unweighted");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphType getType()
    {
        return super.getType().asUnweighted();
    }

}

// End AsUnweightedGraph.java
