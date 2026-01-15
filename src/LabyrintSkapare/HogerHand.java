package LabyrintSkapare;


public class HogerHand {

    /*
     Flyttar en position ett steg i given riktning
     Ex: "A1" + HOGER → "A2"
     */
    static String move(String pos, Riktning d) {
        char row = pos.charAt(0);                 // Rad, t.ex. 'A'
        int col = Integer.parseInt(pos.substring(1)); // Kolumn, t.ex. 1

        // Justerar koordinaten beroende på riktning
        switch (d) {
            case UPP:     row--; break;
            case NER:     row++; break;
            case HOGER:   col++; break;
            case VANSTER: col--; break;
        }

        // Bygger ny koordinat som String
        // row, collumn
        return "" + row + col;
    }

    /*
     Enum som representerar riktningar i labyrinten.
     Innehåller även hjälpmetoder för rotation.
     */
    enum Riktning {
        UPP, HOGER, NER, VANSTER;

        // Returnerar riktningen till höger (90°)
        Riktning right() {
            return values()[(ordinal() + 1) % 4];
        }

        // Returnerar riktningen till vänster (90°)
        Riktning left() {
            return values()[(ordinal() + 3) % 4];
        }

        // Returnerar motsatt riktning (180°)
        Riktning back() {
            return values()[(ordinal() + 2) % 4];
        }
    }

    public static void main(String[] args) {

        // Skapar labyrinten
        Labyrint lab = new Labyrint(5, 5, 30);

        // Skriver ut labyrinten visuellt
        System.out.println(lab.mazeBuilder());
        System.out.println(lab);

        // Startposition och mål
        String position = "E5";
        String goal = "A1";

        // Startar med att titta åt höger
        Riktning riktning = Riktning.HOGER;

        // Säkerhetsräknare för att undvika oändlig loop
        int safety = 0;

        /*
         Huvudloop för högerhandsregeln.
         Fortsätter tills målet nås eller säkerhetsgränsen uppnås.
         */
        while (!position.equals(goal) && safety++ < 1000) {

            /*
             * Högerhandsregel (relativt aktuell riktning):
             1. höger
             2. rakt fram
             3. vänster
             4. bakåt
             */
            Riktning[] checks = {
                    riktning.right(),
                    riktning,
                    riktning.left(),
                    riktning.back()
            };

            // Testar riktningarna i prioriterad ordning
            for (Riktning d : checks) {
                String next = move(position, d);

                // Flytta till första möjliga riktning
                if (lab.isConnected(position, next)) {
                    riktning = d;
                    position = next;
                    break;
                }
            }
        }

        // Slutresultat
        System.out.println("Slutposition: " + position);
        System.out.println("Antal steg: " + safety);
    }
}

