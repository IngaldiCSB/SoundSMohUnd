package TOOLS.AudioIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;


public class fileWavReader implements WavReader
{

	private AudioInputStream in;
	private AudioFormat format;
	private byte [] audioBytes;
	
	private String path;
	private int downsampling;

	public fileWavReader (String path , int downsampling)
	{
		this.path = path;
		this.downsampling = downsampling;
		in = null;
		format = null;
		audioBytes = null;
		
		this.wavread();
	}
	
	
	//QUESTA DEVE DIVENTARE SOLO METODO DI TEST
	private void wavread()
	{
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try
		{
			File file = new File (path);
			in = AudioSystem.getAudioInputStream(file);
			
			int read;
			byte[] buff = new byte[1024];
			while ((read = in.read(buff)) > 0)
			{
			    out.write(buff, 0, read);
			}
			
			out.flush();
			
			audioBytes = out.toByteArray();

		} 
		catch (FileNotFoundException e)
		{
	
			e.printStackTrace();
		} 
		catch (IOException e)
		{

			e.printStackTrace();
		} 
		catch (UnsupportedAudioFileException e)
		{

			e.printStackTrace();
		}

		format = in.getFormat ();
	}
	
	@Override
	public float [] getStereoArray ()
	{
		ShortBuffer sbuf =  ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
		
		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);
		
		float[] buffer = new float[audioShorts.length];
		for (int i = 0; i < audioShorts.length; i++) 
		{
		    buffer[i] = ( (float)audioShorts[i] )/0x8000;
		}
		
		return buffer;

	}
	
	@Override
	public float [] getMonoFloatArray ()
	{
		ShortBuffer sbuf =  ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();
		
		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);
		
		int dataLen = audioShorts.length/(2*downsampling);
		float[] buffer = new float[dataLen];
		
		int j = 0;
		for (int i = 0 ; i < dataLen; i++) 
		{
			j = i*downsampling*2;
			buffer[i] = (( (float)audioShorts[j] )/0x8000 + ( (float)audioShorts[j+1] )/0x8000)/2;
			
			//debug (non usare mai)
			//System.out.print(j + "\t");
		}
		
		return buffer;
	}
	
	@Override
	public short [] getMonoShortArray ()
	{
		ShortBuffer sbuf =  ByteBuffer.wrap(audioBytes).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();

		short[] audioShorts = new short[sbuf.capacity()];
		sbuf.get(audioShorts);
		
		int len = audioShorts.length;
		
		short [] buffer2 = new short [len];
		
		for (int i = 0 ; i < len ; i++)
		{
			buffer2[i] = audioShorts [i*downsampling];
		}
		
		return audioShorts;

	}
	
	@Override
	public int getDuration ()
	{
		return (int) ((audioBytes.length/4)/format.getFrameRate());
	}

	@Override
	public float getFrameRate ()
	{
		return format.getFrameRate();
	}
	
	@Override
	public AudioFormat getFormat ()
	{
		return format;
	}
}
