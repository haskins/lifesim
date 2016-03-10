/**
 * This is the Display Panel, it displays the entire view of the simulation.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import world.Event;
import world.Manager;

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel {

    private JPanel contentPane;
    public static JTextArea logView;
    private static JScrollPane sp;
    public static JProgressBar progressBar;
    private final AnimPanel ap;

    /**
     * Updates the GUI.
     */
    public void updateEverything() {
        update(getGraphics());

    }

    /**
     * Cycles to the next Panel.
     */
    public void nextPanel() {
        CardLayout cardLayout = (CardLayout) contentPane
                .getLayout();
        cardLayout.next(contentPane);
    }

    /**
     * Updates the data of both House panels and the Event panel.
     *
     * @param bb1 Bank balance for house 1
     * @param ma1 number of male adults in house 1
     * @param fa1 number of female adults in house 1
     * @param mc1 number of male children in house 1
     * @param fc1 number of female children in house 1
     * @param e   the current event
     * @param bb2 Bank balance for house 2
     * @param ma2 number of male adults in house 2
     * @param fa2 number of female adults in house 2
     * @param mc2 number of male children in house 2
     * @param fc2 number of female children in house 2
     */
    public static void updateData(int bb1, int ma1, int fa1, int mc1, int fc1,
            Event e, int bb2, int ma2, int fa2, int mc2, int fc2) {
        AnimPanel.hp1.updateInfo(bb1, ma1, fa1, mc1, fc1);
        AnimPanel.hp2.updateInfo(bb2, ma2, fa2, mc2, fc2);
        AnimPanel.ep.updateInfo(e);
    }

    /**
     * Updates the data of House panel and the Event panel.
     *
     * @param bb Bank balance for house 1
     * @param ma number of male adults in house 1
     * @param fa number of female adults in house 1
     * @param mc number of male children in house 1
     * @param fc number of female children in house 1
     * @param e  the current event
     */
    public static void updateData(int bb, int ma, int fa, int mc, int fc,
            Event e) {
        AnimPanel.hp1.updateInfo(bb, ma, fa, mc, fc);
        AnimPanel.ep.updateInfo(e);
    }

    /**
     * Constructor for Display Panel.
     */
    public DisplayPanel(JPanel panel) {

        contentPane = panel;

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 500));
        setLayout(null);

        // log view
        logView = new JTextArea();
        logView.setEditable(false);
        logView.setBounds(12, 358, 476, 200);

        //auto scrolling
        DefaultCaret caret = (DefaultCaret) logView.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // scroll
        sp = new JScrollPane(logView);
        sp.setBounds(12, 358, 476, 100);
        add(sp);

        // panel to view houses and events
        ap = new AnimPanel(Manager.getNumberHouses());
        ap.setBounds(0, 10, 500, 320);
        add(ap);

        // progress bar
        progressBar = new JProgressBar();
        progressBar.setBounds(12, 336, 476, 14);
        add(progressBar);


        JButton btnFB = new JButton("Force Next");
        btnFB.setBounds(122, 474, 100, 14);
        btnFB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager.forceBarrier();
            }
        });
        add(btnFB);
        
        
               JButton btnFR = new JButton("Force Results");
        btnFR.setBounds(227, 474, 100, 14);
        btnFR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manager.forceResults();
            }
        });
        add(btnFR);


    }

    /**
     * Repaints the panel.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
