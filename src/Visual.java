import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;    // Using Swing's components and containers

/** Custom Drawing Code Template */
// A Swing application extends javax.swing.JFrame
public class Visual extends JFrame {
    // Define constants
    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;
    public static final Color CANVAS_BG_COLOR = Color.white;
    private BufferedImage image;

    public MapOfCity visMap;
    public ArrayList<Taxi> visCabs;
    public ArrayList<Client> visClients;
    public ArrayList<Cell> vertexToClear;
    GraphCells VertCells;

    //mustbethesameasinmap
    //int vertexlen = 30;
    //int vertexheight = 20;
    private int vertexlen;
    private int vertexheight;

    private final int pixelShiftLine = 30;
    private final int pixelShiftColumn = 30;
    private final int vertSize = 24;

    // Declare an instance of the drawing canvas,
    // which is an inner class called DrawCanvas extending javax.swing.JPanel.
    private DrawCanvas canvas;
    private DrawClnTx canvUpdt;

    //  MapOfCity myMap, ArrayList<Taxi> myCabs,
    // Constructor to set up the GUI components and event handlers
    public Visual(MapOfCity myMap, ArrayList<Taxi> myCabs, ArrayList<Client> myClients) throws IOException {

        //initialize some useful stuff
        vertexToClear = new ArrayList<>(0);
        vertexlen = myMap.horizontalVertices;
        vertexheight = myMap.verticalVertices;
        visMap = myMap;
        visCabs = myCabs;
        visClients = myClients;
        VertCells =new GraphCells();
        String pathname = "/home/Eugene/Taxi/src/taxi.jpg"; // "src\\taxi.jpg"
        image = ImageIO.read(new File(pathname));
    }

    public void DrawMap() {
        canvas = new DrawCanvas();
        // Construct the drawing canvas
        canvUpdt = new DrawClnTx();
        canvUpdt.setVisible(true);
        //canvas.add(canvUpdt);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvUpdt.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setLayout(null);
        // Set the Drawing JPanel as the JFrame's content-pane
        Container cp = getContentPane();
        cp.add(canvas);


      //  cp.add(new DrawClnTx());
        // or "setContentPane(canvas);"

        setDefaultCloseOperation(EXIT_ON_CLOSE);   // Handle the CLOSE button
        pack();              // Either pack() the components; or setSize()
        setTitle("CurentMap");  // "super" JFrame sets the title
        setVisible(true);    // "super" JFrame show*/
    }

    public void UpDate(){
        //canvUpdt=new C
    }
    /**
     * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
     */
    private class DrawCanvas extends JPanel {
        // Override paintComponent to perform your own painting

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);     // paint parent's background
            setBackground(Color.WHITE);  // set background color for this JPanel
            g.setColor(Color.BLACK);   //colour to paint rectangles

            //basicPointsWhereWeStartToDraw
            int currentHieght = 10;
            int currentLength = 10;

            VertCells = new GraphCells();
            for (int i = 0; i < vertexheight; i++) {
                for (int j = 0; j < vertexlen; j++) {
                    g.drawRect(currentLength, currentHieght, vertSize, vertSize);
                    VertCells.AddCell(currentLength, currentHieght, "v" + (i * vertexlen + j));
                    currentLength += pixelShiftColumn;

                }
                currentHieght += pixelShiftLine;
                currentLength = 10;
            }

            g.setColor(Color.BLACK);
            for (int i = 0; i < vertexheight; i++) {
                for (int j = 0; j < vertexlen; j++) {

                    String name1 = "v" + (i * vertexlen + j);
                    String name2 = "v" + (i * vertexlen + j + 1);
                    String name3 = "v" + ((i - 1) * vertexlen + j);
                    if (visMap.map.containsEdge(name1, name2))
                        g.fillRect(VertCells.GetCell(name1).x + vertSize,
                                VertCells.GetCell(name1).y + vertSize / 2,
                                6, pixelShiftColumn - vertSize);
                    if (visMap.map.containsEdge(name1, name3))
                        g.fillRect(VertCells.GetCell(name3).x + vertSize / 2 - 3,
                                VertCells.GetCell(name3).y + vertSize,
                                6, pixelShiftLine - vertSize);
                }
                currentHieght += pixelShiftLine;
                // System.out.println(mapOfCity.map.containsEdge("v7","v37"));
                // g.drawLine(10,10,100,100);
                currentLength = 10;

                //g.fillRect(VertCells.GetCell("v150").x,VertCells.GetCell("v150").y,vertSize,vertSize)
            }


            // System.out.println("тут");
            //VertCells.PrintThemAll();
            //System.out.println(visMap);
            //DRAW ALL TAXIS HERE

            //DRAW ALL CLIENT HERE
           /* g.setColor(Color.green);
            for (Client cl:
                    visClients) {
                g.fillRect(VertCells.GetCell(cl.sourceVertex).x,
                        VertCells.GetCell(cl.sourceVertex).y,
                        vertSize, vertSize);
            }
*/
            g.setColor(Color.YELLOW);
            //g.fillRect(VertCells.GetCell(newCab.sourceVertex).x,VertCells.GetCell(newCab.sourceVertex).y,vertSize,vertSize);

