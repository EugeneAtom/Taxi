/*
 * (C) Copyright 2005-2017, by Trevor Harmon and Contributors.
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

/**
 * Assigns a unique integer to represent each component. Each instance of provider maintains an
 * internal map between every component it has ever seen and the unique integer representing that
 * edge. As a result it is probably desirable to have a separate instance for each distinct graph.
 * 
 * @param <T> the component type
 *
 * @author Trevor Harmon
 * @deprecated Use {@link org.jgrapht.io.IntegerComponentNameProvider} instead.
 */
@Deprecated
public class IntegerComponentNameProvider<T>
    extends org.jgrapht.io.IntegerComponentNameProvider<T>
    implements ComponentNameProvider<T>
{
}

// End IntegerComponentNameProvider.java
