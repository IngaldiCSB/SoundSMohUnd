package DATABASE;

import java.util.ArrayList;
import java.util.List;

import DATABASE.Connectors.BerkeleyConnection;
import ENTITY.songFeature;

import com.sleepycat.bind.tuple.ByteBinding;
import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

//TODO non supporta storing della distanza dal centroide
public class featureGateway
{
	private BerkeleyConnection conn;
	
	public featureGateway ()
	{
		conn = new BerkeleyConnection ();
		
	}
	
	public boolean create (int key , byte value)
	{
		conn.Connect();
		Database db = conn.getDB();
		Transaction txn = conn.getTransaction();
		
		
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry valueEntry = new DatabaseEntry();
		
		IntegerBinding.intToEntry(key, keyEntry);
		ByteBinding.byteToEntry(value, valueEntry);
		
		OperationStatus status = db.put(txn, keyEntry, valueEntry);
		
		
		
		if (status == OperationStatus.SUCCESS)
		{
			txn.commit();
			conn.disconnect();
			return true;
		}
		else 
		{
			System.out.println ("errore inserimento dati");
			conn.disconnect();
			return false;
		}

	}
	
	public byte read (int key)
	{
		conn.Connect();
		Database db = conn.getDB();
		Transaction txn = conn.getTransaction();
				
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry valueEntry = new DatabaseEntry ();

		IntegerBinding.intToEntry(key, keyEntry);

		OperationStatus status = db.get(txn, keyEntry, valueEntry, null);
		
		byte ret = ByteBinding.entryToByte(valueEntry);
		
		conn.disconnect();
		
		return ret;

	}
	
	public boolean exists (int key , byte value)
	{
		conn.Connect();
		Database db = conn.getDB();
		Transaction txn = conn.getTransaction();
		
		
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry valueEntry = new DatabaseEntry();
		
		IntegerBinding.intToEntry(key, keyEntry);
		ByteBinding.byteToEntry(value, valueEntry);
		
		OperationStatus status = db.getSearchBoth(txn, keyEntry, valueEntry , null);
		txn.commit();
		conn.disconnect();
		
		if (status != OperationStatus.NOTFOUND)
		{
			return true;
		}
		else 
		{
			return false;
		}


	}
	
	//TODO GRANDISSIMO INGUACCHIO
	public ArrayList <songFeature> readAll (byte val)
	{
		conn.Connect();
		ArrayList <songFeature> ret = new ArrayList <songFeature> ();
		
		Cursor c =  conn.getDB().openCursor( null, null);
				
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry valueEntry = new DatabaseEntry();
		
		
		ByteBinding.byteToEntry(val, valueEntry);
		
		int key = 0; 
		

		while (c.getNext(keyEntry, valueEntry, null) != OperationStatus.NOTFOUND)
		{
			key = IntegerBinding.entryToInt(keyEntry);
			
			if ( exists(key , val) )
			{
				ret.add(new songFeature(key , val));
			}
		}
		
		conn.disconnect();
		
		return ret;
	}
}
