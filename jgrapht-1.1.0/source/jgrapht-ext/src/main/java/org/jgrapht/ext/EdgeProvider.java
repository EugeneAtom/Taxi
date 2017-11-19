/*
 * (C) Copyright 2015-2017, by Wil Selwood and Contributors.
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
package org.jgrapht.ext;

import java.util.*;

/**
 * Defines a provider of edges of type E
 *
 * @param <V> the type of vertex being linked.
 * @param <E> the type of edge being created.
 * 
 * @deprecated Use {@link org.jgrapht.io.EdgeProvider} instead.
 */
@Deprecated
public interface EdgeProvider<V, E>
{

    /**
     * Construct an edge.
     *
     * @param from the source vertex
     * @param to the target vertex
     * @param label the label of the edge.
     * @param attributes extra attributes for the edge.
     *
     * @return the edge
     */
    E buildEdge(V from, V to, String label, Map<String, String> attributes);

}

// End EdgeProvider.java
