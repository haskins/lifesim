/**
 * This is the House Panel, it draws all information related to that specific
 * house.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package gui;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.*;
import world.Building;

@SuppressWarnings("serial")
public final class HousePanel extends JPanel {

    // holds images to be drawn
    private Berry maleAdult, femaleAdult, maleChild, femaleChild;
    // keeps track of amounts to be used for labels
    private int numMaleAdult = 0, numFemaleAdult = 0, numMaleChild = 0, numFemaleChild = 0, bankBalance = 0;
    private String name = "?";
    // for drawing the people dots on the panel
    private int posPersonDots_X = 195, posPersonDots_Y = 0;
    // to format currency
    private DecimalFormat formatter = new DecimalFormat("'$'#,###");
    private boolean singleImages = false;
    // labels that will be updated in paint method
    private JLabel lblDollar, lblPeople, lblLastName, lblFemA, lblFemK, lblMaleK, lblMaleA;
    private Building building;

    /**
     * Updates info for required UI updates.
     *
     * @param d the bank balance
     * @param ma number of male adults
     * @param fa number of female adults
     * @param mc number of male children
     * @param fc number of female children
     */
    public void updateInfo(int d, int ma, int fa, int mc, int fc) {
        bankBalance = d;
        numMaleAdult = ma;
        numFemaleAdult = fa;
        numMaleChild = mc;
        numFemaleChild = fc;
    }

    /**
     * Sets info for required UI updates.
     *
     * @param s name of family
     * @param b the building object
     */
    void setInfo(String s, Building b) {
        lblDollar.setText("$" + 0);
        lblLastName.setText(s);
        numMaleAdult = 1;
        numFemaleAdult = 1;
        lblPeople.setText(numMaleAdult + numFemaleAdult + numMaleChild
                + numFemaleChild + " People");
        building = b;
    }

    /**
     * Creates arrays of all needed images.
     */
    public void createImages() {

        // people
        maleAdult = new Berry("gui/images/genders/male.png", 0, 0, 25, 25);
        maleChild = new Berry("gui/images/genders/male_s.png", 0, 6, 15, 15);
        femaleAdult = new Berry("gui/images/genders/female.png", 0, 0, 25,
                25);
        femaleChild = new Berry("gui/images/genders/female_s.png", 0, 6,
                15, 15);
    }

    /*
     * Constructor for panel.
     */
    public HousePanel() {

        createImages(); // creates all the images needed

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 100));
        setLayout(null);

        // label house, shouldn't have to change this
        JLabel lblHouse = new JLabel("House");
        lblHouse.setHorizontalAlignment(SwingConstants.CENTER);
        lblHouse.setBounds(10, 25, 75, 14);
        add(lblHouse);

        // seperators
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setForeground(Color.BLACK);
        separator.setBounds(90, 11, 2, 78);
        add(separator);
        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setForeground(Color.BLACK);
        separator_1.setBounds(190, 11, 2, 78);
        add(separator_1);

        // bank account for house, will update
        lblDollar = new JLabel("" + bankBalance);
        lblDollar.setHorizontalAlignment(SwingConstants.CENTER);
        lblDollar.setBounds(0, 50, 80, 14);
        add(lblDollar);

        // number of people in the house
        lblPeople = new JLabel("000 People");
        lblPeople.setHorizontalAlignment(SwingConstants.CENTER);
        lblPeople.setBounds(10, 75, 75, 14);
        add(lblPeople);

        // last name of the house
        lblLastName = new JLabel(name);
        lblLastName.setHorizontalAlignment(SwingConstants.CENTER);
        lblLastName.setBounds(10, 11, 75, 14);
        add(lblLastName);

        // labels will be used if max size have been reached
        lblFemA = new JLabel("");
        lblFemA.setBounds(400, 21, 61, 11);
        add(lblFemA);

        lblFemK = new JLabel("");
        lblFemK.setBounds(400, 63, 61, 16);
        add(lblFemK);

        lblMaleK = new JLabel("");
        lblMaleK.setBounds(270, 63, 61, 16);
        add(lblMaleK);

        lblMaleA = new JLabel("");
        lblMaleA.setBounds(270, 21, 61, 11);
        add(lblMaleA);

    }

    /**
     * Checks to see if the current line is full, if so it will then move to the
     * next line.
     */
    void imageRowLineChecker() {
        if (posPersonDots_X > 470) {
            posPersonDots_X = 195;
            posPersonDots_Y += 36;
        }
    }

    /**
     * Checks to see if the columns are full, if they are then it tells the
     * program to only display single images.
     */
    void imageColumnChecker() {
        if (posPersonDots_Y >= 73) {
            singleImages = true;
        }
    }

    /**
     * Redraws the panel.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        posPersonDots_X = 195;
        posPersonDots_Y = 0;

        Berry b;

        // updates these two labels
        lblPeople.setText(numMaleAdult + numFemaleAdult + numMaleChild
                + numFemaleChild + " People");
        lblDollar.setText(formatter.format(bankBalance));

        g.drawImage(building.getImage(), 90, 0, building.getWidth(),
                building.getHeight(), null);

        if (!singleImages) {
            // prints male adult
            b = maleAdult;
            for (int x = 0; x < numMaleAdult; x++) {
                g.drawImage(b.getImage(), posPersonDots_X, posPersonDots_Y,
                        b.getWidth(), b.getHeight(), null);
                posPersonDots_X += b.getWidth() + 1;
                imageRowLineChecker();
                imageColumnChecker();
            }

            // prints female adult
            b = femaleAdult;
            for (int x = 0; x < numFemaleAdult; x++) {
                g.drawImage(b.getImage(), posPersonDots_X, posPersonDots_Y,
                        b.getWidth(), b.getHeight(), null);
                posPersonDots_X += b.getWidth() + 1;
                imageRowLineChecker();
                imageColumnChecker();
            }

            // prints male children
            b = maleChild;
            for (int x = 0; x < numMaleChild; x++) {
                g.drawImage(b.getImage(), posPersonDots_X,
                        posPersonDots_Y + b.getY(), b.getWidth(),
                        b.getHeight(), null);
                posPersonDots_X += b.getWidth() + 1;
                imageRowLineChecker();
                imageColumnChecker();
            }

            // prints female children
            b = femaleChild;
            for (int x = 0; x < numFemaleChild; x++) {
                g.drawImage(b.getImage(), posPersonDots_X,
                        posPersonDots_Y + b.getY(), b.getWidth(),
                        b.getHeight(), null);
                posPersonDots_X += b.getWidth() + 1;
                imageRowLineChecker();
                imageColumnChecker();
            }
        } else {

            // prints male adult
            b = maleAdult;
            g.drawImage(b.getImage(), 232, 15, b.getWidth(), b.getHeight(),
                    null);
            lblMaleA.setText("x " + numMaleAdult);

            // prints female adult
            b = femaleAdult;
            g.drawImage(b.getImage(), 363, 15, b.getWidth(), b.getHeight(),
                    null);
            lblFemA.setText("x " + numFemaleAdult);

            // prints male children
            b = maleChild;
            g.drawImage(b.getImage(), 237, 64, b.getWidth(), b.getHeight(),
                    null);
            lblMaleK.setText("x " + numMaleChild);

            // prints female children
            b = femaleChild;
            g.drawImage(b.getImage(), 368, 64, b.getWidth(), b.getHeight(),
                    null);
            lblFemK.setText("x " + numFemaleChild);
        }
    }
}