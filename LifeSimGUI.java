
/**
 * This is the main GUI for the program, everything is initiated from here.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
import gui.*;
import java.awt.CardLayout;
import javax.swing.*;

public class LifeSimGUI {

    /**
     * A constructor used to initialize the window.
     */
    private void displayGUI() {
        JFrame window = new JFrame("Life Simulation");
        window.setResizable(false);
        window.setAlwaysOnTop(true);
        window.setTitle("Life Simulator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * This is the panel where everything is put into and cycled through
         * using CardLayout.
         */
        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new CardLayout());

        /*
         * This adds the first panel to the contentPane, the rest are added in
         * WelcomePanel once the user selects how many houses they would like to
         * simulate.
         */
        WelcomePanel wp = new WelcomePanel(contentPane);
        contentPane.add(wp, "Welcome Panel");

        //Packs up eveything and readies it for display
        window.setContentPane(contentPane);
        window.pack();
        window.setLocationByPlatform(true);
        window.setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LifeSimGUI().displayGUI();
            }
        });
    }
}
