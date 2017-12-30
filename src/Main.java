import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        String mapName = "Moscow";
        String mapAddress = "map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 5, 5);

        String clientAddress = "client.txt";
        int numberOfClients = 5;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        ArrayList<Client> visualClients = new ArrayList<>();
        visualClients.addAll(clients);

        String taxiAddress = "taxi.txt";
        int numberOfTaxi = 2;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);
        ArrayList<Taxi> visualTaxi = Controller.loadTaxi(taxiAddress);
        ArrayList<Path> pathsForCabs = new ArrayList<>();

        ArrayList<ArrayList<Client>> clientsForCabs = Controller.clientsToCabs(clients, numberOfTaxi);

        for (int k = 0; k < numberOfTaxi; k++) {
            Path path = new Path(mapOfCity, clientsForCabs.get(k), taxi.get(k));
            pathsForCabs.add(path);
            path.run();
        }
        Thread.sleep(1000);

        Visual visual = new Visual(mapOfCity, visualTaxi, visualClients);
        visual.DrawMap();
        Thread.sleep(1000);

        // i - number of taxi
        // j - number of clients which taxi[i] left
        // k - number of vertex in taxi path to client

        int max_j = 0;
        for (Path path : pathsForCabs) {
            if (max_j < path.paths.size()) {
                max_j = path.paths.size();
            }
        }

        System.out.println("max_j " + max_j);

        for (int j = 0; j < max_j; j++) {
            int max_k = 0;
            for (Path path : pathsForCabs) {
                if (j < path.paths.size()) {
                    if (max_k < path.paths.get(j).size()) {
                        max_k = path.paths.get(j).size();
                    }
                }
            }
            for (int k = 0; k < max_k; k++) {
                for (int i = 0; i < pathsForCabs.size(); i++) {
                    if (j < pathsForCabs.get(i).paths.size() && k < pathsForCabs.get(i).paths.get(j).size()) {
                        Path path = pathsForCabs.get(i);
                        path.changeLocation(visualTaxi.get(i), path.orderedClients.get(j), path.paths.get(j));
                        path.paths.get(j).remove(0);
                    }
                }
                Thread.sleep(600);
                visual.DrawMap();
            }
        }

//        for (int i = 0; i < x; i++) {
//            for (int j = 0; j < ; j++) {
//                for (int k = 0; k < pathsForCabs.size(); k++) {
//                    Path path = pathsForCabs.get(k);
//                    path.changeLocation(visualTaxi.get(k), path.orderedClients.get(j), path.paths.get(j));
//                    path.paths.get(j).remove(0);
//                }
//                visual.DrawMap();
//            }
        }

//        for (int k = 0; k < pathsForCabs.size(); k++) {
//            int finalK = k;
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                Path path = pathsForCabs.get(finalK);
//                for (int j = 0; j < path.paths.size(); j++) {
//                    int size = path.paths.get(j).size();
//                        for (int i = 0; i < size; i++) {
//                            try {
//                                Thread.sleep(400);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                            path.changeLocation(visualTaxi.get(finalK), path.orderedClients.get(j), path.paths.get(j));
//                            path.paths.get(j).remove(0);
//                            visual.DrawMap();
//                        }
//                    }
//                }
//            });
//            thread.run();
//        }
}