package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import model.Member;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;
import view.client.SettingPanel;
import view.client.util.MemberTableModel;

/**
 *This action class is for member table which located in the setting panel
 * @author S3259511
 */
public class MemberManagementAction implements ActionListener
{

    //private fields
    private ArrayList<Member> checkedMembers;
    private BaseClient baseClient;
    private JTable membersTable;
    private SettingPanel settingPanel;

    //public constructor
    public MemberManagementAction(SettingPanel settingPanel)
    {
        this.settingPanel = settingPanel;
        this.baseClient = this.settingPanel.getBaseClient();
        this.membersTable = this.settingPanel.getMemberTable();
    }

    public void actionPerformed(ActionEvent e)
    {
        MemberTableModel memberTableModel = (MemberTableModel) membersTable.getModel();
        checkedMembers = memberTableModel.getCheckedMembers();

        //DELETE members
        if (e.getActionCommand().equals(IConstant.MEMBER_MGMT_BUTTON) && this.settingPanel.getMemberCmbBox().getSelectedItem().equals("Delete"))
        {

            for (Member checkedMember : checkedMembers)
            {
                String username = checkedMember.getUsername();
                Object deleteArguments[] =
                {
                    username
                };
                try
                {
                    //passing the query and arguments to the server
                    this.baseClient.getIServer().showMembers(IConstant.DELETE_MEMBER_QUERY, deleteArguments);
                    this.baseClient.getIServer().showMembers(IConstant.GET_ALL_MEMBER_QUERY, null);
                    this.baseClient.getIServer().showGroups(IConstant.GET_ALL_GROUP_QUERY, null);
                    this.baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);

                } catch (RemoteException ex)
                {
                    Logger.getLogger(MemberManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex)
                {
                    Logger.getLogger(MemberManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        //UPDATE members
        if (e.getActionCommand().equals(IConstant.MEMBER_MGMT_BUTTON) && this.settingPanel.getMemberCmbBox().getSelectedItem().equals("Update"))
        {
            for (Member checkedMember : checkedMembers)
            {
                String password = checkedMember.getPassword();
                int privilege = checkedMember.getPrivilege();
                int status = checkedMember.getStatus();
                String username = checkedMember.getUsername();
                Object updateArguments[] =
                {
                    password, privilege, status, username
                };
                try
                {
                    //passing the query and arguments to the server
                    this.baseClient.getIServer().showMembers(IConstant.UPDATE_MEMBER_QUERY, updateArguments);
                    this.baseClient.getIServer().showMembers(IConstant.GET_ALL_MEMBER_QUERY, null);
                    this.baseClient.getIServer().showGroups(IConstant.GET_ALL_GROUP_QUERY, null);
                    this.baseClient.showContentPanel(EPanelTypes.ADMIN_SETTING);

                } catch (RemoteException ex)
                {
                    Logger.getLogger(MemberManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex)
                {
                    Logger.getLogger(MemberManagementAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
