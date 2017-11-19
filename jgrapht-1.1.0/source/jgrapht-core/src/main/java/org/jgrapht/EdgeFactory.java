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
package org.jgrapht;

/**
 * An edge factory used by graphs for creating new edges.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Barak Naveh
 * @since Jul 14, 2003
 */
public interface EdgeFactory<V, E>
{
    /**
     * Creates a new edge whose endpoints are the specified source and target vertices.
     *
     * @param sourceVertex the source vertex.
     * @param targetVertex the target vertex.
     *
     * @return a new edge whose endpoints are the specified source and target vertices.
     */
    E createEdge(V sourceVertex, V targetVertex);
}

// End EdgeFactory.java
