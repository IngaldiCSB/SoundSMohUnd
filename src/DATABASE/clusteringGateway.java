package DATABASE;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

//TODO apprendere informazioni su dimensione (k) dal file, e permetti al programmatore di modificarle

public class clusteringGateway
{
	private final String filePath = "C:\\Users\\gennaro\\SoundSMhoUnd\\dati\\clusteringData.txt";
	private File file;
	
	private final int k = 16;
	private final int dim = 12;
	
	DataOutputStream os;
	DataInputStream is;

	public clusteringGateway ()
	{
		try
		{
			file = new File (filePath);
			

			
			if (file.exists() == false)
			{
				file.createNewFile();
			}
			
			os = new DataOutputStream (new FileOutputStream (file) );
			is = new DataInputStream (new FileInputStream (file) );

		}
		catch (FileNotFoundException e)
		{

			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public float[][] readVals () throws IOException
	{
		float [][] ret = new float [k][dim];
		
		for (int i = 0 ; i < k ; i++)
		{
			for (int j = 0 ; j < dim ; j++)
			{
				ret[i][j] = is.readFloat();
				System.out.println(ret[i][j]);
			}
			System.out.println();

		}
		
		return ret;
	}
	
	public int readK ()
	{
		return k;
	}
	
	public int readDim ()
	{
		return dim;
	}
	
	public boolean updateVals (float[][] upd) throws IOException
	{
		file.delete();
		file.createNewFile();
		
		for (int i = 0 ; i < k ; i++)
		{
			for (int j = 0 ; j < dim ; j++)
			{
				os.writeFloat(upd[i][j]);
				os.flush();
			}
			
		}
	
		

		return true;
	}

	public void updateK(int k2)
	{
		// TODO Auto-generated method stub
		
	}

	public void updateDim(int dim2)
	{
		// TODO Auto-generated method stub
		
	}
	

	
}
