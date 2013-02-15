package model.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import model.Member;
import util.EPanelTypes;
import view.client.BaseClient;
import view.client.ChatFrame;

/**
 *
 * @author S3224743
 */
public class UpdateChat extends UnicastRemoteObject implements IUpdateChat
{
    //private fields
    private ChatFrame chatFrame;
    private BaseClient baseClient;

    //public constructor
    public UpdateChat(ChatFrame chatFrame)throws RemoteException
    {
        this.chatFrame=chatFrame;
        this.baseClient=this.chatFrame.getBaseClient();
    }

    @Override
    public void sendMessage(String username, String message,String groupName) throws RemoteException, Exception
    {
        //broadcast to everyone based on the groupName. 
        if(this.chatFrame.getGroupName().equals(groupName))
            this.chatFrame.getMessageTextArea().append(String.format("[%s]%s\n", username, message));
    }
}
