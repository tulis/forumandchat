package model;

import java.io.Serializable;

/**
 *
 * @author S3259511
 */
public class Instruction implements Serializable
{

    private int mesgId;             //mesgId: 1 for admin group; 0 for user group
    private String message;

    public Instruction(int mesgId, String message)
    {
        this.mesgId = mesgId;
        this.message = message;
    }

    public int getMesgId()
    {
        return mesgId;
    }

    public void setMesgId(int mesgId)
    {
        this.mesgId = mesgId;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "Instruction{" + "mesgId=" + mesgId + " message=" + message + '}';
    }
}
