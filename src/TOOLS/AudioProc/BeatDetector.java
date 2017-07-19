package TOOLS.AudioProc;

import TOOLS.mathTools;
import TOOLS.ImgProc.SpecRender;


//TODO classe stub. implementa algoritmo beat tracking
public class BeatDetector
{
	private float[][] spec;
	private int minBin;
	private int maxBin;
	
	private float sens;
	
	

	public BeatDetector(float[][] spec, int minBin, int maxBin, float sens)
	{
		super();
		this.spec = spec;
		this.minBin = minBin;
		this.maxBin = maxBin;
		this.sens = sens;
	}

	public BeatDetector ()
	{
		
	}

	//TODO fa cacare
	public double[] getMarkers ()
	{
		int interv = maxBin-minBin;
		int len = spec.length;

		float [] Eav = new float [len];
		float temp = 0;
		
		for (int i = 0 ; i < len ; i++ )
		{
			temp = 0;
			
			for (int j = 0 ; j < interv ; j++ )
			{
				temp += spec[i][j];
			}
			
			Eav[i] = temp;

		}
		
		
		float [] R = mathTools.autoCorr(Eav);
		R[0] = 0;

		int beat = mathTools.maxPoint(R);
		
		System.out.println(60/(beat*0.02f));

		return null;
	}

	
	//TODO ancora peggio
	public double[] getMarkers (int mPerBeat)
	{
		float bpm = 120f;
		int Nbeats = 32;
		int Nmis = Nbeats*mPerBeat;
		
		int shift = 220;
		int sampFreq = 11025;
		
		double Tbeat = 60/bpm;
		double Tmis = Tbeat / mPerBeat;
		double Tframe = shift/sampFreq;
		
		double FramesPerMis = Tmis/Tframe;
		
		double [] ret = new double [Nmis];
		for (int i = 1 ; i <Nmis-1 ; i++)
		{
			ret[i] = i*5.92; //disclousure-control
			//System.out.println(ret[i]);
		}
		

		return ret;
	}
	
}
