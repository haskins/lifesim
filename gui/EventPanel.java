/**
 * This is the Event Panel, it displays the current Event along with all its
 * information.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package gui;

import java.awt.*;
import javax.swing.*;
import world.Event;

@SuppressWarnings("serial")
class EventPanel extends JPanel {

    private String name, desc;
    private JLabel lblName;
    private Event event;
    private JTextPane txtDesc;

    /**
     * Updates the panel with new Event info.
     *
     * @param e the current Event
     */
    void updateInfo(Event e) {
        event = e;
        name = e.getName();
        desc = e.getDescription();
    }

    /**
     * Constructor for Event Panel.
     */
    public EventPanel() {
        super();

        // adjust size and set layout
        setPreferredSize(new Dimension(500, 100));
        setLayout(null);

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

        // name of the Event
        lblName = new JLabel("");
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(0, 36, 90, 14);
        add(lblName);

        // description of event
        txtDesc = new JTextPane();
        txtDesc.setEditable(false);
        txtDesc.setBounds(197, 11, 293, 78);
        add(txtDesc);
    }

    /**
     * Redraws the panel with the new information.
     *
     * @param g graphics object (not used)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        lblName.setText(name);
        txtDesc.setText(desc);

        if (event != null) {
            g.drawImage(event.getImage(), event.getX(), event.getY(),
                    event.getWidth(), event.getHeight(), null);
        }

    }
}