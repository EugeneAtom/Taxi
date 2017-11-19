import org.jgrapht.graph.SimpleWeightedGraph;

public class Main {
    public static void main(String [] args){
        SimpleWeightedGraph mapOfCity = MapOfCity.createMap(10);
        System.out.println(mapOfCity);
    }
}
