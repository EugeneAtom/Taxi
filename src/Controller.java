import java.io.IOException;
import java.util.ArrayList;


public class Controller {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String mapName = "Moscow";
        String mapAddress = "map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 40, 20);
        //MapOfCity mapOfCity = Controller.loadMap(mapAddress);

        String clientAddress = "client.txt";
        int numberOfClients = 10;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        //ArrayList<Client> clients = Controller.loadClients(clientAddress);

        String taxiAddress = "taxi.txt";
        int numberOfTaxi = 3;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);
        //ArrayList<Taxi> taxi = Controller.loadTaxi(taxiAddress);
        ArrayList<Taxi> visualTaxi = Controller.loadTaxi(taxiAddress);

        ArrayList<Path> pathsForCabs = new ArrayList<>();
        ArrayList<ArrayList<Client>> clientsForCabs = Controller.clientsToCabs(clients, numberOfTaxi);
        ArrayList<ArrayList<Integer>> arrayForSizes = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int k = 0; k < numberOfTaxi; k++) {
            Path path = new Path(mapOfCity, clientsForCabs.get(k), taxi.get(k));
            path.start();
            pathsForCabs.add(path);
        }

        for (Path path : pathsForCabs) {
            path.join();
            ArrayList<Integer> tmpArray = new ArrayList<>();
            for (ArrayList<String> stringPath : path.paths) {
                tmpArray.add(stringPath.size());
            }
            arrayForSizes.add(tmpArray);
        }


        long endTime   = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");


        Visual visual = new Visual(mapOfCity, visualTaxi, clients);
        visual.DrawMap();
        Thread.sleep(1000);

        // iterCab - iterate of number of taxi
        // iterClient - iterate of number of clients which taxi[i] left
        // iterVertex - iterate of number of vertex in taxi path to client

        int maxClientsForCab;
        if (numberOfClients % numberOfTaxi == 0) {
            maxClientsForCab = numberOfClients / numberOfTaxi;
        } else {
            maxClientsForCab = numberOfClients / numberOfTaxi + 1;
        }

        for (int iterClient = 0; iterClient < maxClientsForCab; iterClient++) {
            int maxVertex = 0;
            for (Path path : pathsForCabs) {
                if (iterClient < path.paths.size()) {
                    if (maxVertex < path.paths.get(iterClient).size()) {
                        maxVertex = path.paths.get(iterClient).size();
                    }
                }
            }

            for (int iterVertex = 0; iterVertex < maxVertex; iterVertex++) {

                for (int iterCab = 0; iterCab < pathsForCabs.size(); iterCab++) {
                    if (iterClient < pathsForCabs.get(iterCab).paths.size()
                            && iterVertex < arrayForSizes.get(iterCab).get(iterClient)) {
                        Path path = pathsForCabs.get(iterCab);
                        path.changeLocation(visualTaxi.get(iterCab), path.orderedClients.get(iterClient), path.paths.get(iterClient));
                        path.paths.get(iterClient).remove(0);
                    }
                }
                Thread.sleep(400);
                visual.DrawMap();
            }
        }
    }

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