import org.jgrapht.Graph;

import java.util.ArrayList;


public class Controller {
    public static MapOfCity getMap(String name, int horizontal, int vertical) {
        MapOfCity map = new MapOfCity(name, horizontal, vertical);
        return map;
    }

    public static ArrayList<Client> getClients(MapOfCity mapOfCity, ArrayList<String> namesOfClients) {
        return mapOfCity.createClients(namesOfClients);
    }

    public static ArrayList<Taxi> getTaxi(MapOfCity mapOfCity, ArrayList<String> taxists) {
        return mapOfCity.createTaxi(taxists);
    }
}
