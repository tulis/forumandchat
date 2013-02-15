package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.JLabel;
import model.server.IServer;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.ChatFrame;

/**
 *
 * @author S3224743
 */
public class NavigationAction implements ActionListener
{
    //private fields
    private BaseClient baseClient;
    private String currentGroupName;

    //public constructor
    public NavigationAction(BaseClient baseClient)
    {
        this.baseClient=baseClient;
    }//end of public NavigationAction(BaseClient baseClient)

    //override public methods
    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        //local fields
        HashMap<String, String> groupDescriptions=this.baseClient.getGroupDescriptions();
        String groupName=(String)this.baseClient.getGroupsCmbBox().getSelectedItem();

        if(actionEvent.getActionCommand().equals(IConstant.NEW_THREAD_BUTTON))
        {
            this.baseClient.showContentPanel(EPanelTypes.NEW_TOPIC);
        }
        if(actionEvent.getActionCommand().equals(IConstant.SUGGEST_GROUP))
        {
            this.baseClient.showContentPanel(EPanelTypes.SUGGEST_GROUP);
        }
        if(actionEvent.getActionCommand().equals(IConstant.GROUP_COMBO_BOX))
        {
            if(groupName!=null)
            {
                this.showGroupDescription(groupDescriptions, groupName);
                this.sendChatMessage(currentGroupName, IConstant.QUIT_MESAGE);
                this.groupNameToChatFrame(groupName);
                this.sendChatMessage(groupName,IConstant.JOIN_MESSAGE);
                this.listTopic(groupDescriptions, groupName);
                this.currentGroupName=groupName;
            }
        }
        //Refresh the group description and group list from server and notify the
        //server to broadcast to everyone that this client is sign in.
        if(actionEvent.getActionCommand().equals(IConstant.SIGN_IN))
        {
            this.showGroupDescription(groupDescriptions, groupName);
            this.groupNameToChatFrame(groupName);
            this.sendChatMessage(groupName,IConstant.JOIN_MESSAGE);
            this.listTopic(groupDescriptions, groupName);
            currentGroupName=groupName;
        }
        //Refresh the group description and group list from server and notify the
        //server to broadcast to everyone that this client is sign out.
        if(actionEvent.getActionCommand().equals(IConstant.SIGN_OUT))
        {
            this.showGroupDescription(groupDescriptions, groupName);
            this.sendChatMessage(currentGroupName, IConstant.QUIT_MESAGE);
            this.listTopic(groupDescriptions, groupName);
            currentGroupName=null;
        }
        //Refresh the group description and group list from server and this
        //action is only being done by ADMIN
        if(actionEvent.getActionCommand().equals(IConstant.DELETE_POST_BUTTON))
        {
            this.showGroupDescription(groupDescriptions, groupName);
            this.listTopic(groupDescriptions, groupName);
        }
    }//end of public void actionPerformed(ActionEvent actionEvent)

    //private methods
    private void showGroupDescription(HashMap<String, String> groupDescriptions, String groupName)
    {
        JLabel groupDescriptionLabel=this.baseClient.getGroupDescriptionLbl();
        String description=groupDescriptions.get(groupName);
        groupDescriptionLabel.setText(description);
    }

    private void listTopic(HashMap<String, String> groupDescriptions, String groupName)
    {
        //local field
        Object arguments[]={groupName};
        IServer iServer=this.baseClient.getIServer();

        //Pass groupName to the server
        try
        {
            if(iServer!=null)
            {
                iServer.showTopics(IConstant.SHOW_TOPIC_QUERY,arguments);
                this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
                this.baseClient.getTopics().getTopicsTable().revalidate();
            }
        }
        catch(RemoteException remoteException)
        {
            remoteException.printStackTrace();
        }
        catch(Exception exception){exception.printStackTrace();}
    }//end of private void listTopic(HashMap<String, String> groupDescriptions, String groupName)

    /**
     * Set the current groupName into ChatFrame's groupName
     */
    private void groupNameToChatFrame(String groupName)
    {
        //local field
        ChatFrame chatFrame=this.baseClient.getChatFrame();

        if(chatFrame!=null)
            chatFrame.setGroupName(groupName);
    }

    /**
     * Tell the server that this client has move to the other group
     * @param groupName
     */
    private void sendChatMessage(String groupName, String message)
    {
        //local field
        String username=null;
        String constructMessage=String.format(message, groupName);

        if(this.baseClient.getMember()==null)
            return;

        try
        {
            username=this.baseClient.getMember().getUsername();
            this.baseClient.getIServer().sendMessage(username, constructMessage, groupName);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
