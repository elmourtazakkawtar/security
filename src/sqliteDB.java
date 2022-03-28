import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sqliteDB {
Connection c=null;
Statement stmt=null;
public sqliteDB() {
	super();
	try {
		Class.forName("org.sqlite.JDBC");
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		c=DriverManager.getConnection("jdbc:sqlite:stagaDatabase.sqlite");
		System.out.println("con ok");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
public void add(String query) {
	try {
		this.stmt=c.createStatement();
		stmt.executeQuery(query);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void closeConnection() {
	try {
		c.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
