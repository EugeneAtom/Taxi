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
        String mapAddress = "/home/Eugene/Taxi/map.txt";
        Graph mapOfCity = MapOfCity.createMap(20);
        MapOfCity.SaveToFile(mapOfCity, mapAddress);
        mapOfCity = MapOfCity.LoadFromFile(mapAddress);
        System.out.println(mapOfCity);

        String clientAddress = "/home/Eugene/Taxi/client.txt";
        ArrayList<String> namesOfClients = new ArrayList<>(Arrays.asList("Ar'chill", "Dimitrii"));
        ArrayList<Client> clients = MapOfCity.createClients(mapOfCity, namesOfClients);
        Client.SaveToFile(clients.get(0), clientAddress);
        Client client = Client.LoadFromFile(clientAddress);
        System.out.println(client);

        String taxiAddress = "/home/Eugene/Taxi/taxi.txt";
        ArrayList<String> taxists = new ArrayList<>(Arrays.asList("A701BC", "X702YT"));
        ArrayList<Taxi> taxis = MapOfCity.createTaxi(mapOfCity, taxists);
        Taxi.SaveToFile(taxis.get(0), taxiAddress);
        Taxi taxi = Taxi.LoadFromFile(taxiAddress);
        System.out.println(taxi);

        Path path = new Path(mapOfCity);
        System.out.println(path.createPath(client, taxi));
    }
}
