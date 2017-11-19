/*
 * (C) Copyright 2003-2017, by Tim Shearouse and Contributors.
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
package org.jgrapht.demo;

import java.util.*;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;

/**
 * Demonstrates how to create a complete graph and perform a depth first search on it.
 *
 */
public final class CompleteGraphDemo
{
    static Graph<String, DefaultEdge> completeGraph;

    // Number of vertices
    static int size = 10;

    /**
     * Main demo entry point.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        // Create the graph object; it is null at this point
        completeGraph = new SimpleGraph<>(DefaultEdge.class);

        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
            new CompleteGraphGenerator<>(size);

        // Create the VertexFactory so the generator can create vertices
        VertexFactory<String> vFactory = new VertexFactory<String>()
        {
            private int id = 0;

            @Override
            public String createVertex()
            {
                return "v" + id++;
            }
        };

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph, vFactory, null);

        // Print out the graph to be sure it's really complete
        Iterator<String> iter = new DepthFirstIterator<>(completeGraph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out.println(
                "Vertex " + vertex + " is connected to: "
                    + completeGraph.edgesOf(vertex).toString());
        }
    }
}

// End CompleteGraphDemo.java
