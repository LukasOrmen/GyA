package LabyrintSkapare;

import java.util.Collections;
import java.util.LinkedList;

public class Labyrint {
    private int x = 5; // Bredd på labyrinten
    private int y = 5; // Höjd på labyrinten
    private String headCoordinate; // Startkordinat för generationsalgoritmen
    private LinkedList<String> availableMoves;
    private String maze;
    private LinkedList<String> visitedCoordinates;

    public Labyrint(int x, int y) {
        this.x = x;
        this.y = y;
        this.headCoordinate = "A1";
        this.availableMoves = null;
        this.maze = "";
        this.visitedCoordinates = new LinkedList<>();

        while (true) {

            availableMoves = lookForAvailableMoves(headCoordinate);

            // Stops when the program has visited all the possible moves
            if (visitedCoordinates.size() >= x*y) break;

            // Happens when no moves are available, then it has to backtrack
            if (availableMoves.isEmpty()) {
                if (!visitedCoordinates.contains(headCoordinate)) {
                    visitedCoordinates.add(headCoordinate);
                }
                headCoordinate = visitedCoordinates.get(visitedCoordinates.indexOf(headCoordinate) - 1);
            }
            // Meaning it has options in available moves, which means we are going to randomize a choice
            else {
                if (!visitedCoordinates.contains(headCoordinate)) visitedCoordinates.add(headCoordinate);

                Collections.shuffle(availableMoves);
                headCoordinate = availableMoves.getFirst();

                // Clearing the available moves list to refresh the algorithms options
                availableMoves.clear();
            }
        }

        String maze = mazeBuilder();

        for (int i = 0; i < visitedCoordinates.size(); i++) {
            if (visitedCoordinates.indexOf(visitedCoordinates.get(i)) != visitedCoordinates.lastIndexOf(visitedCoordinates.get(i))) {
                System.out.println("MULTIPLES DETECTED WARNING: " + visitedCoordinates.get(i));
            }
        }
    }



    public LinkedList<String> lookForAvailableMoves(String headCoordinate) {
        LinkedList<String> availableMoves = new LinkedList<>();

        char headLetter = headCoordinate.charAt(0);
        int headInt = Integer.parseInt(headCoordinate.substring(1));

        String upCoordinate = "" + (char)(headLetter-1) + headInt;
        String downCoordinate = "" + (char)(headLetter+1) + headInt;
        String rightCoordinate = "" + headLetter + (headInt+1);
        String leftCoordinate = "" + headLetter + (headInt-1);

        // Kollar om den kan gå höger eller vänster ifrån vart "huvudet" är
        if ((headInt+1) <= x && !visitedCoordinates.contains(rightCoordinate)) availableMoves.add(rightCoordinate);
        if ((headInt-1) > 0 && !visitedCoordinates.contains(leftCoordinate)) availableMoves.add(leftCoordinate);

        // Kollar om den kan gå upp eller ner ifrån vart "huvudet" är
        if ((headLetter-1 >= 'A') && !visitedCoordinates.contains(upCoordinate)) availableMoves.add(upCoordinate);
        if ((headLetter+1 <= 'A'+y-1) && !visitedCoordinates.contains(downCoordinate)) availableMoves.add(downCoordinate);

        return availableMoves;
    }

    public String mazeBuilder() {

        char mazeStartLetter = 'A';
        int mazeStartInt = 1;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                maze += (mazeStartLetter + "") + mazeStartInt++ + " ";
            }
            maze += "\n";
            mazeStartInt = 1;
            mazeStartLetter++;
        }

        return maze;
    }

    public LinkedList<String> getVisitedCoordinates() {
        return visitedCoordinates;
    }

    @Override
    public String toString() {
        String completedMazeString = maze;
        for (int i = 0; i < visitedCoordinates.size(); i++) {
            if (i <= 9)
                completedMazeString = completedMazeString.replace(visitedCoordinates.get(i) + " ", " " + (i + 1) + " ");
            if (i >= 9)
                completedMazeString = completedMazeString.replace(visitedCoordinates.get(i) + " ", (i + 1) + " ");
        }
        return completedMazeString;
    }
}
