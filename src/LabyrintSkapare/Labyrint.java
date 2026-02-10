package LabyrintSkapare;

import java.util.LinkedList;

public class Labyrint {
    private final int x; // Bredd på labyrinten
    private final int y; // Höjd på labyrinten
    private String headCoordinate; // Startkoordinat för generationsalgoritmen
    private LinkedList<String> availableMoves;
    private String maze;
    private LinkedList<String> visitedCoordinates;
    private LinkedList<String> intersections;
    private boolean hasBackTracked;
    private double testV;

    public Labyrint(int x, int y) {
        this.x = x;
        this.y = y;
        this.testV = 0;
        this.headCoordinate = "" + (char) ('A' + y - 1) + x;
        this.availableMoves = null;
        this.maze = "";
        this.visitedCoordinates = new LinkedList<>();
        this.intersections = new LinkedList<>();
        this.hasBackTracked = false;

        generation();
    }

    public void generation() {
        while (true) {

            // Adds the current headCoordinate, where the algorithm is currently located, to the visitedCoordinates list
            if (!visitedCoordinates.contains(headCoordinate)) visitedCoordinates.add(headCoordinate);

            // Stops when the program has visited all possible moves
            if (visitedCoordinates.size() >= x * y) break;

            // The random backtracker logic
            double chance = Math.random();

            // Looks around the headCoordinate to see which moves are available
            availableMoves = lookForAvailableMoves(headCoordinate);

            if (!availableMoves.isEmpty()) {
                if (hasBackTracked) {
                    intersections.add(headCoordinate);
                    hasBackTracked = false;
                }

                // "Randomises" which availableCoordinate it chooses, using the decisionMaker function
                headCoordinate = availableMoves.get(decisionMaker(availableMoves));

                availableMoves.clear();

            } else {

                // If no available moves are found then it has to backtrack
                // Backtracking logic in this case:
                int idx = visitedCoordinates.indexOf(headCoordinate) - 1;

                // Backtracks one move
                if (idx > 0) {
                    headCoordinate = visitedCoordinates.get(idx);
                    hasBackTracked = true;
                }

                // Triggers if the backtracker has backed upp all the way back to the start
                // Finds the first available move where it starts up again:
                if (idx == 0) {
                    for (int i = 0; i < visitedCoordinates.size(); i++) {
                        if (!lookForAvailableMoves(visitedCoordinates.get(i)).isEmpty()) {
                            headCoordinate = visitedCoordinates.get(i);
                        }
                    }
                }
            }
        }
    }

    public int decisionMaker(LinkedList<String> availableMoves) {
        int sum = 0;
        double[] numbers = new double[availableMoves.size()];
        for (int i = 0; i < availableMoves.size(); i++) {
            String move = availableMoves.get(i);
            numbers[i] = move.charAt(0) + Integer.parseInt(move.substring(1)) - 'A' + 1;
        }

        double low = lowest(numbers);

        for (int i = 0; i < availableMoves.size(); i++) numbers[i] = Math.pow((numbers[i]), testV);
        for (int i = 0; i < availableMoves.size(); i++) sum += (int) numbers[i];
        for (int i = 0; i < availableMoves.size(); i++) numbers[i] /= sum;

        double decision = Math.random();

        for (int i = 0; i < availableMoves.size(); i++) {
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

    public double lowest(double[] arr) {
        double low = arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            if (low > arr[i] && arr[i] != 0) low = arr[i];
        }
        return low;
    }

    public LinkedList<String> getIntersections() {
        return intersections;
    }

    public LinkedList<String> getVisitedCoordinates() {
        return visitedCoordinates;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}