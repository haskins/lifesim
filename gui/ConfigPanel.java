/**
 * This is the Configuration Panel. This aids the user in selecting the
 * information for each house.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package gui;

import data.*;
import world.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class ConfigPanel extends JPanel {

    private JPanel cp;
    private static int count;

    /**
     * Cycles to the next Panel.
     */
    public void nextPanel() {
        CardLayout cardLayout = (CardLayout) cp.getLayout();
        cardLayout.next(cp);
    }

    /**
     * Constructor for Configuration Panel.
     *
     * @param panel the 'array' of panels to be cycled through
     * @param hn    the number of houses in the simulation
     */
    public ConfigPanel(JPanel panel, int hn) {

        cp = panel;
        count = hn;

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 500));
        setLayout(null);

        // jobs


        final JComboBox<Job> cbMaleJob = new JComboBox(Job.values());
        cbMaleJob.setBounds(133, 127, 120, 20);
        cbMaleJob.setSelectedIndex(-1);
        add(cbMaleJob);

        final JComboBox<Job> cbFemJob = new JComboBox(Job.values());
        cbFemJob.setBounds(278, 127, 120, 20);
        cbFemJob.setSelectedIndex(-1);
        add(cbFemJob);

        // names
        final JTextField tfMaleName = new JTextField();
        tfMaleName.setBounds(133, 97, 120, 20);
        add(tfMaleName);
        tfMaleName.setColumns(10);

        final JTextField tfFemName = new JTextField();
        tfFemName.setColumns(10);
        tfFemName.setBounds(278, 97, 120, 20);
        add(tfFemName);

        final JTextField tfFamilyName = new JTextField();
        tfFamilyName.setColumns(10);
        tfFamilyName.setBounds(133, 204, 120, 20);
        add(tfFamilyName);

        // house type and location
        final JComboBox<HouseType> cbHouseType = new JComboBox(HouseType.values());
        cbHouseType.setBounds(133, 279, 120, 20);
        cbHouseType.setSelectedIndex(-1);
        add(cbHouseType);

        final JComboBox<Location> cbCountry = new JComboBox(Location.values());
        cbCountry.setEnabled(false);
        cbCountry.setBounds(133, 323, 120, 20);
        cbCountry.setSelectedIndex(-1);
        add(cbCountry);

        // kids
        final ButtonGroup kidsGroup = new ButtonGroup();
        final JRadioButton rKidsYes = new JRadioButton("Yes");
        rKidsYes.setBounds(133, 234, 57, 20);
        final JRadioButton rKidsNo = new JRadioButton("No");
        rKidsNo.setBounds(192, 234, 61, 20);
        kidsGroup.add(rKidsYes);
        kidsGroup.add(rKidsNo);
        add(rKidsYes);
        add(rKidsNo);

        // beard
        final ButtonGroup beardGroup = new ButtonGroup();
        final JRadioButton rBeardNo = new JRadioButton("No");
        rBeardNo.setBounds(192, 155, 61, 20);
        beardGroup.add(rBeardNo);
        final JRadioButton rBeardYes = new JRadioButton("Yes");
        rBeardYes.setBounds(133, 155, 57, 20);
        beardGroup.add(rBeardYes);
        add(rBeardYes);
        add(rBeardNo);

        // labels
        JLabel lblMale = new JLabel("Male");
        lblMale.setHorizontalAlignment(SwingConstants.CENTER);
        lblMale.setBounds(133, 77, 120, 14);
        add(lblMale);

        JLabel lblFemale = new JLabel("Female");
        lblFemale.setHorizontalAlignment(SwingConstants.CENTER);
        lblFemale.setBounds(278, 77, 120, 14);
        add(lblFemale);

        JLabel lblName = new JLabel("Name");
        lblName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblName.setBounds(70, 97, 57, 20);
        add(lblName);

        JLabel lblJob = new JLabel("Job");
        lblJob.setHorizontalAlignment(SwingConstants.RIGHT);
        lblJob.setBounds(70, 127, 57, 20);
        add(lblJob);

        JLabel lblBeard = new JLabel("Beard");
        lblBeard.setHorizontalAlignment(SwingConstants.RIGHT);
        lblBeard.setBounds(70, 155, 57, 20);
        add(lblBeard);

        JLabel lblKids = new JLabel("Kids");
        lblKids.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKids.setBounds(70, 234, 57, 20);
        add(lblKids);

        JLabel lblLastName = new JLabel("Name");
        lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLastName.setBounds(70, 204, 57, 20);
        add(lblLastName);

        JLabel lblHouseType = new JLabel("Type");
        lblHouseType.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHouseType.setBounds(70, 279, 57, 20);
        add(lblHouseType);

        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(SystemColor.activeCaptionText);
        separator_1.setBounds(75, 66, 415, 14);
        add(separator_1);

        JLabel lblCountry = new JLabel("Location");
        lblCountry.setHorizontalAlignment(SwingConstants.RIGHT);
        lblCountry.setBounds(70, 323, 57, 20);
        add(lblCountry);

        // category labels
        JLabel lblPeople = new JLabel("PEOPLE");
        lblPeople.setFont(new Font("Dialog", Font.BOLD, 14));
        lblPeople.setBounds(12, 60, 70, 15);
        add(lblPeople);

        JLabel lblFamily = new JLabel("FAMILY");
        lblFamily.setFont(new Font("Dialog", Font.BOLD, 14));
        lblFamily.setBounds(12, 182, 70, 15);
        add(lblFamily);

        JLabel lblHouse = new JLabel("HOUSE");
        lblHouse.setFont(new Font("Dialog", Font.BOLD, 14));
        lblHouse.setBounds(12, 261, 70, 15);
        add(lblHouse);

        JLabel lblLocation = new JLabel("LOCATION");
        lblLocation.setFont(new Font("Dialog", Font.BOLD, 14));
        lblLocation.setBounds(12, 306, 80, 15);
        add(lblLocation);

        JLabel lblExtra = new JLabel("PRESETS");
        lblExtra.setFont(new Font("Dialog", Font.BOLD, 14));
        lblExtra.setBounds(12, 349, 80, 15);
        add(lblExtra);

        // separators
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setBounds(75, 188, 415, 14);
        add(separator);

        JSeparator separator_2 = new JSeparator();
        separator_2.setForeground(Color.BLACK);
        separator_2.setBounds(71, 267, 419, 14);
        add(separator_2);

        JSeparator separator_3 = new JSeparator();
        separator_3.setForeground(Color.BLACK);
        separator_3.setBounds(102, 312, 388, 14);
        add(separator_3);

        JSeparator separator_4 = new JSeparator();
        separator_4.setForeground(Color.BLACK);
        separator_4.setBounds(90, 355, 400, 14);
        add(separator_4);

        JSeparator separator_5 = new JSeparator();
        separator_5.setForeground(Color.BLACK);
        separator_5.setBounds(12, 450, 478, 14);
        add(separator_5);

        // buttons
        JButton btnContinue = new JButton("Continue");
        btnContinue.setBounds(216, 466, 100, 23);
        btnContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 * checks to see if there are any empty places that need to be
                 * filled in
                 */
                if (tfMaleName.getText().equals("")
                        || tfFemName.getText().equals("")
                        || tfFamilyName.getText().equals("")
                        || cbMaleJob.getSelectedIndex() == -1
                        || cbFemJob.getSelectedIndex() == -1
                        || cbHouseType.getSelectedIndex() == -1
                        || (rKidsYes.isSelected() == rKidsNo.isSelected())
                        || (rBeardYes.isSelected() == rBeardNo.isSelected())) {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null,
                            "You need to fill in all the information",
                            "More Information Needed",
                            JOptionPane.INFORMATION_MESSAGE);

                } //otherwise it will submit the information
                else {
                    System.out.println("Man - " + tfMaleName.getText() + " - "
                            + cbMaleJob.getSelectedItem() + " - "
                            + rBeardYes.isSelected());
                    System.out.println("Woman - " + tfFemName.getText() + " - "
                            + cbFemJob.getSelectedItem());
                    System.out.println("Family - " + tfFamilyName.getText()
                            + " - " + rKidsYes.isSelected());
                    System.out.println("House - "
                            + cbHouseType.getSelectedItem());
                    System.out.println("Location - "
                            + cbCountry.getSelectedItem());

                    Manager.setHouseInfo(new FamilyInfo((Job) cbMaleJob.getSelectedItem(),
                            rKidsYes.isSelected(), tfMaleName.getText(),
                            (Job) cbFemJob.getSelectedItem(), tfFemName.getText(),
                            (HouseType) cbHouseType.getSelectedItem(),
                            cbHouseType.getSelectedIndex(), tfFamilyName.getText(), cp));

                    if (count == 1) {
                        Manager.startSim();
                        nextPanel();
                    } else {
                        nextPanel();
                        count--;
                    }

                }
            }
        });
        add(btnContinue);

        JButton btnResetValues = new JButton("Reset");
        btnResetValues.setBounds(12, 466, 100, 23);
        btnResetValues.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Values inputted have been reset");

                // resets cb boxes
                cbMaleJob.setSelectedIndex(-1);
                cbFemJob.setSelectedIndex(-1);
                cbHouseType.setSelectedIndex(-1);
                cbCountry.setSelectedIndex(-1);

                // resets text fields
                tfMaleName.setText("");
                tfFemName.setText("");
                tfFamilyName.setText("");

                // resets radio buttons
                beardGroup.clearSelection();
                kidsGroup.clearSelection();
            }
        });
        add(btnResetValues);

        JButton btnHelp = new JButton("Help");
        btnHelp.setBounds(390, 466, 100, 23);
        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,
                        "Fill in the blanks with the required information, or select a preset.", "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(btnHelp);

        // presets
        JButton btnPreset1 = new JButton("Preset 1");
        btnPreset1.setFont(new Font("Dialog", Font.BOLD, 10));
        btnPreset1.setBounds(27, 424, 85, 15);
        btnPreset1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sets cb boxes
                cbMaleJob.setSelectedIndex(2);
                cbFemJob.setSelectedIndex(10);
                cbHouseType.setSelectedIndex(1);
                cbCountry.setSelectedIndex(1);

                // sets text fields
                tfMaleName.setText("Bob");
                tfFemName.setText("Mary");
                tfFamilyName.setText("Jackson");

                // sets radio buttons
                rBeardYes.setSelected(true);
                rKidsNo.setSelected(true);
            }
        });
        add(btnPreset1);

        JButton btnPreset2 = new JButton("Preset 2");
        btnPreset2.setFont(new Font("Dialog", Font.BOLD, 10));
        btnPreset2.setBounds(117, 424, 85, 15);
        btnPreset2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sets cb boxes
                cbMaleJob.setSelectedIndex(5);
                cbFemJob.setSelectedIndex(8);
                cbHouseType.setSelectedIndex(2);
                cbCountry.setSelectedIndex(3);

                // sets text fields
                tfMaleName.setText("Harry");
                tfFemName.setText("Ginny");
                tfFamilyName.setText("Potter");

                // sets radio buttons
                rBeardNo.setSelected(true);
                rKidsYes.setSelected(true);
            }
        });
        add(btnPreset2);

        JButton btnPreset3 = new JButton("Preset 3");
        btnPreset3.setFont(new Font("Dialog", Font.BOLD, 10));
        btnPreset3.setBounds(206, 424, 85, 15);
        btnPreset3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sets cb boxes
                cbMaleJob.setSelectedIndex(20);
                cbFemJob.setSelectedIndex(15);
                cbHouseType.setSelectedIndex(7);
                cbCountry.setSelectedIndex(3);

                // sets text fields
                tfMaleName.setText("Gerome");
                tfFemName.setText("Gertrude");
                tfFamilyName.setText("Garner");

                // sets radio buttons
                rBeardYes.setSelected(true);
                rKidsNo.setSelected(true);
            }
        });
        add(btnPreset3);

        JButton btnPreset4 = new JButton("Preset 4");
        btnPreset4.setFont(new Font("Dialog", Font.BOLD, 10));
        btnPreset4.setBounds(295, 424, 85, 15);
        btnPreset4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sets cb boxes
                cbMaleJob.setSelectedIndex(14); //plumber
                cbFemJob.setSelectedIndex(3); //attourney
                cbHouseType.setSelectedIndex(5); //apt
                cbCountry.setSelectedIndex(3);

                // sets text fields
                tfMaleName.setText("Larry");
                tfFemName.setText("Emma");
                tfFamilyName.setText("Smith");

                // sets radio buttons
                rBeardYes.setSelected(true);
                rKidsNo.setSelected(true); //no
            }
        });
        add(btnPreset4);

        JButton btnPreset5 = new JButton("Preset 5");
        btnPreset5.setFont(new Font("Dialog", Font.BOLD, 10));
        btnPreset5.setBounds(384, 424, 85, 15);
        btnPreset5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // sets cb boxes
                cbMaleJob.setSelectedIndex(14); //plumber
                cbFemJob.setSelectedIndex(14); //plumber
                cbHouseType.setSelectedIndex(9); //castle
                cbCountry.setSelectedIndex(3);

                // sets text fields
                tfMaleName.setText("Mario");
                tfFemName.setText("Luigi");
                tfFamilyName.setText("Mushroom");

                // sets radio buttons
                rBeardYes.setSelected(true);
                rKidsNo.setSelected(true); //no
            }
        });
        add(btnPreset5);
    }
}