package BOUNDARY;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends Thread
{
	private final int port = 4000;
	
	private ServerSocket myServer;
	
	public Server ()
	{
		try
		{
			myServer = new ServerSocket (port);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run ()
	{

		try
		{
			while (true)
			{

				Socket myClient = myServer.accept();
				Connect myConn = new Connect (myClient);
				myConn.start ();

				
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
