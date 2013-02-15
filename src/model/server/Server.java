package model.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import model.Instruction;
import model.InterestGroup;
import model.Member;
import model.Post;
import model.Topic;
import model.client.IUpdateChat;
import model.client.IUpdateForum;

import util.EPanelTypes;
import util.IConstant;

/**
 * Server send the query to database and pass the result from database
 * to UpdateForum
 * Server also handle the chat message and tell the UpdateChat to broadcast
 * for every members who are log-in.
 * @author S3224743
 */
public class Server extends UnicastRemoteObject implements IServer
{
    //private fields
    private Registry registry;
    private DBConnection dBConnection;
    private HashMap<String, IUpdateChat>iUpdateChats;
    private ArrayList<IUpdateForum> iUpdateForums;
    private ArrayList<InterestGroup> interestGroups;
    private ArrayList<Topic> topics;
    private ArrayList<Post> posts;
    private ArrayList<Member>members;
    private ArrayList<Instruction>instructions;
    
    //Public Constructor
    public Server(String username, String password) throws RemoteException
    {
        //Inititalized
        this.registry = LocateRegistry.createRegistry(IConstant.PORT_NUMBER);
        this.dBConnection = new DBConnection(username, password);
        this.iUpdateChats=new HashMap<String, IUpdateChat>();
        this.iUpdateForums = new ArrayList<IUpdateForum>();
        this.interestGroups = new ArrayList<InterestGroup>();
        this.topics = new ArrayList<Topic>();
        this.posts = new ArrayList<Post>();
        this.members=new ArrayList<Member>();
        this.instructions=new ArrayList<Instruction>();

        //Registry
        this.registry.rebind(IConstant.SERVER_NAME_REBIND, this);
        System.out.println("Server Bound to RMIRegistry");
    }

    //Override Public Methods
    @Override
    public Member login(String username, String password) throws RemoteException, Exception
    {
        return this.dBConnection.login(username, password);
    }

    @Override
    public boolean searchMember(String username) throws RemoteException, Exception
    {
        return this.dBConnection.searchMember(username);
    }
    
    @Override
    public EPanelTypes signUp(String username, String password) throws RemoteException, Exception
    {
        System.out.println(username +"  "+password);
        return this.dBConnection.signUp(username, password);
    }

    @Override
    public void addUpdateForum(IUpdateForum iUpdateForum) throws RemoteException, Exception
    {
        this.iUpdateForums.add(iUpdateForum);
        this.initialData(iUpdateForum);
        System.out.println("adding: Size of updateForums: " + this.iUpdateForums.size());
    }

    @Override
    public void removeUpdateForum(IUpdateForum iUpdateForum) throws RemoteException, Exception
    {
        this.iUpdateForums.remove(iUpdateForum);
        System.out.println("removing: Size of updateForums: " + this.iUpdateForums.size());
    }
    
    @Override
    public ArrayList<Post> showPosts(String query, Object arguments[]) throws RemoteException, Exception
    {
        this.posts = this.dBConnection.getPostList(query, arguments);
        for (IUpdateForum iUpdateForum : iUpdateForums)
        {
            iUpdateForum.updatePosts(posts);
        }
        return this.posts;
    }

    /**
     * Triggers when user changed the Interest Group Combo Box. It will return
     * list of topics based on the Interest Group Name.
     * @param groupName
     * @throws RemoteException
     */
    @Override
    public ArrayList<Topic> showTopics(String query, Object arguments[]) throws RemoteException, Exception
    {
        this.topics = this.dBConnection.getTopicList(query, arguments);
        for (IUpdateForum iUpdateForum : iUpdateForums)
        {
            iUpdateForum.updateTopics(topics);
        }
        return this.topics;
    }

    @Override
    public ArrayList<Member> showMembers(String query, Object[] arguments) throws RemoteException, Exception
    {
        this.members=this.dBConnection.getMemberList(query, arguments);
        for(IUpdateForum iUpdateForum:iUpdateForums)
        {
            iUpdateForum.updateMembers(members);
        }
        return this.members;
    }

    @Override
    public ArrayList<InterestGroup> showGroups(String query, Object[] arguments) throws RemoteException, Exception
    {
        this.interestGroups=this.dBConnection.getGroupList(query, arguments);
        for(IUpdateForum iUpdateForum:iUpdateForums)
        {
            iUpdateForum.updateGroups(interestGroups);
        }
        return this.interestGroups;
    }

    @Override
    public ArrayList<Instruction> showInstructions(String query, Object arguments[])throws RemoteException, Exception
    {
        this.instructions=this.dBConnection.getInstructionList(query, arguments);
        for(IUpdateForum iUpdateForum:iUpdateForums)
        {
            iUpdateForum.updateInstructions(instructions);
        }
        return this.instructions;
    }

    @Override
    public int getSequence(String query, Object arguments[])throws RemoteException, Exception
    {
        return this.dBConnection.getSequence(query, arguments);
    }

    @Override
    public void addUpdateChat(String username,IUpdateChat iUpdateChat,String groupName)throws RemoteException,Exception
    {
        this.iUpdateChats.put(username, iUpdateChat);
    }

    @Override
    public void sendMessage(String username,String message,String groupName)throws RemoteException,Exception
    {
        for(String key:iUpdateChats.keySet())
            this.iUpdateChats.get(key).sendMessage(username, message, groupName);
    }

    @Override
    public void removeUpdateChat(String username, String groupName)throws RemoteException,Exception
    {
        this.iUpdateChats.remove(username);
    }

    //private methods
    /**
     * Sets up initial data for any client that connect to server at the very first time.
     * @param iUpdateForum
     * @throws RemoteException
     */
    private void initialData(IUpdateForum iUpdateForum) throws RemoteException, Exception
    {
        this.interestGroups = this.dBConnection.getGroupList(IConstant.GET_ALL_GROUP_QUERY, null);
        Object arguments[] =
        {
            this.interestGroups.get(0).getGroupName()
        };
        this.topics = this.dBConnection.getTopicList(IConstant.SHOW_TOPIC_QUERY, arguments);
        this.instructions=this.dBConnection.getInstructionList(IConstant.SHOW_INSTRUCTION_QUERY, null);

        iUpdateForum.initialData(this.interestGroups, this.topics,this.instructions);
    }
}
