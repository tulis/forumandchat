package model.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Instruction;
import model.InterestGroup;
import model.Member;
import model.Post;
import model.Topic;

/**
 *
 * @author S3224743
 */
public interface IUpdateForum extends Remote
{
    public void initialData(ArrayList<InterestGroup> interestGroups, ArrayList<Topic> topics,ArrayList<Instruction>instructions)throws RemoteException, Exception;
    public ArrayList<InterestGroup> updateGroups(ArrayList<InterestGroup> interestGroups) throws RemoteException, Exception;
    public ArrayList<Topic> updateTopics(ArrayList<Topic> topics)throws RemoteException, Exception;
    public ArrayList<Post> updatePosts(ArrayList<Post> posts)throws RemoteException, Exception;
    public ArrayList<Member> updateMembers(ArrayList<Member> members)throws RemoteException,Exception;
    public ArrayList<Instruction>updateInstructions(ArrayList<Instruction> instructions)throws RemoteException,Exception;
}
