package model.server;

import model.Member;
import model.InterestGroup;
import model.Post;
import model.Instruction;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Topic;
import util.EPanelTypes;
import util.IConstant;
import view.client.BaseClient;

/**
 * @author S3259511
 */
public class DBConnection
{
    //private fields
    private final String dburl;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private DatabaseMetaData dbMD;
    private ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
    private ArrayList<InterestGroup> groupList = new ArrayList<InterestGroup>();
    private ArrayList<Member> memberList = new ArrayList<Member>();
    private ArrayList<Topic> topicList = new ArrayList<Topic>();
    private ArrayList<Post> postList = new ArrayList<Post>();

    //public constructor
    public DBConnection(String dbUser, String dbPassword)
    {        
        this.dburl = "jdbc:oracle:thin:@emu.cs.rmit.edu.au:1521:GENERAL";
        try
        {
            //Connect to database
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
            this.dbMD = connection.getMetaData();
            this.connection.setAutoCommit(false);
            statement = connection.createStatement();
        }
        catch (SQLException sqlEx)
        {
            System.out.println("Error connecting to database");
            try
            {
                connection.rollback();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            sqlEx.printStackTrace();
            System.exit(1);

        } catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        } finally
        {
            try
            {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    //Set up the database
    public void initialDB()
    {
        try
        {            
            this.createSequence();
            System.out.println("Sequences created.");
            this.createTable();
            System.out.println("Database created.");
        }
        catch (SQLException sqlEx)
        {
            System.out.println("Error connecting to database");
            try
            {
                connection.rollback();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            sqlEx.printStackTrace();
            System.exit(1);
        } catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(1);
        } finally
        {
            try
            {
                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public void closeDB() throws SQLException
    {
        statement.close();
        connection.close();
    }

    //Create sequence that will be used for generating id which property is number, automatically
    public void createSequence() throws SQLException
    {
        String groupSequence = "CREATE SEQUENCE groupIncrement "
                + "START WITH 1 "
                + "INCREMENT BY 1 "
                + "NOMAXVALUE ";
        try
        {
            statement.executeUpdate(groupSequence);
        } catch (SQLException ex)
        {
            statement.executeUpdate("DROP SEQUENCE groupIncrement");
            statement.executeUpdate(groupSequence);
        }

        String topicSequence = "CREATE SEQUENCE topicIncrement "
                + "START WITH 1 "
                + "INCREMENT BY 1 "
                + "NOMAXVALUE ";
        try
        {
            statement.executeUpdate(topicSequence);
        } catch (SQLException ex)
        {
            statement.executeUpdate("DROP SEQUENCE topicIncrement");
            statement.executeUpdate(topicSequence);
        }
    }

    //Checks if the table is existed
    public boolean tableExist(String tableName) throws SQLException
    {
        ResultSet metaResults = dbMD.getTables(null, null, null,
                new String[]
                {
                    "TABLE"
                });
        if (metaResults != null)
        {
            while (metaResults.next())
            {
                String table = metaResults.getString(3);
                if (tableName.equalsIgnoreCase(table))
                {
                    return true;
                }
            }
        }
        return false;
    }

    //create tables
    public void createTable() throws SQLException
    {
        String table = "";

        String tableNames[] =
        {
            "instruction", "member", "interestgroup", "topic", "post"
        };
        for (int i = 0; i < tableNames.length; i++)
        {
            if (tableExist(tableNames[i]))
            {
                statement.executeUpdate("drop table " + tableNames[i] + " cascade constraint");
                System.out.println("Table " + tableNames[i] + " dropped");
            }
        }

        table = "create table instruction"
                + "(mesgId NUMBER(1),"
                + "message VARCHAR2(500) NOT NULL)";
        statement.executeUpdate(table);
        System.out.println("Instruction table created.");

        table = "create table member"
                + "(username VARCHAR2(20),"
                + "password VARCHAR2(20) NOT NULL,"
                + "privilege NUMBER(1) NOT NULL,"
                + "status NUMBER(1) NOT NULL,"
                + "CONSTRAINT pk_member PRIMARY KEY (username) )";
        statement.executeUpdate(table);
        System.out.println("Member table created.");

        table = "create table interestGroup"
                + "(groupID NUMBER(5),"
                + "groupName  VARCHAR2(20) NOT NULL,"
                + "description VARCHAR2(100) NOT NULL,"
                + "creatorName VARCHAR2(20) NOT NULL,"
                + "createDate DATE NOT NULL,"
                + "groupStatus NUMBER(1) NOT NULL,"
                + "CONSTRAINT pk_interestGroup PRIMARY KEY (groupID),"
                + "FOREIGN KEY(creatorName) REFERENCES member (username) ON DELETE CASCADE)";
        statement.executeUpdate(table);
        System.out.println("InterestGroup table created.");

        table = "create table topic"
                + "(topicID NUMBER(5),"
                + "title VARCHAR2(50) NOT NULL,"
                + "uName VARCHAR2(20) NOT NULL,"
                + "topicDate DATE NOT NULL,"
                //+ "content VARCHAR2(500) NOT NULL,"
                + "gName VARCHAR2(20) NOT NULL,"
                + "CONSTRAINT pk_topic PRIMARY KEY (topicID),"
                + "FOREIGN KEY(uName) REFERENCES member (username) ON DELETE CASCADE)";
        statement.executeUpdate(table);
        System.out.println("Topic table created.");

        table = "create table post"
                + "(postID NUMBER(5),"
                + "tID NUMBER(5),"
                + "member VARCHAR2(20) NOT NULL,"
                + "postDate DATE NOT NULL,"
                + "content VARCHAR2(500) NOT NULL,"
                + "CONSTRAINT pk_post PRIMARY KEY (postID, tID),"
                + "CONSTRAINT fk_post FOREIGN KEY (tID) REFERENCES topic (topicID),"
                + "FOREIGN KEY(member) REFERENCES member (username) ON DELETE CASCADE)";
        statement.executeUpdate(table);
        System.out.println("Post table created.");

        statement.addBatch("insert into instruction values(1,'Welcome to Discussion Forum System! Click \"setting\" to maintain the Forum.')");
        statement.addBatch("insert into instruction values(0,'Welcome to Discussion Forum System! Select a group and post a topic.')");
        statement.addBatch("insert into member values('admin','admin',1,1)");
        statement.addBatch("insert into member values('weipeng','123',0,1)");
        statement.addBatch("insert into member values('tanri','123',1,1)");
        statement.addBatch("insert into member values('user','user',0,1)");
        statement.addBatch("insert into member values('guest','guest',0,0)");
        statement.addBatch("insert into member values('test','test',0,1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'IT','Topics about Information and Techonology','weipeng','21-Apr-2011',1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'Movie','Topics about Movies','admin','21-Apr-2011',1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'Music','Topics about Music','tanri','21-Apr-2011',1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'Sport','Topics about Sport','admin','21-Apr-2011',1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'Food','Topics about Food','user','21-Apr-2011',1)");
        statement.addBatch("insert into interestGroup values(groupIncrement.nextval,'News','Topics about latest news','test','21-Apr-2011',0)");
        statement.addBatch("insert into topic values(topicIncrement.nextval,'ASUS Eee Pad Transformer','weipeng','21-Apr-2010','IT')");
        statement.addBatch("insert into topic values(topicIncrement.nextval,'Fast and Furious 5','weipeng','21-Apr-2010','Movie')");
        statement.addBatch("insert into topic values(topicIncrement.nextval,'Greate music!','tanri','21-Apr-2010','Music')");
        statement.addBatch("insert into topic values(topicIncrement.nextval,'Tennis','weipeng','21-Apr-2010','Sport')");
        statement.addBatch("insert into topic values(topicIncrement.nextval,'iPhone keeps track of your movements','weipeng','21-Apr-2010','IT')");

        statement.addBatch("insert into post values(1,1,'weipeng','21-Apr-2010','ASUS Eee Pad Transformer hits US on April 26th for $399')");
        statement.addBatch("insert into post values(1,2,'weipeng','21-Apr-2010','FAST and FURIOUS 5 – More High Speed, High-Octane Action But Not Too Smart')");
        statement.addBatch("insert into post values(2,2,'user','21-Apr-2010','That''s interesting.')");
        statement.addBatch("insert into post values(3,2,'weipeng','21-Apr-2010','No. That''s so boring.')");
        statement.addBatch("insert into post values(4,2,'test','21-Apr-2010','Haven''t watched it yet. No comment.')");
        statement.addBatch("insert into post values(1,3,'tanri','21-Apr-2010','That''s a greate music! You must listen it.')");
        statement.addBatch("insert into post values(2,3,'weipeng','21-Apr-2010','What music is it?' )");
        statement.addBatch("insert into post values(1,4,'weipeng','21-Apr-2010','We are going to play tennis at this weekend. Please come and join us.')");
        statement.addBatch("insert into post values(2,4,'admin','21-Apr-2010','Where is it?' )");
        statement.addBatch("insert into post values(1,5,'weipeng','21-Apr-2010','The iPhone keeps a log of where it has been. A new open source program plots the data on a map for easy viewing. Privacy advocates are worried.')");

        statement.executeBatch();

    }

    public void getAll()
    {
        this.getInstructionList(IConstant.GET_ALL_INSTRUCTION_QUERY, null);
        this.getGroupList(IConstant.GET_ALL_GROUP_QUERY, null);
        this.getMemberList(IConstant.GET_ALL_MEMBER_QUERY, null);
        this.getPostList(IConstant.GET_ALL_POST_QUERY, null);
        this.getTopicList(IConstant.GET_ALL_TOPIC_QUERY, null);
    }

    public void printArrayLists()
    {

        for (Instruction i : instructionList)
        {
            System.out.println(i.toString());
        }

        for (InterestGroup ig : groupList)
        {
            System.out.println(ig.toString());
        }

        for (Member m : memberList)
        {
            System.out.println(m.toString());
        }

        for (Topic t : topicList)
        {
            System.out.println(t.toString());
        }

        for (Post p : postList)
        {
            System.out.println(p.toString());
        }
    }

    public void addMember(String username, String password, int privilege, int status) throws SQLException
    {
//        Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//        ResultSet rs = stmt.executeQuery("select * from member");

        try
        {
            System.out.println(username+" "+password+" "+privilege+" "+status);
            String query = "insert into member values(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, privilege);
            preparedStatement.setInt(4, status);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println(username+" "+password+" "+privilege+" "+status);
        memberList.add(new Member(username, password, privilege, status));
    }

    public boolean searchMember(String username)
    {
        for (Member m : memberList)
        {
            if (m.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public void displayTable(String s)
    {

        System.out.println("Printing " + s + " table ");
        try
        {
            ResultSet resultSet =
                    statement.executeQuery("select * from " + s);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
            {
                System.out.print(rsMetaData.getColumnName(i) + "\t");
            }
            System.out.println();
            while (resultSet.next())
            {
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++)
                {
                    System.out.print(resultSet.getString(i) + "\t");
                }
                System.out.println();
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }//end of public void displayTable(String s)

    public Member login(String username, String password)
    {
        try
        {
            String query = "select * from member where username = ? and password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            this.memberList.clear();
            this.addMembersArrayList(resultSet);
            if (this.memberList.isEmpty())
            {
                this.showDialog("Invalid Username and Password!");
                return null;
            }

            Member member = this.memberList.get(0);
            switch (member.getStatus())
            {
                case IConstant.BLOCK_USER:
                    this.showDialog("Your account is being suspended");
                    return null;
                case IConstant.UN_BLOCK_USER:
                    return member;
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                preparedStatement.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }//end of public login(String username, String password)

    public EPanelTypes signUp(String username, String password)
    {
        try
        {
            this.addMember(username, password, 0, 1);
            return EPanelTypes.SIGN_IN;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return EPanelTypes.SIGN_UP;

    }//end of public EPanelTypes signUp(String username, String password)

    public int getSequence(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        } finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return 0;
    }

    public ArrayList<InterestGroup> getGroupList(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            this.groupList.clear();
            this.addGroupsArrayList(resultSet);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return this.groupList;
    }

    public ArrayList<Instruction> getInstructionList(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            this.instructionList.clear();
            this.addInstructionArrayList(resultSet);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return instructionList;
    }

    public ArrayList<Member> getMemberList(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            this.memberList.clear();
            this.addMembersArrayList(resultSet);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return memberList;
    }

    public ArrayList<Post> getPostList(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            this.postList.clear();
            this.addPostArrayList(resultSet);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return this.postList;
    }

    public ArrayList<Topic> getTopicList(String query, Object arguments[])
    {
        try
        {
            ResultSet resultSet = this.executeQuery(query, arguments);
            this.topicList.clear();
            this.addTopicsArrayList(resultSet);
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception exception){exception.printStackTrace();}
        finally
        {
            try
            {
                this.preparedStatement.close();
            } catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return this.topicList;
    }    

    //private methods
    private EPanelTypes userPrevileged(ResultSet resultSet) throws SQLException,Exception
    {
        switch (resultSet.getInt(3))
        {
            case IConstant.MEMBER:
                return EPanelTypes.MEMBER;
            case IConstant.ADMIN:
                return EPanelTypes.ADMIN;
        }//end of switch(resultSet.getInt(3))
        return null;
    }

    private void addPostArrayList(ResultSet resultSet) throws SQLException,Exception
    {
        //local field
        Post post = null;
        int postID = 0;
        int tID = 0;
        String member = null;
        Date postDate = null;
        String content = null;

        while (resultSet.next())
        {
            System.out.println(resultSet.getString(1));
            postID = resultSet.getInt(1);
            tID = resultSet.getInt(2);
            member = resultSet.getString(3);
            postDate = resultSet.getDate(4);
            content = resultSet.getString(5);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String postDateString = simpleDateFormat.format(postDate);
            post = new Post(postID, tID, member, postDateString, content);
            this.postList.add(post);
        }
    }//end of private void addPostArrayList(ResultSet resultSet) throws SQLException

    private void addTopicsArrayList(ResultSet resultSet) throws SQLException,Exception
    {
        //local field
        int topicID = 0;
        String title = null;
        String uName = null;
        String topicDate = null;
        String content = null;
        String gName = null;
        Topic topic = null;

        while (resultSet.next())
        {
            topicID = resultSet.getInt(1);
            title = resultSet.getString(2);
            uName = resultSet.getString(3);
            topicDate = resultSet.getString(4);
            gName = resultSet.getString(5);
            topic = new Topic(topicID, title, uName, topicDate, gName);
            topicList.add(topic);
        }
    }//end of private void addTopicsArrayList(ResultSet resultSet) throws SQLException

    private void addMembersArrayList(ResultSet resultSet) throws SQLException,Exception
    {
        //local field
        String username = null;
        String password = null;
        int privilege = 0;
        int status = 0;
        Member member = null;

        while (resultSet.next())
        {
            username = resultSet.getString(1);
            password = resultSet.getString(2);
            privilege = resultSet.getInt(3);
            status = resultSet.getInt(4);
            member = new Member(username, password, privilege, status);
            memberList.add(member);
        }
    }//end of private void addMembersArrayList(ResultSet resultSet) throws SQLException

    private void addGroupsArrayList(ResultSet resultSet) throws SQLException,Exception
    {
        //local field
        int groupID = 0;
        String groupName = null;
        String description = null;
        String creatorName = null;
        String createDate = null;
        int groupStatus = 0;
        InterestGroup interestGroup = null;

        while (resultSet.next())
        {
            groupID = resultSet.getInt(1);
            groupName = resultSet.getString(2);
            description = resultSet.getString(3);
            creatorName = resultSet.getString(4);
            createDate = resultSet.getString(5);
            groupStatus = resultSet.getInt(6);
            interestGroup = new InterestGroup(groupID, groupName, description, creatorName, createDate, groupStatus);
            groupList.add(interestGroup);
        }
    }//end of private void addGroupsArrayList(ResultSet resultSet) throws SQLException

    private void addInstructionArrayList(ResultSet resultSet) throws SQLException,Exception
    {
        //local field
        int mesgId = 0;
        String message = null;
        Instruction instruction = null;

        while (resultSet.next())
        {
            mesgId = resultSet.getInt(1);
            message = resultSet.getString(2);
            instruction = new Instruction(mesgId, message);
            instructionList.add(instruction);
        }
    }//end of private void addInstructionArrayList(ResultSet resultSet) throws SQLException

    private void showDialog(String message)
    {
        JOptionPane.showMessageDialog(BaseClient.getInstance(),
                message, "!", JOptionPane.INFORMATION_MESSAGE);
    }

    private ResultSet executeQuery(String query, Object arguments[])
    {
        //local field
        ResultSet resultSet = null;

        try
        {
            this.preparedStatement = connection.prepareStatement(query);

            if (arguments != null)
            {
                for (int i = 1; i <= arguments.length; i++)
                {
                    this.setPreparedStatementArguments(i, arguments[i - 1]);
                }
            }
            resultSet = this.preparedStatement.executeQuery();
        } catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return resultSet;
    }//end of private ResultSet executeQuery(String query, Object arguments[])

    private void setPreparedStatementArguments(int paramIndex, Object argument) throws SQLException
    {
        if (argument instanceof String)
        {
            this.preparedStatement.setString(paramIndex, (String) argument);
        }
        else if (argument instanceof Integer)
        {
            this.preparedStatement.setInt(paramIndex, (Integer) argument);
        }
    }
}
