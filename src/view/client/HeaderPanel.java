package view.client;

import control.client.HeaderAction;
import control.client.SettingAction;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.EPanelTypes;

/**
 *
 * @author S3224743
 */
public class HeaderPanel extends javax.swing.JPanel
{
    //private members
    private static HeaderPanel headerPanel;
    private JLabel signInBtn;
    private JLabel signUpBtn;
    private JLabel signOutMemberBtn;
    private JLabel signOutAdminBtn;
    private JLabel settingBtn;
    private JPanel mainHeaderPanel;
    private JPanel memberHeaderPanel;
    private JPanel adminHeaderPanel;
    private CardLayout buttonCardLayout;
    private HeaderAction headerAction;
    private SettingAction settingAction;
    private BaseClient baseClient;
    private EPanelTypes ePanelTypes;


    //Constructor
    private HeaderPanel(BaseClient baseClient)
    {
        this.initComponents();

        //Initialized
        this.baseClient=baseClient;
        this.headerAction=new HeaderAction(this);
        this.signInBtn=new JLabel("Sign-in");
        this.signUpBtn=new JLabel("Sign-up");
        this.signOutAdminBtn=new JLabel("Sign-out");
        this.signOutMemberBtn=new JLabel("Sign-out");
        this.settingBtn=new JLabel("Setting");
        this.mainHeaderPanel=new JPanel(new FlowLayout(2,5, 5));
        this.memberHeaderPanel=new JPanel(new FlowLayout(2,5, 5));
        this.adminHeaderPanel=new JPanel(new FlowLayout(2,5, 5));
        this.buttonCardLayout=new CardLayout(5,5);
        this.ePanelTypes=EPanelTypes.GUEST;
        this.settingAction=new SettingAction(this);

        //This Header Panel
        this.add(Box.createHorizontalStrut(15),BorderLayout.WEST);

        //mainHeaderPanel
        this.mainHeaderPanel.add(this.signInBtn);
        this.mainHeaderPanel.add(Box.createHorizontalStrut(5));
        this.mainHeaderPanel.add(this.signUpBtn);

        //memberHeaderPanel
        this.memberHeaderPanel.add(this.signOutMemberBtn);

        //adminHeaderPanel
        this.adminHeaderPanel.add(this.settingBtn);
        this.adminHeaderPanel.add(Box.createHorizontalStrut(5));
        this.adminHeaderPanel.add(this.signOutAdminBtn);

        //buttonPanel
        this.buttonPanel.setLayout(buttonCardLayout);
        this.buttonPanel.add(this.mainHeaderPanel,EPanelTypes.GUEST.name());
        this.buttonPanel.add(this.memberHeaderPanel,EPanelTypes.MEMBER.name());
        this.buttonPanel.add(this.adminHeaderPanel,EPanelTypes.ADMIN.name());

        //Sign-in
        this.signInBtn.setForeground(Color.BLUE);
        this.signInBtn.addMouseMotionListener(this.headerAction);
        this.signInBtn.addMouseListener(this.headerAction);
        this.signInBtn.setFont(new Font("Tahoma",Font.PLAIN,14));

        //Sign-up
        this.signUpBtn.setForeground(Color.BLUE);
        this.signUpBtn.addMouseMotionListener(this.headerAction);
        this.signUpBtn.addMouseListener(this.headerAction);
        this.signUpBtn.setFont(new Font("Tahoma",Font.PLAIN,14));

        //Sign-out Admin
        this.signOutAdminBtn.setForeground(Color.BLUE);
        this.signOutAdminBtn.addMouseMotionListener(this.headerAction);
        this.signOutAdminBtn.addMouseListener(this.headerAction);
        this.signOutAdminBtn.setFont(new Font("Tahoma",Font.PLAIN,14));

        //Sign-out Member
        this.signOutMemberBtn.setForeground(Color.BLUE);
        this.signOutMemberBtn.addMouseMotionListener(this.headerAction);
        this.signOutMemberBtn.addMouseListener(this.headerAction);
        this.signOutMemberBtn.setFont(new Font("Tahoma",Font.PLAIN,14));

        //Setting
        this.settingBtn.setForeground(Color.BLUE);
        this.settingBtn.addMouseMotionListener(this.headerAction);
        this.settingBtn.addMouseListener(this.headerAction);
        this.settingBtn.addMouseListener(this.settingAction);
        this.settingBtn.setFont(new Font("Tahoma",Font.PLAIN,14));
    }//end of private HeaderPanel()

    //public static methods
    public static HeaderPanel getInstance(BaseClient baseClient)
    {
        if(headerPanel==null)
            headerPanel=new HeaderPanel(baseClient);
        return headerPanel;
    }//end of public static HeaderPanel getInstance()

    //Properties
    public JLabel getSettingBtn()
    {
        return this.settingBtn;
    }

    public JLabel getSignInBtn()
    {
        return this.signInBtn;
    }

    public JLabel getSignOutAdminBtn()
    {
        return this.signOutAdminBtn;
    }

    public JLabel getSignOutMemberBtn()
    {
        return signOutMemberBtn;
    }

    public JLabel getSignUpBtn()
    {
        return this.signUpBtn;
    }

    public BaseClient getBaseClient()
    {
        return this.baseClient;
    }

    public EPanelTypes getEPanelTypes()
    {
        return this.ePanelTypes;
    }

    public JLabel getWelcomeLbl()
    {
        return welcomeLbl;
    }

    //public methods
    public void showButtonPanel(EPanelTypes ePanelTypes,String welcomeMessage)
    {
        this.welcomeLbl.setText(welcomeMessage);
        this.buttonCardLayout.show(this.buttonPanel,ePanelTypes.name());
        
        
        switch(ePanelTypes)
        {
            case GUEST:
                this.baseClient.showContentPanel(EPanelTypes.LIST_TOPIC);
                this.baseClient.showNavigationPanel(EPanelTypes.GUEST);
                this.baseClient.getTopics().showNorthPanel(EPanelTypes.GUEST);
                this.ePanelTypes=EPanelTypes.GUEST;
                break;
            case MEMBER:
                this.baseClient.showNavigationPanel(EPanelTypes.MEMBER);
                this.baseClient.getTopics().showNorthPanel(EPanelTypes.MEMBER);
                this.ePanelTypes=EPanelTypes.MEMBER;
                break;
            case ADMIN:
                this.baseClient.showNavigationPanel(EPanelTypes.ADMIN);
                this.baseClient.getTopics().showNorthPanel(EPanelTypes.ADMIN);
                this.ePanelTypes=EPanelTypes.ADMIN;
                break;
        }//end of switch(ePanelTypes)
    }//end of public void showButtonPanel(EPanelTypes ePanelTypes,String welcomeMessage)

    public void showButtonPanel(EPanelTypes ePanelTypes)
    {
        this.showButtonPanel(ePanelTypes, this.welcomeLbl.getText());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeLbl = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();

        setAlignmentX(20.0F);
        setAlignmentY(20.0F);
        setLayout(new java.awt.BorderLayout());

        welcomeLbl.setText("Hello message.");
        add(welcomeLbl, java.awt.BorderLayout.CENTER);

        buttonPanel.setLayout(new java.awt.CardLayout());
        add(buttonPanel, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel welcomeLbl;
    // End of variables declaration//GEN-END:variables

}
