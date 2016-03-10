/**
 * Tells you what Days are available.
 *
 * @author Josh Haskins
 * @version 2013-03-08
 */
package data;

public enum Day {

    SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

    public static int getThat() {
        return 7;
    }
}