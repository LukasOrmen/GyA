package LabyrintSkapare;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class LabyrintGrafik {
    // Måste översätta allt till engelska insåg jag

    // Labyrinten
    private final Labyrint lab;

    // Antal rader och kolumner
    private final int xPanelAntal;
    private final int yPanelAntal;

    // Varje ruta har sin Cell
    private final Cell[][] cells;

    // Lista på besökta koordinater:
    private final LinkedList<String> visitedCoordinates;

    // Lista på intersections:
    private final LinkedList<String> intersections;

    // Högerhandsregelns väg:
    private final LinkedList<String> path;

    // Musen
    private final ImageIcon mouseIcon;
    private final JLabel mouseLabel;
    private final Image scaledMouse;

    // Lista med panelerna i
    private LinkedList<JPanel> panels;

    // Konstruktorn
    // public LabyrintGrafik(int xPanelAntal, int yPanelAntal) {
    public LabyrintGrafik(Labyrint lab, Solver solver) {
        this.lab = lab;
        this.xPanelAntal = lab.getX;
        this.yPanelAntal = lab.getY;
        this.visitedCoordinates = lab.getVisitedCoordinates();
        this.intersections = lab.getIntersections();

        // Skapar en cell för varje panel
        cells = new Cell[yPanelAntal][xPanelAntal];
        for (int y = 0; y < yPanelAntal; y++) {
            for (int x = 0; x < xPanelAntal; x++) {
                cells[y][x] = new Cell();
            }
        }

        // Tar bort väggar baserat på visitedCoordinates
        removeWallsFromVisited();

        // Skapar panellistan
        panels = new LinkedList<>();

        // Ritar labyrinten
        drawMaze();

        // Lägger till klassen med högerhandsregeln
        HogerHandSolver hogerHandSolver = new HogerHandSolver(cells, 0, 0, HogerHandSolver.Riktning.NER);
        hogerHandSolver.solve(5, 5);
        path = hogerHandSolver.getPath();

        // Musen
        mouseIcon = new ImageIcon("bilder/mus.png");
        scaledMouse = mouseIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        mouseLabel = new JLabel(new ImageIcon(scaledMouse));

        // Löser med högerhandsmetoden
        SolveRightHand();

        // Bara för att kontrollera ritandet
        System.out.println();
        System.out.println("Mouse width: " + mouseIcon.getIconWidth());
        System.out.println();
        System.out.println(visitedCoordinates);
        System.out.println("Intersections: " + lab.getIntersections());
        System.out.println();
        System.out.println(lab.mazeBuilder());
        System.out.println(lab);
    }

    // Tar bort väggar baserat på visitedCoordinates
    private void removeWallsFromVisited() {
        // Räknar hur många intersections som använts
        int n = 0;

        for (int i = 1; i < visitedCoordinates.size(); i++) {
            String from = visitedCoordinates.get(i - 1);
            String to = visitedCoordinates.get(i);

            int x1 = Integer.parseInt(from.substring(1)) - 1;
            int y1 = from.charAt(0) - 'A';

            int x2 = Integer.parseInt(to.substring(1)) - 1;
            int y2 = to.charAt(0) - 'A';

            // Horisontellt
            if (x2 == x1 + 1 && y1 == y2) {
                cells[y1][x1].removeRight();
                cells[y2][x2].removeLeft();
            } else if (x2 == x1 - 1 && y1 == y2) {
                cells[y1][x1].removeLeft();
                cells[y2][x2].removeRight();
            }
            // Vertikalt
            else if (y2 == y1 + 1 && x1 == x2) {
                cells[y1][x1].removeBottom();
                cells[y2][x2].removeTop();
            } else if (y2 == y1 - 1 && x1 == x2) {
                cells[y1][x1].removeTop();
                cells[y2][x2].removeBottom();
            }

            // Tar bort väggar när det skett backtracking m.h.a "intersections"
            else {
                from = intersections.get(n);

                x1 = Integer.parseInt(from.substring(1)) - 1;
                y1 = from.charAt(0) - 'A';

                // Horisontellt
                if (x2 == x1 + 1 && y1 == y2) {
                    cells[y1][x1].removeRight();
                    cells[y2][x2].removeLeft();
                } else if (x2 == x1 - 1 && y1 == y2) {
                    cells[y1][x1].removeLeft();
                    cells[y2][x2].removeRight();
                }
                // Vertikalt
                else if (y2 == y1 + 1 && x1 == x2) {
                    cells[y1][x1].removeBottom();
                    cells[y2][x2].removeTop();
                } else if (y2 == y1 - 1 && x1 == x2) {
                    cells[y1][x1].removeTop();
                    cells[y2][x2].removeBottom();
                }

                // Så att nästa backtrack kollar på nästa intersection
                n++;
            }
        }
    }

    // Ritar hela labyrinten (panelerna)
    private void drawMaze() {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(yPanelAntal, xPanelAntal));

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

    // Musen löser med högerhandsregeln
    public void SolveRightHand() {
        panels.get(0).add(mouseLabel);

        // Behövs för att rita om en panel
        panels.get(0).revalidate();
        panels.get(0).repaint();
    }

    // Metod till Sirwans lösningar
    public Cell[][] getCells() {
        return cells;
    }






    // Sånt som inte används skrivs nedan, kan komma till användning senare

    /*
    // Metoder för att dela upp koordinaterna:
    public int yCoordinate(String string) {
        char stringLetter = string.charAt(0);

        return char;
    }

    public int xCoordinate(String string) {
        String stringNumber = string.substring(1);
        int number = Integer.parseInt(stringNumber);

        return number;
    }
*/



}
