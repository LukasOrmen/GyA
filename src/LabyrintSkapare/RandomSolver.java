package LabyrintSkapare;

import java.util.*;

public class RandomSolver implements Solver {

    private final Cell[][] cells;


    private final int width, height;

    private int x = 0;
    private int y = 0;
    private final boolean[][] visited;
    private final LinkedList<String> path = new LinkedList<>();

    private final Random random = new Random();

    // Konstruktor
    public RandomSolver(Labyrint lab) {

        // Skapar celler från labyrinten
        cells = new MakeCells(lab).getCells();

        // Sätter höjd och bredd
        height = cells.length;
        width = cells[0].length;
        visited = new boolean[height][width];

        // Lägger till startpositionen i vägen
        path.add(toCoord());
        solve(width - 1, height - 1);
    }

    // Startmetod
    public void solve(int goalX, int goalY) {

        // Kör DFS med slump
        dfs(x, y, goalX, goalY);

        System.out.println("Slutposition: " + toCoord());
        System.out.println("Antal steg: " + path.size());
        System.out.println("Väg: " + path);
    }

    // DFS-algoritm med slumpmässig riktning
    private boolean dfs(int cx, int cy, int goalX, int goalY) {

        // besökt
        visited[cy][cx] = true;

        // Om vi nått målet = sant
        if (cx == goalX && cy == goalY) return true;

        // möjliga riktningar
        List<Riktning> dirs = getPossibleDirections(cx, cy);

        // Slumpa ordningen (detta gör algoritmen "random")
        Collections.shuffle(dirs, random);

        // Testa varje riktning
        for (Riktning d : dirs) {

            // Spara nuvarande position (för backtracking)
            int oldX = x;
            int oldY = y;

            // Flytta
            move(d);

            // söka från nya positionen
            if (dfs(x, y, goalX, goalY)) return true;

            // BACKTRACK
            // Om det inte funkade → gå tillbaka
            x = oldX;
            y = oldY;

            // visar tillbaka rörelsen
            path.add(toCoord());
        }

        // Ingen väg hittades härifrån
        return false;
    }

    // Returnerar alla möjliga riktningar vi kan gå från en position
    private List<Riktning> getPossibleDirections(int cx, int cy) {

        List<Riktning> dirs = new ArrayList<>();

        // Nuvarande cell
        Cell c = cells[cy][cx];

        // UPP
        if (c.getTop() == 0 && cy > 0 && !visited[cy - 1][cx]) {
            dirs.add(Riktning.UPP);
        }

        // NER
        if (c.getBottom() == 0 && cy < height - 1 && !visited[cy + 1][cx]) {
            dirs.add(Riktning.NER);
        }

        // HÖGER
        if (c.getRight() == 0 && cx < width - 1 && !visited[cy][cx + 1]) {
            dirs.add(Riktning.HOGER);
        }

        // VÄNSTER
        if (c.getLeft() == 0 && cx > 0 && !visited[cy][cx - 1]) {
            dirs.add(Riktning.VANSTER);
        }

        return dirs;
    }

    // Flyttar positionen i en viss riktning
    private void move(Riktning d) {

        if (d == Riktning.UPP) y--;       // upp
        if (d == Riktning.NER) y++;       // ner
        if (d == Riktning.HOGER) x++;     // höger
        if (d == Riktning.VANSTER) x--;   // vänster

        // Lägg till nya positionen i path
        path.add(toCoord());
    }

    // Gör om koordinater till formatet A1, B3 osv
    private String toCoord() {
        return "" + (char) ('A' + y) + (x + 1);
    }

    // Enum för riktningar
    public enum Riktning {
        UPP, HOGER, NER, VANSTER
    }
    
    // Returnerar vägen
    public LinkedList<String> getPath() {
        return path;
    }
}