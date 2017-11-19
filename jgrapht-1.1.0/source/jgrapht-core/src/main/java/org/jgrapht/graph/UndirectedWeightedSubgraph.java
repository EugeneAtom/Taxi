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

import java.util.*;

import org.jgrapht.*;

/**
 * An undirected weighted graph that is a subgraph on other graph.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @see Subgraph
 * @deprecated In favor of {@link AsSubgraph}.
 */
@Deprecated
public class UndirectedWeightedSubgraph<V, E>
    extends UndirectedSubgraph<V, E>
    implements UndirectedGraph<V, E>, WeightedGraph<V, E>
{
    private static final long serialVersionUID = 3689346615735236409L;

    /**
     * Creates a new undirected weighted subgraph.
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     * @param vertexSubset vertices to include in the subgraph. If <code>null</code> then all
     *        vertices are included.
     * @param edgeSubset edges to in include in the subgraph. If <code>null</code> then all the
     *        edges whose vertices found in the graph are included.
     */
    public UndirectedWeightedSubgraph(
        UndirectedGraph<V, E> base, Set<? extends V> vertexSubset, Set<? extends E> edgeSubset)
    {
        super(base, vertexSubset, edgeSubset);
    }

    /**
     * Creates a new weighted undirected induced subgraph.
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     * @param vertexSubset vertices to include in the subgraph. If <code>null</code> then all
     *        vertices are included.
     */
    public UndirectedWeightedSubgraph(UndirectedGraph<V, E> base, Set<? extends V> vertexSubset)
    {
        this(base, vertexSubset, null);
    }

    /**
     * Creates a new weighted undirected induced subgraph with all vertices included.
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     */
    public UndirectedWeightedSubgraph(UndirectedGraph<V, E> base)
    {
        this(base, null, null);
    }
}

// End UndirectedWeightedSubgraph.java
