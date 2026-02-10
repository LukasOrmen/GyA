package LabyrintSkapare;

import javax.swing.*;
import java.util.LinkedList;

public class MakeCells {
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

    // Lista med panelerna i
    private LinkedList<JPanel> panels;

    // Konstruktorn
    public MakeCells(Labyrint lab) {
        this.lab = lab;
        this.xPanelAntal = lab.getX();
        this.yPanelAntal = lab.getY();
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

    }

    // Tar bort väggar baserat på visitedCoordinates
    public void removeWallsFromVisited() {
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

    public Cell[][] getCells() {
        return cells;
    }
}