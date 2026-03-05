package LabyrintSkapare;

import java.util.LinkedList;

public interface Solver {
    void solve(int startX, int startY, int goalX, int goalY);

    LinkedList<String> getPath();

    LinkedList<String> getIntersections();
}