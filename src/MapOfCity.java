import java.io.*;
import java.util.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


public class MapOfCity implements Serializable {

    String name;
    int horizontalVertices;
    int verticalVertices;
    int allVertices;
    Graph map;

    public MapOfCity(String name, int horizontalVertices, int verticalVertices) {
        this.name = name;
        this.horizontalVertices = horizontalVertices;
        this.verticalVertices = verticalVertices;
        this.allVertices = horizontalVertices * verticalVertices;
        this.map = createMap(horizontalVertices, verticalVertices);
    }

    @Override
    public String toString() {
        return "MapOfCity{" +
                "name='" + name + '\'' +
                ", horizontalVertices=" + horizontalVertices +
                ", verticalVertices=" + verticalVertices +
                ", allVertices=" + allVertices +
                ", map=" + map +
                '}';
    }

    public ArrayList<Integer> loop(int begin, int end) {
        ArrayList<Integer> array = new ArrayList<Integer>(end - begin);
        for (int i = begin; i < end; i++) {
            array.add(i);
        }
        return array;
    }

    public Graph createMap(int horizontalVertices, int verticalVertices) {

        // create weighted graph
        Graph<String, DefaultWeightedEdge> mapOfCity = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        // generate Vertices
        for (int i = 0; i < allVertices; i++) {
            mapOfCity.addVertex("v" + i);
        }

        // generate random edges
        int begin = 0;
        int end = horizontalVertices;
        Random rand = new Random();
        for (int i = 0; i < verticalVertices; i++) {
            ArrayList<Integer> listOfEdgesHorizontal = loop(begin, end);
            ArrayList<Integer> listOfEdgesVertical = loop(begin, end);
            int cohesion = listOfEdgesHorizontal.size() / 5 * 4;
            for (int j = 0; j < cohesion; j++) {
                int randIndexHorizontal = rand.nextInt(listOfEdgesHorizontal.size() - 1);
                int randElementHorizontal = listOfEdgesHorizontal.get(randIndexHorizontal);
                int randIndexVertical = rand.nextInt(listOfEdgesVertical.size());
                int randElementVertical = listOfEdgesVertical.get(randIndexVertical);

                String sourceVertexHorizontal = "v" + randElementHorizontal;
                int nextHorizontal = randElementHorizontal + 1;

                String sourceVertexVertical = "v" + randElementVertical;
                int nextVertical = randElementVertical + horizontalVertices;

                String targetVertexHorizontal = "v" + nextHorizontal;
                String targetVertexVertical = "v" + nextVertical;

                DefaultWeightedEdge horizontal = mapOfCity.addEdge(sourceVertexHorizontal, targetVertexHorizontal);
                mapOfCity.setEdgeWeight(horizontal, rand.nextInt(allVertices / 10) + 5);
                listOfEdgesHorizontal.remove(randIndexHorizontal);

                if (nextVertical < allVertices) {
                    DefaultWeightedEdge vertical = mapOfCity.addEdge(sourceVertexVertical, targetVertexVertical);
                    mapOfCity.setEdgeWeight(vertical, rand.nextInt(allVertices / 10) + 5);
                    listOfEdgesVertical.remove(randIndexVertical);
                }
            }
            begin = end;
            end = begin + horizontalVertices;
        }

        return mapOfCity;
    }

    public static void SaveToFile(MapOfCity mapOfCity, String adress) throws IOException {
        FileOutputStream fos = new FileOutputStream(adress);
        ObjectOutputStream asd = new ObjectOutputStream(fos);
        asd.writeObject(mapOfCity);
        asd.close();
    }

    public static MapOfCity LoadFromFile(String adress) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(adress);
        ObjectInputStream asd = new ObjectInputStream(fis);
        MapOfCity obj = (MapOfCity) asd.readObject();
        asd.close();
        return obj;
    }

    public ArrayList<Client> createClients(int numberOfClients) {
        ArrayList VerticesList = new ArrayList(map.vertexSet());
        ArrayList<Client> clients = new ArrayList();

        for (int i = 0; i < numberOfClients; i++) {

            int intSource = new Random().nextInt(VerticesList.size());
            String source = (String) VerticesList.get(intSource);

            int intTarget = new Random().nextInt(VerticesList.size());
            while (intTarget == intSource) {
                intTarget = new Random().nextInt(VerticesList.size());
            }
            String target = (String) VerticesList.get(intTarget);

            Client client = new Client(i, source, target);

            clients.add(client);
        }

        return clients;
    }

    public ArrayList<Taxi> createTaxi(int numberOfTaxi) {
        ArrayList VerticesList = new ArrayList(map.vertexSet());
        ArrayList<Taxi> taxiList = new ArrayList();
        for (int i = 0; i < numberOfTaxi; i++) {
            int intSource = new Random().nextInt(VerticesList.size());
            String source = (String) VerticesList.get(intSource);
            Taxi taxi = new Taxi(i, source);
            taxiList.add(taxi);
            VerticesList.remove(intSource);
        }
        return taxiList;
    }
}