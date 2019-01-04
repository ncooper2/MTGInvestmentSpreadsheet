import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDB {
	
	private static Connection con;
	private static boolean hasData = false;
	
	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, fname, lname FROM user");
		
		return res;
	}
	
	public ResultSet displaySets() throws ClassNotFoundException, SQLException {
		if(con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT id, setName FROM mtgSet");
		
		return res;
	}
	
	private void getConnection() throws ClassNotFoundException, SQLException {
		//Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
		initialize();
	}
	
	private void initialize() throws SQLException {
		if (!hasData) {
			hasData = true;
			Statement state = con.createStatement();
			ResultSet res = state.executeQuery("SELECT name FROM sqlite_master "
					+ "WHERE type='table' AND name='mtgSet'");
			if (!res.next()) {
				System.out.print("Building the set table");
				//build table
				Statement state2 = con.createStatement();
				state2.execute("CREATE TABLE mtgSet(id integer, setName varchar(60), "
						+ "primary key(id));");
			}
		}
	}
	
	/**
	 * Add a user to the database
	 * @param firstName
	 * @param lastName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addUser(String firstName, String lastName) throws ClassNotFoundException, SQLException {
		
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		state.execute("INSERT INTO user (fName, lName) values ('" + firstName + "', '" + lastName + "');");
		
	}
	
	/**
	 * Add a new set to the database
	 * @param setName
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addSet(String setName) throws ClassNotFoundException, SQLException {
		
		if (con == null) {
			getConnection();
		}
		
		Statement state = con.createStatement();
		
		ResultSet res = state.executeQuery("SELECT setName FROM mtgSet "
				+ "WHERE setName='" + setName + "'");
		
		if (!res.next()) {
			state.execute("INSERT INTO mtgSet (setName) values ('" + setName + "');");
		} else {
			System.out.println("The set '" + setName + "' already exists in the table.");
		}
	}

}
