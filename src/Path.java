import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;

public class Path {
    MapOfCity mapOfCity;

    public Path(MapOfCity mapOfCity) {
        this.mapOfCity = mapOfCity;
    }

    public String createPath(Client client, Taxi taxi) {
        String clientSource = client.sourceVertex;
        String clientTarget = client.targetVertex;
        String taxiSource = taxi.sourceVertex;
        String taxiTarget = clientSource;

        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity.map, mapOfCity.map.vertexSet());
        String path;

        if (taxiSource != clientSource) {
            AStarShortestPath taxiSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            path = (taxiSourceTargetPath.getPath(taxiSource, taxiTarget)).toString() +
                    (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }
        else {
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity.map, heuristic);
            path = (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }

        return path;
    }
}