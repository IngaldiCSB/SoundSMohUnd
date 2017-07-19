package TOOLS.AudioProc;

import java.io.IOException;

import com.tagtraum.jipes.audio.ConstantQTransform;
import com.tagtraum.jipes.audio.LogFrequencySpectrum;
import com.tagtraum.jipes.audio.RealAudioBuffer;

import TOOLS.AudioIO.WavReader;
import TOOLS.AudioIO.fileWavReader;

public class ChromagramMaker
{
	WavReader WR;
	
	private float fmin;
	private float fmax;
	
	private int binPerOct;
	private int nBins;
	
	private int winLength;
	private int shift;
	private int nFrames;
	
	public ChromagramMaker (String path , int sampFreq , float fmin , float fmax , int binPerOct , int winLength , int shift)
	{
		this.WR = new fileWavReader (path , 44100/sampFreq);
		this.fmax = fmax;
		this.fmin = fmin;
		this.binPerOct = binPerOct;
		this.winLength = winLength;
		this.shift = shift;
		
		nBins = (int) Math.ceil(binPerOct * Math.log(fmax / fmin)/ Math.log(2));
		
	}
	
	public float [][] myCQT ()
	{
		ConstantQTransform cqt = new ConstantQTransform (fmin , fmax , binPerOct);
		float[] data = WR.getMonoFloatArray();
		
		float [] buf = new float [winLength];
		RealAudioBuffer AudioBuf;
		LogFrequencySpectrum cqtOut;
		
		int cursor = 0;
			
		nFrames = 0;
		
		nBins = (int) Math.ceil(binPerOct * Math.log(fmax / fmin)/ Math.log(2));

		
		while ((cursor + winLength) < data.length)
		{
			nFrames++;
			
			
			//VADO AVANTI
			cursor += shift;

		}
		
		float [][] out = new float[nFrames][nBins];
		
		cursor = 0;
		
		for (int i = 0 ; i < nFrames ; i++)
		{
			//CREO LA FINESTRA (NO PADDING)
			for (int j = 0 ; j < winLength ; j++)
			{
				buf [j] = data [j+cursor];
				//DEBUG
				//System.out.println (buf[i] + " ");
			}
			
			AudioBuf = new RealAudioBuffer (cursor , buf , WR.getFormat());
			
			
			
			//VADO AVANTI
			cursor += shift;

					
			//AGGIUNGO COLONNA ALLA MATRICE
			try
			{
				cqt.process(AudioBuf);
				cqtOut = cqt.getOutput();
				out[i] = cqtOut.getMagnitudes();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
				
	
		return out;
	}

	public float [][] processChromagram (float [][] in , double [] markers)
	{
		float [][] ret = new float[markers.length][12];
		float temp = 0;
		int octaves = getnBins()/12;
		
			for (int i = 1 ; i < markers.length ; i++ )
			{
				for (int j = 0 ; j < 12 ; j++)
				{
					for (int k = (int)Math.ceil(markers[i-1]) ; k < (int)Math.floor(markers[i]) ; k++)
					{
						for (int m = 0 ; m < octaves ; m++)
						{						
							temp += in[k][(12*m)+j];
						}
					}
					ret[i][j] = temp*50;
					temp = 0;
					//DEBUG
					//System.out.print(ret[i][j] + "\t");

				}
				//System.out.println("\n");
			}

		return ret;
	}
	public int getnBins()
	{
		return nBins;
	}

	public int getnFrames()
	{
		return nFrames;
	}
	
	
}
