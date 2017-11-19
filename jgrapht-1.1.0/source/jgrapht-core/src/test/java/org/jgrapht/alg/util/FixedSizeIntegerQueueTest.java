/*
 * (C) Copyright 2017-2017, by Joris Kinable and Contributors.
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
package org.jgrapht.alg.util;

import junit.framework.*;

/**
 * Tests for FixedSizeIntegerQueue
 * 
 * @author Joris Kinable
 */
public class FixedSizeIntegerQueueTest
    extends TestCase
{

    public void testQueue()
    {
        FixedSizeIntegerQueue queue = new FixedSizeIntegerQueue(10);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue(1);
        assertFalse(queue.isEmpty());
        assertEquals(1, queue.size());

        int v = queue.poll();
        assertEquals(1, v);
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.enqueue(2);
        assertFalse(queue.isEmpty());
        queue.clear();
        assertTrue(queue.isEmpty());
    }
}
