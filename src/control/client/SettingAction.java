package control.client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.HeaderPanel;

/**
 *
 * @author S3224743
 */
public class SettingAction extends MouseAdapter
{
    //private fields
    private BaseClient baseClient;
    private HeaderPanel headerPanel;

    //public constructor
    public SettingAction(HeaderPanel headerPanel)
    {
        //Initialized
        this.headerPanel=headerPanel;
        this.baseClient=this.headerPanel.getBaseClient();
    }

    //overrides public method
    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
        try
        {
            //send the query to the server to get the all members and groups
            this.baseClient.getIServer().showMembers(IConstant.GET_ALL_MEMBER_QUERY, null);
            this.baseClient.getIServer().showGroups(IConstant.GET_ALL_GROUP_QUERY, null);
            this.baseClient.getIServer().showInstructions(IConstant.SHOW_INSTRUCTION_QUERY, null);
            this.baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
