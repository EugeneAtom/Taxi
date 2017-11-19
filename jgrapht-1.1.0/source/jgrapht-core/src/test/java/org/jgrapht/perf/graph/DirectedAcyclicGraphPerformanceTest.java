/*
 * (C) Copyright 2008-2017, by Peter Giles and Contributors.
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
package org.jgrapht.perf.graph;

import java.util.concurrent.*;

import org.jgrapht.graph.*;
import org.jgrapht.graph.DirectedAcyclicGraphTest.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.*;
import org.openjdk.jmh.runner.options.*;

import junit.framework.*;

/**
 * A small benchmark comparing the different dag implementations.
 * 
 * @author Peter Giles
 * @author Dimitrios Michail
 */
public class DirectedAcyclicGraphPerformanceTest
    extends TestCase
{
    @State(Scope.Benchmark)
    private static abstract class RandomGraphBenchmarkBase
    {
        abstract DirectedAcyclicGraph<Long, DefaultEdge> createDAG();

        @Setup(Level.Iteration)
        public void setup()
        {
        }

        @Benchmark
        public void run()
        {
            int trialsPerConfiguration = 10;
            int maxVertices = 1024;
            int maxConnectednessFactor = 4;

            for (int numVertices = 64; numVertices <= maxVertices; numVertices *= 2) {
                for (int connectednessFactor = 1; (connectednessFactor <= maxConnectednessFactor)
                    && (connectednessFactor < (numVertices - 1)); connectednessFactor *= 2)
                {
                    for (int seed = 0; seed < trialsPerConfiguration; seed++) { // test with random
                                                                                // graph
                                                                                // configurations
                        RepeatableRandomGraphGenerator<Long, DefaultEdge> gen =
                            new RepeatableRandomGraphGenerator<>(
                                numVertices, numVertices * connectednessFactor, seed);
                        DirectedAcyclicGraph<Long, DefaultEdge> dag = createDAG();
                        gen.generateGraph(dag, new LongVertexFactory(), null);
                    }

                }
            }
        }
    }

    public static class ArrayDAGRandomGraphBenchmark
        extends RandomGraphBenchmarkBase
    {

        @Override
        DirectedAcyclicGraph<Long, DefaultEdge> createDAG()
        {
            return new ArrayDAG<>(DefaultEdge.class);
        }
    }

    public static class ArrayListDAGRandomGraphBenchmark
        extends RandomGraphBenchmarkBase
    {
        @Override
        DirectedAcyclicGraph<Long, DefaultEdge> createDAG()
        {
            return new ArrayListDAG<>(DefaultEdge.class);
        }
    }

    public static class HashSetDAGRandomGraphBenchmark
        extends RandomGraphBenchmarkBase
    {
        @Override
        DirectedAcyclicGraph<Long, DefaultEdge> createDAG()
        {
            return new HashSetDAG<>(DefaultEdge.class);
        }
    }

    public static class BitSetDAGRandomGraphBenchmark
        extends RandomGraphBenchmarkBase
    {
        @Override
        DirectedAcyclicGraph<Long, DefaultEdge> createDAG()
        {
            return new BitSetDAG<>(DefaultEdge.class);
        }
    }

    public void testDirectedAcyclicGraphRandomGraphBenchmark()
        throws RunnerException
    {
        Options opt = new OptionsBuilder()
            .include(".*" + ArrayDAGRandomGraphBenchmark.class.getSimpleName() + ".*")
            .include(".*" + ArrayListDAGRandomGraphBenchmark.class.getSimpleName() + ".*")
            .include(".*" + HashSetDAGRandomGraphBenchmark.class.getSimpleName() + ".*")
            .include(".*" + BitSetDAGRandomGraphBenchmark.class.getSimpleName() + ".*")
            .mode(Mode.SingleShotTime).timeUnit(TimeUnit.MILLISECONDS).warmupIterations(5)
            .measurementIterations(10).forks(1).shouldFailOnError(true).shouldDoGC(true).build();

        new Runner(opt).run();
    }

    /**
     * A DAG using the array visited strategy
     */
    private static class ArrayDAG<V, E>
        extends DirectedAcyclicGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        /**
         * Construct a directed acyclic graph.
         * 
         * @param edgeClass the edge class
         */
        public ArrayDAG(Class<? extends E> edgeClass)
        {
            super(
                new ClassBasedEdgeFactory<>(edgeClass), new VisitedArrayImpl(),
                new TopoVertexBiMap<>(), false);
        }
    }

    /**
     * A DAG using the array list visited strategy
     */
    private static class ArrayListDAG<V, E>
        extends DirectedAcyclicGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        /**
         * Construct a directed acyclic graph.
         * 
         * @param edgeClass the edge class
         */
        public ArrayListDAG(Class<? extends E> edgeClass)
        {
            super(
                new ClassBasedEdgeFactory<>(edgeClass), new VisitedArrayListImpl(),
                new TopoVertexBiMap<>(), false);
        }
    }

    /**
     * A DAG using the hash set visited strategy
     */
    private static class HashSetDAG<V, E>
        extends DirectedAcyclicGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        /**
         * Construct a directed acyclic graph.
         * 
         * @param edgeClass the edge class
         */
        public HashSetDAG(Class<? extends E> edgeClass)
        {
            super(
                new ClassBasedEdgeFactory<>(edgeClass), new VisitedHashSetImpl(),
                new TopoVertexBiMap<>(), false);
        }
    }

    /**
     * A DAG using the bitset visited strategy
     */
    private static class BitSetDAG<V, E>
        extends DirectedAcyclicGraph<V, E>
    {
        private static final long serialVersionUID = 1L;

        /**
         * Construct a directed acyclic graph.
         * 
         * @param edgeClass the edge class
         */
        public BitSetDAG(Class<? extends E> edgeClass)
        {
            super(
                new ClassBasedEdgeFactory<>(edgeClass), new VisitedBitSetImpl(),
                new TopoVertexBiMap<>(), false);
        }
    }

}
