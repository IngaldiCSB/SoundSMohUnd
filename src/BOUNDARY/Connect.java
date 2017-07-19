package BOUNDARY;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import CONTROL.recognizer;

public class Connect extends Thread
{
	private Socket c;
	private DataInputStream in;
	private DataOutputStream out;
	
	
	public Connect (Socket c)
	{
		this.c = c;
		
		try
		{
			in = new DataInputStream(c.getInputStream());
			out = new DataOutputStream (c.getOutputStream());

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void run ()
	{
		System.out.println("guard:client connesso");
		ArrayList<Float> in = readInput ();
		
		recognizer r = new recognizer ();
		r.recognize( in , this);
		
	}
	
	public void send (String titolo , String autore , int anno , String youtube)
	{
		try
		{
			//Non cambiare ordine
			out.writeUTF(titolo);
			out.writeUTF(autore);
			out.writeUTF(Integer.toString(anno));
			out.writeUTF(youtube);
			out.writeUTF(protocol.CMD_STOP);
			out.flush();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


		

	}
	
	private ArrayList<Float> readInput ()
	{

		ArrayList<Float> to_return = new ArrayList <Float> ();
		
		
		try
		{
			float readline = in.readFloat();

			while (readline != protocol.CMD_ENDARRAY)
			{
					to_return.add(readline);
					readline = in.readFloat();


			}
			return to_return;

		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}

		
	}
}
