package model;

import java.io.Serializable;

/**
 *
 * @author S3259511
 */
public class InterestGroup implements Serializable {

    private static final int ACTIVE = 1;
    private static final int PROPOSED = 0;
    private int groupID;
    private String groupName;
    private String description;
    private String creatorName;
    private String createDate;
    private int groupStatus = PROPOSED;        //1:active, 0:proposed

    public InterestGroup(int groupID, String groupName, String description, String creatorName, String createDate, int groupStatus) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.description = description;
        this.creatorName = creatorName;
        this.createDate = createDate;
        this.groupStatus = groupStatus;
    }



    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(int groupStatus) {
        this.groupStatus = groupStatus;
    }

    public String getStatus() {
        if (groupStatus == ACTIVE) {
            return "Active";
        } else {
            return "Proposed";
        }
    }

    public void setStatus(String status) {
        if (status.equals("Active")) {
            this.groupStatus = ACTIVE;
        }
        else{
            this.groupStatus = PROPOSED;
        }
    }

    @Override
    public String toString() {
        return "InterestGroup{" + "groupID=" + groupID + " groupName=" + groupName + " description=" + description + " creatorName=" + creatorName + " createDate=" + createDate + " groupStatus=" + getStatus() + '}';
    }
}
