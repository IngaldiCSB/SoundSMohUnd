package CONTROL;

import TOOLS.mathTools;
import ENTITY.clusteringInfo;
import ENTITY.songFeature;

public class descriptorExtractor
{
	int id;
	
	public descriptorExtractor (int id)
	{
		this.id = id;
	}
	
	public void descriptorExtract (float [][] featureData)
	{
		songFeature feature = null;
		
		for (int i = 0 ; i < featureData.length ; i++)
		{
			feature = new songFeature (id , i  , mathTools.normalize(featureData[i] , 100) );
			feature.save();
			
			//DEBUG
			System.out.print(feature.getKey() + "-" + feature.getValue() + "\n");
		}
	}
}
