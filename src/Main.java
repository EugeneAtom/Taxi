import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.GraphWalk;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        String mapName = "Moscow";
        String mapAddress = "/home/Eugene/Taxi/map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 6, 5);
        //MapOfCity mapOfCity = Controller.loadMap(mapAddress);
        System.out.println(mapOfCity);

        String clientAddress = "/home/Eugene/Taxi/client.txt";
        int numberOfClients = 10;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        //ArrayList<Client> clients = Controller.loadClients(clientAddress);
        System.out.println(clients);

        String taxiAddress = "/home/Eugene/Taxi/taxi.txt";
        int numberOfTaxi = 4;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);
        System.out.println(taxi);

        Path path = new Path(mapOfCity);
        System.out.println(mapOfCity.map.edgeSet());
        System.out.println(taxi.get(0).sourceVertex);
        ArrayList pathList = path.createPath(clients.get(0), taxi.get(0));
        System.out.println(pathList);
        System.out.println(taxi.get(0).sourceVertex);
    }
}