package LabyrintSkapare;

import java.util.*;

public class AStarSolver implements Solver {

    private final Cell[][] cells;
    private final int width, height;

    private final LinkedList<String> path = new LinkedList<>();

    public AStarSolver(Cell[][] cells) {
        this.cells = cells;
        this.height = cells.length;
        this.width = cells[0].length;

        solve(0,0,width-1,height-1);
    }

    public void solve(int startX, int startY, int goalX, int goalY) {

        PriorityQueue<Node> open = new PriorityQueue<>();
        boolean[][] closed = new boolean[height][width];

        Node start = new Node(startX,startY,null,0,
                heuristic(startX,startY,goalX,goalY));

        open.add(start);

        Node goalNode = null;

        while(!open.isEmpty()){

            Node current = open.poll();

            if(closed[current.y][current.x]) continue;

            closed[current.y][current.x] = true;

            if(current.x == goalX && current.y == goalY){
                goalNode = current;
                break;
            }

            Cell c = cells[current.y][current.x];

            // UPP
            if(c.getTop()==0 && current.y>0){
                addNeighbor(open,current,
                        current.x,current.y-1,
                        goalX,goalY,closed);
            }

            // HÖGER
            if(c.getRight()==0 && current.x<width-1){
                addNeighbor(open,current,
                        current.x+1,current.y,
                        goalX,goalY,closed);
            }

            // NER
            if(c.getBottom()==0 && current.y<height-1){
                addNeighbor(open,current,
                        current.x,current.y+1,
                        goalX,goalY,closed);
            }

            // VÄNSTER
            if(c.getLeft()==0 && current.x>0){
                addNeighbor(open,current,
                        current.x-1,current.y,
                        goalX,goalY,closed);
            }
        }

        if(goalNode != null){
            buildPath(goalNode);

            System.out.println("A* found path");
            System.out.println("Path: "+path);
            System.out.println("Steps: "+path.size());
        }
    }

    private void addNeighbor(PriorityQueue<Node> open,
                             Node parent,
                             int x,int y,
                             int goalX,int goalY,
                             boolean[][] closed){

        if(closed[y][x]) return;

        int g = parent.g + 1;
        int h = heuristic(x,y,goalX,goalY);

        open.add(new Node(x,y,parent,g,h));
    }

    private int heuristic(int x,int y,int goalX,int goalY){
        return Math.abs(x-goalX) + Math.abs(y-goalY);
    }

    private void buildPath(Node node){

        LinkedList<String> reverse = new LinkedList<>();

        while(node != null){
            reverse.addFirst(toCoord(node.x,node.y));
            node = node.parent;
        }

        path.addAll(reverse);
    }

    private String toCoord(int x,int y){
        return "" + (char)('A'+y) + (x+1);
    }

    public LinkedList<String> getPath(){
        return path;
    }

    public LinkedList<String> getIntersections(){
        return null;
    }

    private static class Node implements Comparable<Node>{

        int x,y;
        Node parent;

        int g;
        int h;

        Node(int x,int y,Node parent,int g,int h){
            this.x=x;
            this.y=y;
            this.parent=parent;
            this.g=g;
            this.h=h;
        }

        int f(){
            return g+h;
        }

        public int compareTo(Node o){
            return Integer.compare(this.f(),o.f());
        }
    }
}
