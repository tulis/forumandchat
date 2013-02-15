package control.client;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.rmi.RemoteException;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Topic;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.TopicPanel;
import view.client.util.TopicsTableModel;

/**
 *
 * @author S3224743
 */
public class ListTopicsAction extends MouseMotionAdapter implements ListSelectionListener
{
    //private fields
    private TopicPanel topics;
    private BaseClient baseClient;
    private JTable topicsTable;

    //public constructor
    public ListTopicsAction(TopicPanel topics)
    {
        //Initialized
        this.topics=topics;
        this.baseClient=this.topics.getBaseClient();
        this.topicsTable=this.topics.getTopicsTable();
    }

    //overrides public methods
    /**
     * Gets the selected cell and show the Content of the particular topic
     * that is being selected.
     * @param listSelectionEvent
     */
    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent)
    {
        //local field
        TopicsTableModel topicsTableModel=null;
        int row=this.topicsTable.getSelectedRow();
        int column=this.topicsTable.getSelectedColumn();
        Topic selectedTopic=null;
        int topicID=0;

        if(this.setHandCursor(column))
        {
            topicsTableModel=(TopicsTableModel)this.topicsTable.getModel();
            selectedTopic=(Topic) topicsTableModel.getSelectedTopic(row);
            topicID=selectedTopic.getTopicID();
            Object arguments[]={topicID};

            //Pass the topicID and groupName with the query
            try
            {
                this.baseClient.getIServer().showPosts(IConstant.SHOW_POST_QUERY,arguments);
                this.baseClient.getPostCollection().setTopicID(topicID);
                this.baseClient.showContentPanel(EPanelTypes.SHOW_POSTS);
            }
            catch(RemoteException remoteException){remoteException.printStackTrace();}
            catch(Exception exception){exception.printStackTrace();}
        }
    }

    /**
     * When the cursor is located at the "Topic" Column, the cursor will change
     * into {@link IConstant#HAND_CURSOR}, otherwise do nothing which means
     * the cursor is {@link IConstant#DEFAULT_CURSOR}.
     * @param mouseEvent
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        //local fields
        Point point= mouseEvent.getPoint();
        int columnIndex=this.topicsTable.columnAtPoint(point);

        this.setHandCursor(columnIndex);
    }

    //private methods
    private EPanelTypes getEPanelTypes()
    {
        return this.baseClient.getHeaderPanelTypes();
    }

    //Set the hand cursor when the column title is TITLE
    private boolean setHandCursor(int columnIndex)
    {
        switch(this.getEPanelTypes())
        {
            case ADMIN:
                return this.isTopicTitleColumn(columnIndex, IConstant.ADMIN_COLUMN_TOPIC_TTILE);
            case MEMBER:
                return this.isTopicTitleColumn(columnIndex, IConstant.DEFAULT_COLUMN_TOPIC_TITLE);
            case GUEST:
                return this.isTopicTitleColumn(columnIndex, IConstant.DEFAULT_COLUMN_TOPIC_TITLE);
        }
        this.topics.setCursor(IConstant.DEFAULT_CURSOR);
        return false;
    }

    //Check if the mouse cursor in the TITLE column
    private boolean isTopicTitleColumn(int column, int expectedColumnTopic)
    {
        if(column==expectedColumnTopic)
        {
            this.topics.setCursor(IConstant.HAND_CURSOR);
            return true;
        }
        else
        {
            this.topics.setCursor(IConstant.DEFAULT_CURSOR);
            return false;
        }
    }
}
