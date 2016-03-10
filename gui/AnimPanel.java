/**
 * This is the Animation Panel, everything that happens during a simulation is
 * drawn on top of this panel.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package gui;

import java.awt.Dimension;
import javax.swing.JPanel;
import world.Building;

@SuppressWarnings("serial")
public class AnimPanel extends JPanel {

    static HousePanel hp1, hp2;
    static EventPanel ep;

    /**
     * Used to relay information into the house panels. This is used only when
     * there are two houses in the simulation.
     *
     * @param b1 Building object for house 1
     * @param s1 last name of house 1
     * @param b2 Building object for house 2
     * @param s2 last name of house 2
     */
    public static void setInfo(Building b1, String s1, Building b2, String s2) {
        hp1.setInfo(s1, b1);
        hp2.setInfo(s2, b2);
    }

    /**
     * Used to relay information into the house panels. This is used only when
     * there is one house in the simulation.
     *
     * @param b Building object for house 1
     * @param s last name of house 1
     */
    public static void setInfo(Building b, String s) {
        hp1.setInfo(s, b);
    }

    /**
     * Constructor for the Animation Panel.
     *
     * @param hn the number of houses in the simulation
     */
    public AnimPanel(int hn) {

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 320));
        setLayout(null);

        //creates house 2 only if user is running a two house simulation
        if (hn == 2) {

            // for house 2
            hp2 = new HousePanel();
            hp2.setBounds(0, 110, 500, 100);
            add(hp2);
        }

        // for house 1
        hp1 = new HousePanel();
        hp1.setBounds(0, 0, 500, 100);
        add(hp1);

        // for all events
        ep = new EventPanel();
        ep.setBounds(0, 220, 500, 100);
        add(ep);
    }
}