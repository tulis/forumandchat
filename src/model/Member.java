package model;

import java.io.Serializable;

/**
 *
 * @author S3259511
 */
public class Member implements Serializable
{
    //private static final
    private static final int ADMIN=1;
    private static final int USER=0;
    private static final int SUSPEND=0;
    private static final int ACTIVE=1;

    //private fields
    private String username;
    private String password;
    private int privilege = USER;
    private int status = ACTIVE;

    public Member(String username, String password, int privilege, int status)
    {
        this.username = username;
        this.password = password;
        this.privilege = privilege;     //1:administrators, 0:users
        this.status = status;           //1:active, 0:suspend
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getPrivilege()
    {
        return privilege;
    }

    public String getUserPrivilege()
    {
        if (privilege == ADMIN)
        {
            return "Admin";
        }
        else
        {
            return "User";
        }
    }

    public String getUserStatus()
    {
        if (status == ACTIVE)
        {
            return "Active";
        }
        else
        {
            return "Suspend";
        }
    }

    public void setPrivilege(int privilege)
    {
        this.privilege = privilege;
    }

    public void setUserPrivilege(String privilege)
    {
        if (privilege.equals("Admin"))
        {
            this.privilege=ADMIN;
        }
        else
        {
            this.privilege=USER;
        }
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public void setUserStatus(String userStatus)
    {
        if (userStatus.equals("Active"))
        {
            this.status=ACTIVE;
        }
        else
        {
            this.status=SUSPEND;
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Override
    public String toString()
    {
        return "Member{" + "username=" + username + " password=" + password + " privilege=" + getUserPrivilege() + " status=" + getUserStatus() + '}';
    }
}
