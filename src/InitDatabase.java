
import model.server.DBConnection;

/**
 *
 * @author S3224743
 */
public class InitDatabase
{
    public static void main(String argsS[])
    {
        if(argsS.length==2)
        {
            String username=argsS[0];
            String passwordS=argsS[1];
            DBConnection dBConnection=new DBConnection(username, passwordS);
            dBConnection.initialDB();
        }
        else
            System.out.println("Please provide username and password before execute the InitDatabase");
    }
}
