package data;

/**
 * Tells you what kids of Houses are available.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-03-08
 */
public enum HouseType {
    // NOTE the prices listed here is the complete cost, we will likely need to
    // adjust these
    // NAME (exp, feel)

    Cardboard_Box(0, -20),
    Tent(100, -10),
    Shack(500, -5),
    Trailer(30000, 0),
    Houseboat(40000, 15),
    Apartment(9000, 5),
    Rancher(45000, 15),
    Lakehouse(65000, 25),
    Mansion(100000, 35),
    Castle(125000, 50);
    private final int expense;
    private final int feeling;

    HouseType(int expense, int feeling) {
        this.expense = expense;
        this.feeling = feeling;
    }

    public int getExpense() {
        return expense;
    }

    int getFeeling() {
        return feeling;
    }
}
