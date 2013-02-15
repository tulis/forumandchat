package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.NewTopic;

/**
 *
 * @author S3224743
 */
public class NewTopicAction implements ActionListener
{
    //private fields

    private BaseClient baseClient;
    private NewTopic newTopic;
    private SimpleDateFormat simpleDateFormat;

    //public constructor
    public NewTopicAction(NewTopic newTopic)
    {
        this.newTopic = newTopic;
        this.baseClient = this.newTopic.getBaseClient();
        this.simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        //simpleDateFormat
        this.simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    //Overriding public methods
    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        //local field
        Integer topicID = null;
        String topicTitle = this.newTopic.getTitleTxtField().getText();
        String content = this.newTopic.getContentTxtArea().getText();
        String groupName = (String) this.baseClient.getGroupsCmbBox().getSelectedItem();
        String username = this.baseClient.getMember().getUsername();
        String currentDate = this.simpleDateFormat.format(new Date());

        if (!isBlank(topicTitle, content))
        {
            try
            {
                //Add New Topic and get the current topicID
                topicID = this.addNewTopic(topicID, topicTitle, username, currentDate, groupName);

                //Add First Post into this new Topic
                this.addNewPost(topicID, content, username, currentDate);

                //change the content panel
                this.baseClient.showContentPanel(EPanelTypes.SHOW_POSTS);

                //refresh
                this.refresh(groupName, topicID);
            } catch (RemoteException remoteException)
            {
                remoteException.printStackTrace();
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }//end of if(!isBlank(topicTitle, content))
    }//end of public void actionPerformed(ActionEvent actionEvent)

    //private methods
    /**
     * Checks whether the the titleTxtField or contextTxtField are blank.
     * @param topicTitle
     * @param content
     * @return
     */
    private boolean isBlank(String topicTitle, String content)
    {
        if (topicTitle.equals("") || content.equals(""))
        {
            this.baseClient.showDialog("Fills all the blank field before posting!");
            return true;
        }
        return false;
    }

    /**
     *
     * @param nextGroupIncrement
     * @param topicTitle
     * @param username
     * @param currentDate
     * @param groupName
     * @throws RemoteException
     * @throws Exception
     */
    private Integer addNewTopic(Integer nextGroupIncrement, String topicTitle,
            String username, String currentDate,
            String groupName)
            throws RemoteException, Exception
    {
        //Gets the lastest group increment number from the server
        nextGroupIncrement = this.baseClient.getIServer().getSequence(IConstant.TOPIC_INCREMENT_QUERY, null);

        //set up the arguments for prepared statement sql query
        Object addArgs[] =
        {
            nextGroupIncrement, topicTitle, username, currentDate, groupName
        };

        //pass the query statement and arguments to the server
        this.baseClient.getIServer().showTopics(IConstant.ADD_NEW_TOPIC, addArgs);
        
        return nextGroupIncrement;
    }//end of private void addNewTopic

    /**
     * 
     * @param nextGroupIncrement
     * @param topicTitle
     * @param username
     * @param currentDate
     * @param groupName
     * @throws RemoteException
     * @throws Exception
     */
    private void addNewPost(Integer topicID, String content, String username,
            String currentDate)
            throws RemoteException, Exception
    {
        //Gets the maximum number of postID from the server
        Object sequnceArgs[] =
        {
            topicID
        };
        Integer postID = this.baseClient.getIServer().getSequence(IConstant.POST_MAX_QUERY, sequnceArgs);
        postID += 1;

        //Add new post
        Object addArgs[] =
        {
            postID, topicID, username, currentDate, content
        };
        this.baseClient.getIServer().showPosts(IConstant.ADD_NEW_POST, addArgs);
    }

    private void refresh(String groupName, Integer topicID)throws RemoteException, Exception
    {
        Object refreshTopicArgs[] ={groupName};
        Object refreshPostArgs[] ={topicID};
        
        this.baseClient.getPostCollection().setTopicID(topicID);
        this.baseClient.getIServer().showTopics(IConstant.SHOW_TOPIC_QUERY, refreshTopicArgs);
        this.baseClient.getIServer().showPosts(IConstant.SHOW_POST_QUERY, refreshPostArgs);
    }
}
