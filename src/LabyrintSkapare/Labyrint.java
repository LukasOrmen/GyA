package LabyrintSkapare;

import java.util.Collections;
import java.util.LinkedList;

public class Labyrint {

    static final int x = 5; // Bredd
    static final int y = 5; // Höjd
    static String headCoordinate = "A1";
    static LinkedList<String> visitedCoordinates = new LinkedList<>();
    static LinkedList<String> availableMoves;

    public static void main(String[] args) {
        while (true) {
            availableMoves = lookForAvailableMoves(headCoordinate, visitedCoordinates);

            if (visitedCoordinates.size() >= x*y) {
                System.out.println("The visited coordinates length is : " + visitedCoordinates.size());
                System.out.println("Therefore it is done");
                break;
            }
            if (availableMoves.isEmpty()) {
                if (!visitedCoordinates.contains(headCoordinate)) {
                    visitedCoordinates.add(headCoordinate);
                }
                headCoordinate = visitedCoordinates.get(visitedCoordinates.indexOf(headCoordinate) - 1);
            } else {
                if (!visitedCoordinates.contains(headCoordinate)) visitedCoordinates.add(headCoordinate);

                for (String availableMove : availableMoves) {
                    for (String visitedCoordinate : visitedCoordinates) {
                        if (availableMove.equals(visitedCoordinate))
                            System.out.println("WARNING AVAILABLE MOVES IS NOT SEPARATE");
                    }
                }

                Collections.shuffle(availableMoves);
                System.out.println(availableMoves);
                System.out.println(availableMoves.getFirst());
                headCoordinate = availableMoves.getFirst();

                // Clearing the available moves list to refresh the algorithms options
                availableMoves.clear();
            }
        }

        String maze = mazeBuilder();

        for (int i = 0; i < visitedCoordinates.size(); i++) {
            if (i <= 9) maze = maze.replace(visitedCoordinates.get(i) + " ", " " + (i+1) + " ");
            if (i >= 9) maze = maze.replace(visitedCoordinates.get(i) + " ",(i+1) + " ");

            //Thread.sleep(1000);

        }

        for (int i = 0; i < visitedCoordinates.size(); i++) {
            if (visitedCoordinates.indexOf(visitedCoordinates.get(i)) != visitedCoordinates.lastIndexOf(visitedCoordinates.get(i))) {
                System.out.println("MULTIPLES DETECTED WARNING: " + visitedCoordinates.get(i));
            }
        }

        System.out.println(maze);
        System.out.println(visitedCoordinates);
    } // main

    public Labyrint() {

    }



    public static LinkedList<String> lookForAvailableMoves(String headCoordinate, LinkedList<String> visitedCoordinates) {
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

    public static String mazeBuilder() {
        String maze = "";

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

        System.out.println(maze);

        return maze;
    }
}
