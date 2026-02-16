package LabyrintSkapare;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class LabyrintGrafik {

    // Hela fönstret
    private JFrame frame;

    private LinkedList<JPanel> panels;
    private int xPanelAntal;
    private int yPanelAntal;

    // Interfacet
    Solver solver;

    // Path från interfacet
    LinkedList<String> path;

    // Musen
    private ImageIcon mouseIcon;
    private JLabel mouseLabel;
    private Image scaledMouse;

    // Osten
    private ImageIcon cheeseIcon;
    private JLabel cheeseLabel;
    private Image scaledCheese;

    // HashMap med coordinater som nyckel och panelerna som värde
    HashMap<String, JPanel> panelMap;

    // Timern för flyttning av musen
    private Timer timer;
    private int steg;

    // Statistiklabeln när musen är klar
    private JLabel endingLabel;

    // Konstruktorn
    public LabyrintGrafik(Labyrint lab, Solver solver) {
        this.xPanelAntal = lab.getX();
        this.yPanelAntal = lab.getY();
        this.solver = solver;

        // Själva fönstret
        frame = new JFrame();
        // Lösningsvägen
        path = solver.getPath();
        // Listan för panelerna
        panels = new LinkedList<>();
        // HashMapen för panelerna
        panelMap = new HashMap<>();

        initiateLabels();
        drawMaze(lab);
        drawSolution();
    }

    // Ritar labyrinten med hjälp av panelerna
    private void drawMaze(Labyrint lab) {
        Cell[][] cells = new MakeCells(lab).getCells();
        frame.setLayout(new GridLayout(xPanelAntal, yPanelAntal));

        for (int y = 0; y < yPanelAntal; y++) {
            for (int x = 0; x < xPanelAntal; x++) {
                Cell c = cells[y][x];

                int top    = c.getTop();
                int left   = c.getLeft();
                int bottom = c.getBottom();
                int right  = c.getRight();

                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                panel.setBackground(new Color(156, 236, 126));

                panels.add(panel);
                frame.add(panel);
            }
        }

        // Ritar fönsret
        frame.setSize(500, 500);
        frame.setLocation(430, 100);
        // frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // Musen löser labyrinten
    public void drawSolution() {
        // Lägger in nycklar och värden i panel-HashMapen
        LinkedList<String> cordList = new LinkedList<>();
        for (int i = 0; i < xPanelAntal; i++) {
           for (int j = 0; j < yPanelAntal; j++) {
               char letter = (char) (65 + i);
               int number = j + 1;
               String cord = letter + "" + number;

               cordList.add(cord);
           }
       }
        for (int i = 0; i < panels.size(); i++) {
            panelMap.put(cordList.get(i), panels.get(i));
        }

        // Osten läggs till
        panels.getLast().add(cheeseLabel);
        panels.getLast().revalidate();
        panels.getLast().repaint();

        // Musen flyttas en panel i taget
        timer = new Timer(35, e -> {
            if (steg == path.size()) {
                timer.stop();

                // Tar bort osten när musen är framme
                panels.getLast().remove(cheeseLabel);
                panels.getLast().revalidate();
                panels.getLast().repaint();

                // Visar statistiklabeln:
                endingLabel.setVisible(true);

                // För att inte fortsätta efter timern stoppats
                return;
            }

            // Lägger till musen på nuvarande panel
            String currentCord = path.get(steg);
            panelMap.get(currentCord).add(mouseLabel);

            // Behövs för att rita om en panel
            panelMap.get(currentCord).revalidate();
            panelMap.get(currentCord).repaint();

            // Tar bort musen från panelen innan
            if (steg > 0) {
                String recentCord = path.get(steg - 1);
                panelMap.get(recentCord).remove(mouseLabel);
                panelMap.get(recentCord).revalidate();
                panelMap.get(recentCord).repaint();
            }

            // Ökar antal steg med 1
            steg++;
        } );
        timer.start();
    }

    public void initiateLabels() {
        // Musen
        mouseIcon = new ImageIcon("bilder/mus.png");
        scaledMouse = mouseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        mouseLabel = new JLabel(new ImageIcon(scaledMouse));

        // Osten
        cheeseIcon = new ImageIcon("bilder/cheese.png");
        scaledCheese = cheeseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        cheeseLabel = new JLabel(new ImageIcon(scaledCheese));

        // Statistiklabeln när musen är klar
        endingLabel = new JLabel("Antal steg: " + (path.size() - 1));
        endingLabel.setFont(new Font("", Font.PLAIN, 60));
        endingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endingLabel.setVerticalAlignment(SwingConstants.CENTER);
        endingLabel.setForeground(new Color(7, 19, 60, 255));
        frame.setGlassPane(endingLabel);
    }
}
