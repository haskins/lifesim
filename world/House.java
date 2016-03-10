package world;

/**
 * The house class that will be hold all if the information about the house and
 * the information of a family living in the house.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 */
import data.*;

public class House {

    private int bankBalance;
    private int numPeople;
    private HouseType houseType;
    private String familyName;
    private int expense;
    private int feeling;
    private int numMaleAdults;
    private int numFemaleAdults;
    private int numMaleKids;
    private int numFemaleKids;
    private boolean flag;
    private final int MAX_PEOPLE;

    /**
     * General House creation constructor.
     *
     * @param houseType  The type of house that is being created.
     * @param familyName the name of the family living in the house.
     */
    public House(HouseType houseType, String familyName) {
        this.MAX_PEOPLE = 13;
        this.houseType = houseType;
        this.familyName = familyName;
        expense = houseType.getExpense();
        flag = true;
        bankBalance = 0;
    }

    /**
     * Updates the bank balance based on the integer "n" passed to the method.
     */
    public void updateBankBalance(int n) {
        bankBalance += n;
    }

    /**
     * returns the current Bank balance.
     */
    public int getBankBalance() {
        return bankBalance;
    }

    /**
     * Add and remove methods for the number of people.
     *
     * @param p The person being added to the house.
     */
    public void addPerson(Person p) {
        if (numPeople <= MAX_PEOPLE) {
            if (p.getGender() == Gender.Male && p.getAge() >= 20) {
                setNumMaleAdults(getNumMaleAdults() + 1);
            } else if (p.getGender() == Gender.Male && p.getAge() < 20) {
                setNumMaleKids(getNumMaleKids() + 1);
            } else if (p.getGender() == Gender.Female && p.getAge() >= 20) {
                setNumFemaleAdults(getNumFemaleAdults() + 1);
            } else if (p.getGender() == Gender.Female && p.getAge() < 20) {
                setNumFemaleKids(getNumFemaleKids() + 1);
            }
            numPeople++;
        } else {
            //DO NOTHING! :O
        }
    }

    /**
     * Removes a person from the house by decrementing the number of people
     * living in the house.
     *
     * @param p The person to remove from the house.
     */
    public void removePerson(Person p) {
        if (p.getGender() == Gender.Male && p.getAge() >= 20) {
            setNumMaleAdults(getNumMaleAdults() - 1);
        } else if (p.getGender() == Gender.Male && p.getAge() < 20) {
            setNumMaleKids(getNumMaleKids() - 1);
        } else if (p.getGender() == Gender.Female && p.getAge() >= 20) {
            setNumFemaleAdults(getNumFemaleAdults() - 1);
        } else if (p.getGender() == Gender.Female && p.getAge() < 20) {
            setNumFemaleKids(getNumFemaleKids() - 1);
        }
        numPeople--;

    }

    /**
     * Changes the person in the house form a person to an adult.
     *
     * @param p The child that will be growing up.
     */
    public void changePerson(Person p) {
        if (p.getGender() == Gender.Male) {
            setNumMaleKids(getNumMaleKids() - 1);
            setNumMaleAdults(getNumMaleAdults() + 1);

        } else if (p.getGender() == Gender.Female) {
            setNumFemaleKids(getNumFemaleKids() - 1);
            setNumFemaleAdults(getNumFemaleAdults() + 1);
        }

    }

    // ///////////////////////////////////////////////////////////////////
    // ///Below are the genaric getters and setters for the house class
    // ///////////////////////////////////////////////////////////////////
    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setHouseType(HouseType type) {
        houseType = type;
    }

    public HouseType getHouseType() {
        return houseType;
    }

    public void setFamilyName(String name) {
        familyName = name;
    }

    public String getfamilyName() {
        return familyName;
    }

    public int getExpense() {
        return expense;
    }

    public int getFeeling() {
        return feeling;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    public void setBankBalance(int i) {
        bankBalance = i;
    }

    public int getNumMaleAdults() {
        return numMaleAdults;
    }

    public void setNumMaleAdults(int numMaleAdults) {
        this.numMaleAdults = numMaleAdults;
    }

    public int getNumFemaleKids() {
        return numFemaleKids;
    }

    public void setNumFemaleKids(int numFemaleKids) {
        this.numFemaleKids = numFemaleKids;
    }

    public int getNumMaleKids() {
        return numMaleKids;
    }

    public void setNumMaleKids(int numMaleKids) {
        this.numMaleKids = numMaleKids;
    }

    public int getNumFemaleAdults() {
        return numFemaleAdults;
    }

    public void setNumFemaleAdults(int numFemaleAdults) {
        this.numFemaleAdults = numFemaleAdults;
    }

    public int getMaxPeople() {
        return MAX_PEOPLE;
    }
}
