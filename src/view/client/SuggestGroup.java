package view.client;

import control.client.SuggestGroupAction;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import sun.security.jca.GetInstance;

/**
 *
 * @author S3224743
 */
public class SuggestGroup extends javax.swing.JPanel
{
    //private static field
    private static SuggestGroup suggestGroup;
    
    //private fields
    private BaseClient baseClient;
    private SuggestGroupAction suggestGroupAction;

    //private constructor
    public SuggestGroup(BaseClient baseClient)
    {
        initComponents();

        //Initialized
        this.baseClient=baseClient;
        this.suggestGroupAction=new SuggestGroupAction(this);

        //suggestBtn
        this.suggestBtn.addActionListener(suggestGroupAction);
    }

    //public static method
    public static SuggestGroup getInstance(BaseClient baseClient)
    {
        if(suggestGroup==null)
            suggestGroup=new SuggestGroup(baseClient);
        return suggestGroup;
    }

    //properties
    public BaseClient getBaseClient()
    {
        return baseClient;
    }

    public String getDescription() {
        return descriptionTxtArea.getText();
    }

    public String getGroupName() {
        return groupNameTxtField.getText();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        groupNameTxtField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        descriptionTxtArea = new javax.swing.JTextArea();
        suggestBtn = new javax.swing.JButton();

        jPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 500));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Group Name:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Description:");

        groupNameTxtField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        descriptionTxtArea.setColumns(20);
        descriptionTxtArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        descriptionTxtArea.setLineWrap(true);
        descriptionTxtArea.setRows(5);
        jScrollPane1.setViewportView(descriptionTxtArea);

        suggestBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        suggestBtn.setText("Suggest New Group");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(groupNameTxtField, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(307, Short.MAX_VALUE)
                        .addComponent(suggestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(groupNameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addComponent(suggestBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        add(jPanel1);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descriptionTxtArea;
    private javax.swing.JTextField groupNameTxtField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton suggestBtn;
    // End of variables declaration//GEN-END:variables
}
