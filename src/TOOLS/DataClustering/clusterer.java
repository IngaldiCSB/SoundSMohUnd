package TOOLS.DataClustering;

import java.util.Random;

import TOOLS.mathTools;
import ENTITY.clusteringInfo;
import ENTITY.songFeature;


//TODO completare

//SINGLETON!!!!!!!!!!!!!!!!!!!!!!!!!!
public class clusterer
{
	private clusteringInfo cInfo;;
	
	private static clusterer instance = null;
	
	public static int IND_CLUSTER = 0;
	public static int IND_DIST = 1;
	private clusterer ()
	{
		cInfo = new clusteringInfo ();
		cInfo.load ();

	}
	
	public static clusterer getInstance()
	{
		if (instance == null)
		{
			instance = new clusterer ();
		}
		return instance;
	}
		
		public Object [] getCluster (float [] v1)
		{
			byte clust = 0;
			double minDist = Double.MAX_VALUE;
			
			int k = cInfo.getK();
			double temp = 0;
			
			
			for (int i = 0 ; i < k ; i++)
			{
				temp = mathTools.defaultMetric(v1, cInfo.getCentroid(i));
				
				
				if (temp < minDist)
				{
					minDist = temp;
					clust = (byte)i;
				}
			}
			
			Object[] res = {clust , minDist};
			return res;
		}
}
