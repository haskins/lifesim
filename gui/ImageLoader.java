/**
 * Helps GUI in drawing images.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */

package gui;

import javax.swing.*;

@SuppressWarnings("serial")
public class ImageLoader extends JPanel {
    ImageLoader(String location) {
        add(new JLabel(new ImageIcon(getClass().getResource(location))));
    }
}
