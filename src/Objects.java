import java.io.*;
import java.util.ArrayList;

public class Objects<T> implements Serializable{
    ArrayList<T> objects;

    public Objects(ArrayList<T> objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "Objects{" +
                "objects=" + objects +
                '}';
    }

    public static void SaveToFile(Objects objects, String adress) throws IOException {
        FileOutputStream fos = new FileOutputStream(adress);
        ObjectOutputStream asd = new ObjectOutputStream(fos);
        asd.writeObject(objects);
        asd.close();
    }

    public static Objects LoadFromFile(String adress) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(adress);
        ObjectInputStream asd = new ObjectInputStream(fis);
        Objects obj = (Objects) asd.readObject();
        asd.close();
        return obj;
    }
}
