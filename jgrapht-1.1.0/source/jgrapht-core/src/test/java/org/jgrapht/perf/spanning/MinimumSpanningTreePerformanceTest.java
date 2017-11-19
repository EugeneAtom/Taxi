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
package org.jgrapht.perf.spanning;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

import org.jgrapht.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.spanning.*;
import org.jgrapht.alg.util.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.util.*;
import org.openjdk.jmh.runner.*;

import junit.framework.*;

/**
 * A small benchmark comparing spanning tree algorithms on random graphs.
 * 
 * @author Dimitrios Michail
 */
public class MinimumSpanningTreePerformanceTest
    extends TestCase
{
    private static final int PERF_BENCHMARK_VERTICES_COUNT = 1000;
    private static final double PERF_BENCHMARK_EDGES_PROP = 0.5;
    private static final int WARMUP_REPEAT = 5;
    private static final int REPEAT = 10;
    private static final long SEED = 13l;

    private static abstract class BenchmarkBase
    {
        protected Random rng = new Random(SEED);
        protected GraphGenerator<Integer, DefaultWeightedEdge, Integer> generator = null;
        protected Graph<Integer, DefaultWeightedEdge> graph;

        abstract SpanningTreeAlgorithm<DefaultWeightedEdge> createSolver(
            Graph<Integer, DefaultWeightedEdge> graph);

        public void setup()
        {
            if (generator == null) {
                // lazily construct generator
                generator = new GnpRandomGraphGenerator<>(
                    PERF_BENCHMARK_VERTICES_COUNT, PERF_BENCHMARK_EDGES_PROP, rng, false);
            }

            DirectedWeightedPseudograph<Integer, DefaultWeightedEdge> weightedGraph =
                new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
            this.graph = weightedGraph;

            generator.generateGraph(graph, new IntegerVertexFactory(), null);

            for (DefaultWeightedEdge e : weightedGraph.edgeSet()) {
                weightedGraph.setEdgeWeight(e, rng.nextDouble());
            }
        }

        public void run()
        {
            SpanningTreeAlgorithm<DefaultWeightedEdge> algo = createSolver(graph);
            algo.getSpanningTree();
        }
    }

    public static class PrimBenchmark
        extends BenchmarkBase
    {
        @Override
        SpanningTreeAlgorithm<DefaultWeightedEdge> createSolver(
            Graph<Integer, DefaultWeightedEdge> graph)
        {
            return new PrimMinimumSpanningTree<>(graph);
        }

        @Override
        public String toString()
        {
            return "Prim";
        }
    }

    public static class KruskalBenchmark
        extends BenchmarkBase
    {
        @Override
        SpanningTreeAlgorithm<DefaultWeightedEdge> createSolver(
            Graph<Integer, DefaultWeightedEdge> graph)
        {
            return new KruskalMinimumSpanningTree<>(graph);
        }

        @Override
        public String toString()
        {
            return "Kruskal";
        }
    }

    public static class BoruvkaBenchmark
        extends BenchmarkBase
    {
        @Override
        SpanningTreeAlgorithm<DefaultWeightedEdge> createSolver(
            Graph<Integer, DefaultWeightedEdge> graph)
        {
            return new BoruvkaMinimumSpanningTree<>(graph);
        }

        @Override
        public String toString()
        {
            return "Boruvka";
        }
    }

    public void testBenchmark()
        throws RunnerException
    {
        System.out.println("Minimum Spanning Tree Benchmark");
        System.out.println("-------------------------------");
        System.out.println(
            "Using G(n,p) random graph with n = " + PERF_BENCHMARK_VERTICES_COUNT + ", p = "
                + PERF_BENCHMARK_EDGES_PROP);
        System.out.println("Warmup phase " + WARMUP_REPEAT + " executions");
        System.out.println("Averaging results over " + REPEAT + " executions");

        List<Supplier<BenchmarkBase>> algFactory = new ArrayList<>();
        algFactory.add(() -> new PrimBenchmark());
        algFactory.add(() -> new KruskalBenchmark());
        algFactory.add(() -> new BoruvkaBenchmark());

        for (Supplier<BenchmarkBase> alg : algFactory) {

            System.gc();
            StopWatch watch = new StopWatch();

            BenchmarkBase benchmark = alg.get();
            System.out.printf("%-30s :", benchmark.toString());

            for (int i = 0; i < WARMUP_REPEAT; i++) {
                System.out.print("-");
                benchmark.setup();
                benchmark.run();
            }
            double avgGraphCreate = 0d;
            double avgExecution = 0d;
            for (int i = 0; i < REPEAT; i++) {
                System.out.print("+");
                watch.start();
                benchmark.setup();
                avgGraphCreate += watch.getElapsed(TimeUnit.MILLISECONDS);
                watch.start();
                benchmark.run();
                avgExecution += watch.getElapsed(TimeUnit.MILLISECONDS);
            }
            avgGraphCreate /= REPEAT;
            avgExecution /= REPEAT;

            System.out.print(" -> ");
            System.out
                .printf("setup %.3f (ms) | execution %.3f (ms)\n", avgGraphCreate, avgExecution);
        }

    }

}
