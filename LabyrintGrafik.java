package LabyrintSkapare;

import algoritmer.HogerHandSolver;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class LabyrintGrafik {
    // Måste översätta allt till engelska insåg jag


    // Antal rader och kolumner
    private final int xPanelAntal;
    private final int yPanelAntal;

    // Varje ruta har sin Cell
    private Cell[][] cells;

    // Lista på besökta koordinater:
    private final LinkedList<String> visitedCoordinates;

    // Lista på intersections:
    private final LinkedList<String> intersections;

    // Konstruktorn
    public LabyrintGrafik(int xPanelAntal, int yPanelAntal) {
        this.xPanelAntal = xPanelAntal;
        this.yPanelAntal = yPanelAntal;

        // Hämtar en färdig labyrint med det inskrivna antalet rader och kolumner
        Labyrint lab = new Labyrint(xPanelAntal, yPanelAntal, 10);
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

        // Lösning genom högerhandsmetoden
        HogerHandSolver solver = new HogerHandSolver(cells, 4, 4, HogerHandSolver.Riktning.HOGER);
        solver.solve(0, 0);

        // Ritar labyrinten
        drawMaze();

        // Bara för att kontrollera ritandet
        System.out.println();
        System.out.println(visitedCoordinates);
        System.out.println(lab.getIntersections());
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

    // Ritar hela labyrinten
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

                frame.add(panel);
            }
        }

        // Ritar fönsret
        frame.setSize(500, 500);
        // frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    // Bara main
    public static void main(String[] args) {
        new LabyrintGrafik(5, 5);
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