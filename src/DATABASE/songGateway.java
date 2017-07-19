package DATABASE;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DATABASE.Connectors.SQLConnection;

public class songGateway
{
	private SQLConnection conn;
	
	public static final int IND_TITOLO = 0;
	public static final int IND_AUTORE = 1;
	public static final int IND_ANNO = 2;
	public static final int IND_YOUTUBE = 3;
	
	public songGateway ()
	{
		conn = new SQLConnection ();
		conn.connect ();
	}
	
	public int create (String titolo , String autore , int anno , String youtube)
	{
		String sql = "INSERT INTO song VALUES ('" + titolo + "' , '" + autore + "' , " + anno + " , '" + youtube + "' , " + "null" + ")";
		String sql2 = "SELECT ID FROM song ORDER BY ID DESC LIMIT 1";
		try
		{
			PreparedStatement ps = conn.getConnection().prepareStatement(sql);
			ps.execute();
			ps.close();
			
			PreparedStatement ps2 = conn.getConnection().prepareStatement(sql2);
			ResultSet rs2 = ps2.executeQuery();
			
			rs2.next();
			int lastID = rs2.getInt("ID");
			
			rs2.close();
			ps2.close();
			return lastID;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return -1;
		}

	}
	
	//RESTITUISCE IL RISULTATO DELLA QUERY IN UN VETTORE DI OBJECT
	//LE VARIABILI STATICHE IND_XX FANNO ACCEDERE AL CAMPO DI INTERESSE
	public Object[] read (int ID)
	{
		String sql = "SELECT * FROM song WHERE ID = " + ID;
		
		Object [] to_return = null;
		
		try
		{
			PreparedStatement ps;
			ps = conn.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs.next())
			{
				to_return = new Object [4];
				
				to_return[IND_TITOLO] = rs.getString("titolo");
				to_return[IND_AUTORE] = rs.getString("artista");
				to_return[IND_ANNO] = rs.getInt("anno");
				to_return[IND_YOUTUBE] = rs.getString("youtube");

				
			}
			
			
			
			rs.close();
			ps.close();

			
			return to_return;
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		

		
	}
}