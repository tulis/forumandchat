package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.SignUpPanel;

/**
 *
 * @author S3259511
 */
public class SignUpAction implements ActionListener
{

    private SignUpPanel signUpPanel;
    private BaseClient baseClient;

    public SignUpAction(SignUpPanel signUpPanel)
    {
        this.signUpPanel = signUpPanel;
        this.baseClient = this.signUpPanel.getBaseClient();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("Sign - up"))
        {

            EPanelTypes ePanelTypes = EPanelTypes.SIGN_UP;
            String username = this.baseClient.getSignUpPanel().getUsername();
            String password = this.baseClient.getSignUpPanel().getPassword();
            try
            {
                if (this.baseClient.getIServer().searchMember(username))
                {
                    this.baseClient.showDialog("This username is exist, please try another one.");
                }
                else if (username.length() < IConstant.USERNAME_MIN_LENGTH
                        || password.length() < IConstant.PASSWORD_MIN_LENGTH)
                {
                    this.baseClient.showDialog("Please check the fields.");
                    //this.signUpPanel.reset();
                }
                else
                {
                    Object arguments[] ={username,password,IConstant.MEMBER,IConstant.UN_BLOCK_USER};
                    this.baseClient.getIServer().showMembers(IConstant.ADD_NEW_MEMBER_QUERY, arguments);
                    this.baseClient.showDialog("Congratulations!");

                    this.baseClient.showContentPanel(EPanelTypes.SIGN_IN);

                }
            } catch (RemoteException ex)
            {
                ex.printStackTrace();
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
            this.signUpPanel.reset();
//
//           //If server failed to signup the user
//           if(ePanelTypes==EPanelTypes.SIGN_UP)
//                return;
//
//            this.baseClient.getHeaderPanel().showButtonPanel(EPanelTypes.SIGN_IN);
//            this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
//            this.signUpPanel.getBaseClient().setChatFrame();



        }



    }
}
