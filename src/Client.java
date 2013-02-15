import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import model.client.IUpdateForum;
import model.client.UpdateForum;
import model.server.IServer;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;


/**
 *
 * @author S3224743
 */
public class Client extends UnicastRemoteObject
{
    //private static field
    private static Client client;
    private static String arguments[];

    //private methods
    private BaseClient baseClient;
    private Registry registry;
    private IServer iServer;
    private UpdateForum updateForum;

    private Client()throws RemoteException
    {
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    createGUI();
                    initialized();
                    updateForumToIServer();
                }
                catch(RemoteException remoteException){remoteException.printStackTrace();}
                catch(NotBoundException notBoundException){notBoundException.printStackTrace();}
                catch(Exception exception){exception.printStackTrace();}
            }
        });
    }

    //properties
    public IServer getIServer()
    {
        return this.iServer;
    }

    //private methods
    private void createGUI()
    {
        baseClient = BaseClient.getInstance();
        baseClient.setVisible(true);
        baseClient.getHeaderPanel().showButtonPanel(
                EPanelTypes.GUEST, "Welcome, Guest!");
        baseClient.setBounds(500, 500, 500, 500);
        baseClient.setLocationRelativeTo(null);
    }

    private void initialized()throws RemoteException,NotBoundException
    {
        this.registry = LocateRegistry.getRegistry(arguments[0], IConstant.PORT_NUMBER);
        this.iServer=(IServer)registry.lookup(IConstant.SERVER_NAME_REBIND);
        this.updateForum=new UpdateForum();
    }

    private void updateForumToIServer()throws RemoteException, Exception
    {
        //updateForum
        this.updateForum.setBaseClient(this.baseClient);
        this.updateForum.setPostCollection(this.baseClient.getPostCollection());
        this.updateForum.setSettingPanel(this.baseClient.getSettingPanel());
        this.updateForum.setTopics(this.baseClient.getTopics());

        //iserver
        this.iServer.addUpdateForum((IUpdateForum)updateForum);

        //baseClient
        this.baseClient.setIServer(iServer);
        this.baseClient.setIUpdateForum(updateForum);
    }

    //public static method
    public static Client getInstance()
    {
        try
        {
            if(client==null)
                client=new Client();
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}

        return client;
    }

    public static void main(String args[])
    {
        if(args.length==1)
        {
            arguments=args;
            Client.getInstance();
        }
        else
            System.out.println("Please provide the RMI Server address");
    }//end of public static void main(String args[])
}
