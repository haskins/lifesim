package world;

/**
 * An instance of this class is created for each 'person' that is present during
 * the simulation. It keeps track of a Person's personal data and has methods
 * that alter such data.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
import data.*;
import java.util.Random;
import java.util.concurrent.*;
import log.LogWriter;

public class Person implements Runnable {

    // Used for generating random numbers when they are required.
    private static Random rng = new Random();
    // Member variables.
    private Semaphore sem;
    private Gender gender;
    private static final int LIFE_EXPENESES = 7000;
    private House house;
    private String name;
    private Job job;
    private Person mom;
    private Person dad;
    private Person[] kid = new Person[4];
    private Person partner;
    private int baseLifespan = 75;
    private int age;
    private int income;
    private int expenses = 1;
    private boolean wantKids = false;
    private boolean workStatus;
    private boolean alive = true;
    private int currentHappiness = 0;

    /**
     * Constructs an adult (age 20) person. Used for the initial couple(s) and
     * to generate spouses.
     *
     * @param theGender   The gender to give this Person.
     * @param theJob      The job to give to this Person.
     * @param theWantKids Whether or not this Person wants kids.
     * @param theName     The name of this Person.
     * @param theSem      The semaphore that this person uses to ensure mutual
     *                    exclusive access to their house's data.
     * @param theHouse    The house that this person belongs to.
     */
    public Person(Gender gender, Job theJob, boolean theWantKids, String theName,
            Semaphore theSem, House theHouse) {
        // Set member variables w/ parameters.
        setGender(gender);
        setJob(theJob);
        setWantKids(theWantKids);
        setName(theName);
        setSem(theSem);
        setAge(20);
        setHouse(theHouse);

        // Set remaining member variables. Ladies cost more than men.
        income = theJob.getIncome();

        if (getGender() == Gender.Male) {
            setExpenses(LIFE_EXPENESES);
        } else {
            setExpenses((int) (LIFE_EXPENESES * 1.2));
        }


        // Create an entry in the log detailing the creation of this person.
        LogWriter.writeFile("Adult created " + name + " with job of " + job);
        //System.out.println("New adult: " + name);

    }

    /**
     * Constructs a child (age 0) person.
     *
     * @param theGender The gender to give this person.
     * @param theHouse  The house that this Person belongs to.
     * @param theMom    The mother of this Person.
     * @param theDad    The father of this Person.
     * @param theSem    The semaphore that this person uses to ensure mutual
     *                  exclusive access to their house's data.
     * @param theName   The name to give this person.
     */
    public Person(Gender theGender, House theHouse, Person theMom, Person theDad,
            Semaphore theSem, String theName) {

        // Set member variables w/ parameters.
        setWantKids(true);
        setGender(theGender);
        setHouse(theHouse);
        setMom(theMom);
        setDad(theDad);
        setSem(theSem);
        setAge(0);
        setName(theName);

        // Set remaining variables.
        income = 0;

        if (getGender() == Gender.Male) {
            setExpenses(LIFE_EXPENESES);
        } else {
            setExpenses((int) (LIFE_EXPENESES * 1.2));
        }


        // Create an entry in the log detailing the creation of this person.
        LogWriter.writeFile("Child created: " + name);
        //System.out.println("New child: " + name);
    }

    /**
     * Causes a person to 'live their life' until they either die or the
     * simulation ends.
     */
    @Override
    public void run() {
        // Loop until this person is dead or the simulation is done.
        while (alive && !Manager.getSimDone()) {

            // Increment age and alter life span if affected by the current random event.
            age++;
            if (rng.nextInt(100) + 1 <= Manager.getCurrentEvent().getPercentChance()) {
                setBaseLifespan(getBaseLifespan()
                        - Manager.getCurrentEvent().getDamageLife());
            }

            //Do the part of the happiness calculation that does not require acess 
            calculateCurrentHappinessPartOne();

            // Acquire the semaphore to ensure ME.
            try {
                sem.acquire();
                calculateCurrentHappinessPartTwo();

                LogWriter.writeFile(name + ": Current Happiness: " + currentHappiness + ". Current age: " + age + ". Maximum age: " + calcCurrentLifespan() + ".");
                // 'Promote' a child into an adult if they are of age.
                if (job == null && age >= 20) {
                    getAJob();
                    house.changePerson(this);
                }

                // Do this person's expenses.
                house.updateBankBalance(getIncome() - getExpenses());

                // Kill the person if they are to old, otherwise, check if they should get married or have kids.
                deathCheck();

                // Add new people to the house if necessary, this person is still alive.
                if (isAlive()) {
                    haveKids();
                    getMarried();
                }

                // Release the semaphore, then wait at the barrier for all other people.
                sem.release();
            } catch (Exception e) {
                System.err.println("Person.java in runnable, before barrier " + e);
            }
            
            try {
                Manager.barrier.await();
                Thread.sleep(Manager.getSpeed() / 7000);

            } catch (InterruptedException | BrokenBarrierException e) {
                System.err.println("Person.java in Sleep " + e);
            }
        }
    }

    /**
     * Increases the base lifespan by the specified amount. Negative values can
     * be used to decrease the base lifespan.
     *
     * @param amount The magnitude of the adjustment. Positive increases the
     *               base life span, negative decreases.
     */
    public void updateBaseLifespan(int amount) {
        setBaseLifespan(getBaseLifespan() + amount);
    }

    /**
     * Gives this person a random job, and sets their income to the job's
     * income.
     */
    public void getAJob() {
        job = Manager.getJobs()[rng.nextInt(22)];
        setIncome(getJob().getIncome());

        LogWriter.writeFile("Person  " + name + " gets a job of " + job);

    }

    /**
     * Checks to see if this Person has exceeded their (current) lifespan OR if
     * this child has no parents. If true, then kill them.
     */
    public void deathCheck() {
        if ((getAge() >= calcCurrentLifespan())
                || (getAge() < 20 && (getMom() == null || !getMom().isAlive()) && (getDad() == null || !getDad()
                .isAlive()))) {
            kill();
        }

    }

    /**
     * If a couple is happy enough, allow them to have kids.
     */
    public void haveKids() {
        if (currentHappiness == 100) {
            setWantKids(true);
        }


        if (wantKids && kid[3] == null && getGender() == Gender.Female
                && getPartner() != null && getPartner().isAlive() && getAge() >= 20) {
            Manager.makePerson(this, true);
        }

    }

    /**
     * If a person does not have a spouse and they are happy enough, give them
     * one.
     */
    public void getMarried() {

        if ((this.getPartner() == null || !this.getPartner().isAlive())
                && currentHappiness >= 40 && this.getAge() >= 20) {
            Manager.makePerson(this, false);
        }

    }

    /**
     * Calculates the lifespan of a Person at the moment in time the method is
     * called. The lifespan of a Person fluctuates according to their happiness.
     *
     * @return The current lifespan of a Person.
     */
    public int calcCurrentLifespan() {
        // This will hold the 'correction' for the lifespan that results from
        // the current feelings.
        int feelingCorrection;

        if (currentHappiness > 50) {
            feelingCorrection = currentHappiness - 50;
        } else {
            feelingCorrection = 0;
        }


        return getBaseLifespan() + feelingCorrection;
    }

    /**
     * Calculates the feelings of a Person at the moment the method is called.
     * This method does the portion of the calculation that does not require
     * mutual exclusion.
     *
     * Person's feelings fluctuate according to: The ratio of their income to
     * their expenses, The type of job the Person has, Current age, The
     * existence of kids and how old they are, The current bank balance of the
     * home, The type of home being lived in, and the death of a relative.
     *
     * @return The feelings of the Person at this moment in time.
     */
    public void calculateCurrentHappinessPartOne() {
        // Create a number of temporary variables used in the calculations of
        // the feelings.
        // Part 1: Feelings related to money.
        int incomeToExpenseCorrection = 0;
        double divisonResult = getIncome();

        // Expenses should never be 0, so that shouldn't be a problem.
        divisonResult /= getExpenses();

        // If there is (significantly) more income than expenses,
        // The feeling should increase accordingly. Similar for less income then expenses.
        if (divisonResult > 1.5) {
            incomeToExpenseCorrection = (int) (divisonResult * 10);
        } else if (divisonResult < 1) {
            incomeToExpenseCorrection = (int) ((1 - divisonResult) * -10);
        }


        // Determine how job affects happiness.
        int jobTypeCorrection = 0;
        if (getJob() != null) {
            jobTypeCorrection = getJob().getFeeling();
        }


        // Determine houw age affects happiness.
        int ageCorrection = 0;

// Children (not infants) get a boost to their feeling.
        if (getAge() < 25 && getAge() > 5) {
            ageCorrection = 10;
        } // Older Persons get a penalty to their feeling.
        else if (getAge() > 50) {
            ageCorrection = -10;
        }


        // Determine how the type of home a person is living in affects theire feelings.
        int houseTypeCorrection = getHouse().getFeeling();

        int sumOfCorrections = incomeToExpenseCorrection + jobTypeCorrection
                + ageCorrection + houseTypeCorrection;

        currentHappiness = sumOfCorrections;

    }

    /**
     * Calculates the feelings of a Person at the moment the method is called.
     * This method does the portion of the calculation that requires mutual
     * exclusion.
     *
     * Person's feelings fluctuate according to: The ratio of their income to
     * their expenses, The type of job the Person has, Current age, The
     * existence of kids and how old they are, The current bank balance of the
     * home, The type of home being lived in, and the death of a relative.
     *
     * @return The feelings of the Person at this moment in time.
     */
    public void calculateCurrentHappinessPartTwo() {
        // Determine how the age/alive status of relatives affects happiness.
        int relativeStatusCorrection = 0;

        Person theMom = getMom();
        Person theDad = getDad();

        // Kids hurt happiness if they are young, parents hurt happiness if they are old.
        for (int i = 0; i < 4; ++i) {
            if (kid[i] != null && kid[i].isAlive() && kid[i].getAge() < 20) {
                relativeStatusCorrection -= 5;
            }

        }

        // Dead kids/parents hurt happiness.
        if (theMom != null && theMom.isAlive() && theMom.getAge() > 60) {
            relativeStatusCorrection -= 5;
        }


        if (theDad != null && theDad.isAlive() && theDad.getAge() > 60) {
            relativeStatusCorrection -= 5;
        }


        for (int i = 0; i < 4; ++i) {
            if (kid[i] != null && !kid[i].isAlive()) {
                relativeStatusCorrection -= 10;
            }

        }

        if (theMom != null && !theMom.isAlive()) {
            relativeStatusCorrection -= 10;
        }


        if (theDad != null && !theDad.isAlive()) {
            relativeStatusCorrection -= 10;
        }


        // Determine how the house's bank balance affects happiness.
        int houseBankBalanceCorrection = 0;
        if (getHouse().getBankBalance() > 50000) {
            houseBankBalanceCorrection = getHouse().getBankBalance() / 5000;
        } else if (getHouse().getBankBalance() < 10000) {
            houseBankBalanceCorrection = -10;
        }

        // Add up all of the values determined above, cap it at 100/-100.
        int sumOfCorrections = currentHappiness + houseBankBalanceCorrection + relativeStatusCorrection;

        if (sumOfCorrections > 100) {
            sumOfCorrections = 100;
        } else if (sumOfCorrections < -100) {
            sumOfCorrections = -100;
        }


        currentHappiness = sumOfCorrections;
    }

    /**
     * Kills this Person (sets their living status to false), updates the size
     * of the barrier. Logs this event.
     */
    public void kill() {

        LogWriter.logIt(name+" died at age " + age);

        try {
            house.removePerson(this);
            Manager.setBarrierSize(Manager.getBarrierSize() - 1);

        } catch (Exception e) {
            System.err.println("Person.java " + e);
        }
        setAlive(false);
    }

    // /////////////////////////////////////////////////////////////////////////////////
    // The next block of methods are generic setter methods.
    // /////////////////////////////////////////////////////////////////////////////////
    /**
     * Changes this Person's gender.
     *
     * @param theGender The new gender of this Person.
     */
    public final void setGender(Gender theGender) {
        gender = theGender;
    }

    /**
     * Changes this Person's home.
     *
     * @param theGender The new home of this Person.
     */
    public final void setHouse(House theHouse) {
        house = theHouse;
    }

    /**
     * Changes this Person's name.
     *
     * @param theName The new name of this Person.
     */
    public final void setName(String theName) {
        name = theName;
    }

    /**
     * Changes this Person's job.
     *
     * @param theJob The new job of this Person.
     */
    public final void setJob(Job j) {
        job = j;
    }

    /**
     * Changes this Person's mother.
     *
     * @param theMom The new mother of this Person.
     */
    public final void setMom(Person theMom) {
        mom = theMom;
    }

    /**
     * Changes this Person's father.
     *
     * @param theDad The new father of this Person.
     */
    public final void setDad(Person theDad) {
        dad = theDad;
    }

    /**
     * Changes this Person's partner.
     *
     * @param thePartner The new partner of this Person.
     */
    public final void setPartner(Person thePartner) {
        partner = thePartner;
    }

    /**
     * Changes this Person's kid.
     *
     * @param theKid The new kid of this Person.
     * @param n      The nth kid to set.
     */
    public final void setKid(Person theKid, int n) {
        kid[n] = theKid;
    }

    /**
     * Changes this Person's base lifespan.
     *
     * @param theBaseLifespan The new base lifespan of this Person.
     */
    public final void setBaseLifespan(int theBaseLifespan) {
        baseLifespan = theBaseLifespan;
    }

    /**
     * Changes this Person's age.
     *
     * @param theAge The new age of this Person.
     */
    public final void setAge(int theAge) {
        age = theAge;
    }

    /**
     * Changes this Person's income.
     *
     * @param theIncome The new income of this Person.
     */
    public final void setIncome(int theIncome) {
        income = theIncome;
    }

    /**
     * Changes this Person's expenses.
     *
     * @param theExpenses The new expenses of this Person.
     */
    public final void setExpenses(int theExpenses) {
        expenses = theExpenses;
    }

    /**
     * Changes this Person's desire for kids.
     *
     * @param theWantKids The new desire (or lack of) for kids of this Person.
     */
    public final void setWantKids(boolean theWantKids) {
        wantKids = theWantKids;
    }

    /**
     * Changes this Person's work status.
     *
     * @param theWorkStatus The new working status of this Person.
     */
    public final void setWorkStatus(boolean theWorkStatus) {
        workStatus = theWorkStatus;
    }

    /**
     * Changes this Person's living status.
     *
     * @param theAlive The new living status of this Person.
     */
    public final void setAlive(boolean theAlive) {
        alive = theAlive;
    }

    /**
     * Changes this Person's semaphore.
     *
     * @param theSem The new semaphore of this Person.
     */
    public final void setSem(Semaphore theSem) {
        sem = theSem;
    }
    // public void setBarrier(CyclicBarrier theBarrier) {
    // barrier = theBarrier;
    // }
    // /////////////////////////////////////////////////////////////////////////////////
    // The next block of methods are generic getter methods.
    // /////////////////////////////////////////////////////////////////////////////////

    /**
     * Gets this Person's gender.
     *
     * @return The gender of this Person.
     */
    public final Gender getGender() {
        return gender;
    }

    /**
     * Gets this Person's house.
     *
     * @return THe house of this Person.
     */
    public final House getHouse() {
        return house;
    }

    /**
     * Gets this Person's name.
     *
     * @return The name of this Person.
     */
    public final String getName() {
        return name;
    }

    /**
     * Gets this Person's job.
     *
     * @return The job of this Person.
     */
    public final Job getJob() {
        return job;
    }

    /**
     * Gets this Person's mother.
     *
     * @return The mother of this Person.
     */
    public final Person getMom() {
        return mom;
    }

    /**
     * Gets this Person's father.
     *
     * @return The father of this Person.
     */
    public final Person getDad() {
        return dad;
    }

    /**
     * Gets this Person's kid.
     *
     * @param n The nth kid to look at
     * @return The kid of this Person.
     */
    public final Person getKid(int n) {
        return kid[n];
    }

    /**
     * Gets this Person's partner.
     *
     * @return The partner of this Person.
     */
    public final Person getPartner() {
        return partner;
    }

    /**
     * Gets this Person's base lifespan.
     *
     * @return The base lifespan of this Person.
     */
    public final int getBaseLifespan() {
        return baseLifespan;
    }

    /**
     * Gets this Person's age.
     *
     * @return The age of this Person.
     */
    public final int getAge() {
        return age;
    }

    /**
     * Gets this Person's income.
     *
     * @return The income of this Person.
     */
    public final int getIncome() {
        return income;
    }

    /**
     * Gets this Person's expenses.
     *
     * @return The expenses of this Person.
     */
    public final int getExpenses() {
        return expenses;
    }

    /**
     * Gets this Person's desire for kids.
     *
     * @return The desire for kids of this Person.
     */
    public final boolean getWantKids() {
        return wantKids;
    }

    /**
     * Gets this Person's working status.
     *
     * @return The working status of this Person.
     */
    public final boolean getWorkStatus() {
        return workStatus;
    }

    /**
     * Gets this Person's living status.
     *
     * @return The living status of this Person.
     */
    public final boolean isAlive() {
        return alive;
    }

    /**
     * Gets this Person's semaphore.
     *
     * @return The semaphore of this Person.
     */
    public final Semaphore getSem() {
        return sem;
    }
}
