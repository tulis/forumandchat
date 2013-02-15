/**
 *
 * @author S3224743
 */
public class Server
{
    public static void main(String[] args) throws Exception
    {
        if(args.length==2)
        {
            String username=args[0];
            String password=args[1];
            model.server.Server server = new model.server.Server(username, password);
        }
        else
            System.out.println("Please provide username and password before execute the Server");
    }
}
