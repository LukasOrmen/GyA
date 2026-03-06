package LabyrintSkapare;

import java.util.LinkedList;

public class EgenSolver implements Solver {

    private final Cell[][] cells;
    private final boolean[][] visited;
    private final int width, height;

    private final LinkedList<String> path = new LinkedList<>();
    private boolean found = false;

    public EgenSolver(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells[0].length;
        this.visited = new boolean[height][width];

        solve(0,0,width-1,height-1);
    }

    public void solve(int startX, int startY, int goalX, int goalY){

        dfs(startX,startY,goalX,goalY);

        if(found){
            System.out.println("DFS found path");
            System.out.println("väg: " + path);
            System.out.println("amount of steps: " + path.size());
        }
        else{
            System.out.println("DFS didn't find the route");
        }
    }

    private void dfs(int x,int y,int goalX,int goalY){

        if(found) return;

        visited[y][x] = true;
        path.add(toCoord(x,y));

        if(x == goalX && y == goalY){
            found = true;
            return;
        }

        Cell c = cells[y][x];

        // HÖGER
        if(!found && c.getRight()==0 && x<width-1 && !visited[y][x+1]){
            dfs(x+1,y,goalX,goalY);
            if(!found) path.add(toCoord(x,y));
        }

        // NER
        if(!found && c.getBottom()==0 && y<height-1 && !visited[y+1][x]){
            dfs(x,y+1,goalX,goalY);
            if(!found) path.add(toCoord(x,y));
        }

        // VÄNSTER
        if(!found && c.getLeft()==0 && x>0 && !visited[y][x-1]){
            dfs(x-1,y,goalX,goalY);
            if(!found) path.add(toCoord(x,y));
        }
        // UPP
        if(!found && c.getTop()==0 && y>0 && !visited[y-1][x]){
            dfs(x,y-1,goalX,goalY);
            if(!found) path.add(toCoord(x,y)); // tillbaka
        }


    }

    private String toCoord(int x,int y){
        return "" + (char)('A'+y) + (x+1);
    }

    public LinkedList<String> getPath(){
        return path;
    }
}