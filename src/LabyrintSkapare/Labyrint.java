package LabyrintSkapare;

import java.util.LinkedList;

public class Labyrint {
    private boolean hasToGoForward;
    private int x; // Bredd på labyrinten
    private int y; // Höjd på labyrinten
    private String headCoordinate; // Startkoordinat för generationsalgoritmen
    private LinkedList<String> availableMoves;
    private String maze;
    private LinkedList<String> visitedCoordinates;
    private LinkedList<String> intersections;
    private LinkedList<String> queue;
    private boolean hasBackTracked = false;
    private int backTrackChance;
    private int backTrack;


    public Labyrint(int x, int y, int backTrackChance) {
        this.x = x;
        this.y = y;
        this.headCoordinate = "E5";
        this.availableMoves = null;
        this.maze = "";
        this.visitedCoordinates = new LinkedList<>();
        this.intersections = new LinkedList<>();
        this.queue = new LinkedList<>();
        this.hasBackTracked = hasBackTracked;
        this.backTrackChance = backTrackChance;
        this.backTrack = 0;


        while (true) {
            if (!visitedCoordinates.contains(headCoordinate)) {
                visitedCoordinates.add(headCoordinate);
            }

            if (backTrack == 0) {
                double chance = Math.random();

                if (chance < (double) backTrackChance / 100.0 && backTrack == 0) {
                    backTrack = (int) (chance * visitedCoordinates.indexOf(headCoordinate));
                }

                while (backTrack != 0) {
                    headCoordinate = visitedCoordinates.get(visitedCoordinates.indexOf(headCoordinate) - 1);
                    hasBackTracked = true;

                    backTrack--;
                }

                availableMoves = lookForAvailableMoves(headCoordinate);

                // Stops when the program has visited all possible moves
                if (visitedCoordinates.size() >= x * y) break;

                // It has to backtrack in this case
                if (availableMoves.isEmpty()) {
                    int idx = visitedCoordinates.indexOf(headCoordinate) - 1;

                    if (idx == 0) {
                        for (int i = 0; i < visitedCoordinates.size(); i++) {
                            if (!lookForAvailableMoves(visitedCoordinates.get(i)).isEmpty()) {
                                headCoordinate = visitedCoordinates.get(i);

                                // JAG LÖSTE SKITEN, se till att den alltid väljer den första där den stöter på ett problem
                            }
                        }
//                        for (int i = 0; i < visitedCoordinates.size(); i++) {
//                            System.out.println(lookForAvailableMoves());
//                            }

                    }

                    if (idx > 0) {
                        headCoordinate = visitedCoordinates.get(idx);
                        hasBackTracked = true;
                    }

                // Continues as usual
                } else {
                    if (hasBackTracked) {
                        intersections.add(headCoordinate);
                        hasBackTracked = false;
                    }

                    headCoordinate = availableMoves.get(decisionMaker());

                    availableMoves.clear();
                }
            }
        }
    }

    public int decisionMaker() {
        int sum = 0;
        double[] numbers = new double[4];
        for (int i = 0; i < availableMoves.size(); i++) {
            String move = availableMoves.get(i);
            char moveChar = move.charAt(0);
            int moveInt = Integer.parseInt(move.substring(1));

            numbers[i] = moveChar + moveInt - 'A' + 1;
            sum += numbers[i];
        }

        double decision = Math.random();

        for (int i = 0; i < availableMoves.size(); i++) {
            numbers[i] /= sum;

            if (numbers[i] != 0 && i != 0) {
                numbers[i] += numbers[i - 1];
            }

            if (decision < numbers[i]) {
                decision = i;
                break;
            }
        }
        return (int) decision;
    }




    public LinkedList<String> lookForAvailableMoves(String headCoordinate) {
        LinkedList<String> availableMoves = new LinkedList<>();

        char headLetter = headCoordinate.charAt(0);
        int headInt = Integer.parseInt(headCoordinate.substring(1));

        String upCoordinate = "" + (char)(headLetter-1) + headInt;
        String downCoordinate = "" + (char)(headLetter+1) + headInt;
        String rightCoordinate = "" + headLetter + (headInt+1);
        String leftCoordinate = "" + headLetter + (headInt-1);

        if ((headInt-1) > 0 && !visitedCoordinates.contains(leftCoordinate)) availableMoves.add(leftCoordinate);
        if ((headLetter-1 >= 'A') && !visitedCoordinates.contains(upCoordinate)) availableMoves.add(upCoordinate);

        if ((headInt+1) <= x && !visitedCoordinates.contains(rightCoordinate)) availableMoves.add(rightCoordinate);
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

    public LinkedList<String> getIntersections() {
        return intersections;
    }
}
