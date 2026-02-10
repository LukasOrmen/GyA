package LabyrintSkapare;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class LabyrintGrafik {

    private LinkedList<JPanel> panels;
    private Labyrint lab;
    private int xPanelAntal;
    private int yPanelAntal;

    public LabyrintGrafik(Labyrint lab) {
        this.xPanelAntal = lab.getX();
        this.yPanelAntal = lab.getY();
        this.panels = new LinkedList<>();

        drawMaze(lab);
    }

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
}