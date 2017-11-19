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
package org.jgrapht.ext;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import org.jgrapht.*;

/**
 * Imports a graph from a GML file (Graph Modeling Language).
 * 
 * <p>
 * For a description of the format see <a href="http://www.infosun.fmi.uni-passau.de/Graphlet/GML/">
 * http://www. infosun.fmi.uni-passau.de/Graphlet/GML/</a>.
 *
 * <p>
 * Below is small example of a graph in GML format.
 * 
 * <pre>
 * graph [
 *   node [ 
 *     id 1
 *   ]
 *   node [
 *     id 2
 *   ]
 *   node [
 *     id 3
 *   ]
 *   edge [
 *     source 1
 *     target 2 
 *     weight 2.0
 *   ]
 *   edge [
 *     source 2
 *     target 3
 *     weight 3.0
 *   ]
 * ]
 * </pre>
 * 
 * <p>
 * In case the graph is an instance of {@link org.jgrapht.WeightedGraph} then the importer also
 * reads edge weights. Otherwise edge weights are ignored.
 *
 * @param <V> the vertex type
 * @param <E> the edge type
 * 
 * @deprecated Use {@link org.jgrapht.io.GmlImporter} instead.
 */
@Deprecated
public class GmlImporter<V, E>
    implements GraphImporter<V, E>
{
    private org.jgrapht.io.GmlImporter<V, E> delegate;
    private VertexProvider<V> vertexProvider;
    private EdgeProvider<V, E> edgeProvider;

    /**
     * Constructs a new importer.
     * 
     * @param vertexProvider provider for the generation of vertices. Must not be null.
     * @param edgeProvider provider for the generation of edges. Must not be null.
     */
    public GmlImporter(VertexProvider<V> vertexProvider, EdgeProvider<V, E> edgeProvider)
    {
        this.vertexProvider = vertexProvider;
        this.edgeProvider = edgeProvider;
        this.delegate = new org.jgrapht.io.GmlImporter<>(
            delegateProvider(vertexProvider), delegateProvider(edgeProvider));
    }

    /**
     * Get the vertex provider
     * 
     * @return the vertex provider
     */
    public VertexProvider<V> getVertexProvider()
    {
        return (VertexProvider<V>) vertexProvider;
    }

    /**
     * Set the vertex provider
     * 
     * @param vertexProvider the new vertex provider. Must not be null.
     */
    public void setVertexProvider(VertexProvider<V> vertexProvider)
    {
        this.vertexProvider = vertexProvider;
        this.delegate.setVertexProvider(delegateProvider(vertexProvider));
    }

    /**
     * Get the edge provider
     * 
     * @return The edge provider
     */
    public EdgeProvider<V, E> getEdgeProvider()
    {
        return (EdgeProvider<V, E>) edgeProvider;
    }

    /**
     * Set the edge provider.
     * 
     * @param edgeProvider the new edge provider. Must not be null.
     */
    public void setEdgeProvider(EdgeProvider<V, E> edgeProvider)
    {
        this.edgeProvider = edgeProvider;
        this.delegate.setEdgeProvider(delegateProvider(edgeProvider));
    }

    /**
     * Import a graph.
     * 
     * <p>
     * The provided graph must be able to support the features of the graph that is read. For
     * example if the gml file contains self-loops then the graph provided must also support
     * self-loops. The same for multiple edges.
     * 
     * <p>
     * If the provided graph is a weighted graph, the importer also reads edge weights. Otherwise
     * edge weights are ignored.
     * 
     * @param graph the output graph
     * @param input the input reader
     * @throws ImportException in case an error occurs, such as I/O or parse error
     */
    @Override
    public void importGraph(Graph<V, E> graph, Reader input)
        throws ImportException
    {
        try {
            delegate.importGraph(graph, input);
        } catch (org.jgrapht.io.ImportException e) {
            throw new ImportException(e);
        }
    }

    private org.jgrapht.io.EdgeProvider<V, E> delegateProvider(EdgeProvider<V, E> edgeProvider)
    {
        return (from, to, label, attributes) -> {
            return edgeProvider.buildEdge(
                from, to, label, attributes.entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())));
        };
    }

    private org.jgrapht.io.VertexProvider<V> delegateProvider(VertexProvider<V> vertexProvider)
    {
        return (id, attributes) -> {
            return vertexProvider.buildVertex(
                id, attributes.entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())));
        };
    }

}
