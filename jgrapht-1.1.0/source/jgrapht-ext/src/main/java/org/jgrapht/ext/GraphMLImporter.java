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
 * Imports a graph from a GraphML data source.
 * 
 * <p>
 * For a description of the format see <a href="http://en.wikipedia.org/wiki/GraphML">
 * http://en.wikipedia.org/wiki/ GraphML</a> or the
 * <a href="http://graphml.graphdrawing.org/primer/graphml-primer.html">GraphML Primer</a>.
 * </p>
 * 
 * <p>
 * Below is small example of a graph in GraphML format.
 * 
 * <pre>
 * {@code
 * <?xml version="1.0" encoding="UTF-8"?>
 * <graphml xmlns="http://graphml.graphdrawing.org/xmlns"  
 *     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *     xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns 
 *     http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">
 *   <key id="d0" for="node" attr.name="color" attr.type="string">
 *     <default>yellow</default>
 *   </key>
 *   <key id="d1" for="edge" attr.name="weight" attr.type="double"/>
 *   <graph id="G" edgedefault="undirected">
 *     <node id="n0">
 *       <data key="d0">green</data>
 *     </node>
 *     <node id="n1"/>
 *     <node id="n2">
 *       <data key="d0">blue</data>
 *     </node>
 *     <node id="n3">
 *       <data key="d0">red</data>
 *     </node>
 *     <node id="n4"/>
 *     <node id="n5">
 *       <data key="d0">turquoise</data>
 *     </node>
 *     <edge id="e0" source="n0" target="n2">
 *       <data key="d1">1.0</data>
 *     </edge>
 *     <edge id="e1" source="n0" target="n1">
 *       <data key="d1">1.0</data>
 *     </edge>
 *     <edge id="e2" source="n1" target="n3">
 *       <data key="d1">2.0</data>
 *     </edge>
 *     <edge id="e3" source="n3" target="n2"/>
 *     <edge id="e4" source="n2" target="n4"/>
 *     <edge id="e5" source="n3" target="n5"/>
 *     <edge id="e6" source="n5" target="n4">
 *       <data key="d1">1.1</data>
 *     </edge>
 *   </graph>
 * </graphml>
 * }
 * </pre>
 * 
 * <p>
 * The importer reads the input into a graph which is provided by the user. In case the graph is an
 * instance of {@link org.jgrapht.WeightedGraph} and the corresponding edge key with
 * attr.name="weight" is defined, the importer also reads edge weights. Otherwise edge weights are
 * ignored.
 * 
 * <p>
 * GraphML-Attributes Values are read as string key-value pairs and passed on to the
 * {@link VertexProvider} and {@link EdgeProvider} respectively.
 * 
 * <p>
 * The provided graph object, where the imported graph will be stored, must be able to support the
 * features of the graph that is read. For example if the GraphML file contains self-loops then the
 * graph provided must also support self-loops. The same for multiple edges. Moreover, the parser
 * completely ignores the attribute "edgedefault" which denotes whether an edge is directed or not.
 * Whether edges are directed or not depends on the underlying implementation of the user provided
 * graph object.
 * 
 * <p>
 * The importer validates the input using the 1.0
 * <a href="http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd">GraphML Schema</a>.
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 * @author Dimitrios Michail
 * @since July 2016
 * @deprecated Use {@link org.jgrapht.io.GraphMLImporter} instead.
 */
@Deprecated
public class GraphMLImporter<V, E>
    implements GraphImporter<V, E>
{
    private org.jgrapht.io.GraphMLImporter<V, E> delegate;
    private VertexProvider<V> vertexProvider;
    private EdgeProvider<V, E> edgeProvider;

    /**
     * Constructs a new importer.
     * 
     * @param vertexProvider provider for the generation of vertices. Must not be null.
     * @param edgeProvider provider for the generation of edges. Must not be null.
     */
    public GraphMLImporter(VertexProvider<V> vertexProvider, EdgeProvider<V, E> edgeProvider)
    {
        this.vertexProvider = vertexProvider;
        this.edgeProvider = edgeProvider;
        this.delegate = new org.jgrapht.io.GraphMLImporter<>(
            delegateProvider(vertexProvider), delegateProvider(edgeProvider));
    }

    /**
     * Get the vertex provider
     * 
     * @return the vertex provider
     */
    public VertexProvider<V> getVertexProvider()
    {
        return vertexProvider;
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
        return edgeProvider;
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
     * Get the attribute name for edge weights
     * 
     * @return the attribute name
     */
    public String getEdgeWeightAttributeName()
    {
        return delegate.getEdgeWeightAttributeName();
    }

    /**
     * Set the attribute name to use for edge weights.
     * 
     * @param edgeWeightAttributeName the attribute name
     */
    public void setEdgeWeightAttributeName(String edgeWeightAttributeName)
    {
        delegate.setEdgeWeightAttributeName(edgeWeightAttributeName);
    }

    /**
     * Import a graph.
     * 
     * <p>
     * The provided graph must be able to support the features of the graph that is read. For
     * example if the GraphML file contains self-loops then the graph provided must also support
     * self-loops. The same for multiple edges.
     * 
     * <p>
     * If the provided graph is a weighted graph, the importer also reads edge weights.
     * 
     * <p>
     * GraphML-Attributes Values are read as string key-value pairs and passed on to the
     * {@link VertexProvider} and {@link EdgeProvider} respectively.
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
            throw new ImportException("Failed to parse GraphML", e);
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
