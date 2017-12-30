import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        String mapName = "Moscow";
        String mapAddress = "map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 40, 20);

        String clientAddress = "client.txt";
        int numberOfClients = 20;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        ArrayList<Client> visualClients = new ArrayList<>();
        visualClients.addAll(clients);

        String taxiAddress = "taxi.txt";
        int numberOfTaxi = 5;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);
        ArrayList<Taxi> visualTaxi = Controller.loadTaxi(taxiAddress);
        ArrayList<Path> pathsForCabs = new ArrayList<>();

        ArrayList<ArrayList<Client>> clientsForCabs = Controller.clientsToCabs(clients, numberOfTaxi);

        for (int k = 0; k < numberOfTaxi; k++) {
            Path path = new Path(mapOfCity, clientsForCabs.get(k), taxi.get(k));
            pathsForCabs.add(path);
            path.run();
            Thread.sleep(1000);
        }

        Thread.sleep(5000);

        Visual visual = new Visual(mapOfCity, visualTaxi, visualClients);
        visual.DrawMap();
        Thread.sleep(400);

        for (int k = 0; k < pathsForCabs.size(); k++) {
            int finalK = k;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                Path path = pathsForCabs.get(finalK);
                for (int j = 0; j < path.paths.size(); j++) {
                    int size = path.paths.get(j).size();
                        for (int i = 0; i < size; i++) {
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            path.changeLocation(visualTaxi.get(finalK), path.orderedClients.get(j), path.paths.get(j));
                            path.paths.get(j).remove(0);
                            visual.DrawMap();
                        }
                    }
                }
            });
            thread.run();
        }
    }
}