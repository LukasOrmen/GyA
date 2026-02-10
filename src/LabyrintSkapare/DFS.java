package LabyrintSkapare;

import java.util.LinkedList;
/*
  DFS (Depth First Search) klass för att hitta en väg genom en labyrint.
  Algoritmen går så djupt som möjligt i en riktning innan den backar.
 */
public class DFS implements Solver{
    private final Cell[][] cells;   // Labyrintens celler
    private final boolean [][] visited;     // Håller koll på vilka celler vi redan besökt
    private final int width, height;

    // Sparar vägen som en lista av koordinater
    private final LinkedList<String> path = new LinkedList<>();
    // Flagga som blir true när vi når målet
    private  boolean found = false;
        public DFS(Cell[][]cells){
            this.cells = cells;
            this.height = cells.length;
            this.width = cells[0].length;
            this.visited = new boolean[height][width];
solve(0,0,width-1,height-1);
        }
        //Startpunkt för att lösa labyrinten.
        public void solve(int startX, int startY, int goalX,int goalY){
            dfs(startX, startY, goalX, goalY);
            if (found){
                System.out.println("DFS found path");
                System.out.println("väg: "+ path);
                System.out.println("amount off steps: "+ path.size());
            }
            else {
                System.out.println("DFS didn't find the route");
            }

        }
        // metod som utför själva sökningen.
        private void dfs(int x, int y, int goalX, int goalY){
            // Om vi redan hittat målet, avbryt direkt
            if (found) return;
            // Markera nuvarande cell som besökt och lägg till i vägen
            visited[y][x] = true;
            path.add(toCoord(x, y));
            // Om vi är framme vid målet - klart
            if (x == goalX && y == goalY) {
                found = true;
                return;
            }
            // Hämta aktuell cell för att kontrollera väggar (0 = ingen vägg)
            Cell c = cells[y][x];
            // Prova alla riktningar (valfri ordning)
            // UPP
            if (c.getTop() == 0 && y > 0 && !visited[y - 1][x]) {
                dfs(x, y - 1, goalX, goalY);
            }

            // HÖGER
            if (!found && c.getRight() == 0 && x < width - 1 && !visited[y][x + 1]) {
                dfs(x + 1, y, goalX, goalY);
            }

            // NER
            if (!found && c.getBottom() == 0 && y < height - 1 && !visited[y + 1][x]) {
                dfs(x, y + 1, goalX, goalY);
            }

            // VÄNSTER
            if (!found && c.getLeft() == 0 && x > 0 && !visited[y][x - 1]) {
                dfs(x - 1, y, goalX, goalY);
            }

            // Om ingen väg härifrån leder till mål --> backtracka
            // Om vi kommer hit och found fortfarande är false, betyder det att
            // denna väg var en återvändsgränd. Ta bort cellen från path.
            if (!found) {
                path.removeLast();
            }

        }
        //skriver ut texten i rätt format
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
}

