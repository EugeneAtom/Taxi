import java.io.IOException;
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

    public static ArrayList<Client> createAndSaveClients(MapOfCity mapOfCity, String address, int numberOfClients) throws IOException {
        ArrayList<Client> clients = mapOfCity.createClients(numberOfClients);
        Objects serializableClients = new Objects(clients);
        serializableClients.SaveToFile(address);
        return clients;
    }

    public static ArrayList<Client> loadClients(String address) throws IOException, ClassNotFoundException {
        Objects objects = new Objects();
        return objects.LoadFromFile(address);
    }

    public static ArrayList<Taxi> createAndSaveTaxi(MapOfCity mapOfCity, String address, int numberOfTaxists) throws IOException {
        ArrayList<Taxi> taxi = mapOfCity.createTaxi(numberOfTaxists);
        Objects serializableTaxi = new Objects(taxi);
        serializableTaxi.SaveToFile(address);
        return taxi;
    }

    public static ArrayList<Taxi> loadTaxi(String address) throws IOException, ClassNotFoundException {
        Objects objects = new Objects();
        return objects.LoadFromFile(address);
    }

    public static ArrayList<ArrayList<Client>> clientsToCabs(ArrayList<Client> clients, int numberOfCabs) {
        ArrayList<ArrayList<Client>> clientsForCabs = new ArrayList<>(numberOfCabs);
        for (int i = 0; i < numberOfCabs; i++) {
            ArrayList<Client> tmpClients = new ArrayList<>();
            int j = i;
            while (j < clients.size()) {
                tmpClients.add(clients.get(j));
                j += numberOfCabs;
            }
            clientsForCabs.add(tmpClients);
        }
        return clientsForCabs;
    }

    public static void oneCabMoveAllClients(MapOfCity mapOfCity, ArrayList<Taxi> taxi,
                                            ArrayList<Client> clients) throws IOException, InterruptedException {
        Visual visual = new Visual(mapOfCity, taxi, clients);
        visual.DrawMap();

        boolean firstTime=true;
        boolean isInCab = false;
        taxi.get(0).number=12463;
        clients.get(0).name="TestName";


        int counter = 1;
        for (int k = 0; k < taxi.size(); k++) {
            for (int j = 0; j < counter * 4 && j < clients.size(); j++) {
                Path path = new Path(mapOfCity, clients, taxi.get(0));
                ArrayList<String> pathString = path.createPath(clients.get(0), taxi.get(0));
                System.out.println("Такси номер" +  taxi.get(0).number + "приняло заказ от клиента " + clients.get(0).name );
                for (int i = 0; i < pathString.size(); i++) {
                    visual.DrawMap();
                    taxi.get(0).sourceVertex = pathString.get(i);

                    if (isInCab){
                        System.out.println("Пассажир " + clients.get(0).name + " едет в такси номер " + taxi.get(0).number);
                        clients.get(0).sourceVertex = taxi.get(0).sourceVertex;
                    }
                    Thread.sleep(300);

                    if(clients.get(0).sourceVertex.equals(taxi.get(0).sourceVertex)) {
                        if(firstTime)
                        {
                            System.out.println("Пассажир " + clients.get(0).name + " сел в такси номер " + taxi.get(0).number);
                            firstTime=false;
                        }
                        isInCab = true;

                    }
                    if (clients.get(0).sourceVertex.equals(clients.get(0).targetVertex)) {
                        System.out.println("Пассажир " +clients.get(0).name + " прибыл на Место");
                        visual.visClients.remove(0);
                        isInCab = false;
                    }
                }
            }
        }
    }
}