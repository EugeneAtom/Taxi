import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final String RELATIVE_PATH = "/first_map.txt";
    public static void main(String [] args) throws IOException, ClassNotFoundException {
        // MapOfCity.createMap(10);
        //  MapOfCity.SaveToFile(mapOfCity, "/home/Eugene/Taxi/first_map.txt");
        // mapOfCity = MapOfCity.LoadFromFile(address);

        String filePath = new File("").getAbsolutePath() + RELATIVE_PATH;
        System.out.println(filePath);
        Graph mapOfCity = MapOfCity.LoadFromFile(filePath);
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
