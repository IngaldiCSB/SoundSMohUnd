package DATABASE.Connectors;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;

//TODO manca file
//TODO completare

public class BerkeleyConnection
{
	private final String DBPath = "C:\\Users\\gennaro\\SoundSMhoUnd\\dati";
	private final String dbName = "SSMHU_DB";
	private File dbFile;
	
	private EnvironmentConfig envConfig;
	private Environment env;
	
	private DatabaseConfig dbConfig;
	private Database db;
	
	public BerkeleyConnection ()
	{
		dbFile = new File (DBPath);
	}
	
	public boolean Connect ()
	{
		envConfig = new EnvironmentConfig ();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		
		env = new Environment (dbFile , envConfig);
		
        dbConfig = new DatabaseConfig();
        dbConfig.setTransactional(true);
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(false);
        
        Transaction txn = env.beginTransaction(null, null);
        db = env.openDatabase(txn, dbName, dbConfig);
        txn.commit ();
        
        
		return true;
	}
	
	public void disconnect ()
	{
		db.close();
		env.close();
	}
	
	public Database getDB ()
	{
		return db;
	}
	
	public Transaction getTransaction ()
	{
		return env.beginTransaction(null, null);
	}
}
