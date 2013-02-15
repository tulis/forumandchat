package view.client;

import control.client.NavigationAction;
import control.client.WindowAction;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Member;
import model.client.IUpdateForum;
import model.server.IServer;
import util.EPanelTypes;
import util.IConstant;

/**
 *
 * @author S3224743
 */
public class BaseClient extends javax.swing.JFrame implements Serializable
{
    //private static field
    private static BaseClient baseClient;

    //private fields
    private HeaderPanel headerPanel;
    private ChatFrame chatFrame;
    private NavigationAction navigationAction;
    private SignInPanel signInPanel;
    private SignUpPanel signUpPanel;
    private CardLayout cardLayout;
    private NewTopic newTopic;
    private PostCollection postCollection;
    private SettingPanel settingPanel;
    private TopicPanel topics;
    private SuggestGroup suggestGroup;
    private IServer iServer;
    private IUpdateForum iUpdateForum;
    private HashMap<String, String> groupDescriptions;
    private EPanelTypes currentContentPanel;
    private Member member;
    private WindowAction windowAction;

    //private constructor
    private BaseClient() throws RemoteException,NotBoundException
    {
        this.initComponents();

        //Instantiate
        this.instantiate();

        //This BaseClient
        this.add(this.headerPanel, BorderLayout.NORTH);
        this.setTitle("Discussion Board");
        this.addWindowListener(this.windowAction);

        //groupCmbBox
        this.groupsCmbBox.addActionListener(this.navigationAction);
        this.groupsCmbBox.setActionCommand(IConstant.GROUP_COMBO_BOX);

        //newThreadBtn
        this.newThreadBtn.addActionListener(this.navigationAction);
        this.newThreadBtn.setActionCommand(IConstant.NEW_THREAD_BUTTON);

        //suggestBtn
        this.suggestBtn.addActionListener(this.navigationAction);
        this.suggestBtn.setActionCommand(IConstant.SUGGEST_GROUP);

        //ContentPanel
        this.contentPanel.setLayout(this.cardLayout);
        this.contentPanel.add(this.settingPanel,EPanelTypes.ADMIN_SETTING.name());
        this.contentPanel.add(this.topics,EPanelTypes.LIST_TOPIC.name());
        this.contentPanel.add(this.signInPanel,EPanelTypes.SIGN_IN.name());
        this.contentPanel.add(this.signUpPanel,EPanelTypes.SIGN_UP.name());
        this.contentPanel.add(this.newTopic,EPanelTypes.NEW_TOPIC.name());
        this.contentPanel.add(this.postCollection,EPanelTypes.SHOW_POSTS.name());
        this.contentPanel.add(this.settingPanel,EPanelTypes.ADMIN_SETTING.name());
        this.contentPanel.add(this.suggestGroup, EPanelTypes.SUGGEST_GROUP.name());

        //currentContentPanel
        this.currentContentPanel=EPanelTypes.LIST_TOPIC;
    }//end of public BaseClient()

    //Properties
    public HeaderPanel getHeaderPanel()
    {
        return this.headerPanel;
    }//end of public HeaderPanel getHeaderPanel()
   
    public JButton getNewThreadBtn()
    {
        return this.newThreadBtn;
    }//end of public JButton getNewThreadBtn()

    public JPanel getContentPanel()
    {
        return this.contentPanel;
    }

    public SignInPanel getSignInPanel()
    {
        return this.signInPanel;
    }

    public SignUpPanel getSignUpPanel()
    {
        return signUpPanel;
    }

    public JPanel getDescriptionPanel()
    {
        return descriptionPanel;
    }

    public JComboBox getGroupsCmbBox()
    {
        return groupsCmbBox;
    }

    public JLabel getGroupDescriptionLbl()
    {
        return groupDescriptionLbl;
    }

    public NewTopic getNewTopic()
    {
        return newTopic;
    }

    public PostCollection getPostCollection()
    {
        return postCollection;
    }

    public SuggestGroup getSuggestGroup()
    {
        return suggestGroup;
    }

    public TopicPanel getTopics()
    {
        return topics;
    }

    public HashMap getGroupDescriptions()
    {
        return groupDescriptions;
    }

    public SettingPanel getSettingPanel()
    {
        return settingPanel;
    }

    public ChatFrame getChatFrame()
    {
        return chatFrame;
    }
    
    public IServer getIServer()
    {
        return iServer;
    }

    public void setIServer(IServer iServer)
    {
        this.iServer = iServer;
    }

    public IUpdateForum getIUpdateForum()
    {
        return iUpdateForum;
    }

    public void setIUpdateForum(IUpdateForum iUpdateForum)
    {
        this.iUpdateForum = iUpdateForum;
    }

    public EPanelTypes getHeaderPanelTypes()
    {
        return this.headerPanel.getEPanelTypes();
    }

    public EPanelTypes getCurrentContentPanel()
    {
        return currentContentPanel;
    }

    public Member getMember()
    {
        return member;
    }

    public void setMember(Member member)
    {
        this.member = member;
    }

