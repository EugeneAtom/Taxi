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
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        String mapName = "Moscow";
        String mapAddress = "/home/Eugene/Taxi/map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 40, 20);
        //MapOfCity mapOfCity = Controller.loadMap(mapAddress);
        //System.out.println(mapOfCity);

        String clientAddress = "/home/Eugene/Taxi/client.txt";
        int numberOfClients = 12;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        //ArrayList<Client> clients = Controller.loadClients(clientAddress);
        //System.out.println(clients);

        String taxiAddress = "/home/Eugene/Taxi/taxi.txt";
        int numberOfTaxi = 4;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);

        Visual visual = new Visual(mapOfCity, taxi, clients);
        visual.DrawMap();
        Thread.sleep(3000);

        int counter = 1;
        for (int k = 0; k < taxi.size(); k++) {
            for (int j = 0; j < counter * 4 && j < clients.size(); j++) {
                Path path = new Path(mapOfCity, clients, taxi.get(k));
                ArrayList<String> pathString = path.createPath(clients.get(j), taxi.get(k));
                for (int i = 0; i < pathString.size(); i++) {
                    visual.DrawMap();
                    taxi.get(0).sourceVertex = pathString.get(i);
                    Thread.sleep(400);
                    if (clients.get(j).sourceVertex.equals(taxi.get(k).sourceVertex)) {
                        visual.visClients.remove(0);
                    }
                }
            }
        }

        //System.out.println(taxi);

        }
        //Path path = new Path(mapOfCity, clients, taxi.get(0));
        //ArrayList<String> pathString = path.createPath(clients.get(0), taxi.get(0));
        //int taxiNumber = 0;
        //for (ArrayList path : paths) {
        //    for (int i = 0; i < path.size(); i++){
        //        visual.DrawMap();
        //        System.out.println(path.get(0) + "!!!!!!!!!!!!!!!!!!!!!!!!");
        //        taxi.get(taxiNumber).sourceVertex = (String) path.get(0);
        //        Thread.sleep(400);
        //        if (clients.get(i).sourceVertex.equals(taxi.get(taxiNumber).sourceVertex)) {
        //            visual.visClients.remove(i);
        //        }
        //    }
        //    taxiNumber++;
        //}



        //System.out.println(mapOfCity.map.edgeSet());
        //for (Client client : clients) {
        //    System.out.println(client.isWait);
        //}

        //System.out.println(taxi.get(0).sourceVertex);
        //ArrayList pathList = path.createPath(clients.get(0), taxi.get(0));
        //System.out.println(pathList);
        //System.out.println(taxi.get(0).sourceVertex);
}