import org.apache.commons.lang3.ArrayUtils;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path implements Runnable {
    MapOfCity mapOfCity;
    ArrayList<Client> clients;
    Taxi taxi;
    public volatile ArrayList paths;


    public Path(MapOfCity mapOfCity, ArrayList<Client> clients, Taxi taxi) {
        this.mapOfCity = mapOfCity;
        this.clients = clients;
        this.taxi = taxi;
        this.paths = new ArrayList();
    }

    public Path(MapOfCity mapOfCity) {
        this.mapOfCity = mapOfCity;
    }

    public ArrayList<String> createPath(Client client, Taxi taxi) {
        System.out.println("client is: " + client);
        taxi.isFree = false;
        client.isWait = false;
        String clientSource = client.sourceVertex;
        String clientTarget = client.targetVertex;
        String taxiSource = taxi.sourceVertex;
        String taxiTarget = clientSource;

        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity.map, mapOfCity.map.vertexSet());

        ArrayList<String> path;
        if (taxiSource != clientSource) {
            AStarShortestPath taxiSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            String beginString = (taxiSourceTargetPath.getPath(taxiSource, taxiTarget)).toString();
            beginString = beginString.substring(1, beginString.length() - 1);
            String[] begin = beginString.split(", ");

            String endString = (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
            endString = endString.substring(1, endString.length() - 1);
            String[] end = endString.split(", ");
            String[] both = ArrayUtils.addAll(begin, end);
            path = new ArrayList<>(Arrays.asList(both));
        }
        else {
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            String pathString = clientSourceTargetPath.getPath(clientSource, clientTarget).toString();
            pathString = pathString.substring(1, pathString.length() - 1);
            path = new ArrayList<>(Arrays.asList((pathString.split(", "))));
        }
        taxi.sourceVertex = client.targetVertex;
        taxi.isFree = true;
        return path;
    }

    @Override
    public void run() {
        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity.map, mapOfCity.map.vertexSet());
        AStarShortestPath shortestPath = new AStarShortestPath(mapOfCity.map, heuristic);

        while (clients.size() > 0) {
            double shortestDistance = Double.POSITIVE_INFINITY;
            Client luckyClient = null;
            int index = 0;
            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);
                double distance = shortestPath.getPathWeight(taxi.sourceVertex, client.sourceVertex);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    luckyClient = client;
                    index = i;
                }
            }
            ArrayList<String> pathList = createPath(luckyClient, taxi);
            clients.remove(index);
            paths.add(pathList);
        }
    }
}