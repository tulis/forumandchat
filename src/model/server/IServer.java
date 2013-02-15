package model.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Instruction;
import model.InterestGroup;
import model.Member;
import model.Topic;
import model.client.IUpdateChat;
import model.client.IUpdateForum;
import util.EPanelTypes;
import model.Post;

public interface IServer extends Remote
{
    public Member login(String username, String password) throws RemoteException, Exception;
    public EPanelTypes signUp(String username, String password) throws RemoteException,Exception;
    public boolean searchMember(String username) throws RemoteException, Exception, Exception;
    public void addUpdateForum(IUpdateForum iUpdateForum) throws RemoteException, Exception;
    public void removeUpdateForum(IUpdateForum iUpdateForum)throws RemoteException, Exception;
    public ArrayList<Topic> showTopics(String query, Object arguments[])throws RemoteException, Exception;
    public ArrayList<Post> showPosts(String query, Object arguments[])throws RemoteException, Exception;
    public ArrayList<Member> showMembers(String query, Object arguments[])throws RemoteException, Exception;
    public ArrayList<InterestGroup> showGroups(String queryS, Object arguments[])throws RemoteException,Exception;
    public ArrayList<Instruction> showInstructions(String query, Object arguments[])throws RemoteException, Exception;
    public int getSequence(String query, Object arguments[])throws RemoteException, Exception;
    public void addUpdateChat(String username,IUpdateChat iUpdateChat,String groupName)throws RemoteException, Exception;
    public void sendMessage(String username,String message,String groupName)throws RemoteException,Exception;
    public void removeUpdateChat(String username, String groupName)throws RemoteException,Exception;
}
