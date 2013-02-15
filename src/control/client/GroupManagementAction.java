/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.InterestGroup;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.SettingPanel;
import view.client.util.GroupTableModel;

/**
 * This action class is for group table which located in the setting panel
 * @author S3259511
 */
public class GroupManagementAction implements ActionListener
{

    //private fields
    private ArrayList<InterestGroup> checkedInterestGroups;
    private BaseClient baseClient;
    private JTable groupsTable;
    private SettingPanel settingPanel;

    public GroupManagementAction(SettingPanel settingPanel)
    {
        this.settingPanel = settingPanel;
        this.baseClient = this.settingPanel.getBaseClient();
        this.groupsTable = this.settingPanel.getGroupsTable();
    }

    public void actionPerformed(ActionEvent e)
    {
 
        String groupCmbString = (String)this.settingPanel.getGroupsCmbBox().getSelectedItem();
        GroupTableModel GroupTableModel = (GroupTableModel) groupsTable.getModel();
        checkedInterestGroups = GroupTableModel.getCheckedInterestGroups();

        //DELETE group
        if (e.getActionCommand().equals(IConstant.GROUP_MGMT_BUTTON) && groupCmbString.equals("Delete"))
        {  
            for (InterestGroup checkedGroup : checkedInterestGroups)
            {
                String groupname = checkedGroup.getGroupName();
                Object deleteArguments[] = {groupname};
                try
                {
                    this.baseClient.getIServer().showGroups(IConstant.DELETE_GROUP_QUERY, deleteArguments);
                    this.baseClient.getIServer().showGroups(IConstant.GET_ALL_GROUP_QUERY, null);
                    this.baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);

                } catch (RemoteException ex)
                {
                    ex.printStackTrace();
                } catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }

        //Update group
        if (e.getActionCommand().equals(IConstant.GROUP_MGMT_BUTTON) && groupCmbString.equals("Update"))
        {
            for (InterestGroup checkedGroup : checkedInterestGroups)
            {
                int groupID = checkedGroup.getGroupID();
                String groupname = checkedGroup.getGroupName();
                String description = checkedGroup.getDescription();
                int groupStatus = checkedGroup.getGroupStatus();
                Object updateArguments[] = {groupname,description,groupStatus,groupID};
                try
                {
                    this.baseClient.getIServer().showGroups(IConstant.UPDATE_GROUP_QUERY, updateArguments);
                    this.baseClient.getIServer().showGroups(IConstant.GET_ALL_GROUP_QUERY, null);
                    this.baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);
                } catch (RemoteException ex)
                {
                    Logger.getLogger(GroupManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex)
                {
                    Logger.getLogger(GroupManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            
        }

    }
}
