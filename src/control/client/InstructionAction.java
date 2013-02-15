package control.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import util.IConstant;
import view.client.BaseClient;
import view.client.SettingPanel;

/**
 *
 * @author S3224743
 */
public class InstructionAction extends MouseAdapter
{
    //private fields
    private SettingPanel settingPanel;
    private BaseClient baseClient;

    //public constructor
    public InstructionAction(SettingPanel settingPanel)
    {
        //initialized
        this.settingPanel=settingPanel;
        this.baseClient=this.settingPanel.getBaseClient();
    }

    //override public methods
    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        //local field
        String welcomeMessage=this.settingPanel.getWelcomeTxtField().getText();
        Object arguments[]={welcomeMessage};

        if(welcomeMessage.equals(""))
        {
            this.baseClient.showDialog("Welcome Message cannot be empty!");
            return;
        }

        try
        {
            //pass the query and the arguments to the server
            this.baseClient.getIServer().showInstructions(IConstant.UPDATE_INSTRUCTION_QUERY, arguments);
            this.baseClient.getIServer().showInstructions(IConstant.SHOW_INSTRUCTION_QUERY, null);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
