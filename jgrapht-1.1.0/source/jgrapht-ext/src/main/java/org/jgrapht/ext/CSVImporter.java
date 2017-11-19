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
 * Imports a graph from a CSV Format or any other Delimiter-separated value format.
 * 
 * <p>
 * The importer supports various different formats which can be adjusted using the
 * {@link #setFormat(CSVFormat) setFormat} method. The supported formats are the same CSV formats
 * used by <a href="https://gephi.org/users/supported-graph-formats/csv-format">Gephi </a>. For some
 * of the formats, the behavior of the importer can be adjusted using the
 * {@link #setParameter(org.jgrapht.ext.CSVFormat.Parameter, boolean) setParameter} method. See
 * {@link CSVFormat} for a description of the formats.
 * </p>
 * 
 * <p>
 * The importer respects <a href="http://www.ietf.org/rfc/rfc4180.txt">rfc4180</a>. The caller can
 * also adjust the separator to something like semicolon or pipe instead of comma. In such a case,
 * all fields are unescaped using the new separator. See
 * <a href="https://en.wikipedia.org/wiki/Delimiter-separated_values">Delimiter- separated
 * values</a> for more information.
 * </p>
 * 
 * <p>
 * This importer does not distinguish between {@link CSVFormat#EDGE_LIST} and
 * {@link CSVFormat#ADJACENCY_LIST}. In both cases it assumes the format is
 * {@link CSVFormat#ADJACENCY_LIST}.
 * </p>
 * 
 * @see CSVFormat
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 * 
 * @author Dimitrios Michail
 * @since August 2016
 * @deprecated Use {@link org.jgrapht.io.CSVImporter} instead.
 */
@Deprecated
public class CSVImporter<V, E>
    implements GraphImporter<V, E>
{
    private static final char DEFAULT_DELIMITER = ',';

    private org.jgrapht.io.CSVImporter<V, E> delegate;

    /**
     * Constructs a new importer using the {@link CSVFormat#ADJACENCY_LIST} format as default.
     * 
     * @param vertexProvider provider for the generation of vertices. Must not be null.
     * @param edgeProvider provider for the generation of edges. Must not be null.
     */
    public CSVImporter(VertexProvider<V> vertexProvider, EdgeProvider<V, E> edgeProvider)
    {
        this(vertexProvider, edgeProvider, CSVFormat.ADJACENCY_LIST, DEFAULT_DELIMITER);
    }

    /**
     * Constructs a new importer.
     * 
     * @param vertexProvider provider for the generation of vertices. Must not be null.
     * @param edgeProvider provider for the generation of edges. Must not be null.
     * @param format format to use out of the supported ones
     */
    public CSVImporter(
        VertexProvider<V> vertexProvider, EdgeProvider<V, E> edgeProvider, CSVFormat format)
    {
        this(vertexProvider, edgeProvider, format, DEFAULT_DELIMITER);
    }

    /**
     * Constructs a new importer.
     * 
     * @param vertexProvider provider for the generation of vertices. Must not be null.
     * @param edgeProvider provider for the generation of edges. Must not be null.
     * @param format format to use out of the supported ones
     * @param delimiter delimiter to use (comma, semicolon, pipe, etc.)
     */
    public CSVImporter(
        VertexProvider<V> vertexProvider, EdgeProvider<V, E> edgeProvider, CSVFormat format,
        char delimiter)
    {
        switch (format) {
        case ADJACENCY_LIST:
            this.delegate = new org.jgrapht.io.CSVImporter<>(
                delegateProvider(vertexProvider), delegateProvider(edgeProvider),
                org.jgrapht.io.CSVFormat.ADJACENCY_LIST, delimiter);
            break;
        case EDGE_LIST:
            this.delegate = new org.jgrapht.io.CSVImporter<>(
                delegateProvider(vertexProvider), delegateProvider(edgeProvider),
                org.jgrapht.io.CSVFormat.EDGE_LIST, delimiter);
            break;
        case MATRIX:
            this.delegate = new org.jgrapht.io.CSVImporter<>(
                delegateProvider(vertexProvider), delegateProvider(edgeProvider),
                org.jgrapht.io.CSVFormat.MATRIX, delimiter);
            break;
        }
    }

    /**
     * Get the format that the importer is using.
     * 
     * @return the input format
     */
    public CSVFormat getFormat()
    {
        switch (delegate.getFormat()) {
        case ADJACENCY_LIST:
            return CSVFormat.ADJACENCY_LIST;
        case MATRIX:
            return CSVFormat.MATRIX;
        case EDGE_LIST:
        default:
            return CSVFormat.EDGE_LIST;
        }
    }

    /**
     * Set the format of the importer
     * 
     * @param format the format to use
     */
    public void setFormat(CSVFormat format)
    {
        switch (format) {
        case ADJACENCY_LIST:
            this.delegate.setFormat(org.jgrapht.io.CSVFormat.ADJACENCY_LIST);
            break;
        case MATRIX:
            this.delegate.setFormat(org.jgrapht.io.CSVFormat.MATRIX);
            break;
        case EDGE_LIST:
            this.delegate.setFormat(org.jgrapht.io.CSVFormat.EDGE_LIST);
            break;
        }
    }

    /**
     * Get the delimiter (comma, semicolon, pipe, etc).
     * 
     * @return the delimiter
     */
    public char getDelimiter()
    {
        return delegate.getDelimiter();
    }

    /**
     * Set the delimiter (comma, semicolon, pipe, etc).
     * 
     * @param delimiter the delimiter to use
     */
    public void setDelimiter(char delimiter)
    {
        this.delegate.setDelimiter(delimiter);
    }

    /**
     * Return if a particular parameter of the exporter is enabled
     * 
     * @param p the parameter
     * @return {@code true} if the parameter is set, {@code false} otherwise
     */
    public boolean isParameter(CSVFormat.Parameter p)
    {
        switch (p) {
        case MATRIX_FORMAT_EDGE_WEIGHTS:
            return delegate
                .isParameter(org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_EDGE_WEIGHTS);
        case MATRIX_FORMAT_NODEID:
            return delegate.isParameter(org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_NODEID);
        case MATRIX_FORMAT_ZERO_WHEN_NO_EDGE:
            return delegate
                .isParameter(org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_ZERO_WHEN_NO_EDGE);
        }
        return false;
    }

    /**
     * Set the value of a parameter of the exporter
     * 
     * @param p the parameter
     * @param value the value to set
     */
    public void setParameter(CSVFormat.Parameter p, boolean value)
    {
        switch (p) {
        case MATRIX_FORMAT_EDGE_WEIGHTS:
            delegate
                .setParameter(org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_EDGE_WEIGHTS, value);
            break;
        case MATRIX_FORMAT_NODEID:
            delegate.setParameter(org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_NODEID, value);
            break;
        case MATRIX_FORMAT_ZERO_WHEN_NO_EDGE:
            delegate.setParameter(
                org.jgrapht.io.CSVFormat.Parameter.MATRIX_FORMAT_ZERO_WHEN_NO_EDGE, value);
            break;
        }
    }

    /**
     * Import a graph.
     * 
     * <p>
     * The provided graph must be able to support the features of the graph that is read. For
     * example if the input contains self-loops then the graph provided must also support
     * self-loops. The same for multiple edges.
     * 
     * <p>
     * If the provided graph is a weighted graph, the importer also reads edge weights.
     * 
     * @param graph the graph
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

// End CSVImporter.java
