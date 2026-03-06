package LabyrintSkapare;

import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        Labyrint lab = new Labyrint(10 ,10);
        Cell[][] cells = new MakeCells(lab).getCells();

        DFS dfs = new DFS(cells);
        HogerHandSolver hogerHandSolver = new HogerHandSolver(lab);
        TestSolver testSolver = new TestSolver();
        TestSolver2 testSolver2 = new TestSolver2();
        RandomSolver randomSolver = new RandomSolver(cells);
        AStarSolver aStarSolver = new AStarSolver(lab);


        new LabyrintGrafik(lab, randomSolver, hogerHandSolver, testSolver2);
        System.out.println(randomSolver.getPath().size());
    }
}
