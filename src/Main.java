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
        //MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 30, 50);
        MapOfCity mapOfCity = Controller.loadMap(mapAddress);
        System.out.println(mapOfCity);

        String clientAddress = "/home/Eugene/Taxi/client.txt";
        ArrayList<String> namesOfClients = new ArrayList<>(Arrays.asList("Ar'chill", "Dimitrii"));
        //ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, namesOfClients);
        ArrayList<Client> clients = Controller.loadClients(clientAddress);
        System.out.println(clients);

        String taxiAddress = "/home/Eugene/Taxi/taxi.txt";
        ArrayList<String> taxists = new ArrayList<>(Arrays.asList("A701BC", "X702YT"));
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, taxists);
        System.out.println(taxi);

        Path path = new Path(mapOfCity);
        System.out.println(path.createPath(clients.get(0), taxi.get(0)));
    }
}