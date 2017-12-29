import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.AStarAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.ALTAdmissibleHeuristic;
import org.jgrapht.alg.shortestpath.AStarShortestPath;
import org.jgrapht.event.VertexSetListener;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.GraphWalk;

import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String [] args) throws IOException, ClassNotFoundException, InterruptedException {
        String mapName = "Moscow";
        //String mapAddress = "/home/Eugene/Taxi/map" + mapName + ".txt";
        String mapAddress = "map" + mapName + ".txt";
        MapOfCity mapOfCity = Controller.createAndSaveMap(mapAddress, mapName, 40, 20);
        //MapOfCity mapOfCity = Controller.loadMap(mapAddress);
        //System.out.println(mapOfCity);

        //String clientAddress = "/home/Eugene/Taxi/client.txt";
        String clientAddress = "client.txt";
        int numberOfClients = 12;
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, numberOfClients);
        //ArrayList<Client> clients = Controller.loadClients(clientAddress);
        //System.out.println(clients);

        ///String taxiAddress = "/home/Eugene/Taxi/taxi.txt";
        String taxiAddress = "taxi.txt";
        int numberOfTaxi = 4;
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, taxiAddress, numberOfTaxi);

        Visual visual = new Visual(mapOfCity, taxi, clients);
        visual.DrawMap();
        //Thread.sleep(3000);
        boolean firstTime=true;
        boolean isInCab = false;
        taxi.get(0).number=12463;
        clients.get(0).name="TestName";

        /**
         * One CAB LIFT EVERYONE
         */
      /*  int counter = 1;
        //for (int k = 0; k < taxi.size(); k++) {
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
                    Thread.sleep(100);

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
        //}
*/

        int ind=0;
        int[] clientInd = new int[taxi.size()];
        boolean isSysRun = true;
        Path myPath = new Path(mapOfCity,clients,taxi.get(0));
        ArrayList<ArrayList<String>> taxiStringPathes = new ArrayList<>(0);
        int[] taxCounter = new int[taxi.size()];
        ArrayList<String> curPath= new ArrayList<>(0);
        while (isSysRun) {
            //Drawing
            visual.DrawMap();
            Thread.sleep(250);

            //building Pathes
            for (int i = 0; i < taxi.size(); i++){
                if (taxi.get(i).isFree) {
                    for(int j =0; j <clients.size();j++)
                        if(clients.get(j).isWait) {
                            curPath = myPath.createPath(clients.get(j), taxi.get(i));
                            clientInd[i]=j;
                            //clients.get(j).isWait=false;
                           j=clients.size();
                        }
                    taxiStringPathes.add(curPath);
                    taxCounter[i] = 0;
                    System.out.println("Такси номер " + taxi.get(i).number + "выехало за клиентом " + clients.get(clientInd[i]).name);

                }
            }
            //One Step Moving
            for (int i = 0; i < taxi.size(); i++) {
                if (!taxi.get(i).isFree){
                    taxi.get(i).sourceVertex = taxiStringPathes.get(i).get(taxCounter[i]);
                taxCounter[i]++;

                //reach Finale Point
                /*if (taxi.get(i).sourceVertex.equals((taxiStringPathes.get(i)).get(taxiStringPathes.get(i).size() - 1))) {
                    //rm CLient
                    clients.remove(clientInd[i]);
                    //recount
                    for (int k = 0; k < clientInd[i]; k++)
                        if (clientInd[k] > clientInd[i])
                            clientInd[k]--;
                    clientInd[i] = 0;
                }*/


                taxi.get(i).isFree = true;
                (taxiStringPathes.get(i)).clear();
                }
            }
        }

    }




        //System.out.println(taxi);

        }
        //Path path = new Path(mapOfCity, clients, taxi.get(0));
        //ArrayList<String> pathString = path.createPath(clients.get(0), taxi.get(0));
        //int taxiNumber = 0;
        //for (ArrayList path : paths) {
        //    for (int i = 0; i < path.size(); i++){
        //        visual.DrawMap();
        //        System.out.println(path.get(0) + "!!!!!!!!!!!!!!!!!!!!!!!!");
        //        taxi.get(taxiNumber).sourceVertex = (String) path.get(0);
        //        Thread.sleep(400);
        //        if (clients.get(i).sourceVertex.equals(taxi.get(taxiNumber).sourceVertex)) {
        //            visual.visClients.remove(i);
        //        }
        //    }
        //    taxiNumber++;
        //}



        //System.out.println(mapOfCity.map.edgeSet());
        //for (Client client : clients) {
        //    System.out.println(client.isWait);
        //}

        //System.out.println(taxi.get(0).sourceVertex);
        //ArrayList pathList = path.createPath(clients.get(0), taxi.get(0));
        //System.out.println(pathList);
        //System.out.println(taxi.get(0).sourceVertex);