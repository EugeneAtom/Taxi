import org.jgrapht.Graph;

import java.io.*;
import java.util.ArrayList;

public class Client implements Serializable {
    String name;
    String sourceVertex;
    String targetVertex;

    public Client(String name, String sourceVertex, String targetVertex) {
        this.name = name;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }

    @Override
    public String toString() {
        return "Client{" +
                " name='" + name + '\'' +
                ", sourceVertex='" + sourceVertex + '\'' +
                ", targetVertex='" + targetVertex + '\'' +
                '}';
    }

    public static void SaveToFile(Client client, String adress) throws IOException {
        FileOutputStream fos = new FileOutputStream(adress);
        ObjectOutputStream asd = new ObjectOutputStream(fos);
        asd.writeObject(client);
        asd.close();
    }

    public static Client LoadFromFile(String adress) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(adress);
        ObjectInputStream asd = new ObjectInputStream(fis);
        Client obj = (Client) asd.readObject();
        asd.close();
        return obj;
    }
}