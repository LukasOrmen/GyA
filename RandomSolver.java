package LabyrintSkapare;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomSolver implements Solver {

    private Cell[][] cells;
    private int width, height;  

    private int x, y;

    private boolean[][] visited;
    private final LinkedList<String> path = new LinkedList<>();
    private final Random random = new Random();

    public RandomSolver(Labyrint lab) {
        this.cells = new MakeCells(lab).getCells();
        this.height = cells.length;
        this.width = cells[0].length;

        this.x = 0;
        this.y = 0;

        this.visited = new boolean[height][width];

        path.add(toCoord());

        solve(lab.getX() - 1, lab.getY() - 1);
    }

    public void solve(int goalX, int goalY) {

        dfs(x, y, goalX, goalY);

        System.out.println("Slutposition: " + toCoord());
        System.out.println("Antal steg: " + path.size());
        System.out.println("Väg: " + path);
    }

    private boolean dfs(int currentX, int currentY, int goalX, int goalY) {

        visited[currentY][currentX] = true;

        if (currentX == goalX && currentY == goalY) {
            return true; // mål hittat
        }

        ArrayList<Riktning> directions = new ArrayList<>();

        for (Riktning d : Riktning.values()) {

            int nextX = currentX;
            int nextY = currentY;

            switch (d) {
                case UPP     -> nextY--;
                case NER     -> nextY++;
                case HOGER   -> nextX++;
                case VANSTER -> nextX--;
            }

            if (canMove(currentX, currentY, d)
                    && !visited[nextY][nextX]) {
                directions.add(d);
            }
        }

        // 🔥 Slumpa ordningen vid vägskäl
        Collections.shuffle(directions, random);

        for (Riktning d : directions) {

            int oldX = x;
            int oldY = y;

            move(d);

            if (dfs(x, y, goalX, goalY)) {
                return true;
            }

            // Backtrack
            x = oldX;
            y = oldY;
            path.add(toCoord());

            
            //path.removeLast();
        }

        return false;
    }

    private boolean canMove(int currentX, int currentY, Riktning d) {

        Cell c = cells[currentY][currentX];

        return switch (d) {
            case UPP     -> c.getTop() == 0 && currentY > 0;
            case NER     -> c.getBottom() == 0 && currentY < height - 1;
            case HOGER   -> c.getRight() == 0 && currentX < width - 1;
            case VANSTER -> c.getLeft() == 0 && currentX > 0;
        };
    }

    private void move(Riktning d) {

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

    public enum Riktning {
        UPP, HOGER, NER, VANSTER
    }

    public LinkedList<String> getPath() {
        return path;
    }
}