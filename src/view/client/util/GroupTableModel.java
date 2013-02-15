package view.client.util;

import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import model.InterestGroup;

/**
 *
 * @author S3224743
 */
public class GroupTableModel extends AbstractTableModel
{
    //private static final
    private static final int CHECK=0;
    private static final int NAME=1;
    private static final int DESCRIPTION=2;
    private static final int STATUS=3;

    //private fields
    private ArrayList<InterestGroup> interestGroups;
    private ArrayList<InterestGroup> checkedInterestGroups;
    private ArrayList<Boolean> checks;
    private String headers[];
    private TableColumn statusColumn;
    private JTable groupTable;
    private JComboBox statusComboBox;

    //public constructor
    public GroupTableModel(ArrayList<InterestGroup> groups, JTable groupTable)
    {
        this.interestGroups=groups;
        this.checks = new ArrayList<Boolean>();
        this.checkedInterestGroups = new ArrayList<InterestGroup>();
        this.groupTable = groupTable;

                //checks
        for(InterestGroup ig: interestGroups)
            this.checks.add(false);

        //headers
        this.getHeaders();
    }

    public ArrayList<InterestGroup> getCheckedInterestGroups()
    {
        return checkedInterestGroups;
    }

    

    @Override
    public int getRowCount()
    {
        return this.interestGroups.size();
    }

    @Override
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
        InterestGroup interestGroup=this.interestGroups.get(rowIndex);

        switch(columnIndex)
        {
            case CHECK:
                return check;
            case NAME:
                return interestGroup.getGroupName();
            case DESCRIPTION:
                return interestGroup.getDescription();
            case STATUS:
                return interestGroup.getStatus();
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
            case CHECK:
                return true;
            case NAME:
                return false;
            case DESCRIPTION:
                return true;
            case STATUS:
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
        InterestGroup ig = interestGroups.get(row);

        switch(column)
        {
            case CHECK:
                checks.set(row, (Boolean)value);
                checkedInterestGroups.add(getSelectedGroup(row));
                break;
            case NAME:
                ig.setGroupName((String)value);
                interestGroups.set(row, ig);
                break;
            case DESCRIPTION:
                ig.setDescription((String)value);
                interestGroups.set(row, ig);
                break;
            case STATUS:
                ig.setStatus((String)value);
                interestGroups.set(row, ig);
                break;
        }
    }
    
    //public methods
    public InterestGroup getSelectedGroup(int rowIndex)
    {
        return interestGroups.get(rowIndex);
    }
    
    //This function to set the cell of particular column into ComboBox format
    public void setColumnComboBox()
    {
        //initialized
        this.statusColumn =this.groupTable.getColumnModel().getColumn(STATUS);
        this.statusComboBox=new JComboBox();

        //statusComboBox
        this.statusComboBox.addItem("Active");
        this.statusComboBox.addItem("Proposed");

        //statusColumn
        this.statusColumn.setCellEditor(new DefaultCellEditor(this.statusComboBox));

        //refresh
        this.groupTable.revalidate();
    }



    //private methods
    private void getHeaders()
    {
        headers=new String[4];
        headers[0]="";
        headers[1]="Name";
        headers[2]="Description";
        headers[3]="Approved";
    }
    
}
