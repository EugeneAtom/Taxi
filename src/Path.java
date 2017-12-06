import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;

public class Path {
    MapOfCity mapOfCity;

    public Path(MapOfCity mapOfCity) {
        this.mapOfCity = mapOfCity;
    }

    public String createPath(Client client, Taxi taxi) {
        taxi.isFree = false;
        client.isWait = false;
        String clientSource = client.sourceVertex;
        String clientTarget = client.targetVertex;
        String taxiSource = taxi.sourceVertex;
        String taxiTarget = clientSource;

        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity.map, mapOfCity.map.vertexSet());
        String path;

        if (taxiSource != clientSource) {
            AStarShortestPath taxiSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            path = "Taxi to client: " + (taxiSourceTargetPath.getPath(taxiSource, taxiTarget)).toString() +
                    "  Client from source to target: " + (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }
        else {
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            path = (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }

        taxi.isFree = true;
        return path;
    }
}