package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import model.Member;
import model.client.UpdateChat;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.SignInPanel;

/**
 *
 * @author S3224743
 */
public class SignInAction implements ActionListener
{
    //private fields

    private SignInPanel signInPanel;
    private BaseClient baseClient;

    //public constructor
    public SignInAction(SignInPanel signInPanel)
    {
        //Initialized
        this.signInPanel = signInPanel;
        this.baseClient = this.signInPanel.getBaseClient();
    }

    //overriding public methods
    public void actionPerformed(ActionEvent actionEvent)
    {
        if (actionEvent.getActionCommand().equals("Sign - in"))
        {
            //local field
            EPanelTypes ePanelTypes=EPanelTypes.SIGN_IN;
            String username = this.baseClient.getSignInPanel().getUsername();
            String password = this.baseClient.getSignInPanel().getPassword();
            Member member=null;

            if(this.isLoginSuccess(ePanelTypes,username,password,member))
            {
                this.baseClient.callChatFrame();
                this.sendIUpdateChat(username);
                System.out.println(ePanelTypes.toString());                
                this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
                this.baseClient.showNavigationPanel(ePanelTypes);
                this.baseClient.refresh(IConstant.SIGN_IN);
            }
        }//end of if (actionEvent.getActionCommand().equals("Sign - in"))
    }

    private boolean isLoginSuccess(EPanelTypes ePanelTypes,String username, String password, Member member)
    {      
        //Pass username and password to server
        try
        {
             member= this.baseClient.getIServer().login(username, password);
             this.baseClient.setMember(member);
        }
        catch (RemoteException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception exception){exception.printStackTrace();}

        //Reset all text fields of SignIn Panel to blank
        this.signInPanel.reset();

        //If the username and password is invalid then return
        //if the user is being suspended then return
        if(member==null)
            return false;

        switch(member.getPrivilege())
        {
            case IConstant.ADMIN:
                ePanelTypes=EPanelTypes.ADMIN;
                break;
            case IConstant.MEMBER:
                ePanelTypes=EPanelTypes.MEMBER;
                break;
        }
        this.baseClient.getHeaderPanel().showButtonPanel(ePanelTypes, new String("Welcome, " + username));        
        return true;
    }//end of private boolean isLoginSuccess(EPanelTypes ePanelTypes,String username, String password, Member member)

    //Tell the user that this client is login and this client has a right to
    //have a chat with other register members.
    private void sendIUpdateChat(String username)
    {        
        try
        {
            UpdateChat updateChat=new UpdateChat(this.baseClient.getChatFrame());
            String groupName=(String)this.baseClient.getGroupsCmbBox().getSelectedItem();
            this.baseClient.getIServer().addUpdateChat(username,updateChat,groupName);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
