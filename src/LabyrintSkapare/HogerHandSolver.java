package LabyrintSkapare;

import java.util.LinkedList;

public class HogerHandSolver implements Solver {

    private final Cell[][] cells;
    private int x, y;
    private Riktning riktning;
    private final int width, height;

    // Sparar vägen
    private final LinkedList<String> path = new LinkedList<>();

    public HogerHandSolver(Cell[][] cells, int startX, int startY, Riktning startRiktning) {
        this.cells = cells;
        this.x = startX;
        this.y = startY;
        this.riktning = startRiktning;

        this.height = cells.length;
        this.width = cells[0].length;

        path.add(toCoord());
    }


    // Righthandrule

    public void solve(int goalX, int goalY) {

        int safety = 0;

        while ((x != goalX || y != goalY) && safety++ < 10_000) {

            // Högerhandsregel:
            //  höger
            //  rakt fram
            //  vänster
            //  bakåt
            Riktning[] checks = {
                    riktning.right(),
                    riktning,
                    riktning.left(),
                    riktning.back()
            };

            for (Riktning d : checks) {
                if (canMove(d)) {
                    move(d);
                    break;
                }
            }
        }

        System.out.println("Slutposition: " + toCoord());
        System.out.println("Antal steg: " + path.size());
        System.out.println("Väg: " + path);
    }

    // RörelseKontroll
    private boolean canMove(Riktning d) {
        Cell c = cells[y][x];

        return switch (d) {
            case UPP     -> c.getTop() == 0 && y > 0;
            case NER     -> c.getBottom() == 0 && y < height - 1;
            case HOGER   -> c.getRight() == 0 && x < width - 1;
            case VANSTER -> c.getLeft() == 0 && x > 0;
        };
    }

    private void move(Riktning d) {
        riktning = d;

        switch (d) {
            case UPP     -> y--;
            case NER     -> y++;
            case HOGER   -> x++;
            case VANSTER -> x--;
        }

        path.add(toCoord());
    }
    //TooCord
    private String toCoord() {
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

    // Direction
    public enum Riktning {
        UPP, HOGER, NER, VANSTER;

        public Riktning right() {
            return values()[(ordinal() + 1) % 4];
        }

        public Riktning left() {
            return values()[(ordinal() + 3) % 4];
        }

        public Riktning back() {
            return values()[(ordinal() + 2) % 4];
        }
    }
}