    //public static methods
    public static BaseClient getInstance()
    {
        try
        {
            if(baseClient==null)
                baseClient=new BaseClient();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return baseClient;
    }

    //private methods
    private void instantiate()throws RemoteException, NotBoundException
    {
        this.navigationAction=new NavigationAction(this);
        this.headerPanel = HeaderPanel.getInstance(this);
        this.signInPanel=SignInPanel.getInstance(this);
        this.signUpPanel=SignUpPanel.getInstance(this);
        this.newTopic=NewTopic.getInstance(this);
        this.postCollection=PostCollection.getInstance(this);
        this.topics=TopicPanel.getInstance(this);
        this.settingPanel=SettingPanel.getInstance(this);
        this.suggestGroup=new SuggestGroup(this);
        this.cardLayout=new CardLayout(5,5);
        this.groupDescriptions=new HashMap<String,String>();
        this.windowAction=new WindowAction(this);
    }

    //public methods
    public void showContentPanel(EPanelTypes ePanelTypes)
    {
        this.cardLayout.show(this.contentPanel, ePanelTypes.name());
        this.currentContentPanel=ePanelTypes;
    }//end of public void showContentPanel(EPanelTypes ePanelTypes)

    public void showNavigationPanel(EPanelTypes ePanelTypes)
    {
        switch(ePanelTypes)
        {
            case GUEST:
                this.rightNavPanel.setSize(0,60);
                this.rightNavPanel.setPreferredSize(new Dimension(0,60));
                break;
            default:
                this.rightNavPanel.setSize(169,60);
                this.rightNavPanel.setPreferredSize(new Dimension(340,60));
                break;
        }
    }
    public void refresh(String command)
    {
        this.navigationAction.actionPerformed(
                new ActionEvent(this.groupsCmbBox, ActionEvent.ACTION_PERFORMED,
                                command));
    }
    public void showDialog(String message)
    {
        JOptionPane.showMessageDialog(BaseClient.getInstance(),
                                message,"!",JOptionPane.INFORMATION_MESSAGE);
    }

    public void callChatFrame()
    {
        this.chatFrame = ChatFrame.getInstance(this);
        this.chatFrame.setGroupName((String)this.groupsCmbBox.getSelectedItem());
        this.chatFrame.setVisible(true);
        this.chatFrame.setLocationRelativeTo(null);
        this.chatFrame.getMessageTextArea().setText("");
        this.chatFrame.getSendTextArea().setText("");
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
        navigationPanel = new javax.swing.JPanel();
        rightNavPanel = new javax.swing.JPanel();
        newThreadBtn = new javax.swing.JButton();
        suggestBtn = new javax.swing.JButton();
        leftNavPanel = new javax.swing.JPanel();
        groupsCmbBox = new javax.swing.JComboBox();
        descriptionPanel = new javax.swing.JPanel();
        groupDescriptionLbl = new javax.swing.JLabel();
        contentPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 700));
        getContentPane().setLayout(new java.awt.BorderLayout(20, 5));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        navigationPanel.setMaximumSize(new java.awt.Dimension(32767, 60));
        navigationPanel.setMinimumSize(new java.awt.Dimension(0, 60));
        navigationPanel.setPreferredSize(new java.awt.Dimension(566, 60));
        navigationPanel.setLayout(new java.awt.BorderLayout());

        rightNavPanel.setMinimumSize(new java.awt.Dimension(255, 43));

        newThreadBtn.setText("Create New Thread");

        suggestBtn.setText("Suggest a New Group");
        suggestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suggestBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rightNavPanelLayout = new javax.swing.GroupLayout(rightNavPanel);
        rightNavPanel.setLayout(rightNavPanelLayout);
        rightNavPanelLayout.setHorizontalGroup(
            rightNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightNavPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(suggestBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newThreadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        rightNavPanelLayout.setVerticalGroup(
            rightNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rightNavPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rightNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(suggestBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(newThreadBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        navigationPanel.add(rightNavPanel, java.awt.BorderLayout.LINE_END);

        groupsCmbBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Computer", "Lounge", "Movies", "Musics" }));
        groupsCmbBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupsCmbBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftNavPanelLayout = new javax.swing.GroupLayout(leftNavPanel);
        leftNavPanel.setLayout(leftNavPanelLayout);
        leftNavPanelLayout.setHorizontalGroup(
            leftNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftNavPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupsCmbBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        leftNavPanelLayout.setVerticalGroup(
            leftNavPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftNavPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupsCmbBox, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        navigationPanel.add(leftNavPanel, java.awt.BorderLayout.CENTER);

        jPanel1.add(navigationPanel);

        descriptionPanel.setMaximumSize(new java.awt.Dimension(32767, 50));
        descriptionPanel.setMinimumSize(new java.awt.Dimension(0, 50));

        groupDescriptionLbl.setText("Description here.");

        javax.swing.GroupLayout descriptionPanelLayout = new javax.swing.GroupLayout(descriptionPanel);
        descriptionPanel.setLayout(descriptionPanelLayout);
        descriptionPanelLayout.setHorizontalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupDescriptionLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
        descriptionPanelLayout.setVerticalGroup(
            descriptionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(descriptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(groupDescriptionLbl)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        groupDescriptionLbl.getAccessibleContext().setAccessibleName("");

        jPanel1.add(descriptionPanel);

        contentPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        contentPanel.setAutoscrolls(true);
        contentPanel.setMinimumSize(new java.awt.Dimension(0, 500));
        contentPanel.setLayout(new java.awt.CardLayout(5, 5));
        jPanel1.add(contentPanel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void suggestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suggestBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_suggestBtnActionPerformed

    private void groupsCmbBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupsCmbBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_groupsCmbBoxActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JLabel groupDescriptionLbl;
    private javax.swing.JComboBox groupsCmbBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel leftNavPanel;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JButton newThreadBtn;
    private javax.swing.JPanel rightNavPanel;
    private javax.swing.JButton suggestBtn;
    // End of variables declaration//GEN-END:variables
}
