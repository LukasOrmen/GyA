package LabyrintSkapare;

import java.util.LinkedList;

public class HogerHandSolver implements Solver {

    private final Cell[][] cells;        // Labyrinten
    private final boolean[][] visited;   // besökta rutor
    private final int width, height;

    private final LinkedList<String> path = new LinkedList<>(); // Sparar vägen
    private boolean found = false; // Blir true när målet hittas

    public HogerHandSolver(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells[0].length;
        this.visited = new boolean[height][width];
    }

    // Startmetod
    public void solve(int startX, int startY, int goalX, int goalY, Riktning startRiktning) {

        // Starta med riktning
        RightHand(startX, startY, goalX, goalY, startRiktning);

        // Resultat
        if (found) {
            System.out.println("Högerhand hittade väg");
            System.out.println("Väg: " + path);
            System.out.println("Antal steg: " + path.size());
        } else {
            System.out.println("Högerhand hittade ingen väg");
        }
    }

    // högerhandsregel
    private void RightHand(int x, int y, int goalX, int goalY, Riktning riktning) {

        // Om vi redan hittat målet → avbryt
        if (found) return;

        // Markera rutan som besökt
        visited[y][x] = true;

        // Lägg till positionen i vägen
        path.add(toCoord(x, y));

        // Om vi nått målet  klart
        if (x == goalX && y == goalY) {
            found = true;
            return;
        }

        // Hämta nuvarande cell (för att kolla väggar)
        Cell c = cells[y][x];

        // Högerhandsregeln:
        // 1. Höger
        // 2. Fram
        // 3. Vänster
        // 4. Bak
        Riktning[] checks = {
                riktning.right(),
                riktning,
                riktning.left(),
                riktning.back()
        };

        // Loopar igenom riktningarna i rätt ordning
        for (Riktning d : checks) {

            int newX = x;
            int newY = y;

            // Räkna ut nästa position beroende på riktning
            switch (d) {
                case UPP     -> newY--;
                case NER     -> newY++;
                case HOGER   -> newX++;
                case VANSTER -> newX--;
            }

            // Om vi kan gå dit -> fortsätt
            if (canMove(c, x, y, newX, newY, d)) {
                RightHand(newX, newY, goalX, goalY, d);
            }

            // Om målet hittades -> sluta direkt
            if (found) return;
        }

        // Backtracking
        // Om ingen väg fungerade -> ta bort sista steget
        if (!found) {
            path.removeLast();
        }
    }

    // om kan gå i en riktning
    private boolean canMove(Cell c, int x, int y, int newX, int newY, Riktning d) {

        //  inte går utanför labyrinten
        if (newX < 0 || newX >= width || newY < 0 || newY >= height)
            return false;

        // Kolla så vi inte redan varit där
        if (visited[newY][newX])
            return false;

        // Kolla väggar (0 = ingen vägg = vi kan gå)
        return switch (d) {
            case UPP     -> c.getTop() == 0;
            case NER     -> c.getBottom() == 0;
            case HOGER   -> c.getRight() == 0;
            case VANSTER -> c.getLeft() == 0;
        };
    }

    // Omvandlar koordinater till formatet "A1"
    private String toCoord(int x, int y) {
        return "" + (char) ('A' + y) + (x + 1);
    }

    @Override
    public LinkedList<String> getPath() {
        return path;
    }

    @Override
    public LinkedList<String> getIntersections() {
        return null;
    }

    // Enum för riktningar
    public enum Riktning {
        UPP, HOGER, NER, VANSTER;

        // Höger om nuvarande riktning
        public Riktning right() {
            return values()[(ordinal() + 1) % 4];
        }

        // Vänster om nuvarande riktning
        public Riktning left() {
            return values()[(ordinal() + 3) % 4];
        }

        // Bakåt (vänd 180°)
        public Riktning back() {
            return values()[(ordinal() + 2) % 4];
        }
    }
}