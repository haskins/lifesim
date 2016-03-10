package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import world.Building;
import javax.swing.JTable;
import world.Manager;

/**
 * The result panel is the final panel for the GUI that the user interacts. It
 * shows the results of the simulation, which includes all of the statistics for
 * each house.
 */
@SuppressWarnings("serial")
public class ResultPanel extends JPanel {

    @SuppressWarnings("unused")
    private JPanel contentPane;
    Building b1, b2;
    JLabel lblName1, lblName2, lblMoney1, lblMoney2, lblPeople1, lblPeople2, lblPlace1, lblPlace2;
    DecimalFormat formatter = new DecimalFormat("'$'#,###");
    private JTable table;
    String win1 = null, win2 = null, win3 = null;
    private int numHouses;

    public void setInfo(Building bu1, String n1, int m1, int p1,
            Building bu2, String n2, int m2, int p2) {
        //house 1
        b1 = bu1;
        lblName1.setText(n1);
        lblMoney1.setText(formatter.format(m1));
        lblPeople1.setText(p1 + " People");

        //house 2
        b2 = bu2;
        lblName2.setText(n2);
        lblMoney2.setText(formatter.format(m2));
        lblPeople2.setText(p2 + " People");

        if (m1 > m2) {
            win1 = n1;
        } else if (m1 < m2) {
            win1 = n2;
        } else {
            win1 = "Tie";
        }

        if (p1 == 0 && p2 == 0) {
            win2 = "Tie, no people";
        } else if (p1 == 0) {
            win2 = n2;
        } else if (p2 == 0) {
            win2 = n1;
        } else if (m1 / p1 > m2 / p2) {
            win2 = n1;
        } else {
            win2 = n2;
        }

        if (p1 > p2) {
            win3 = n1;
        } else if (p1 < p2) {
            win3 = n2;
        } else {
            win3 = "Tie";
        }

        table.setValueAt(win1, 0, 1);
        table.setValueAt(win2, 1, 1);
        table.setValueAt(win3, 2, 1);
    }

    public void setInfo(Building bu1, String n1, int m1, int p1) {
        //house 1
        b1 = bu1;
        lblName1.setText(n1);
        lblMoney1.setText(formatter.format(m1));
        lblPeople1.setText(p1 + " People");
    }

    public ResultPanel(JPanel panel, int x) {

        contentPane = panel;
        numHouses = x;

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 500));
        setLayout(null);
        setLayout(null);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBounds(240, 109, 10, 226);
        add(separator);

        JLabel lblResults = new JLabel("Results");
        lblResults.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        lblResults.setHorizontalAlignment(SwingConstants.CENTER);
        lblResults.setBounds(6, 6, 488, 96);
        add(lblResults);

        if (numHouses == 2) {

            //House 1
            lblName1 = new JLabel("");
            lblName1.setHorizontalAlignment(SwingConstants.CENTER);
            lblName1.setBounds(75, 221, 100, 16);
            add(lblName1);

            lblMoney1 = new JLabel("");
            lblMoney1.setHorizontalAlignment(SwingConstants.CENTER);
            lblMoney1.setBounds(75, 257, 100, 16);
            add(lblMoney1);

            lblPeople1 = new JLabel("");
            lblPeople1.setHorizontalAlignment(SwingConstants.CENTER);
            lblPeople1.setBounds(75, 285, 100, 16);
            add(lblPeople1);

            lblPlace1 = new JLabel("");
            lblPlace1.setHorizontalAlignment(SwingConstants.CENTER);
            lblPlace1.setBounds(75, 432, 100, 16);
            add(lblPlace1);

            //House 2
            lblName2 = new JLabel("");
            lblName2.setHorizontalAlignment(SwingConstants.CENTER);
            lblName2.setBounds(325, 221, 100, 16);
            add(lblName2);

            lblMoney2 = new JLabel("");
            lblMoney2.setHorizontalAlignment(SwingConstants.CENTER);
            lblMoney2.setBounds(325, 257, 100, 16);
            add(lblMoney2);

            lblPeople2 = new JLabel("");
            lblPeople2.setHorizontalAlignment(SwingConstants.CENTER);
            lblPeople2.setBounds(325, 285, 100, 16);
            add(lblPeople2);

            lblPlace2 = new JLabel("");
            lblPlace2.setHorizontalAlignment(SwingConstants.CENTER);
            lblPlace2.setBounds(325, 432, 100, 16);
            add(lblPlace2);


            // Create columns names
            String columnNames[] = {"Column 1", "Column 2"};

            // Create some data
            String dataValues[][] = {
                {"Total Cash", win1},
                {"Cash per Person", win2},
                {"Total People", win3},};

            table = new JTable(dataValues, columnNames);
            table.setBounds(98, 403, 300, 86);
            add(table);

            JLabel lblWinners = new JLabel("Winners");
            lblWinners.setHorizontalAlignment(SwingConstants.CENTER);
            lblWinners.setFont(new Font("Dialog", Font.PLAIN, 25));
            lblWinners.setBounds(6, 359, 488, 33);
            add(lblWinners);

        } else {

            //House 1
            lblName1 = new JLabel("");
            lblName1.setHorizontalAlignment(SwingConstants.CENTER);
            lblName1.setBounds(75, 221, 100, 16);
            add(lblName1);

            lblMoney1 = new JLabel("");
            lblMoney1.setHorizontalAlignment(SwingConstants.CENTER);
            lblMoney1.setBounds(75, 257, 100, 16);
            add(lblMoney1);

            lblPeople1 = new JLabel("");
            lblPeople1.setHorizontalAlignment(SwingConstants.CENTER);
            lblPeople1.setBounds(75, 285, 100, 16);
            add(lblPeople1);

            lblPlace1 = new JLabel("");
            lblPlace1.setHorizontalAlignment(SwingConstants.CENTER);
            lblPlace1.setBounds(75, 432, 100, 16);
            add(lblPlace1);

        }


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (numHouses == 2) {
            g.drawImage(b2.getImage(), 325, 109, b2.getWidth(), b2.getHeight(), null);
        }
        g.drawImage(b1.getImage(), 75, 109, b1.getWidth(), b1.getHeight(), null);
    }
}