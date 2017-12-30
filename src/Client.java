import java.io.*;

public class Client implements Serializable {
    int id;
    String name;
    String sourceVertex;
    String targetVertex;
    boolean isWait = true;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", sourceVertex='" + sourceVertex + '\'' +
                ", targetVertex='" + targetVertex + '\'' +
                ", isWait=" + isWait +
                '}';
    }


    public Client(int id, String sourceVertex, String targetVertex) {
        this.id = id;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }

    public Client(String name, String sourceVertex, String targetVertex) {
        this.name = name;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
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