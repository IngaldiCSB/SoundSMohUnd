package CONTROL;

import ENTITY.songInfo;
import TOOLS.AudioProc.BeatDetector;
import TOOLS.AudioProc.ChromagramMaker;
import TOOLS.ImgProc.LaplacianFilter;
import TOOLS.ImgProc.SpecRender;
public class analyzer
{


	@SuppressWarnings("unused")
	public boolean analyze (String path , String artista , String titolo , int anno , String youtube)
	{
		songInfo song = new songInfo (artista , titolo , anno , youtube);
		song.save();
		
		//TODO risistemare tutto il codice, questo è solo test!!!		
		
		String Path ="C:\\Users\\gennaro\\SoundSMhoUnd\\samples\\";
		
		
		ChromagramMaker cm;
		float [][] cqtData;
		float [][] filtData;
		LaplacianFilter lap;
	
		SpecRender rend = new SpecRender ();
		
		cm = new ChromagramMaker (path , 11025 , 65.4063f, 7902.1328f, 12 , 512 , 220);
		cqtData = cm.myCQT();

		lap = new LaplacianFilter (cm.getnFrames() , cm.getnBins() , 0.01 );
		filtData = lap.getFloatFiltered(cqtData);
		
		//TODO teeeeeeeeest
		/*
		ChromagramMaker cm2;
		float [][] cqtData2;
		float [][] filtData2;
		LaplacianFilter lap2;
		
		
		cm2 = new ChromagramMaker (Path + "miles.wav" , 11025 , 65.4063f, 7902.1328f, 12 , 512 , 220);
		cqtData2 = cm2.myCQT();

		lap2 = new LaplacianFilter (cm2.getnFrames(), cm2.getnBins() , 0.01 );
		filtData2 = lap2.getFloatFiltered(cqtData2);
		
		//-------------------------------
		
		rend.render(filtData, cm.getnFrames(), cm.getnBins() , "click");
		rend.render(filtData2, cm2.getnFrames(), cm2.getnBins() , "miles davies - blue in green");
		 */
		
		//-------tempo tracking--------
		BeatDetector bd = new BeatDetector ();
		double [] markers = bd.getMarkers(4);
		//-----------------------------
		
		//-------quantizzazione---------
		float[][] featVec = cm.processChromagram(filtData, markers);
		
		
		//float[][] featVec2 = cm2.processChromagram(filtData2, markers);
		//int [][] diff = new int [featVec.length][12];
		
		/*
		for (int i = 0 ; i < featVec.length ; i++)
		{
			for (int j = 0 ; j < 12 ; j++)
			{
				diff [i][j] = (int)Math.abs(featVec[i][j] - featVec2[i][j]);
			}
		}
			
		rend.render(featVec, markers.length , 12 , "croma");
		rend.render(featVec2, markers.length , 12 , "croma3");
		rend.render(diff, markers.length , 12 , "differenza - diverse");
		*/
		


		
		//------------------------------
		
	
		descriptorExtractor de = new descriptorExtractor (song.getID());
		de.descriptorExtract(featVec);
		
		return true;
	}
}
