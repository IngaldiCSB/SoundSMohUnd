package TOOLS.AudioProc;


import com.badlogic.gdx.audio.analysis.FFT;
import TOOLS.AudioProc.FFTwins.*;

public class SpectrogramMaker
{	
	private float[][] spec;
	private float [] win;
	private int nFrames;
	
	private int winLength;
	private int shift;
	private int nBins;
	
	private float max;
	private float min;

	
	
	public SpectrogramMaker (int winLength , int shift , int nsegs , FFTwin myWin)
	{
		nFrames = 0;
		max = 0;
		min = 10000;
		
		this.winLength = winLength;
		this.shift = shift;
		this.nBins = nsegs;
		this.win = myWin.floatMake();
		

	}

	
	
	public float [][] mySTFT (float[] data, int sampFreq)
	{

		float [] buf = new float [winLength];

		int cursor = 0;
			
		nFrames = 0;
		
		while ((cursor + winLength) < data.length)
		{
			nFrames++;
			
			
			//VADO AVANTI
			cursor += shift;

		}
		
		spec = new float[nFrames][nBins];
		
		cursor = 0;
		
		for (int i = 0 ; i < nFrames ; i++)
		{
			//CREO LA FINESTRA (NO PADDING)
			for (int j = 0 ; j < winLength ; j++)
			{
				buf [j] = data [j+cursor] * win [j];
				//DEBUG
				//System.out.println (buf[i] + " ");
			}
			
			
			//VADO AVANTI
			cursor += shift;

					
			//AGGIUNGO COLONNA ALLA MATRICE
			spec [i] = DFT (buf , sampFreq);
		
		}
				
	
		return spec;

	}
	
	public float [] DFT (float [] buf , int sampFreq)
	{
		int quantF = winLength/nBins;
		
		float [] Rebuf = null;
		float [] Imbuf = null;
		
		float [] Magbuf = new float [nBins] ;
		FFT fft = new FFT(winLength, sampFreq);


		//ESEGUO LA FFT

		fft.forward(buf);
		Imbuf = fft.getImaginaryPart();
		Rebuf = fft.getRealPart();
		
		//CALCOLO IL MODULO
		if (quantF == 1)
		{
			for (int j = 0 ; j < nBins ; j++)
			{
				Magbuf[j] = (float) Math.sqrt(Rebuf[j]*Rebuf[j] + Imbuf[j]*Imbuf[j]);
				
				if (Magbuf[j] > max) max = Magbuf[j];
				else if (Magbuf[j] < min && Magbuf[j] != 0) min = Magbuf[j];
					
				//DEBUG
				//System.out.println (Rebuf[j*decimaz] + " + j" + Imbuf[j*decimaz] + " ");
			}
			
		}
		else
		{
			for (int j = 0 ; j < nBins ; j++)
			{
				//quantizzazione in frequenza => potenza
				Magbuf[j] = 0;
				
				for (int k = j*quantF ; k < (j+1)*quantF ; k++)
				{
					Magbuf[j] += (float) Rebuf[k]*Rebuf[k] + Imbuf[k]*Imbuf[k];
					
				}
				
				Magbuf[j] /= quantF;
				
				if (Magbuf[j] > max) max = Magbuf[j];
				else if (Magbuf[j] < min && Magbuf[j] != 0) min = Magbuf[j];

												
				//DEBUG
				//System.out.println (Rebuf[j*decimaz] + " + j" + Imbuf[j*decimaz] + " ");
			}
			
		}
		
		//DEBUG
		//System.out.println ("\n");
		
		return Magbuf;

	}
	
	public int getNFrames ()
	{
		return nFrames;
	}



	public float getMax()
	{
		return max;
	}

	public int getNBins()
	{
		return nBins;
	}


}