import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import java.io.*;
import java.util.*;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.ListenableDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


public class MapOfCity implements Serializable {
    public static Graph createMap(int numberOfVertexes) {

        // create weighted graph
        Graph<String, DefaultWeightedEdge> mapOfCity
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

    public static void SaveToFile(Graph mapOfCity, String adress) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(adress);
        ObjectOutputStream asd = new ObjectOutputStream(fos);
        asd.writeObject(mapOfCity);   //???????????
        asd.close();
    }

    public static Graph LoadFromFile(String adress) throws IOException, ClassNotFoundException
    {
        FileInputStream fis = new FileInputStream(adress);
        ObjectInputStream asd = new ObjectInputStream(fis);
        Graph obj = (Graph)asd.readObject();
        asd.close();
        return obj;
    }

    public static ArrayList<String> createClients(Graph graph, int numberOfClients) {
        ArrayList vertexesList = new ArrayList(graph.vertexSet());
        ArrayList<String> clientsList = new ArrayList();
        for (int i = 0; i < numberOfClients; i++) {
            int random_index = new Random().nextInt(vertexesList.size());
            clientsList.add((String) vertexesList.get(random_index));
            vertexesList.remove(random_index);
        }
        return clientsList;
    }

    public static ArrayList<String> createTaxi(Graph graph, int numberOfTaxi) {
        ArrayList vertexesList = new ArrayList(graph.vertexSet());
        ArrayList<String> clientsList = new ArrayList();
        for (int i = 0; i < numberOfTaxi; i++) {
            int random_index = new Random().nextInt(vertexesList.size());
            clientsList.add((String) vertexesList.get(random_index));
            vertexesList.remove(random_index);
        }
        return clientsList;
    }
}