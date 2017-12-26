import java.io.*;
import java.util.ArrayList;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Xml {

    public static void SaveToFile(String mapOfCity, String adress) throws IOException {
        PrintWriter writer = new PrintWriter(adress);
        writer.println(mapOfCity);
        writer.close();
    }

    public static void SaveMapXML(MapOfCity map) throws IOException {
        XStream xstream = new XStream(new StaxDriver());

    //Object to XML Conversion
        String xml = xstream.toXML(map);
        SaveToFile(xml, "/home/dmitriy/IdeaProjects/Taxi/MapXML.xml");

    //XML to Object Conversion
    //    MapOfCity map1 = (MapOfCity) xstream.fromXML(xml);

    }

    public static MapOfCity LoadMapFromXML(){
        MapOfCity map = new MapOfCity("MapXML",1,1);
        XStream xstream = new XStream(new StaxDriver());
        File file = new File("/home/dmitriy/IdeaProjects/Taxi/MapXML.xml");
        map = (MapOfCity) xstream.fromXML(file);
        return map;
    }

    public static void SaveTaxiXML(ArrayList<Taxi> taxi) throws  IOException{
        XStream xstream = new XStream(new StaxDriver());

        String xml = xstream.toXML(taxi);
        SaveToFile(xml,"/home/dmitriy/IdeaProjects/Taxi/Taxi.xml");
    }

    public static ArrayList<Taxi> LoadTaxiFromXML(){
        ArrayList<Taxi> taxi = new ArrayList();
        XStream xstream = new XStream(new StaxDriver());
        File file = new File("/home/dmitriy/IdeaProjects/Taxi/Taxi.xml");
        taxi = (ArrayList<Taxi>) xstream.fromXML(file);
        return taxi;
    }

    public static void SaveClientsXML(ArrayList<Client> clients) throws  IOException{
        XStream xstream = new XStream(new StaxDriver());

        String xml = xstream.toXML(clients);
        SaveToFile(xml,"/home/dmitriy/IdeaProjects/Taxi/Clients.xml");
    }

    public static ArrayList<Client> LoadClientsFromXML(){
        ArrayList<Client> clients = new ArrayList();
        XStream xstream = new XStream(new StaxDriver());
        File file = new File("/home/dmitriy/IdeaProjects/Taxi/Clients.xml");
        clients = (ArrayList<Client>) xstream.fromXML(file);
        return clients;
    }


    public static void main(String args[]) throws IOException {
        /*
        //!!!!Это сделать один раз, чтобы у тебя быд файлы xml с картой, такси и клиентами
        ArrayList<Taxi> taxi = new ArrayList();
        ArrayList<Client> clients = new ArrayList();

        MapOfCity map = new MapOfCity("Test", 40,20); // Создали карту
        taxi = map.createTaxi(5);
        clients = map.createClients(5); //тут просто что угодно нам важен размер карты

        SaveMapXML(map); //записали карту туды в XML
        SaveTaxiXML(taxi);
        SaveClientsXML(clients);
        */


        //TEST
        MapOfCity maptest = LoadMapFromXML(); // Загрузили карту из xml

        ArrayList<Taxi> taxiForTest = new ArrayList();
        ArrayList<Client> clientsForTest = new ArrayList();
        taxiForTest = LoadTaxiFromXML(); //Загрузили такси из xml
        clientsForTest = LoadClientsFromXML(); //Загрузили клиентов из xml

        //Taxi taxi1 = new Taxi(1, "v0"); // Создали такси
        //taxiForTest.add(taxi1);

        //int ver = maptest.allVertices - 1;
        //String ver1 = "v" + Integer.toString(ver);
        //Client client1 = new Client(1, ver1, "v0"); //Создали клиента
        //clientsForTest.add(client1);

        Visual myMapDraw = new Visual(maptest, taxiForTest, clientsForTest);
        myMapDraw.DrawMap();

    }
} // Серёжа просто красава
