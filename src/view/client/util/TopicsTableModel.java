package view.client.util;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import model.Topic;
import util.EPanelTypes;

/**
 *
 * @author S3224743
 */
public class TopicsTableModel extends AbstractTableModel
{
    //private static final
    private static final int ADMIN_CHECK = 0;
    private static final int ADMIN_ID = 1;
    private static final int ADMIN_TITLE = 2;
    private static final int ADMIN_STARTER = 3;
    
    private static final int DEFAULT_TITLE = 0;
    private static final int DEFAULT_STARTER = 1;
    
    //private fields
    private ArrayList<Topic> topics;
    private ArrayList<Topic> checkedTopics;
    private ArrayList<Boolean> checks;
    private EPanelTypes ePanelTypes;
    private String headers[];

    //public constructor
    public TopicsTableModel(ArrayList<Topic> topics, EPanelTypes ePanelTypes)
    {
        this.topics = topics;
        this.checks=new ArrayList<Boolean>();
        this.checkedTopics = new ArrayList<Topic>();
        this.ePanelTypes = ePanelTypes;

        //checks
        for(Topic topic:topics)
            this.checks.add(Boolean.FALSE);

        //headers
        this.getHeaders();
    }

    //overrides public methods
    @Override
    public int getRowCount()
    {
        return this.topics.size();
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
        Topic topic = this.topics.get(rowIndex);

        if (this.ePanelTypes == EPanelTypes.ADMIN)
        {
            switch (columnIndex)
            {
                case ADMIN_CHECK:
                    return checks.get(rowIndex);
                case ADMIN_ID:
                    return topic.getTopicID();
                case ADMIN_TITLE:
                    return topic.getTitle();
                case ADMIN_STARTER:
                    return topic.getuName();
            }
        }
        else
        {
            switch (columnIndex)
            {
                case DEFAULT_TITLE:
                    return topic.getTitle();
                case DEFAULT_STARTER:
                    return topic.getuName();
            }
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

    //public methods
    public Topic getSelectedTopic(int rowIndex)
    {
        return topics.get(rowIndex);
    }

    public ArrayList<Topic> getCheckedTopics() {
        return checkedTopics;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    //private methods
    private void getHeaders()
    {
        switch (ePanelTypes)
        {
            case ADMIN:
                headers = new String[4];
                headers[0] = "";
                headers[1] = "ID";
                headers[2] = "Title";
                headers[3] = "Starter";
                return;
            default:
                headers = new String[2];
                headers[0] = "Title";
                headers[1] = "Starter";
                return;
        }
    }

            /*
         * Don't need to implement this method unless your table's
         * editable.
         */
    @Override
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col == ADMIN_CHECK)
                return true;
            
            return false;
            
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
    @Override
        public void setValueAt(Object value, int row, int col) {

            if (this.ePanelTypes == EPanelTypes.ADMIN)
            {
                if(col==ADMIN_CHECK){
                    checks.set(row, (Boolean)value);
                    checkedTopics.add(getSelectedTopic(row));
                }
            }
        
        }


}
