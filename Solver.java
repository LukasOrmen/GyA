package LabyrintSkapare;

import java.util.LinkedList;

public interface Solver {
    LinkedList<String> getPath();

    LinkedList<String> getIntersections();
}
