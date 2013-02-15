/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.IConstant;
import view.client.BaseClient;
import view.client.PostPanel;

/**
 * This class is for admin to block the user inside the postPanel
 * @author S3259511
 */
public class BlockUserAction implements ActionListener
{

    //private fields
    private BaseClient baseClient;
    private PostPanel post;

    public BlockUserAction(PostPanel post)
    {
        //initialized
        this.post = post;
        this.baseClient = BaseClient.getInstance();
    }

    public void actionPerformed(ActionEvent e)
    {
        try
        {
            String username = this.post.getUsername().getText();
            Object arguments[]={username};
            //pass the query and arguments to the server
            this.baseClient.getIServer().showMembers(IConstant.BLOCK_MEMBER_QUERY, arguments);
            this.baseClient.showDialog(username + " has been suspended.");
        } catch (RemoteException ex)
        {
            Logger.getLogger(BlockUserAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(BlockUserAction.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
