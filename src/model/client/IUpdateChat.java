package model.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Member;

/**
 *
 * @author S3224743
 */
public interface IUpdateChat extends Remote
{
    public void sendMessage(String username, String message, String groupName)throws RemoteException, Exception;
}
