import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.GraphWalk;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        // MapOfCity.createMap(10);
        //  MapOfCity.SaveToFile(mapOfCity, "/home/Eugene/Taxi/first_map.txt");
        // mapOfCity = MapOfCity.LoadFromFile(address);

        String address = "/home/Eugene/Taxi/first_map.txt";
        Graph mapOfCity = MapOfCity.LoadFromFile(address);
        System.out.println(mapOfCity);
        System.out.println("Shortest path by A* algorithm:");
        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity, mapOfCity.vertexSet());
        AStarShortestPath path = new AStarShortestPath(mapOfCity, heuristic);
        String shortestPath = (path.getPath("v1", "v5")).toString();
        System.out.println(shortestPath);

        System.out.println(MapOfCity.createClients(mapOfCity, 4));
        System.out.println(MapOfCity.createTaxi(mapOfCity, 4));
    }
}
