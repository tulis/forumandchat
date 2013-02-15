package view.client.util;

import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import model.Member;

/**
 *
 * @author S3259511
 */
public class MemberTableModel extends AbstractTableModel
{
    //private static final
    private static final int ADMIN_CHECK = 0;
    private static final int ADMIN_USERNAME = 1;
    private static final int ADMIN_PASSWORD = 2;
    private static final int ADMIN_PRIVILIGE = 3;
    private static final int ADMIN_STATUS = 4;
    
    //private fields
    private ArrayList<Member> members;
    private ArrayList<Member> checkedMembers;
    private ArrayList<Boolean> checks;
    private String headers[];
    private TableColumn priviligeColumn;
    private TableColumn statusColumn;
    private JTable memberTable;
    private JComboBox priviligeComboBox;
    private JComboBox statusComboBox;

    public MemberTableModel(ArrayList<Member> members, JTable memberTable)
    {
        this.members = members;
        this.checks=new ArrayList<Boolean>();
        this.checkedMembers = new ArrayList<Member>();
        this.memberTable=memberTable;

        //checks
        for(Member member: members)
            this.checks.add(false);

        this.getHeaders();
    }

    public ArrayList<Member> getCheckedMembers()
    {
        return checkedMembers;
    }


    public int getRowCount()
    {
        return this.members.size();
    }

    public int getColumnCount()
    {
        return this.headers.length;
    }

    @Override
    public String getColumnName(int column)
    {
        return this.headers[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Boolean check=checks.get(rowIndex);
        Member member = members.get(rowIndex);
        switch (columnIndex)
        {
            case ADMIN_CHECK:
                return check;
            case ADMIN_USERNAME:
                return member.getUsername();
            case ADMIN_PASSWORD:
                return member.getPassword();
            case ADMIN_PRIVILIGE:
                return member.getUserPrivilege();
            case ADMIN_STATUS:
                return member.getUserStatus();
        }
        return null;
    }

    /**
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the ADMIN_CHECK column would contain text ("true"/"false"),
     * rather than a check box.
     * @param column
     * @return
     */
    @Override
    public Class getColumnClass(int column)
    {
        return getValueAt(0, column).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        switch(column)
        {
            case ADMIN_CHECK:
                return true;
            case ADMIN_PASSWORD:
                return true;
            case ADMIN_PRIVILIGE:
                return true;
            case ADMIN_STATUS:
                return true;
        }
        return false;
    }

    /**
     * This function to set the value on the table
     * @param value
     * @param row
     * @param column
     */
    @Override
    public void setValueAt(Object value, int row, int column)
    {
        Member member=members.get(row);

        switch(column)
        {
            case ADMIN_CHECK:
                checks.set(row, (Boolean)value);
                checkedMembers.add(getSelectedMember(row));
                break;
            case ADMIN_USERNAME:
                member.setUsername((String)value);
                members.set(row, member);
                break;
            case ADMIN_PASSWORD:
                member.setPassword((String)value);
                members.set(row, member);
                break;
            case ADMIN_PRIVILIGE:
                member.setUserPrivilege((String)value);
                members.set(row, member);
                break;
            case ADMIN_STATUS:
                member.setUserStatus((String)value);
                members.set(row, member);
                break;
        }
    }

    //public methods
    public Member getSelectedMember(int rowIndex)
    {
        return members.get(rowIndex);
    }

    //This function to set the cell of particular column into ComboBox format
    public void setColumnComboBox()
    {
        //initialized
        this.statusColumn=this.memberTable.getColumnModel().getColumn(ADMIN_STATUS);
        this.priviligeColumn=this.memberTable.getColumnModel().getColumn(ADMIN_PRIVILIGE);
        this.priviligeComboBox=new JComboBox();
        this.statusComboBox=new JComboBox();
        
        //priviligeComboBox
        this.priviligeComboBox.addItem("Admin");
        this.priviligeComboBox.addItem("User");

        //statusComboBox
        this.statusComboBox.addItem("Active");
        this.statusComboBox.addItem("Suspend");

        //priviligeColumn
        this.priviligeColumn.setCellEditor(new DefaultCellEditor(this.priviligeComboBox));

        //statusColumn
        this.statusColumn.setCellEditor(new DefaultCellEditor(this.statusComboBox));

        //refresh
        this.memberTable.revalidate();
    }

    //private methods
    private void getHeaders()
    {

        headers = new String[5];
        headers[0] = "";
        headers[1] = "Username";
        headers[2] = "Password";
        headers[3] = "Privilege";
        headers[4] = "Status";
        return;

    }
}
