/*
 * (C) Copyright 2006-2017, by Carl Anderson and Contributors.
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
package org.jgrapht.experimental.touchgraph;

import java.applet.*;
import java.awt.*;

import javax.swing.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;

/**
 * SimpleTouchgraphApplet
 *
 * @author Carl Anderson
 */
public class SimpleTouchgraphApplet
    extends Applet
{
    // ~ Static fields/initializers ---------------------------------------------

    /**
     */
    private static final long serialVersionUID = 6213379835360007840L;

    // ~ Methods ----------------------------------------------------------------

    /**
     * Create a sample graph: code taken from non-visible org._3pq.jgrapht.demo.createStringGraph()
     * 
     * @return return a sample graph
     */
    public static Graph<String, DefaultEdge> createSamplegraph()
    {
        UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";
        String v4 = "v4";

        // add the vertices
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);

        // add edges to create a circuit
        g.addEdge(v1, v2);
        g.addEdge(v2, v3);
        g.addEdge(v3, v4);
        g.addEdge(v4, v1);

        return g;
    }

    /**
     * Initialize the applet.
     */
    public void init()
    {
        Graph<String, DefaultEdge> g = createSamplegraph();
        boolean selfReferencesAllowed = false;

        setLayout(new BorderLayout());
        setSize(800, 600);
        add(new TouchgraphPanel<>(g, selfReferencesAllowed), BorderLayout.CENTER);
    }

    /**
     * Main entry point.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args)
    {
        Graph<String, DefaultEdge> g = createSamplegraph();
        boolean selfReferencesAllowed = false;

        JFrame frame = new JFrame();
        frame.getContentPane().add(new TouchgraphPanel<>(g, selfReferencesAllowed));
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setTitle("JGraphT to Touchgraph Converter Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        try {
            Thread.sleep(5000000);
        } catch (InterruptedException ex) {
        }
    }
}

// End SimpleTouchgraphApplet.java
