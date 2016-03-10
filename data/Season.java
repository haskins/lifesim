package data;

/**
 * Tells you what Seasons are available.
 *
 * @author Josh Haskins
 * @version 2013-03-08
 */
public enum Season {

    Spring(1, 1),
    Summer(1, 1),
    Autumn(1, 1),
    Winter(1, 1);
    private final int damageMoney;
    private final int damageFeeling;
    private final double length = 0.25; //in years

    Season(int damageMoney, int damageFeeling) {
        this.damageMoney = damageMoney;
        this.damageFeeling = damageFeeling;
    }

    int getDamageMoney() {
        return damageMoney;
    }

    int getDamageFeeling() {
        return damageFeeling;
    }

    double getLength() {
        return length;
    }
}
