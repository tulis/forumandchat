package model.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import model.Instruction;
import model.InterestGroup;
import model.Member;
import model.Post;
import model.Topic;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.PostCollection;
import view.client.SettingPanel;
import view.client.TopicPanel;
import view.client.util.GroupTableModel;
import view.client.util.MemberTableModel;
import view.client.util.TopicsTableModel;

/**
 * This class is used for updating the View.Client (GUI)
 * @author S3224743
 */
public class UpdateForum extends UnicastRemoteObject implements IUpdateForum
{
    //private fields
    private BaseClient baseClient;
    private PostCollection postCollection;
    private TopicPanel topics;
    private SettingPanel settingPanel;

    //public constructor
    public UpdateForum()throws RemoteException
    {
    }

    //overrides methods
    @Override
    public void initialData(ArrayList<InterestGroup> interestGroups, ArrayList<Topic>topics, ArrayList<Instruction>instructions)throws RemoteException, Exception
    {
        this.updateGroups(interestGroups);
        this.updateTopics(topics);
        this.updateInstructions(instructions);
    }

    @Override
    public ArrayList<Topic> updateTopics(ArrayList<Topic> topics)throws RemoteException, Exception
    {
        //local field
        EPanelTypes headerPanelTypes=this.baseClient.getHeaderPanelTypes();
        String groupName=(String)this.baseClient.getGroupsCmbBox().getSelectedItem();
        JTable topicsTable=this.topics.getTopicsTable();

        //Only clients that are looking at the same group will be updated
        try
        {
            if(!topics.isEmpty())
            {
                if(groupName.equals(topics.get(0).getgName()))
                {
                    topicsTable.setModel(new TopicsTableModel(topics, headerPanelTypes));
                    topicsTable.revalidate();
                }
            }
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException)
        {
            //topicsTable.setModel(new TopicsTableModel(topics, headerPanelTypes));
        }
        return topics;
    }

    @Override
    public ArrayList<Post> updatePosts(ArrayList<Post> posts) throws RemoteException,Exception
    {
        //local field
        EPanelTypes headerPanelTypes=this.baseClient.getHeaderPanelTypes();
        view.client.PostPanel postPanel=null;
        int currentTopicID=this.postCollection.getTopicID();
        int expectedTopicID=0;
        
        if(!posts.isEmpty())
            expectedTopicID=posts.get(0).gettID();

        //Only clients that are looking at the same topic ID will be updated
        if(currentTopicID==expectedTopicID)
        {
            this.postCollection.clearAllPosts();
            for(Post post:posts)
            {

                Object arguments[]={post.getMember()};
                ArrayList<Member> members=this.baseClient.getIServer().showMembers(IConstant.GET_STATUS_MEMBER_QUERY, arguments);

                postPanel=new view.client.PostPanel();
                postPanel.getUsername().setText(post.getMember());
                postPanel.getDateLbl().setText(post.getPostDate());
                postPanel.getMessageLbl().setText(post.getContent());
                postPanel.setTopicID(post.gettID());
                postPanel.setPostID(post.getPostID());
                Member member = members.get(0);
                //System.out.println(member.getUsername());
                int userStatus= member.getStatus();
                if(userStatus ==IConstant.BLOCK_USER){
                    postPanel.getBlockBtn().setEnabled(false);
                }
                this.postCollection.addPost(postPanel);
                System.out.println(post.getMember());
                System.out.println(post.getContent());
            }//end of for(PostPanel post:posts)
            this.postCollection.showPosts(headerPanelTypes);
        }//end of if(currentTopicID==expectedTopicID)
        return posts;
    }//end of public void updatePosts(ArrayList<Post> posts) throws RemoteException,Exception

    
    @Override
    public ArrayList<Member> updateMembers(ArrayList<Member> members)throws RemoteException,Exception
    {
        //local field
        JTable memberTable=this.settingPanel.getMemberTable();
        MemberTableModel memberTableModel=new MemberTableModel(members, memberTable);

        if(members.isEmpty())
            return members;

        memberTable.setModel(memberTableModel);
        memberTableModel.setColumnComboBox();
        return members;
    }

    @Override
    public ArrayList<InterestGroup> updateGroups(ArrayList<InterestGroup> interestGroups)throws RemoteException, Exception
    {        
        this.updateGroupComboBox(interestGroups);
        this.updateGroupTable(interestGroups);
        return interestGroups;
    }

    @Override
    public ArrayList<Instruction>updateInstructions(ArrayList<Instruction> instructions)
    {
        //Check whether this client has login or not
        if(this.baseClient.getMember()!=null)
            return instructions;

        for(Instruction instruction:instructions)
        {
                this.baseClient.getHeaderPanel().getWelcomeLbl().setText(instruction.getMessage());
                this.baseClient.getSettingPanel().getWelcomeTxtField().setText(instruction.getMessage());
        }
        return instructions;
    }

    //properties
    public void setBaseClient(BaseClient baseClient)
    {
        this.baseClient = baseClient;
    }

    public void setPostCollection(PostCollection postCollection)
    {
        this.postCollection = postCollection;
    }

    public void setSettingPanel(SettingPanel settingPanel)
    {
        this.settingPanel = settingPanel;
    }

    public void setTopics(TopicPanel topics)
    {
        this.topics = topics;
    }

    public BaseClient getBaseClient()
    {
        return baseClient;
    }

    public PostCollection getPostCollection()
    {
        return postCollection;
    }

    public SettingPanel getSettingPanel()
    {
        return settingPanel;
    }

    public TopicPanel getTopics()
    {
        return topics;
    }

    public Member member(ArrayList<Member> members ) throws RemoteException, Exception{
        return members.get(0);
    }

    //private methods
    //Update the groupComboBox which is located in the Navigation Panel
    private void updateGroupComboBox(ArrayList<InterestGroup>interestGroups)throws RemoteException, Exception
    {
        //local field
        JComboBox groupComboBox=this.baseClient.getGroupsCmbBox();
        HashMap groupDescriptions=this.baseClient.getGroupDescriptions();
        JLabel groupDescriptionLabel=this.baseClient.getGroupDescriptionLbl();

        if(interestGroups.isEmpty())
            return;

        groupDescriptions.clear();;
        groupComboBox.removeAllItems();
        for(InterestGroup interestGroup: interestGroups)
        {
            //update the groupComboBox
            if(interestGroup.getGroupStatus()== IConstant.ACTIVE_GROUP){
                groupComboBox.addItem(interestGroup.getGroupName());
                groupDescriptions.put(interestGroup.getGroupName(), interestGroup.getDescription());
            }
        }//end of for(InterestGroup interestGroup: interestGroups)
        groupDescriptionLabel.setText(interestGroups.get(0).getDescription());
    }//end of private void updateGroupComboBox(ArrayList<InterestGroup>interestGroups)

    //Update the group table which located in setting panel
    private void updateGroupTable(ArrayList<InterestGroup>interestGroups)throws RemoteException, Exception
    {
        //local field
        JTable groupTable=this.settingPanel.getGroupsTable();
        GroupTableModel groupTableModel=new GroupTableModel(interestGroups, groupTable);
        groupTable.setModel(groupTableModel);
        groupTableModel.setColumnComboBox();
    }


}
