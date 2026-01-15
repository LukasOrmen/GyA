package LabyrintSkapare;


import algoritmer.HogerHandSolver;

import java.util.LinkedList;

public class HogerHand {

    private final Cell[][] cells;
    private int x, y;               // aktuell position
    private HogerHandSolver.Riktning riktning;            // aktuell riktning
    private final int width, height;

    // För att kunna visa vägen
    private final LinkedList<String> path = new LinkedList<>();

    public HogerHand(Cell[][] cells, int startX, int startY, HogerHandSolver.Riktning startrik) {
        this.cells = cells;
        this.x = startX;
        this.y = startY;
        this.riktning = startrik;

        this.height = cells.length;
        this.width = cells[0].length;

        path.add(toCoord());
    }

    //högerhands
    public void solve(int goalX, int goalY) {

        int safety = 0;

        while ((x != goalX || y != goalY) && safety++ < 10_000) {

            // Högerhandsregel i korrekt ordning
            HogerHandSolver.Riktning[] checks = {
                    riktning.right(),
                    riktning,
                    riktning.left(),
                    riktning.back()
            };

            for (HogerHandSolver.Riktning d : checks) {
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

    //

    private boolean canMove(HogerHandSolver.Riktning d) {
        Cell c = cells[y][x];

        return switch (d) {
            case UPP     -> c.getTop() == 0 && y > 0;
            case NER     -> c.getBottom() == 0 && y < height - 1;
            case HOGER   -> c.getRight() == 0 && x < width - 1;
            case VANSTER -> c.getLeft() == 0 && x > 0;
        };
    }

    private void move(HogerHandSolver.Riktning d) {
        riktning = d;

        switch (d) {
            case UPP     -> y--;
            case NER     -> y++;
            case HOGER   -> x++;
            case VANSTER -> x--;
        }

        path.add(toCoord());
    }

    private String toCoord() {
        return "" + (char) ('A' + y) + (x + 1);
    }

    //riktning

    enum Riktning {
        UPP, HOGER, NER, VANSTER;

        Riktning right() {
            return values()[(ordinal() + 1) % 4];
        }

        Riktning left() {
            return values()[(ordinal() + 3) % 4];
        }

        Riktning back() {
            return values()[(ordinal() + 2) % 4];
        }
    }
}
