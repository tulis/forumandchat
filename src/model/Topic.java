package model;

import java.io.Serializable;

/**
 *
 * @author S3259511
 */
public class Topic implements Serializable
{

    private int topicID;
    private String title;
    private String uName;
    private String topicDate;
    private String gName;


    public Topic(int topicID, String title, String uName, String topicDate, String gName)

    {
        this.topicID = topicID;
        this.title = title;
        this.uName = uName;
        this.topicDate = topicDate;

        this.gName = gName;
    }


    public String getgName()
    {
        return gName;
    }

    public void setgName(String gName)
    {
        this.gName = gName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTopicDate()
    {
        return topicDate;
    }

    public void setTopicDate(String topicDate)
    {
        this.topicDate = topicDate;
    }

    public int getTopicID()
    {
        return topicID;
    }

    public boolean getTopicID(String groupName, String topicTitle)
    {
        if(this.gName.equals(groupName) && this.title.equals(topicTitle))
            return true;
        return false;
    }

    public void setTopicID(int topicID)
    {
        this.topicID = topicID;
    }

    public String getuName()
    {
        return uName;
    }

    public void setuName(String uName)
    {
        this.uName = uName;
    }

    @Override
    public String toString()
    {

        return "Topic{" + "topicID=" + topicID + " title=" + title + " uName=" + uName + " topicDate=" + topicDate + " gName=" + gName + '}';

    }
}
