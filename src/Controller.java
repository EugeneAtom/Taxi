import org.jgrapht.Graph;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;


public class Controller {

    public static MapOfCity createAndSaveMap(String mapAdress, String name, int horizontal, int vertical) throws IOException {
        MapOfCity map = new MapOfCity(name, horizontal, vertical);
        MapOfCity.SaveToFile(map, mapAdress);
        return map;
    }

    public static MapOfCity loadMap(String mapAddress) throws IOException, ClassNotFoundException {
        MapOfCity mapOfCity = MapOfCity.LoadFromFile(mapAddress);
        return mapOfCity;
    }

    public static ArrayList<Client> createAndSaveClients(MapOfCity mapOfCity, String address, ArrayList<String> namesOfClients) throws IOException {
        ArrayList<Client> clients = mapOfCity.createClients(namesOfClients);
        Objects serializableClients = new Objects(clients);
        serializableClients.SaveToFile(address);
        return clients;
    }

    public static ArrayList<Client> loadClients(String address) throws IOException, ClassNotFoundException {
        Objects objects = new Objects();
        return objects.LoadFromFile(address);
    }

    public static ArrayList<Taxi> createAndSaveTaxi(MapOfCity mapOfCity, String address, ArrayList<String> taxists) throws IOException {
        ArrayList<Taxi> taxi = mapOfCity.createTaxi(taxists);
        Objects serializableTaxi = new Objects(taxi);
        serializableTaxi.SaveToFile(address);
        return taxi;
    }

    public static ArrayList<Taxi> loadTaxi(String address) throws IOException, ClassNotFoundException {
        Objects objects = new Objects();
        return objects.LoadFromFile(address);
    }
}