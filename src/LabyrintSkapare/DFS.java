package LabyrintSkapare;

import java.util.LinkedList;

public class DFS {
    private final Cell[][] cells;
    private final boolean [][] visited;
    private int x, y;
    private HogerHandSolver.Riktning riktning;
    private final int width, height;

    // Sparar vägen
    private final LinkedList<String> path = new LinkedList<>();
    private  boolean found = false;
        public DFS(Cell[][]cells){
            this.cells = cells;
            this.height = cells.length;
            this.width = cells[0].length;
            this.visited = new boolean[height][width];

        }
        public void solve(int startX, int startY, int goalX,int goalY){
            dfs(startX, startY, goalX, goalY);
            if (found){
                System.out.println("DFS found path");
                System.out.println("väg: "+ path);
                System.out.println("amount off steps: "+ path.size());
            }
            else {
                System.out.println("DFS didnt find the route");
            }

        }
        //metoden
        private void dfs(int x, int y, int goalX, int goalY){
            if (found)
                return;

        }

    }
