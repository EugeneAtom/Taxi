import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.Random;


public class MapOfCity {
    public SimpleWeightedGraph createMap(int numberOfVertexes) {

        // create weighted graph
        SimpleWeightedGraph<String, DefaultWeightedEdge> mapOfCity
                = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // generate vertexes
        for (int i = 1; i <= numberOfVertexes; i++) {
            mapOfCity.addVertex("v" + i);
        }

        // generate random edges
        Random rand = new Random();
        for (int i = 1; i <= numberOfVertexes; i++) {
            for (int j = 0; j <= numberOfVertexes / 4; j++) {
                int numberOfVertex = rand.nextInt(numberOfVertexes) + 1;
                String targetVertex = "v" + numberOfVertex;
                if (numberOfVertex != i && !mapOfCity.containsEdge("v" + i, targetVertex)) {
                    DefaultWeightedEdge e = mapOfCity.addEdge("v" + i, targetVertex);
                    mapOfCity.setEdgeWeight(e, rand.nextInt(numberOfVertexes) + 10);
                }
            }
        }

        return mapOfCity;
    }
}