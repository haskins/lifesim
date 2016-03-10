package log;

import gui.DisplayPanel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextArea;

/**
 * This class is responsible for managing a log file that contains data relevant
 * to the results of a simulation.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
public class LogWriter {

    // Member variables.
    private final static String newline = "\n"; // for newline
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static Date date;
    private static File lifesim = new File("lifesim.log");
    private static DisplayPanel dp;

    /**
     * Writes the input data to the log file as well as the logView panel int
     * display panel.
     *
     * @param ta The text area in the GUI to write.
     * @param s  The output String to write to the GUI and the log file.
     */
    public static void logIt(String s) {
        writeFile(s);
        termPrint(s);
        windowPrint(s);
    }

    /**
     * Writes to the log file.
     *
     * @param s The string to write to the log file.
     */
    public static void writeFile(String s) {
        try {
            date = new Date();
            BufferedWriter bw = new BufferedWriter(new FileWriter(lifesim, true));
            bw.write(dateFormat.format(date) + " " + s);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Prints to the terminal.
     *
     * @param s The string to print to the terminal.
     */
    public static void termPrint(String s) {
        System.out.println(s);
    }

    /**
     * Appends the input string to the bottom of the text area in the Display
     * panel.
     *
     * @param ta The text area to append to.
     * @param s  The String to append.
     */
    public static void windowPrint(String s) {
        dp.logView.append(s + newline);
    }

    public static void setDP(DisplayPanel d) {
        dp = d;
    }
}
