package LabyrintSkapare;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LabyrintGrafik {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(5, 5));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JPanel panel = new JPanel();

                panel.setPreferredSize(new Dimension(200, 200));

                panel.setBorder(new LineBorder(Color.BLACK, 1));

                frame.add(panel);
            }
        }

       frame.setSize(500, 500);
       // frame.pack();
       frame.setVisible(true);
       frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }




}
