package DATABASE.Connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLConnection
{
	private Connection conn;
	private final String driver = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306";
	private final String dbname = "soundsmhound";
	private final String user = "root";
	private final String psw = "admin";

	public SQLConnection ()
	{
		conn = null;
	}
	
	public void connect()
	{
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url + "/" + dbname , user , psw);
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public Connection getConnection ()
	{
		return conn;
	}
}
