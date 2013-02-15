package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.SuggestGroup;

/**
 *
 * @author S3224743
 */
public class SuggestGroupAction implements ActionListener
{
    //private static finals
    private static final Integer DISAPPROVED=0;

    //private fields
    private BaseClient baseClient;
    private SuggestGroup suggestGroup;
    private SimpleDateFormat simpleDateFormat;

    //public constructor
    public SuggestGroupAction(SuggestGroup suggestGroup)
    {
        //initialized
        this.suggestGroup = suggestGroup;
        this.baseClient = this.suggestGroup.getBaseClient();
        this.simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        //simpleDateFormat
        this.simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }//end of public SuggestGroupAction(SuggestGroup suggestGroup)

    //Override public methods'
    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        //local field
        String groupName = this.suggestGroup.getGroupName();
        String description = this.suggestGroup.getDescription();
        String creatorName = this.baseClient.getMember().getUsername();
        Integer groupID=null;
        String currentDate=simpleDateFormat.format(new Date());

        if(groupName.equals("") || description.equals(""))
        {
            this.baseClient.showDialog("Fills all the blank field before suggesting a new group!");
            return;
        }

        try
        {
            //passing the query to the server. NOTE no arguments
            groupID=this.baseClient.getIServer().getSequence(IConstant.GROUP_INCREMENT_QUERY, null);
            Object arguments[]={groupID, groupName, description,creatorName,currentDate, DISAPPROVED};
            this.baseClient.getIServer().showGroups(IConstant.ADD_GROUP_QUERY, arguments);
        }
        catch(RemoteException remoteException){remoteException.printStackTrace();}
        catch(Exception exception){exception.printStackTrace();}
            
        this.baseClient.showDialog("Your request has been added to the list.");
        this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
    }    
}
