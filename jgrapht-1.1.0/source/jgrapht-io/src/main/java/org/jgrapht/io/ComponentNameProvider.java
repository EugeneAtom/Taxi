/*
 * (C) Copyright 2016-2017, by Dimitrios Michail and Contributors.
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
package org.jgrapht.io;

/**
 * Provides a name for a component.
 *
 * @param <T> the type of the component
 */
public interface ComponentNameProvider<T>
{

    /**
     * Returns a unique name. This is useful when exporting a graph, as it ensures that all
     * vertices/edges are assigned simple, consistent names.
     *
     * @param component the component to be named
     * @return the name of the component
     */
    String getName(T component);

}

// End ComponentNameProvider.java
