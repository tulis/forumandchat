package util;

import java.awt.Cursor;

/**
 *
 * @author S3224743
 */
public interface IConstant
{
    //CURSOR
    public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    //RMI REGISTRY
    public static final String SERVER_NAME_REBIND = "Server";
    public static final int PORT_NUMBER = 12345;

    //LOGIN
    public static final int BLOCK_USER = 0;
    public static final int UN_BLOCK_USER = 1;
    public static final int INVALID_USER_AND_PASS = -1;
    public static final int ADMIN = 1;
    public static final int MEMBER = 0;
    public static final int GUEST=0;

    //SIGN-UP
    public static final int USERNAME_MIN_LENGTH = 4;
    public static final int PASSWORD_MIN_LENGTH = 6;
    
    //INTERESTGROUP
    public static final int ACTIVE_GROUP = 1;
    public static final int PROPOSED_GROUP = 0;

    //TOPICS TABLE
    public final static int ADMIN_COLUMN_TOPIC_ID = 1;
    public final static int ADMIN_COLUMN_TOPIC_TTILE = 2;
    public final static int DEFAULT_COLUMN_TOPIC_TITLE = 0;

    //
    public final static String SERVER = "";
    
    //BaseClient setActionCommand
    public final static String NEW_THREAD_BUTTON = "newThreadBtn";
    public final static String NEW_TOPIC_BUTTON = "newTopicBtn";
    public final static String DELETE_TOPIC_BUTTON = "deleteTopicBtn";
    public final static String MEMBER_MGMT_BUTTON = "okMemberBtn";
    public final static String GROUP_MGMT_BUTTON = "okGroupsBtn";
    public final static String SUGGEST_GROUP = "suggestGroupBtn";
    public final static String GROUP_COMBO_BOX = "groupCmbBox";
    public final static String SIGN_IN="Sign-in";
    public final static String SIGN_OUT="Sign-out";
    public final static String DELETE_POST_BUTTON = "deletePostBtn";

    //QUERY POST
    public static final String GET_ALL_POST_QUERY = "SELECT * FROM post";
    public static final String SHOW_POST_QUERY = "SELECT * FROM post WHERE tID = ?";
    public static final String POST_MAX_QUERY = "SELECT MAX(postID) FROM POST WHERE tID= ?";
    public static final String ADD_NEW_POST = "INSERT INTO post VALUES(?,?,?,?,?)";
    public static final String DELETE_ALL_POST_QUERY = "DELETE FROM POST WHERE TID = ?";
    public static final String DELETE_POST_QUERY = "DELETE FROM POST WHERE POSTID = ? AND TID = ?";

    //QUERY INSTRUCTION
    public static final String GET_ALL_INSTRUCTION_QUERY = "SELECT * FROM instruction";
    public static final String UPDATE_INSTRUCTION_QUERY=String.format("UPDATE instruction SET message = ? WHERE mesgID = %d",IConstant.GUEST);
    public static final String SHOW_INSTRUCTION_QUERY=String.format("SELECT * FROM instruction WHERE mesgID = %d", IConstant.GUEST);

    //QUERY GROUP
    public static final String GET_ALL_GROUP_QUERY = "SELECT * FROM interestGroup";
    public static final String ADD_GROUP_QUERY = "INSERT INTO interestGroup VALUES(?,?,?,?,?,?)";
    public static final String GROUP_INCREMENT_QUERY = "SELECT groupIncrement.nextval FROM dual";
    public static final String DELETE_GROUP_QUERY = "DELETE FROM interestGroup WHERE groupname = ? ";
    public static final String UPDATE_GROUP_QUERY = "UPDATE interestGroup SET groupname = ?, description =?, groupstatus =? WHERE groupid = ? ";

    //QUERY MEMEBER
    public static final String GET_ALL_MEMBER_QUERY = "SELECT * FROM member";
    public static final String ADD_NEW_MEMBER_QUERY = "INSERT INTO member VALUES(?,?,?,?)";
    public static final String GET_STATUS_MEMBER_QUERY="SELECT * FROM member WHERE username = ?";
    public static final String DELETE_MEMBER_QUERY = "DELETE FROM member WHERE username = ? ";
    public static final String UPDATE_MEMBER_QUERY = "UPDATE member SET password = ?, privilege = ?, status = ? WHERE username = ?"; 
    public static final String BLOCK_MEMBER_QUERY=String.format("UPDATE member SET status = %s WHERE username = ?",IConstant.BLOCK_USER);

    //QUERY TOPIC
    public static final String TOPIC_INCREMENT_QUERY = "SELECT topicIncrement.nextval FROM dual";
    public static final String GET_ALL_TOPIC_QUERY = "SELECT * FROM topic";
    public static final String SHOW_TOPIC_QUERY = "SELECT * FROM topic WHERE gName = ?";
    public static final String ADD_NEW_TOPIC = "INSERT INTO topic VALUES(?,?,?,?,?)";
    public static final String DELETE_TOPIC_QUERY = "DELETE FROM TOPIC WHERE TOPICID = ?";
        
    //CHAT MESSAGE
    public static final String QUIT_MESAGE=" <has left %s>";
    public static final String JOIN_MESSAGE=" <has joined %s>";
}
