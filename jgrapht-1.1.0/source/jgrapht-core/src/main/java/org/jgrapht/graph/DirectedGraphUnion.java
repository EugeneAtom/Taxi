/*
 * (C) Copyright 2009-2017, by Ilya Razenshteyn and Contributors.
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
import org.jgrapht.util.*;

/**
 * A union of directed graphs.
 * 
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * @deprecated In favor of {@link AsGraphUnion}.
 */
@Deprecated
public class DirectedGraphUnion<V, E>
    extends GraphUnion<V, E, DirectedGraph<V, E>>
    implements DirectedGraph<V, E>
{
    private static final long serialVersionUID = -740199233080172450L;

    /**
     * Construct a new directed graph union.
     * 
     * @param g1 the first graph
     * @param g2 the second graph
     * @param operator the weight combiner (policy for edge weight calculation)
     */
    public DirectedGraphUnion(
        DirectedGraph<V, E> g1, DirectedGraph<V, E> g2, WeightCombiner operator)
    {
        super(g1, g2, operator);
    }

    /**
     * Construct a new directed graph union. The union will use the {@link WeightCombiner#SUM}
     * weight combiner.
     * 
     * @param g1 the first graph
     * @param g2 the second graph
     */
    public DirectedGraphUnion(DirectedGraph<V, E> g1, DirectedGraph<V, E> g2)
    {
        super(g1, g2);
    }

    @Override
    public int inDegreeOf(V vertex)
    {
        Set<E> res = incomingEdgesOf(vertex);
        return res.size();
    }

    @Override
    public Set<E> incomingEdgesOf(V vertex)
    {
        Set<E> res = new HashSet<>();
        if (getG1().containsVertex(vertex)) {
            res.addAll(getG1().incomingEdgesOf(vertex));
        }
        if (getG2().containsVertex(vertex)) {
            res.addAll(getG2().incomingEdgesOf(vertex));
        }
        return Collections.unmodifiableSet(res);
    }

    @Override
    public int outDegreeOf(V vertex)
    {
        Set<E> res = outgoingEdgesOf(vertex);
        return res.size();
    }

    @Override
    public Set<E> outgoingEdgesOf(V vertex)
    {
        Set<E> res = new HashSet<>();
        if (getG1().containsVertex(vertex)) {
            res.addAll(getG1().outgoingEdgesOf(vertex));
        }
        if (getG2().containsVertex(vertex)) {
            res.addAll(getG2().outgoingEdgesOf(vertex));
        }
        return Collections.unmodifiableSet(res);
    }
}

// End DirectedGraphUnion.java
