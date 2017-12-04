import org.jgrapht.Graph;

import java.util.ArrayList;


public class Controller {
    public static MapOfCity getMap(String name, int vertices) {
        MapOfCity map = new MapOfCity(name, vertices);
        return map;
    }

    public static ArrayList<Client> getClients(Graph graph, ArrayList<String> namesOfClients) {
        return MapOfCity.createClients(graph, namesOfClients);
    }

    public static ArrayList<Taxi> getTaxi(Graph graph, ArrayList<String> taxists) {
        return MapOfCity.createTaxi(graph, taxists);
    }
}
