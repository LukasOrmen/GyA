package LabyrintSkapare;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class LabyrintGrafik {

    private LinkedList<JPanel> panels;
    private Labyrint lab;
    private int xPanelAntal;
    private int yPanelAntal;

    // Interfacet
    Solver solver;

    // Path från interfacet
    LinkedList<String> path;

    // Musen
    private final ImageIcon mouseIcon;
    private final JLabel mouseLabel;
    private final Image scaledMouse;

    // Konstruktorn
    public LabyrintGrafik(Labyrint lab, Solver solver) {
        this.xPanelAntal = lab.getX();
        this.yPanelAntal = lab.getY();
        this.panels = new LinkedList<>();

        drawMaze(lab);

        // Interfacet
        this.solver = solver;
        path = solver.getPath();

        // Musen
        mouseIcon = new ImageIcon("bilder/mus.png");
        scaledMouse = mouseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        mouseLabel = new JLabel(new ImageIcon(scaledMouse));

        // Ritar musen som löser labyrinten
        drawSolution();
    }

    // Ritar labyrinten med ghjälp av panelerna
    private void drawMaze(Labyrint lab) {
        Cell[][] cells = new MakeCells(lab).getCells();
        JFrame frame = new JFrame();
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
        panels.get(0).add(mouseLabel);

        // Behövs för att rita om en panel
        panels.get(0).revalidate();
        panels.get(0).repaint();
    }
}
