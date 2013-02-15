package control.client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import model.client.IUpdateForum;
import view.client.BaseClient;

/**
 *
 * @author S3224743
 */
public class WindowAction extends WindowAdapter
{
    //private fields
    private BaseClient baseClient;

    //public constructor
    public WindowAction(BaseClient baseClient)
    {
        this.baseClient=baseClient;
    }

    //Overrides public methods
    @Override
    public void windowClosing(WindowEvent windowEvent)
    {
        //local field
        IUpdateForum iUpdateForum=this.baseClient.getIUpdateForum();
        try
        {
            //When the client is closed, tell the server that this client
            //should be removed from the server
            this.baseClient.getIServer().removeUpdateForum(iUpdateForum);
            System.exit(0);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
    }
}
