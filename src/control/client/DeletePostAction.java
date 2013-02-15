package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.PostPanel;

/**
 * This action class is for post which located in the postPanel
 * @author S3259511
 */
public class DeletePostAction implements  ActionListener{

    //private fields
    private BaseClient baseClient;
    private PostPanel postPanel;

    public DeletePostAction(PostPanel postPanel)
    {
        //initialized
        this.postPanel = postPanel;
        this.baseClient = BaseClient.getInstance();
    }



    public void actionPerformed(ActionEvent e)
    {
        int postID = this.postPanel.getPostID();
        int topicID = this.postPanel.getTopicID();

        Object[] topicArguments = {topicID};
        Object[] postArguments = {postID,topicID};


        try
        {
            //
            //this.postLists = this.baseClient.getIServer().showPosts(IConstant.SHOW_POST_QUERY, arguments);
            if(postID==1){
                this.baseClient.getIServer().showPosts(IConstant.DELETE_ALL_POST_QUERY, topicArguments);
                this.baseClient.getIServer().showTopics(IConstant.DELETE_TOPIC_QUERY, topicArguments);
                this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
                this.baseClient.refresh(IConstant.DELETE_POST_BUTTON);
            }else
            {
                this.baseClient.getIServer().showPosts(IConstant.DELETE_POST_QUERY, postArguments);
                this.baseClient.getIServer().showPosts(IConstant.SHOW_POST_QUERY, topicArguments);
                
            }

            

        } catch (RemoteException ex)
        {
            Logger.getLogger(DeletePostAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(DeletePostAction.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

}