            /**Taxis*/
            DrawAllTaxis(g);
            /**Clients*/
            DrawAllClients(g);




        }
    }

    private class DrawClnTx extends JPanel {
        @Override
        public void paint(Graphics g) {
            //System.out.println("Я попал в пейнт");
            DrawAllClients(g);
            DrawAllTaxis(g);
        }
    }

    private class Cell {
        int x;
        int y;
        String edgeName;

        public Cell(int a, int b, String name) {
            x = a;
            y = b;
            edgeName = name;
        }
    }

    private class GraphCells {
        public ArrayList<Cell> myCells;

        GraphCells() {

            myCells = new ArrayList<>(vertexlen * vertexheight);
        }

        public void AddCell(int x, int y, String name) {
            Cell curCell = new Cell(x, y, name);
            myCells.add(curCell);
        }

        public Cell GetCell(String name) {
            for (Cell curr :
                    myCells) {
                if (name.equals(curr.edgeName))

                    return curr;
            }
            return null;
        }

        public void PrintThemAll() {
            for (Cell curr :
                    myCells) {
                System.out.println("x= " + curr.x + " y= " + curr.y + " name= " + curr.edgeName);
            }
        }
    }

    public void DrawAllTaxis(Graphics g) {
        for (Cell curCell : vertexToClear
                ) {

            g.setColor(CANVAS_BG_COLOR);
            g.fillRect(curCell.x + 1, curCell.y + 1, vertSize - 1, vertSize - 1);
            g.setColor(Color.BLACK);

            g.drawRect(curCell.x, curCell.y, vertSize, vertSize);
        }

        for (Taxi cab : visCabs) {

            vertexToClear.clear();

            g.setColor(Color.YELLOW);
            int curX = VertCells.GetCell(cab.sourceVertex).x;
            int curY = VertCells.GetCell(cab.sourceVertex).y;
            /***
             * Uncomment drawimage = image instead of yellow rect
             */
            g.drawImage(image, curX+1, curY+1, null);

           // g.fillRect(curX + 1, curY + 1, vertSize - 1, vertSize - 1);
            g.setColor(Color.black);
            /*g.fillRect(curX+2,curY+5,5,5);
            g.fillRect(curX+10 ,curY+5,5,5);
            g.fillRect(curX+17 ,curY+5,5,5);*/
            //System.out.println("Щас типа нарисовал таСи "+cab.number);
            vertexToClear.add(new Cell(curX, curY, VertCells.GetCell(cab.sourceVertex).edgeName));
        }
    }

    public void DrawAllClients(Graphics g) {

        g.setColor(Color.green);
        for (Client cl :
                visClients) {
            if (cl.isWait) {
                g.fillRect(VertCells.GetCell(cl.sourceVertex).x + 1,
                        VertCells.GetCell(cl.sourceVertex).y + 1,
                        vertSize - 1, vertSize - 1);
               // System.out.println("Щас типа нарисовал клиента "+cl.name);
            } else {
                g.fillRect(VertCells.GetCell(cl.sourceVertex).x + 1,
                        VertCells.GetCell(cl.sourceVertex).y + 1,
                        8, 8);
             //   System.out.println("Щас типа нарисовал клиента "+cl.name);
            }
            vertexToClear.add(new Cell(VertCells.GetCell(cl.sourceVertex).x, VertCells.GetCell(cl.sourceVertex).y, VertCells.GetCell(cl.sourceVertex).edgeName));
        }
    }


    // The entry main method
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {


      /*  frame.add(component);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();*/


        MapOfCity mapOfCity = Controller.createAndSaveMap("C:\\Users\\Sergey\\Taxi\\TESTMAP", "TESTMAP,", 20, 10);
        // System.out.println(mapOfCity);

        //  String clientAddress = "C:\\Users\\Sergey\\Taxi\\client.txt";
        //ArrayList<String> namesOfClients = new ArrayList<>(Arrays.asList("Ar'chill", "Dimitrii"));
        //ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, clientAddress, namesOfClients);
        // ArrayList<Client> clients = Controller.loadClients(clientAddress);
        // System.out.println(clients);

        //String taxiAddress = "C:\\Users\\Sergey\\Taxi\\taxi.txt";
        ArrayList<String> taxists = new ArrayList<>(Arrays.asList("A701BC", "X702YT"));
        ArrayList<Taxi> taxi = Controller.createAndSaveTaxi(mapOfCity, "C:\\Users\\Sergey\\Taxi\\TESTTAXIS", 1);
        ArrayList<Client> clients = Controller.createAndSaveClients(mapOfCity, "C:\\Users\\Sergey\\Taxi\\TESTCLIENTS", 4);
        //System.out.println(taxi);

        //Path path = new Path(mapOfCity);
        //System.out.println(path.createPath(clients.get(0), taxi.get(0)));

        clients.get(0).sourceVertex = "v1";

        String[] TestVertex = new String[10];
        TestVertex[0] = "v0";
        TestVertex[1] = "v1";
        TestVertex[2] = "v2";
        TestVertex[3] = "v22";
        TestVertex[4] = "v23";
        TestVertex[5] = "v24";
        TestVertex[6] = "v44";
        TestVertex[7] = "v45";
        TestVertex[8] = "v65";
        TestVertex[9] = "v66";


        Visual myMapDraw = new Visual(mapOfCity, taxi, clients);
        while (true) {
            for (int f = 0; f < TestVertex.length; f++) {

                clients.get(0).sourceVertex = TestVertex[f];
                taxi.get(0).sourceVertex = TestVertex[f];
                if (f == 2) {
                    clients.get(0).isWait = false;
                }
                myMapDraw.DrawMap();

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //change cur vertex

            }
        }

    }

    // Run the GUI codes on the Event-Dispatching thread for thread safety
     /*   SwingUtilities.invokeLater(new Runnable() {
            int counter=0;
            @Override
            public void run() {
                Visual myMapDraw = new Visual(mapOfCity,taxi,clients);

                System.out.println();
                myMapDraw.DrawMap();
                counter++;
               // clients.get(0).sourceVertex=TestVertex[counter];

                //new Visual(); // Let the constructor do the job
            }
        });*/
}
