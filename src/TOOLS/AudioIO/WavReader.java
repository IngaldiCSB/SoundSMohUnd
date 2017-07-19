package TOOLS.AudioIO;

import javax.sound.sampled.AudioFormat;

public interface WavReader
{
	public float [] getStereoArray ();
	public float [] getMonoFloatArray ();
	public short [] getMonoShortArray ();
	public int getDuration ();
	public float getFrameRate ();
	public AudioFormat getFormat ();






}
