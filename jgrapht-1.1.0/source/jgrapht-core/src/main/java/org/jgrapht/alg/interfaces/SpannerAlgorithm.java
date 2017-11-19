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
package org.jgrapht.alg.interfaces;

import java.io.*;
import java.util.*;

/**
 * An algorithm which computes a
 * <a href="https://en.wikipedia.org/wiki/Glossary_of_graph_theory#spanner">graph spanner</a> of a
 * given graph.
 *
 * @param <E> edge the graph edge type
 */
public interface SpannerAlgorithm<E>
{

    /**
     * Computes a graph spanner.
     *
     * @return a graph spanner
     */
    Spanner<E> getSpanner();

    /**
     * A graph spanner.
     *
     * @param <E> the graph edge type
     */
    interface Spanner<E>
        extends Iterable<E>
    {

        /**
         * Returns the weight of the graph spanner.
         * 
         * @return weight of the graph spanner
         */
        double getWeight();

        /**
         * Set of edges of the graph spanner.
         * 
         * @return edge set of the spanner
         */
        Set<E> getEdges();

        /**
         * Returns an iterator over the edges in the spanner.
         * 
         * @return iterator over the edges in the spanner.
         */
        @Override
        default Iterator<E> iterator()
        {
            return getEdges().iterator();
        }
    }

    /**
     * Default implementation of the spanner interface.
     *
     * @param <E> the graph edge type
     */
    class SpannerImpl<E>
        implements Spanner<E>, Serializable
    {
        private static final long serialVersionUID = 5951646499902668516L;

        private final double weight;
        private final Set<E> edges;

        /**
         * Construct a new spanner
         *
         * @param edges the edges
         * @param weight the weight
         */
        public SpannerImpl(Set<E> edges, double weight)
        {
            this.edges = edges;
            this.weight = weight;
        }

        @Override
        public double getWeight()
        {
            return weight;
        }

        @Override
        public Set<E> getEdges()
        {
            return edges;
        }

        @Override
        public String toString()
        {
            return "Spanner [weight=" + weight + ", edges=" + edges + "]";
        }
    }

}
