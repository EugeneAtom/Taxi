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
    public static Graph createMap(int numberOfVertices) {

        // create weighted graph
        Graph<String, DefaultWeightedEdge> mapOfCity
                = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // generate Vertices
        for (int i = 1; i <= numberOfVertices; i++) {
            mapOfCity.addVertex("v" + i);
        }

        // generate random edges
        Random rand = new Random();
        for (int i = 1; i <= numberOfVertices; i++) {
            for (int j = 0; j < 2; j++) {
                int numberOfVertex = rand.nextInt(numberOfVertices) + 1;
                String targetVertex = "v" + numberOfVertex;
                if (numberOfVertex != i && !mapOfCity.containsEdge("v" + i, targetVertex)) {
                    DefaultWeightedEdge e = mapOfCity.addEdge("v" + i, targetVertex);
                    mapOfCity.setEdgeWeight(e, rand.nextInt(numberOfVertices) + 10);
                }
            }
        }

        return mapOfCity;
    }

    public static void SaveToFile(Graph mapOfCity, String adress) throws IOException {
        FileOutputStream fos = new FileOutputStream(adress);
        ObjectOutputStream asd = new ObjectOutputStream(fos);
        asd.writeObject(mapOfCity);
        asd.close();
    }

    public static Graph LoadFromFile(String adress) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(adress);
        ObjectInputStream asd = new ObjectInputStream(fis);
        Graph obj = (Graph) asd.readObject();
        asd.close();
        return obj;
    }

    public static ArrayList<Client> createClients(Graph graph, ArrayList<String> namesOfClients) {
        ArrayList VerticesList = new ArrayList(graph.vertexSet());
        ArrayList<Client> clientsList = new ArrayList();

        for (String nameOfClient : namesOfClients) {

            int intSource = new Random().nextInt(VerticesList.size());
            String source = (String) VerticesList.get(intSource);

            int intTarget = new Random().nextInt(VerticesList.size());
            while (intTarget == intSource) {
                intTarget = new Random().nextInt(VerticesList.size());
            }
            String target = (String) VerticesList.get(intTarget);

            Client client = new Client(nameOfClient, source, target);

            clientsList.add(client);
        }

        return clientsList;
    }

    public static ArrayList<Taxi> createTaxi(Graph graph, ArrayList<String> taxists) {
        ArrayList VerticesList = new ArrayList(graph.vertexSet());
        ArrayList<Taxi> taxiList = new ArrayList();
        for (int i = 0; i < taxists.size(); i++) {
            int intSource = new Random().nextInt(VerticesList.size());
            String source = (String) VerticesList.get(intSource);
            Taxi taxi = new Taxi(taxists.get(i), source);
            taxiList.add(taxi);
            VerticesList.remove(intSource);
        }
        return taxiList;
    }
}