/*
 * (C) Copyright 2003-2017, by Barak Naveh and Contributors.
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
package org.jgrapht.graph;

import java.io.*;
import java.util.*;

import org.jgrapht.*;

/**
 * A subgraph is a graph that has a subset of vertices and a subset of edges with respect to some
 * base graph. More formally, a subgraph G(V,E) that is based on a base graph Gb(Vb,Eb) satisfies
 * the following <b><i>subgraph property</i></b>: V is a subset of Vb and E is a subset of Eb. Other
 * than this property, a subgraph is a graph with any respect and fully complies with the
 * <code>Graph</code> interface.
 *
 * <p>
 * If the base graph is a {@link org.jgrapht.ListenableGraph}, the subgraph listens on the base
 * graph and guarantees the subgraph property. If an edge or a vertex is removed from the base
 * graph, it is automatically removed from the subgraph. Subgraph listeners are informed on such
 * removal only if it results in a cascaded removal from the subgraph. If the subgraph has been
 * created as an induced subgraph it also keeps track of edges being added to its vertices. If
 * vertices are added to the base graph, the subgraph remains unaffected.
 * </p>
 *
 * <p>
 * If the base graph is <i>not</i> a ListenableGraph, then the subgraph property cannot be
 * guaranteed. If edges or vertices are removed from the base graph, they are <i>not</i> removed
 * from the subgraph.
 * </p>
 *
 * <p>
 * Modifications to Subgraph are allowed as long as the subgraph property is maintained. Addition of
 * vertices or edges are allowed as long as they also exist in the base graph. Removal of vertices
 * or edges is always allowed. The base graph is <i>never</i> affected by any modification made to
 * the subgraph.
 * </p>
 *
 * <p>
 * A subgraph may provide a "live-window" on a base graph, so that changes made to its vertices or
 * edges are immediately reflected in the base graph, and vice versa. For that to happen, vertices
 * and edges added to the subgraph must be <i>identical</i> (that is, reference-equal and not only
 * value-equal) to their respective ones in the base graph. Previous versions of this class enforced
 * such identity, at a severe performance cost. Currently it is no longer enforced. If you want to
 * achieve a "live-window" functionality, your safest tactics would be to NOT override the
 * <code>equals()</code> methods of your vertices and edges. If you use a class that has already
 * overridden the <code>equals()</code> method, such as <code>String</code>, than you can use a
 * wrapper around it, or else use it directly but exercise a great care to avoid having
 * different-but-equal instances in the subgraph and the base graph.
 * </p>
 *
 * <p>
 * This graph implementation guarantees deterministic vertex and edge set ordering (via
 * {@link LinkedHashSet}).
 * </p>
 *
 * <p>
 * Note that this implementation tries to maintain a "live-window" on the base graph, which has
 * implications in the performance of the various operations. For example iterating over the
 * adjacent edges of a vertex takes time proportional to the number of adjacent edges of the vertex
 * in the base graph even if the subgraph contains only a small subset of those edges. Therefore,
 * the user must be aware that using this implementation for certain algorithms might come with
 * computational overhead. For certain algorithms it is better to maintain a subgraph by hand
 * instead of using this implementation as a black box.
 *
 * @param <V> the vertex type
 * @param <E> the edge type
 * @param <G> the type of the base graph
 *
 * @author Barak Naveh
 * @see Graph
 * @see Set
 * @since Jul 18, 2003
 * @deprecated In favor of {@link AsSubgraph}.
 */
@Deprecated
public class Subgraph<V, E, G extends Graph<V, E>>
    extends AsSubgraph<V, E>
    implements Serializable
{
    private static final long serialVersionUID = 3208313055169665387L;

    protected final G g;

    /**
     * Creates a new Subgraph.
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     * @param vertexSubset vertices to include in the subgraph. If <code>null</code> then all
     *        vertices are included.
     * @param edgeSubset edges to in include in the subgraph. If <code>null</code> then all the
     *        edges whose vertices found in the graph are included.
     */
    public Subgraph(G base, Set<? extends V> vertexSubset, Set<? extends E> edgeSubset)
    {
        super(base, vertexSubset, edgeSubset);
        this.g = Objects.requireNonNull(base, "Invalid graph provided");
    }

    /**
     * Creates a new induced Subgraph. The subgraph will keep track of edges being added to its
     * vertex subset as well as deletion of edges and vertices. If base it not listenable, this is
     * identical to the call Subgraph(base, vertexSubset, null).
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     * @param vertexSubset vertices to include in the subgraph. If <code>null</code> then all
     *        vertices are included.
     */
    public Subgraph(G base, Set<? extends V> vertexSubset)
    {
        this(base, vertexSubset, null);
    }

    /**
     * Creates a new induced Subgraph with all vertices included. The subgraph will keep track of
     * edges being added to its vertex subset as well as deletion of edges and vertices. If base it
     * not listenable, this is identical to the call Subgraph(base, null, null).
     *
     * @param base the base (backing) graph on which the subgraph will be based.
     */
    public Subgraph(G base)
    {
        this(base, null, null);
    }

    /**
     * Get the base graph.
     * 
     * @return the base graph
     */
    public G getBase()
    {
        return g;
    }

}

// End Subgraph.java
