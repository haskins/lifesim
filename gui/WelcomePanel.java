package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import world.Manager;
import log.*;

/**
 * This class creates the first window that the user interacts with when the
 * project is started. The Welcome Panel explains what the simulator does, and
 * it allows the user to chose between a 1 and a 2 house simulation.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
@SuppressWarnings("serial")
public class WelcomePanel extends JPanel {

    private JPanel contentPane;

    /**
     * Constructs the welcome panel.
     *
     * @param panel The panel to turn into the Welcome Panel.
     */
    public WelcomePanel(JPanel panel) {

        contentPane = panel;

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 500));
        setLayout(null);

        // logo
        ImageLoader f = new ImageLoader("images/logo.png");
        f.setBounds(10, 11, 480, 171);
        add(f);

        // labels
        JLabel lblPleaseSelectThe = new JLabel(
                "Please select the number of houses you would like to simulate");
        lblPleaseSelectThe.setHorizontalAlignment(SwingConstants.CENTER);
        lblPleaseSelectThe.setBounds(10, 405, 480, 14);
        add(lblPleaseSelectThe);

        // welcome message
        JTextArea txtWelcome = new JTextArea();
        txtWelcome.setEditable(false);
        txtWelcome.setFont(new Font("Arial", Font.PLAIN, 12));
        txtWelcome.setLineWrap(true);
        txtWelcome.setWrapStyleWord(true);
        txtWelcome.setText("Welcome to Life Simulator. "
                + "This program will simulate 1 or 2 families through a certain amount of cycles. "
                + "Random events happen each cycle, these events can affect a persons lifespan and money. "
                + "A persons lifespan is determined by their happiness level. "
                + "The happiness level also effects the chance of having kids and getting remarried, if their partner dies. "
                + "Simulations are quick, so feel free to run as many as you like, see what options result in the best results. "
                + "Have fun!");
        txtWelcome.setBounds(10, 194, 480, 100);
        add(txtWelcome);

        // number of houses buttons
        ButtonGroup bg = new ButtonGroup();
        final JToggleButton tbtn1 = new JToggleButton("1");
        tbtn1.setBounds(210, 431, 44, 23);
        bg.add(tbtn1);
        add(tbtn1);

        final JToggleButton tbtn2 = new JToggleButton("2");
        tbtn2.setBounds(266, 431, 44, 23);
        bg.add(tbtn2);
        add(tbtn2);

        JLabel lblCycles = new JLabel("Cycles");
        lblCycles.setBounds(90, 305, 61, 28);
        add(lblCycles);

        JLabel lblSpeed = new JLabel("Speed");
        lblSpeed.setBounds(90, 355, 61, 28);
        add(lblSpeed);


        //speed
        final JSlider sldSpeed = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        sldSpeed.setBounds(150, 353, 200, 50);

        sldSpeed.setSnapToTicks(true);
        sldSpeed.setPaintTicks(true);
        sldSpeed.setMajorTickSpacing(1);

        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(1, new JLabel("Slow"));
        labels.put(3, new JLabel("Medium"));
        labels.put(5, new JLabel("Fast"));
        sldSpeed.setLabelTable(labels);

        sldSpeed.setPaintLabels(true);

        add(sldSpeed);



        final JSlider sldCycle = new JSlider(JSlider.HORIZONTAL, 100, 1000, 100);
        sldCycle.setPaintLabels(true);
        sldCycle.setSnapToTicks(true);
        sldCycle.setPaintTicks(true);
        sldCycle.setMajorTickSpacing(100);
        sldCycle.setBounds(153, 295, 337, 52);
        add(sldCycle);

        // continue button
        JButton btnContinue = new JButton("Continue");
        btnContinue.setBounds(210, 466, 100, 23);
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * checks to see if there are any empty places that need to be
                 * filled in
                 */
                if (tbtn1.isSelected() == false && tbtn2.isSelected() == false) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane
                            .showMessageDialog(
                            null,
                            "You need to select how many houses you would like to simulate",
                            "More Information Needed",
                            JOptionPane.INFORMATION_MESSAGE);

                } /*
                 * otherwise it will submit the information
                 */ else {

                    int speed = 0;
                    switch (sldSpeed.getValue()) {
                        case 1:
                            speed = 100000000;
                            break;
                        case 2:
                            speed = 50000000;
                            break;
                        case 3:
                            speed = 10000000;
                            break;
                        case 4:
                            speed = 5000000;
                            break;
                        case 5:
                            speed = 1000000;
                            break;
                    }

                    // tells Manager how many houses are to be created
                    if (tbtn1.isSelected()) {
                        Manager.setParams(sldCycle.getValue(), speed, 1);

                        //cp = new ConfigPanel(contentPane);
                        ConfigPanel cp = new ConfigPanel(contentPane, 1);
                        DisplayPanel dp = new DisplayPanel(contentPane);
                        ResultPanel rp = new ResultPanel(contentPane, 1);

                        /*
                         * adding these to the contentpane allows us to cycle
                         * through the windows and also only pass one paramter
                         * to the next frame
                         */
                        contentPane.add(cp, "Config Panel");
                        contentPane.add(dp, "Display Panel");
                        contentPane.add(rp, "Result Panel");

                    } else if (tbtn2.isSelected()) {
                        Manager.setParams(sldCycle.getValue(), speed, 2);

                        //cp = new ConfigPanel(contentPane);
                        ConfigPanel cp = new ConfigPanel(contentPane, 2);
                        ConfigPanel cp2 = new ConfigPanel(contentPane, 2);
                        DisplayPanel dp = new DisplayPanel(contentPane);
                        ResultPanel rp = new ResultPanel(contentPane, 2);

                        LogWriter.setDP(dp);
                        
                        /*
                         * adding these to the contentpane allows us to cycle
                         * through the windows and also only pass one paramter
                         * to the next frame
                         */
                        contentPane.add(cp, "Config Panel");
                        contentPane.add(cp2, "Config Panel 2");
                        contentPane.add(dp, "Display Panel");
                        contentPane.add(rp, "Result Panel");


                    }

                    CardLayout cardLayout = (CardLayout) contentPane
                            .getLayout();
                    cardLayout.next(contentPane);

                }
            }
        });
        add(btnContinue);
    }
}