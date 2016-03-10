package data;

/**
 * Tells you what kinds of Jobs are available.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-03-08
 */
public enum Job {
//data obtained from http://www.canadavisa.com/canada-salary-wizard.html
    //NAME (inc, feel)

    Accountant(58000, 10),
    Accountant_II(78000, 15),
    Architect(51000, 15),
    Attorney(127000, -5),
    Carpender(55000, 5),
    Carpender_II(65000, 10),
    Chauffeur(35000, 20),
    Cook(46000, -5),
    Electrician(66000, 10),
    Engineer(70000, 15),
    Forklife_Operator(45000, -10),
    IT_Manager(102000, 10),
    Janitor(40000, -30),
    Mechanic(60000, -15),
    Plumber(72000, -50),
    Programmer(53000, 5),
    Real_Estate_Manager(98000, 5),
    Sales_Clerk(38000, -20),
    Sales_Manager(85000, -10),
    Secretary(38000, -25),
    Software_Engineer(66000, 10),
    Welder(51000, 10);
    private final int income;
    private final int feeling;

    Job(int income, int feeling) {
        this.income = income;
        this.feeling = feeling;
    }

    public int getIncome() {
        return income;
    }

    public int getFeeling() {
        return feeling;
    }
}
