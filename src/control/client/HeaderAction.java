package control.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.JLabel;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.HeaderPanel;

/**
 *
 * @author S3224743
 */
public class HeaderAction extends MouseAdapter
{
    //private properties

    private HeaderPanel headerPanel;
    private BaseClient baseClient;

    //public constructor
    public HeaderAction(HeaderPanel headerPanel)
    {
        this.headerPanel = headerPanel;
        this.baseClient=this.headerPanel.getBaseClient();
    }

    //override public methods
    @Override
    public void mouseMoved(MouseEvent mouseEvent)
    {
        JLabel tempJLabel = (JLabel) mouseEvent.getComponent();
        if (tempJLabel.getText().equals("Sign-in"))
        {
            this.headerPanel.getSignInBtn().setText("<html><u>Sign-in</u></html>");
            this.headerPanel.getSignUpBtn().setText("Sign-up");
            this.headerPanel.getSignOutAdminBtn().setText("Sign-out");
            this.headerPanel.getSignOutMemberBtn().setText("Sign-out");
            this.headerPanel.getSettingBtn().setText("Setting");
            this.headerPanel.setCursor(IConstant.HAND_CURSOR);
        }//end of if(tempJLabel.getText().equals("Sign-in"))
        else if (tempJLabel.getText().equals("Sign-up"))
        {
            this.headerPanel.getSignUpBtn().setText("<html><u>Sign-up</u></html>");
            this.headerPanel.getSignInBtn().setText("Sign-in");
            this.headerPanel.getSignOutAdminBtn().setText("Sign-out");
            this.headerPanel.getSignOutMemberBtn().setText("Sign-out");
            this.headerPanel.getSettingBtn().setText("Setting");
            this.headerPanel.setCursor(IConstant.HAND_CURSOR);
        }//end of else if(tempJLabel.getText().equals("Sign-up"))
        else if (tempJLabel.getText().equals("Sign-out"))
        {
            this.headerPanel.getSignOutAdminBtn().setText("<html><u>Sign-out</u></html>");
            this.headerPanel.getSignOutMemberBtn().setText("<html><u>Sign-out</u></html>");
            this.headerPanel.getSignInBtn().setText("Sign-in");
            this.headerPanel.getSignUpBtn().setText("Sign-up");
            this.headerPanel.getSettingBtn().setText("Setting");
            this.headerPanel.setCursor(IConstant.HAND_CURSOR);            
        }//end of else if(tempJLabel.getText().equals("Sign-out"))
        else if (tempJLabel.getText().equals("Setting"))
        {
            this.headerPanel.getSettingBtn().setText("<html><u>Setting</u></html>");
            this.headerPanel.getSignInBtn().setText("Sign-in");
            this.headerPanel.getSignUpBtn().setText("Sign-up");
            this.headerPanel.getSignOutAdminBtn().setText("Sign-out");
            this.headerPanel.getSignOutMemberBtn().setText("Sign-out");
            this.headerPanel.setCursor(IConstant.HAND_CURSOR);
        }//end of else if(tempJLabel.getText().equals("Setting"))
        else
        {
            this.headerPanel.getSignInBtn().setText("Sign-in");
            this.headerPanel.getSignUpBtn().setText("Sign-up");
            this.headerPanel.getSignOutAdminBtn().setText("Sign-out");
            this.headerPanel.getSignOutMemberBtn().setText("Sign-out");
            this.headerPanel.getSettingBtn().setText("Setting");
            this.headerPanel.setCursor(IConstant.DEFAULT_CURSOR);
        }//end of else
    }//end of public void mouseMoved(MouseEvent mouseEvent)

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        JLabel tempJLabel = (JLabel) mouseEvent.getComponent();
        BaseClient baseClient = this.headerPanel.getBaseClient();
        if (tempJLabel.getText().equals("<html><u>Sign-in</u></html>"))
        {
            baseClient.showContentPanel(EPanelTypes.SIGN_IN);
        }//end of if(tempJLabel.getText().equals("Sign-in"))
        else if (tempJLabel.getText().equals("<html><u>Sign-up</u></html>"))
        {
            baseClient.showContentPanel(EPanelTypes.SIGN_UP);
        }//end of else if(tempJLabel.getText().equals("Sign-up"))
        else if (tempJLabel.getText().equals("<html><u>Sign-out</u></html>"))
        {
            //local field
            String username=this.baseClient.getMember().getUsername();
            String groupName=(String)this.baseClient.getGroupsCmbBox().getSelectedItem();

            try
            {
                //Tell the server that this client is sign-out
                this.baseClient.getIServer().removeUpdateChat(username, groupName);                
                this.baseClient.refresh(IConstant.SIGN_OUT);
                this.baseClient.setMember(null);
                this.baseClient.getIServer().showInstructions(IConstant.SHOW_INSTRUCTION_QUERY, null);
            }
            catch(RemoteException remoteException){remoteException.printStackTrace();}
            catch(Exception exception){exception.printStackTrace();}

            this.headerPanel.showButtonPanel(EPanelTypes.GUEST);
            baseClient.getChatFrame().dispose();
        }
        else if (tempJLabel.getText().equals("<html><u>Setting</u></html>"))
        {
            baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);
        }
    }//end of public void mouseClicked(MouseEvent mouseEvent)
}
