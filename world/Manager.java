/**
 * This is the main manger class. It will be managing the entire simulation and
 * houses the semaphore and cyclic barrier used to maintain the simulations
 * integrity. Events, Jobs, and GIU updates are all done in this class.
 *
 * @authors Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package world;

import gui.*;
import log.LogWriter;
import data.*;

import java.awt.CardLayout;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.swing.JPanel;

public class Manager {

    private static int MAX_CYCLES; // max cycle possible
    private static int currentCycle;
    private static int SPEED; // speed
    private static boolean simDone = false;
    private static LinkedList<House> HOUSES = new LinkedList<>();
    private static final int EVENT_IMAGE_X_COORDINATE = 90;
    private static final int EVENT_IMAGE_Y_COORDINATE = 0;
    private static final int EVENT_IMAGE_WIDTH = 100;
    private static final int EVENT_IMAGE_HIGHT = 100;
    private static final String BUILDING_DEMO_TEXT = "A decription will be entered here that decribes the house.";
    private static final int BUILDING_IMAGE_WIDTH = 100;
    private static final int BUILDING_IMAGE_HIGHT = 100;
    private static Random generator = new Random();
    private static Event[] events = new Event[20];
    private static Building[] buildings = new Building[10];
    private static Job[] jobs = new Job[22];
    private static Event currentEvent;
    private static JPanel cp;
    private static int numHouses;
    private static Building building1, building2;
    public static CyclicBarrier barrier;
    private static Semaphore sem = new Semaphore(1), sem2 = new Semaphore(1);
    private static Stack toStart = new Stack();
    private static int barrierSize;
    private static FamilyInfo f1, f2;
    private static boolean barrierTripDone = true;

    /**
     * Generates an array of jobs that the people will be using throughout the
     * simulation.
     */
    public static void genJobs() {
        jobs[0] = Job.Accountant;
        jobs[1] = Job.Accountant_II;
        jobs[2] = Job.Architect;
        jobs[3] = Job.Attorney;
        jobs[4] = Job.Carpender;
        jobs[5] = Job.Carpender_II;
        jobs[6] = Job.Chauffeur;
        jobs[7] = Job.Cook;
        jobs[8] = Job.Electrician;
        jobs[9] = Job.Engineer;
        jobs[10] = Job.Forklife_Operator;
        jobs[11] = Job.IT_Manager;
        jobs[12] = Job.Janitor;
        jobs[13] = Job.Mechanic;
        jobs[14] = Job.Plumber;
        jobs[15] = Job.Programmer;
        jobs[16] = Job.Real_Estate_Manager;
        jobs[17] = Job.Sales_Clerk;
        jobs[18] = Job.Sales_Manager;
        jobs[19] = Job.Secretary;
        jobs[20] = Job.Software_Engineer;
        jobs[21] = Job.Welder;
    }

    /**
     * Creates an array of Building objects.
     */
    public static void buildingConstructor() {

        buildings[0] = new Building("Cardboard Box",
                "gui/images/buildings/box.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[1] = new Building("Tent",
                "gui/images/buildings/tent.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[2] = new Building("Shack",
                "gui/images/buildings/shack.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[3] = new Building("Trailer",
                "gui/images/buildings/trailer.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[4] = new Building("Houseboat",
                "gui/images/buildings/boat.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[5] = new Building("Appartment",
                "gui/images/buildings/apt.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[6] = new Building("Rancher",
                "gui/images/buildings/ranch.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[7] = new Building("Lakehouse",
                "gui/images/buildings/house.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[8] = new Building("Mansion",
                "gui/images/buildings/mansion.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);
        buildings[9] = new Building("Castle",
                "gui/images/buildings/castle.png", BUILDING_IMAGE_WIDTH,
                BUILDING_IMAGE_HIGHT, BUILDING_DEMO_TEXT);

    }

    /**
     * Creates an array of Event objects.
     */
    public static void eventConstructor() {

        // money, life, chance, length

        events[0] = new Event(
                "AIDS",
                "gui/images/events/aids.png",
                EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH,
                EVENT_IMAGE_HIGHT,
                0,
                50,
                15,
                1,
                "A very dangerous disease that currently can't be cured. It destroys your immune system until even a cold can kill you.");
        events[1] = new Event(
                "Blizzard",
                "gui/images/events/blizzard.png",
                EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH,
                EVENT_IMAGE_HIGHT,
                15,
                5,
                100,
                1,
                "A strong storm with dry, driving snow, strong winds, and intense cold. Temperature outside is -40 degrees C.");
        events[2] = new Event("Cancer", "gui/images/events/cancer.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 0, 75, 10, 1,
                "A malignant and invasive tumor.");
        events[3] = new Event("Cold", "gui/images/events/cold.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 5, 1, 80, 1,
                "You've got a cold.");
        events[4] = new Event(
                "Firestorm",
                "gui/images/events/fire.png",
                EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH,
                EVENT_IMAGE_HIGHT,
                45,
                15,
                100,
                1,
                "Areas of the land have caught fire, cold air is rushing in from the North, creating high winds and fueling more flames. The temperature in some areas is almost reaching 2000 degrees C. ");
        events[5] = new Event("Flu", "gui/images/events/flu.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 5, 3, 65, 1,
                "You're sick. :(");
        events[6] = new Event("Hurricane",
                "gui/images/events/hurricane.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 45, 20, 100, 1,
                "A violent, tropical, cyclonic storm having wind speeds of or in excess of 72 miles per hour.");
        events[7] = new Event("Pneumonia", "gui/images/events/pneu.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 5, 6, 20, 1,
                "Inflammation of the lungs with congestion.");

        events[8] = new Event("Polio", "gui/images/events/polio.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 0, 100, 4, 1,
                "An acute viral disease, affecting children and young adults, caused by any of three polioviruses, characterized by inflammation of the motor neurons of the brain stem and spinal cord, and resulting in a motor paralysis, followed by muscular atrophy and permanent deformities.");
        events[9] = new Event("Thunderstorm",
                "gui/images/events/thunder.png", EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE, EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT,
                10, 0, 100, 1, "A transient storm of lightning and thunder, with heavy rain and gusty winds, also light periods of hail, all being produced by cumulonimbus clouds.");
        events[10] = new Event("War", "gui/images/events/war.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 30, 30, 100, 3,
                "War has just been declaired and you have been enlisted. Good luck!");
        events[11] = new Event("Appolypse", "gui/images/events/world.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 100, 100, 100, 100,
                "The end of the world.");
        events[12] = new Event("Lottery", "gui/images/events/lotto.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, -10, -10, 10, 1,
                "Lucky you, winning the lottery. You won 10% of you bank account");
        events[13] = new Event("Bingo", "gui/images/events/bingo.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, -5, -5, 20, 1,
                "Lucky you, winning bingo. You won 5% of you bank account");
        events[14] = new Event("Lightning", "gui/images/events/thunder.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 0, 90, 10, 1,
                "You have been hit by lightning. You're likely dead. Sorry. :(");
        events[15] = new Event("Work Bonus",
                "gui/images/events/plus.png", EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE, EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT,
                -5, -1, 25, 1, "You have won the luck of the draw, 5% bonus of your current bank account.");
        events[16] = new Event("Scammer",
                "gui/images/events/alert.png", EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE, EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT,
                10, 0, 50, 1, "Nigerian Scammer. \n Unfortunately you have fallen prey to being scammed on the Internet. 10% of your money has disappeared.");
        events[17] = new Event("Rogue Bank", "gui/images/events/alert.png",
                EVENT_IMAGE_X_COORDINATE, EVENT_IMAGE_Y_COORDINATE,
                EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT, 50, 5, 25, 1,
                "Unfortunately the bank has made some serious mistakes, they have lost 50% of your total household bank account.");
        events[18] = new Event("Bank Error",
                "gui/images/events/money.png", EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE, EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT,
                -20, -2, 50, 1, "Taking the current cash is your household, a 20% bonus is awarded.");
        events[19] = new Event("Free Money",
                "gui/images/events/pig.png", EVENT_IMAGE_X_COORDINATE,
                EVENT_IMAGE_Y_COORDINATE, EVENT_IMAGE_WIDTH, EVENT_IMAGE_HIGHT,
                -5, -2, 20, 1, "You found some cash on the stree. \n Taking the current cash is your household, a 5% bonus is awarded.");
    }

    /**
     * Gets a random number and chooses an event based on the number generated.
     * The random number is generated in the eventManger() method.
     *
     * @param x The randomly generated number used to determine the event.
     * @return Returns the array position of the event to happen.
     */
    public static int eventProbability(int x) {
        // aids
        if (x < 75) {
            return 0;
        } // blizzard
        else if (x >= 75 && x < 175) {
            return 1;
        } // cancer
        else if (x >= 175 && x < 125) {
            return 2;
        } // cold
        else if (x >= 125 && x < 375) {
            return 3;
        } // firestorm
        else if (x >= 375 && x < 475) {
            return 4;
        } // flu
        else if (x >= 475 && x < 575) {
            return 5;
        } // hurricane
        else if (x >= 575 && x < 625) {
            return 6;
        } // pneu
        else if (x >= 625 && x < 750) {
            return 7;
        } // polio
        else if (x >= 750 && x < 752) {
            return 8;
        } // thunder
        else if (x >= 752 && x < 1100) {
            return 9;
        } // war
        else if (x >= 1100 && x < 1200) {
            return 10;
        } // app
        else if (x >= 1200 && x < 1201) {
            return 11;
        } 
        // lott
        else if (x >= 1201 && x < 1210) {
            return 12;
        } // bingo
        else if (x >= 1210 && x < 1500) {
            return 13;
        } // light
        else if (x >= 1500 && x < 1505) {
            return 14;
        } // bonus
        else if (x >= 1505 && x < 1775) {
            return 15;
        } // scamm
        else if (x >= 1775 && x < 1850) {
            return 16;
        } // -bank
        else if (x >= 1850 && x < 1890) {
            return 17;
        } // +bank
        else if (x >= 1890 && x < 1950) {
            return 18;
        } // money
        else if (x >= 1950 && x < 2000) {
            return 19;
        } /*
         * in case for some reason the other conditions did not satisfy, a
         * thunderstorm will be created
         */ else {
            return 9;
        }
    }

    /**
     * Creates a random event and calculates the effects of the event caused on
     * the people living in the house.
     */
    public static void eventManager() {

        // generates a random event
        int roll = generator.nextInt(2000);
        currentEvent = events[eventProbability(roll)];

        // damage for money
        if (HOUSES.get(0).getNumPeople() > 0) {
            HOUSES.get(0)
                    .setBankBalance(
                    (int) (Math.abs(HOUSES.get(0).getBankBalance()) * (double) ((100 - currentEvent
                    .getDamageMoney()) / 100.0)));
        }
        if (f2 != null && HOUSES.get(1).getNumPeople() > 0) {
            HOUSES.get(1)
                    .setBankBalance(
                    (int) (Math.abs(HOUSES.get(0).getBankBalance()) * (double) ((100 - currentEvent
                    .getDamageMoney()) / 100.0)));
        }
    }

    /**
     * Calls methods that update the GUI in DisplayPanel.
     *
     * @param currentCycle Integer for the current cycle in the simulation.
     */
    public static void updateGUI(int currentCycle) {

        if (f2 != null) {
            DisplayPanel.updateData(HOUSES.get(0).getBankBalance(), HOUSES.get(0)
                    .getNumMaleAdults(), HOUSES.get(0).getNumFemaleAdults(), HOUSES
                    .get(0).getNumMaleKids(), HOUSES.get(0).getNumFemaleKids(),
                    currentEvent, HOUSES.get(1).getBankBalance(), HOUSES.get(1)
                    .getNumMaleAdults(), HOUSES.get(1).getNumFemaleAdults(), HOUSES
                    .get(1).getNumMaleKids(), HOUSES.get(1).getNumFemaleKids());
        } else {
            DisplayPanel.updateData(HOUSES.get(0).getBankBalance(), HOUSES.get(0)
                    .getNumMaleAdults(), HOUSES.get(0).getNumFemaleAdults(), HOUSES
                    .get(0).getNumMaleKids(), HOUSES.get(0).getNumFemaleKids(),
                    currentEvent);
        }

        LogWriter.logIt("End of Cycle " + (currentCycle + 1)+ "\n");
        DisplayPanel.progressBar.setValue((currentCycle + 1));

        ((DisplayPanel) cp.getComponent(numHouses + 1)).updateEverything();
    }

    /**
     * Creates a new person.
     *
     * @param master The person calling the method. If they're having a child
     *               information is taken from them.
     * @param isAKid boolean where true is a kid and false is an adult.
     */
    static void makePerson(Person master, boolean isAKid) {
        Gender gender;

        if (master.getHouse().getNumPeople() + 1 < master.getHouse().getMaxPeople()) {
            if (isAKid) {
                //find out if the master person is capable of having children and if so, what child it is.
                int i = 0;
                for (; i < 4; ++i) {
                    if (master.getKid(i) == null) {
                        break;
                    }
                }
                Random r = new Random();
                int g = r.nextInt(2);
                int n = r.nextInt(50);
                String name;

                if (g == 0) {
                    gender = Gender.Male;
                    name = Names.maleNames[n];
                } else {
                    gender = Gender.Female;
                    name = Names.femaleNames[n];
                }

                master.setKid(new Person(gender, master.getHouse(), master, master.getPartner(), sem, name), i);
                master.getKid(i).getHouse().addPerson(master.getKid(i));
                Thread t1 = new Thread(master.getKid(i));
                toStart.push(t1);
                ++barrierSize;

                LogWriter.logIt(master.getKid(i).getName() + " was created");

            } else {
                Person thePartner;
                Random r = new Random();
                int rNames = r.nextInt(50);
                int rJobs = r.nextInt(22);

                if (master.getGender() == Gender.Male) {

                    thePartner = new Person(Gender.Female, jobs[rJobs], true, Names.femaleNames[rNames], sem, master.getHouse());
                } else {

                    thePartner = new Person(Gender.Male, jobs[rJobs], true, Names.maleNames[rNames], sem, master.getHouse());
                }

                thePartner.setAge(master.getAge());
                master.setPartner(thePartner);
                thePartner.setPartner(master);

                thePartner.getHouse().addPerson(thePartner);

                Thread t1 = new Thread(thePartner);
                toStart.push(t1);
                ++barrierSize;

                LogWriter.logIt(thePartner.getName() + " was created with age of " + thePartner.getAge());
            }
        }
    }

    /**
     * Starts any newly created people.
     */
    public static void startPeople() {
        while (!toStart.isEmpty()) {
            toStart.pop().start();
        }
    }

    /**
     * Sets eh family info.
     *
     * @param fi The Family Info object passed.
     */
    public static void setHouseInfo(FamilyInfo fi) {
        if (f1 == null) {
            f1 = fi;
        } else {
            f2 = fi;
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    //
    // START
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Starts the simulation and keeps it running until parameter says to exit.
     */
    public static void startSim() {
        eventConstructor();
        buildingConstructor();
        genJobs();
        if (f2 == null) {
            AnimPanel.setInfo(buildings[f1.houseNum],
                    f1.familyName);
            barrierSize = 2;
        } else {
            AnimPanel.setInfo(buildings[f1.houseNum],
                    f1.familyName,
                    buildings[f2.houseNum],
                    f2.familyName);
            barrierSize = 4;
        }
        /*
         * build family 1
         */
        building1 = buildings[f1.houseNum];
        HOUSES.add(new House(f1.houseType, f1.familyName));
        House house = HOUSES.get(0);
        Person p1 = new Person(Gender.Male, f1.maleJob, f1.wantKids, f1.maleName, sem, house);
        Person p2 = new Person(Gender.Female, f1.femaleJob, f1.wantKids, f1.femaleName, sem, house);
        p1.setPartner(p2);
        p2.setPartner(p1);
        Thread person1 = new Thread(p1, "Person 1");
        HOUSES.get(0).addPerson(p1);
        Thread person2 = new Thread(p2, "Person 2");
        HOUSES.get(0).addPerson(p2);

        /*
         * Build faimly 2
         */
        Person p3, p4;
        Thread person3 = null, person4 = null;
        if (f2 != null) {
            building2 = buildings[f2.houseNum];
            HOUSES.add(new House(f2.houseType, f2.familyName));
            House house2 = HOUSES.get(1);
            p3 = new Person(Gender.Male, f2.maleJob, f2.wantKids, f2.maleName, sem2, house2);
            p4 = new Person(Gender.Female, f2.femaleJob, f2.wantKids, f2.femaleName, sem2, house2);
            p3.setPartner(p4);
            p4.setPartner(p3);
            person3 = new Thread(p3, "Person 3");
            HOUSES.get(1).addPerson(p3);
            person4 = new Thread(p4, "Person 4");
            HOUSES.get(1).addPerson(p4);
        }

        cp = f1.contentPanel;

        DisplayPanel.progressBar.setMaximum(MAX_CYCLES);
        LogWriter.logIt("World Created");

        eventManager();

        setBarrier(barrierSize);

        // /////////////////
        // START THE PEOPLE
        // /////////////////
        person1.start();
        person2.start();
        if (f2 != null) {
            person3.start();
            person4.start();
        }
    }

    /**
     * rebuild the barrier at the end of every barrier cycle.
     */
    public static void rebuildBarrier() {
        eventManager();
        System.out.println("Barrier size - " + barrierSize);
        if (barrier.getParties() != barrierSize) {
            setBarrier(barrierSize);
        }//else{
        //eventManager();
        //}
    }

    /**
     * Sets some private parameters that the manager uses to run the simulation.
     *
     * @param c The max number of cycles.
     * @param s The speed of the simulation.
     * @param h the Number of houses being simulated(one or two).
     */
    public static void setParams(int c, int s, int h) {

        MAX_CYCLES = c;
        SPEED = s;
        numHouses = h;
    }

    /**
     * Sends the final results to the result panel when the simulation is
     * complete.
     */
    public static void sendResults() {
        if (f2 != null) {
            System.out.println("num people in h1 " + HOUSES.get(0).getNumPeople());
            System.out.println("num people in h2 " + HOUSES.get(1).getNumPeople());

            ((ResultPanel) cp.getComponent(2 + numHouses)).setInfo(building1,
                    HOUSES.get(0).getfamilyName(), HOUSES.get(0)
                    .getBankBalance(), HOUSES.get(0)
                    .getNumPeople(),
                    building2,
                    HOUSES.get(1).getfamilyName(), HOUSES.get(1)
                    .getBankBalance(), HOUSES.get(1)
                    .getNumPeople());
        } else {
            // 2 + numHouses used to be = to 3.
            ((ResultPanel) cp.getComponent(2 + numHouses)).setInfo(building1,
                    HOUSES.get(0).getfamilyName(), HOUSES.get(0)
                    .getBankBalance(), HOUSES.get(0)
                    .getNumPeople());
        }
    }

    /**
     * A delay based on the set speed.
     */
    private static void delay() {
        for (int i = 0; i < SPEED; i++) {
            System.out.print("");
        }
    }

    /**
     * Sets the barrier size. The barrier itself is contained within this
     * method.
     *
     * @param size the size of the Barrier.
     */
    public static void setBarrier(int size) {

        if (size <= 0) {
            delay();
            sendResults();
            nextPanel();
        } else {

            barrier = new CyclicBarrier(size, new Runnable() {
                @Override
                public void run() {
                    //System.out.println("Barrier entry code!");
                    //barrierTripDone = false;
                    if (f2 != null) {
                        //taxes
                        if (HOUSES.get(0).getNumPeople() > 0) {
                            System.out.println("num of people in h1 " + HOUSES.get(0).getNumPeople());
                            HOUSES.get(0).setBankBalance(HOUSES.get(0).getBankBalance() - HOUSES.get(0).getExpense());
                            LogWriter.writeFile("House 1 Bank Balance is  " + HOUSES.get(0).getBankBalance());
                        }
                        if (HOUSES.get(1).getNumPeople() > 0) {
                            System.out.println("num of people in h2 " + HOUSES.get(1).getNumPeople());

                            HOUSES.get(1).setBankBalance(HOUSES.get(1).getBankBalance() - HOUSES.get(1).getExpense());
                            LogWriter.writeFile("House 2 Bank Balance is  " + HOUSES.get(1).getBankBalance());
                        }
                    } else {
                        //taxes
                        if (HOUSES.get(0).getNumPeople() > 0) {
                            System.out.println("num of people in h1 " + HOUSES.get(0).getNumPeople());

                            HOUSES.get(0).setBankBalance(HOUSES.get(0).getBankBalance() - HOUSES.get(0).getExpense());
                            LogWriter.writeFile("House 1 Bank Balance is  " + HOUSES.get(0).getBankBalance());
                        }

                    }
                    updateGUI(currentCycle);



                    ++currentCycle;

                    if (currentCycle == MAX_CYCLES) {

                        setSimDone();

                        sendResults();
                        nextPanel();

                    } else {
                        rebuildBarrier();
                        startPeople();

                    }


                }
            });
        }
    }

    /**
     * Trips the barrier, used to continue when hangup occurs.
     */
    public static void forceBarrier() {
        // Try to fix the barrier size by calculating it from the number of people in the houses.
        while (barrier.isBroken()) {
            //System.out.println("Create a thread!!!");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException ex) {
                        //Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //System.out.println("Rebuild barrier!");
        rebuildBarrier();



    }

    /**
     * Moves the GUI to the next panel.
     */
    private static void nextPanel() {
        CardLayout cardLayout = (CardLayout) cp.getLayout();
        cardLayout.next(cp);
    }

    /**
     * Sets a boolean "simDone" to true, indicates the simulation is complete.
     */
    private static void setSimDone() {
        simDone = true;
    }

    /**
     * Gets the boolean "simDone" and returns it.
     *
     * @return simDone
     */
    public static boolean getSimDone() {
        return simDone;
    }

    /**
     * Gets the speed of the simulation.
     */
    public static int getSpeed() {
        return SPEED;
    }

    /**
     * Forces program to display the Result panel.
     */
    public static void forceResults() {
        sendResults();
        nextPanel();
    }

    /**
     * Returns the barrier size.
     */
    public static int getBarrierSize() {
        return barrierSize;
    }

    /**
     * Sets the barrier size.
     *
     * @param x new barrier size
     */
    public static void setBarrierSize(int x) {
        barrierSize = x;
    }

    /**
     * Returns the number of houses in the simulation.
     */
    public static int getNumberHouses() {
        return numHouses;
    }

    /**
     * Returns the current Event.
     */
    public static Event getCurrentEvent() {
        return currentEvent;
    }

    /**
     * Returns the Job array.
     */
    public static Job[] getJobs() {
        return jobs;
    }
}
