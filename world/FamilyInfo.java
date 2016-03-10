/**
 * A container class, used to store all the parameter relevant to the initial
 * couples of the simulation.
 *
 * @authors Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
package world;

import data.HouseType;
import data.Job;
import javax.swing.JPanel;

public class FamilyInfo {

    public Job maleJob;
    public boolean wantKids;
    public String maleName;
    public Job femaleJob;
    public String femaleName;
    public HouseType houseType;
    public int houseNum;
    public String familyName;
    public JPanel contentPanel;

    /**
     * Creates a FamilyInfo object.
     */
    public FamilyInfo(Job maleJob, boolean wantKids,
            String maleName, Job femaleJob, String femaleName,
            HouseType houseType, int houseNum, String familyName,
            JPanel contentPanel) {
        this.maleJob = maleJob;
        this.wantKids = wantKids;
        this.maleName = maleName;
        this.femaleJob = femaleJob;
        this.femaleName = femaleName;
        this.houseType = houseType;
        this.houseNum = houseNum;
        this.familyName = familyName;
        this.contentPanel = contentPanel;
    }
}
