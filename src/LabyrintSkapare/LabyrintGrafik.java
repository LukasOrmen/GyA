package LabyrintSkapare;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;

public class LabyrintGrafik {

    // Hela fönstret
    private JFrame frame;

    // Panel med alla paneler
    JPanel gridPanel;

    private LinkedList<JPanel> panels;
    private int xPanelAntal;
    private int yPanelAntal;

    // Interfacen
    Solver solver;
    Solver solver2;
    Solver solver3;

    // Path från interfacet
    LinkedList<String> path;

    // Mössen
    private ImageIcon mouseIcon;
    private Image scaledMouse;
    private ImageIcon mouseIcon2;
    private Image scaledMouse2;
    private ImageIcon mouseIcon3;
    private Image scaledMouse3;
    private JLabel mouseLabel;

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

    // Omstartsknappen
    private JButton restartButton;
    private int restartClicks = 0;

    // Konstruktorn
    public LabyrintGrafik(Labyrint lab, Solver solver, Solver solver2, Solver solver3) {

        this.xPanelAntal = lab.getX();
        this.yPanelAntal = lab.getY();
        this.solver = solver;
        this.solver2 = solver2;
        this.solver3 = solver3;

        // Själva fönstret
        frame = new JFrame();
        frame.setLayout(new BorderLayout());

        // Lösningsvägen
        path = solver.getPath();
        // Listan för panelerna
        panels = new LinkedList<>();
        // HashMapen för panelerna
        panelMap = new HashMap<>();

        initiateLabels();
        drawMaze(lab);
        drawSolution();
        createRestartButton();
    }

    // Ritar labyrinten med hjälp av panelerna
    private void drawMaze(Labyrint lab) {

        // Större panel som innehåller alla paneler - för att omstartsknappen ska kunna finnas
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(xPanelAntal, yPanelAntal));
        gridPanel.setBackground(Color.BLACK);

        Cell[][] cells = new MakeCells(lab).getCells();

        for (int y = 0; y < yPanelAntal; y++) {
            for (int x = 0; x < xPanelAntal; x++) {

                Cell c = cells[y][x];

                JPanel panel = new JPanel();
                panel.setBorder(BorderFactory.createMatteBorder(
                        c.getTop(),
                        c.getLeft(),
                        c.getBottom(),
                        c.getRight(),
                        Color.BLACK));

                panel.setBackground(new Color(156, 236, 126));

                panels.add(panel);
                gridPanel.add(panel);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        // Ritar fönstret
        frame.setSize(500, 550);
        frame.setLocation(430, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // Musen löser labyrinten
    public void drawSolution() {

        steg = 0;

        LinkedList<String> cordList = new LinkedList<>();

        for (int i = 0; i < xPanelAntal; i++) {
            for (int j = 0; j < yPanelAntal; j++) {
                char letter = (char) (65 + i);
                int number = j + 1;
                cordList.add(letter + "" + number);
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
        timer = new Timer(50, e -> {

            // Lägger till musen på nuvarande panel
            String currentCord = path.get(steg);

            panelMap.get(currentCord).add(mouseLabel);
            panelMap.get(currentCord).revalidate();
            panelMap.get(currentCord).repaint();

            if (steg > 0) {
                String previousCord = path.get(steg - 1);
                panelMap.get(previousCord).remove(mouseLabel);
                panelMap.get(previousCord).revalidate();
                panelMap.get(previousCord).repaint();
            }

            // Osten tas bort när musen gått klart
            if (steg == path.size() - 1) {

                panels.getLast().remove(cheeseLabel);
                panels.getLast().revalidate();
                panels.getLast().repaint();

                endingLabel.setVisible(true);

                timer.stop();
            }

            steg++;
        });

        timer.start();
    }

    // Knappen längst ned
    private void createRestartButton() {

        restartButton = new JButton("Nästa lösning");
        restartButton.setFocusPainted(false);
        restartButton.setBackground(new Color(172, 3, 3));
        restartButton.setForeground(Color.WHITE);

        restartButton.addActionListener(e -> {

            restartClicks++;

            if (timer != null) {
                timer.stop();
            }

            for (JPanel panel : panels) {
                panel.remove(mouseLabel);
                panel.revalidate();
                panel.repaint();
            }

            endingLabel.setVisible(false);

            panels.getLast().add(cheeseLabel);
            panels.getLast().revalidate();
            panels.getLast().repaint();

            if (restartClicks == 1) {
                path = solver2.getPath();
                endingLabel.setText("Antal steg: " + (path.size() - 1));
                mouseLabel.setIcon(new ImageIcon(scaledMouse2));
            }

            if (restartClicks == 2) {
                path = solver3.getPath();
                endingLabel.setText("Antal steg: " + (path.size() - 1));
                mouseLabel.setIcon(new ImageIcon(scaledMouse3));
            }

            drawSolution();

            if (restartClicks == 2) {
                restartButton.setEnabled(false);
                restartButton.setBackground(Color.GRAY);
            }
        });

        frame.add(restartButton, BorderLayout.SOUTH);
    }

    public void initiateLabels() {

        mouseIcon = new ImageIcon("bilder/musse.png");
        scaledMouse = mouseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        mouseLabel = new JLabel(new ImageIcon(scaledMouse));

        mouseIcon2 = new ImageIcon("bilder/jerry.png");
        scaledMouse2 = mouseIcon2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        mouseIcon3 = new ImageIcon("bilder/minniemouse.png");
        scaledMouse3 = mouseIcon3.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        cheeseIcon = new ImageIcon("bilder/cheese.png");
        scaledCheese = cheeseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        cheeseLabel = new JLabel(new ImageIcon(scaledCheese));

        endingLabel = new JLabel("Antal steg: " + (path.size() - 1));
        endingLabel.setFont(new Font("SansSerif", Font.BOLD, 62));
        endingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        endingLabel.setVerticalAlignment(SwingConstants.CENTER);
        endingLabel.setForeground(new Color(172, 3, 3));

        frame.setGlassPane(endingLabel);
    }
}
