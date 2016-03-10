/**
 * This class helps the GUI in drawing images that represent the different kinds
 * of random events.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */

package world;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Event {

    // Member variables.
    private String name;
    private Image img;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible;
    private int damageMoney;
    private int damageLife;
    private int percentChance;
    private int length;
    private String description;

    /**
     * Constructs an Event object, used to help draw the respective images.
     */
    public Event(String n, String filename, int x, int y, int w, int h, int dm,
            int dl, int pc, int l, String d) {

        name = n;
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        damageMoney = dm;
        damageLife = dl;
        percentChance = pc;
        length = l;
        description = d;

        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
        }

        isVisible = false;
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // The next block of methods are generic getter methods.
    // /////////////////////////////////////////////////////////////////////////////////
    public boolean isVisible() {
        return isVisible;
    }

    public void setInVisible() {
        isVisible = false;
    }

    public void setVisible() {
        isVisible = true;
    }

    public Image getImage() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDamageMoney() {
        return damageMoney;
    }

    public int getDamageLife() {
        return damageLife;
    }

    public int getPercentChance() {
        return percentChance;
    }

    public int getLength() {
        return length;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
