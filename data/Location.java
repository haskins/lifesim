package data;

/**
 * Tells you what Locations are available.
 *
 * @author Josh Haskins
 * @version 2013-03-08
 */
public enum Location {

    North_Pole(10, 100),
    Mexico(10, 100),
    Japan(10, 100),
    Canada(10, 100),
    USA(10, 100),
    India(10, 100);
    private final int damageMoney;
    private final int damageLifespan;

    Location(int damageMoney, int damageLifespan) {
        this.damageMoney = damageMoney;
        this.damageLifespan = damageLifespan;
    }

    int getDamageMoney() {
        return damageMoney;
    }

    int getDamageLifespan() {
        return damageLifespan;
    }
}
