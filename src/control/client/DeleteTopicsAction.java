package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Topic;
import util.IConstant;
import view.client.BaseClient;
import view.client.TopicPanel;
import view.client.util.TopicsTableModel;

/**
 *This action class is for topic table which located in the TOPICPANEL
 * @author S3259511
 */
public class DeleteTopicsAction implements ActionListener
{

    //private fields
    private TopicPanel topics;
    private BaseClient baseClient;
    private JTable topicsTable;
    private ArrayList<Topic> checkedTopics;

    public DeleteTopicsAction(TopicPanel topics)
    {
        //Initialized
        this.topics = topics;
        this.baseClient = this.topics.getBaseClient();
        this.topicsTable = this.topics.getTopicsTable();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals(IConstant.DELETE_TOPIC_BUTTON))
        {
            TopicsTableModel topicsTableModel = (TopicsTableModel) topicsTable.getModel();
            checkedTopics = topicsTableModel.getCheckedTopics();

            for (Topic checkedTopic : checkedTopics)
            {
                Integer topicID = checkedTopic.getTopicID();
                String groupName = (String) this.baseClient.getGroupsCmbBox().getSelectedItem();
                Object deleteArguments[] =
                {
                    topicID
                };
                Object refreshArguments[] =
                {
                    groupName
                };
                try
                {
                    //pass the query and arguments to the server
                    this.baseClient.getIServer().showTopics(IConstant.DELETE_ALL_POST_QUERY, deleteArguments);
                    this.baseClient.getIServer().showTopics(IConstant.DELETE_TOPIC_QUERY, deleteArguments);
                    this.baseClient.getIServer().showTopics(IConstant.SHOW_TOPIC_QUERY, refreshArguments);
                } catch (RemoteException ex)
                {
                    Logger.getLogger(DeleteTopicsAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex)
                {
                    Logger.getLogger(DeleteTopicsAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
