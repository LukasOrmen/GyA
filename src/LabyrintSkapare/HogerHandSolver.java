
package LabyrintSkapare;

import java.util.LinkedList;

public class HogerHandSolver implements Solver {
    private final Cell[][] cells;
    private final int width, height;
    // start
    private int x = 0;
    private int y = 0;
    private Riktning riktning = Riktning.HOGER;
    private final LinkedList<String> path = new LinkedList<>();
    public HogerHandSolver(Labyrint lab) {
        cells = new MakeCells(lab).getCells();
        // Sparar labyrintens storlek
        height = cells.length;
        width = cells[0].length;
        // Lägger till startpositionen i vägen
        path.add(toCoord());
        solve(width - 1, height - 1);
    }


    // högerhandsalgoritmen tills mål
    public void solve(int goalX, int goalY) {

        // Fortsätter tills vi når målet
        while (x != goalX || y != goalY) {

            // Högerhandsregeln testar riktningar i denna ordning
            for (Riktning d : new Riktning[]{
                    riktning.right(),  // testa höger först
                    riktning,          // sedan rakt fram
                    riktning.left(),   // sedan vänster
                    riktning.back()    // sist bakåt
            }) {

                // Om vi kan röra oss i den riktningen
                if (canMove(d)) {
                    // Flytta i den riktningen
                    move(d);
                    // Avbryt loopen eftersom vi redan flyttat
                    break;
                }
            }
        }

        // Skriver ut resultatet i terminalen
        System.out.println("Högerhand hittade väg");
        System.out.println("Väg: " + path);
        System.out.println("Antal steg: " + path.size());
    }


    // Kontrollerar om det går att röra sig i en viss riktning
    private boolean canMove(Riktning d) {

        // Hämtar cellen där vi står just nu
        Cell c = cells[y][x];

        // Kontroll för riktning UPP
        if (d == Riktning.UPP) {
            // Kan gå upp om det inte finns en vägg och vi inte är längst upp
            return c.getTop() == 0 && y > 0;
        }

        // Kontroll för riktning NER
        if (d == Riktning.NER) {
            // Kan gå ner om det inte finns en vägg och vi inte är längst ner
            return c.getBottom() == 0 && y < height - 1;
        }

        // Kontroll för riktning HÖGER
        if (d == Riktning.HOGER) {
            // Kan gå höger om det inte finns en vägg och vi inte är längst till höger
            return c.getRight() == 0 && x < width - 1;
        }

        // Kontroll för riktning VÄNSTER
        if (d == Riktning.VANSTER) {
            // Kan gå vänster om det inte finns en vägg och vi inte är längst till vänster
            return c.getLeft() == 0 && x > 0;
        }

        // Om ingen riktning matchar returneras false
        return false;
    }


    // Flyttar positionen i labyrinten
    private void move(Riktning d) {

        // Uppdaterar riktningen vi tittar åt
        riktning = d;

        // Flyttar positionen beroende på riktning

        if (d == Riktning.UPP) {
            y--; // flytta upp
        }

        if (d == Riktning.NER) {
            y++; // flytta ner
        }

        if (d == Riktning.HOGER) {
            x++; // flytta höger
        }

        if (d == Riktning.VANSTER) {
            x--; // flytta vänster
        }

        // Lägger till den nya positionen i vägen
        path.add(toCoord());
    }


    // Gör om koordinater till formatet A1, B3 osv
    private String toCoord() {
        return "" + (char) ('A' + y) + (x + 1);
    }

    // Enum som beskriver de fyra riktningarna
    public enum Riktning {

        UPP, HOGER, NER, VANSTER;

        // Returnerar riktningen till höger
        public Riktning right() {
            return values()[(ordinal() + 1) % 4];
        }

        // Returnerar riktningen till vänster
        public Riktning left() {
            return values()[(ordinal() + 3) % 4];
        }

        // Returnerar motsatt riktning (180 grader)
        public Riktning back() {
            return values()[(ordinal() + 2) % 4];
        }
    }


    // Returnerar vägen genom labyrinten
    public LinkedList<String> getPath() {
        return path;
    }
}
