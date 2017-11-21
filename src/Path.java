import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;

public class Path {
    Graph mapOfCity;

    public Path(Graph mapOfCity) {
        this.mapOfCity = mapOfCity;
    }

    public String createPath(Client client, Taxi taxi) {
        String clientSource = client.sourceVertex;
        String clientTarget = client.targetVertex;
        String taxiSource = taxi.sourceVertex;
        String taxiTarget = clientSource;

        ALTAdmissibleHeuristic heuristic = new ALTAdmissibleHeuristic(mapOfCity, mapOfCity.vertexSet());
        String path;

        if (taxiSource != clientSource) {
            AStarShortestPath taxiSourceTargetPath = new AStarShortestPath(mapOfCity, heuristic);
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity, heuristic);
            path = (taxiSourceTargetPath.getPath(taxiSource, taxiTarget)).toString() +
                    (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }
        else {
            AStarShortestPath clientSourceTargetPath = new AStarShortestPath(mapOfCity, heuristic);
            path = (clientSourceTargetPath.getPath(clientSource, clientTarget)).toString();
        }

        return path;
    }
}
