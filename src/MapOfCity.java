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

    String name;
    int horizontalVertices;
    int verticalVertices;
    int allVertices;

    public MapOfCity(String name, int horizontalVertices, int verticalVertices) {
        this.name = name;
        this.horizontalVertices = horizontalVertices;
        this.verticalVertices = verticalVertices;
        this.allVertices = horizontalVertices * verticalVertices;
        Graph map = createMap(horizontalVertices, verticalVertices);
    }

    public ArrayList<Integer> loop(int begin, int end) {
        ArrayList<Integer> array = new ArrayList<Integer>(end - begin);
        for (int i = begin; i < end; i++) {
            array.add(i);
        }
        return array;
    }

    public Graph createMap(int horizontalVertices, int verticalVertices) {

        // create weighted graph
        Graph<String, DefaultWeightedEdge> mapOfCity
                = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // generate Vertices
        for (int i = 0; i < allVertices; i++) {
            mapOfCity.addVertex("v" + i);
        }

        // generate random edges
        int begin = 0;
        int end = horizontalVertices;
        Random rand = new Random();
        for (int i = 0; i < verticalVertices; i++) {
            ArrayList<Integer> listOfEdgesHorizontal = loop(begin, end);
            ArrayList<Integer> listOfEdgesVertical = loop(begin, end);
            int cohesion = listOfEdgesHorizontal.size() / 4 * 3;
            for (int j = 0; j < cohesion; j++) {
                System.out.println(listOfEdgesHorizontal);
                System.out.println(listOfEdgesVertical);

                int randIndexHorizontal = rand.nextInt(listOfEdgesHorizontal.size() - 1);
                int randElementHorizontal = listOfEdgesHorizontal.get(randIndexHorizontal);
                int randIndexVertical = rand.nextInt(listOfEdgesVertical.size());
                int randElementVertical = listOfEdgesVertical.get(randIndexVertical);

                String sourceVertexHorizontal = "v" + randElementHorizontal;
                int nextHorizontal = randElementHorizontal + 1;

                String sourceVertexVertical = "v" + randElementVertical;
                int nextVertical = randElementVertical + horizontalVertices;

                String targetVertexHorizontal = "v" + nextHorizontal;
                String targetVertexVertical = "v" + nextVertical;

                DefaultWeightedEdge horizontal = mapOfCity.addEdge(sourceVertexHorizontal, targetVertexHorizontal);
                mapOfCity.setEdgeWeight(horizontal, rand.nextInt(allVertices / 10) + 5);
                System.out.println(sourceVertexHorizontal + " " + targetVertexHorizontal);
                listOfEdgesHorizontal.remove(randIndexHorizontal);

                if (nextVertical < allVertices) {
                    DefaultWeightedEdge vertical = mapOfCity.addEdge(sourceVertexVertical, targetVertexVertical);
                    System.out.println(sourceVertexVertical + " " + targetVertexVertical);
                    mapOfCity.setEdgeWeight(vertical, rand.nextInt(allVertices / 10) + 5);
                    listOfEdgesVertical.remove(randIndexVertical);
                }
            }
            begin = end;
            end = begin + horizontalVertices;
        }


        //Random rand = new Random();
        //for (int i = 1; i <= numberOfVertices; i++) {
        //    for (int j = 0; j < 2; j++) {
          //      int numberOfVertex = rand.nextInt(numberOfVertices) + 1;
            //    String targetVertex = "v" + numberOfVertex;
              //  if (numberOfVertex != i && !mapOfCity.containsEdge("v" + i, targetVertex)) {
                //    DefaultWeightedEdge e = mapOfCity.addEdge("v" + i, targetVertex);
                  //  mapOfCity.setEdgeWeight(e, rand.nextInt(numberOfVertices) + 10);
                //}
            //}
        //}

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
        ArrayList<Client> clients = new ArrayList();

        for (String nameOfClient : namesOfClients) {

            int intSource = new Random().nextInt(VerticesList.size());
            String source = (String) VerticesList.get(intSource);

            int intTarget = new Random().nextInt(VerticesList.size());
            while (intTarget == intSource) {
                intTarget = new Random().nextInt(VerticesList.size());
            }
            String target = (String) VerticesList.get(intTarget);

            Client client = new Client(nameOfClient, source, target);

            clients.add(client);
        }

        return clients;
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