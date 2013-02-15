package control.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import view.client.BaseClient;
import view.client.ChatFrame;

/**
 * This class is for chatFrame to send the message to the server
 * @author S3224743
 */
public class ChatAction extends MouseAdapter
{
    //private fields
    private ChatFrame chatFrame;
    private BaseClient baseClient;

    public ChatAction(ChatFrame chatFrame)
    {
        this.chatFrame=chatFrame;
        this.baseClient=BaseClient.getInstance();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        //local field
        String message=this.chatFrame.getSendTextArea().getText();
        String username=this.baseClient.getMember().getUsername();
        String groupName=(String)this.baseClient.getGroupsCmbBox().getSelectedItem();

        try
        {
            //pass the message to the server
            this.baseClient.getIServer().sendMessage(username, message, groupName);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
