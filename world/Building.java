package world;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class helps the GUI in drawing images that represent the different types
 * of houses.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
public class Building {

    // Member variables.
    private String name;
    private Image img;
    private int width;
    private int height;
    private String description;

    /**
     * Constructs a Building object, used to help draw the respective images.
     */
    public Building(String n, String filename, int w, int h, String d) {

        name = n;
        width = w;
        height = h;
        description = d;

        // Create the image for this building.
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            System.err.println("error on image creation in Building");
        }

    }

    // /////////////////////////////////////////////////////////////////////////////////
    // The next block of methods are generic getter methods.
    // /////////////////////////////////////////////////////////////////////////////////
    public Image getImage() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
