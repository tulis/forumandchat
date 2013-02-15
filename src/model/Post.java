package model;

import java.io.Serializable;

/**
 *
 * @author S3259511
 */
public class Post implements Serializable
{

    private int postID;
    private int tID;
    private String member;
    private String postDate;
    private String content;

    public Post(int postID, int tID, String member, String postDate, String content)
    {
        this.postID = postID;
        this.tID = tID;
        this.member = member;
        this.postDate = postDate;
        this.content=content;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getMember()
    {
        return member;
    }

    public void setMember(String member)
    {
        this.member = member;
    }

    public String getPostDate()
    {
        return postDate;
    }

    public void setPostDate(String postDate)
    {
        this.postDate = postDate;
    }

    public int getPostID()
    {
        return postID;
    }

    public void setPostID(int postID)
    {
        this.postID = postID;
    }

    public int gettID()
    {
        return tID;
    }

    public void settID(int tID)
    {
        this.tID = tID;
    }

    @Override
    public String toString()
    {
        return "Post{" + "postID=" + postID + " tID=" + tID + " member=" + member + " postDate=" + postDate + " content=" + content + '}';
    }
}
