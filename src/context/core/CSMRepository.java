package context.core;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class CSMRepository {
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public int find(String a, String b){
    	return 0;
    }
    // the interfaces we give includes getNextState. And the function of us inlcudes extract states, dynamically create states.
    // they have steady states, we also have. But we handle more FSM so we store them into database.
    
    public void createCASM(JSONObject obj) throws Exception{
    	//create CASM for an attribute, so first, to handle domain, second handle subject, third to handle 
    	//predicate, at last to handle object
    	
    	try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/csm?"
                            + "user=root&password=admin");
            int domainId = obj.getInt("domainId");
            int subjectId = obj.getJSONObject("subject").getInt("subjectId");
	    	//handle domain
	    	if(obj.getInt("domainId")==0){
	                preparedStatement = connect
	                        .prepareStatement("insert into  csm.csm_domains values (default, ?, ?)");
	                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
	                // Parameters start with 1
	                preparedStatement.setString(1, obj.getString("domainName"));
	                preparedStatement.setString(2, obj.getString("domainURI"));
	                preparedStatement.executeUpdate();
	                
	                ResultSet rs=preparedStatement.getGeneratedKeys();
	                if(rs.next()){
	                	domainId=rs.getInt(1);
	                }
	    	}
	    	
	    	//handle subject
	    	if(obj.getJSONObject("subject").getInt("subjectId")==0){
	    		try {
	                preparedStatement = connect
	                        .prepareStatement("insert into  csm.csm_subjects values (default, ?, ?, ?, ?)");
	                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
	                // Parameters start with 1
	                preparedStatement.setString(1, obj.getJSONObject("subject").getString("subjectName"));
	                preparedStatement.setString(2, obj.getJSONObject("subject").getString("subjectURI"));
	                preparedStatement.setString(3, obj.getJSONObject("subject").getString("subjectType"));
	                preparedStatement.setInt(4, domainId);
	                preparedStatement.executeUpdate();
	                
	                ResultSet rs=preparedStatement.getGeneratedKeys();
	                if(rs.next()){
	                	subjectId=rs.getInt(1);
	                }
	            } catch (Exception e) {
	                throw e;
	            } finally {
	                close();
	            }
	    	}
	    	
	    	//handle predicate
	    	if(obj.getJSONObject("predicate").getInt("predicateId")==0){
	    		try {
	                preparedStatement = connect
	                        .prepareStatement("insert into  csm.csm_predicates values (default, ?, ?, ?, ?)");
	                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
	                // Parameters start with 1
	                preparedStatement.setString(1, obj.getJSONObject("predicate").getString("predicateName"));
	                preparedStatement.setString(2, obj.getJSONObject("predicate").getString("predicateURI"));
	                preparedStatement.setString(3, obj.getJSONObject("predicate").getString("predicateType"));
	                preparedStatement.setInt(4, subjectId);
	                preparedStatement.executeUpdate();
	            } catch (Exception e) {
	                throw e;
	            } finally {
	                close();
	            }
	    	}
	    	
	    	//handle object
	    	if(obj.getJSONObject("object").getInt("objectId")==0){
	    		try {
	                preparedStatement = connect
	                        .prepareStatement("insert into  csm.csm_objects values (default, ?, ?, ?, ?, ?)");
	                // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
	                // Parameters start with 1
	                preparedStatement.setString(1, obj.getJSONObject("object").getString("objectName"));
	                preparedStatement.setString(2, obj.getJSONObject("object").getString("objectURI"));
	                preparedStatement.setString(3, obj.getJSONObject("object").getString("objectType"));
	                preparedStatement.setString(4, obj.getJSONObject("object").getString("fromTo"));
	                preparedStatement.setInt(5, subjectId);
	                preparedStatement.executeUpdate();
	            } catch (Exception e) {
	                throw e;
	            } finally {
	                close();
	            }
	    	}
	    	
	    	//how to handle condition? One way is to define standard conditions. Or?
	    	//Aug 11, I will write why deisgn this way. what is the design of the tables,
	    	//and to add transitions..., then next step is to read from the tables to create a CSM in memory
	    	//new table will have csm id; and then the states, and then the transitions
	    	
	    	//create transition
	    	
	    	//update transition
	    	
	    	//register action
    	} catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    public void updateCASM(JSONObject obj){
    	//create transition
    	
    	//update transition
    	
    	//register action
    	
    	//de-register action
    	
    }
    
	public void getContext(String id){}
	public void getSituation(String id){}
	public void getAttributeCurrentState(String id){}
	public void getAttributePreState(String id){}
	public void getAttributPredicatedNextState(String id){}

    
    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/csm?"
                            + "user=root&password=mysql518");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            System.out.println("id: " + "test");
            resultSet = statement
                    .executeQuery("select * from csm.csm_domains");
            writeResultSet(resultSet);
/*
            // PreparedStatements can use variables and are more efficient
            preparedStatement = connect
                    .prepareStatement("insert into  world.city values (default, ?, ?, ?, ? , ?, ?)");
            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            // Parameters start with 1
            preparedStatement.setString(1, "Test");
            preparedStatement.setString(2, "TestEmail");
            preparedStatement.setString(3, "TestWebpage");
            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
            preparedStatement.setString(5, "TestSummary");
            preparedStatement.setString(6, "TestComment");
            preparedStatement.executeUpdate();

            preparedStatement = connect
                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
            resultSet = preparedStatement.executeQuery();
            writeResultSet(resultSet);

            // Remove again the insert comment
            preparedStatement = connect
            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
            preparedStatement.setString(1, "Test");
            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from feedback.comments");
            writeMetaData(resultSet);
*/
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String id = resultSet.getString("ID");
            String name = resultSet.getString("Name");
            String uri = resultSet.getString("uri");
            System.out.println("id: " + id);
            System.out.println("name: " + name);
            System.out.println("uri: " + uri);
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
