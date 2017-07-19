package TOOLS.AudioProc.FFTwins;


public class winHamming implements FFTwin
{

	private int len;
	
	public winHamming (int len)
	{
		this.len = len;
	}
	
	@Override
	public float[] floatMake()
	{
		 float [] win = new float [len];
			
		 for (int i = 0; i<len; i++)
		 {
			 win[i] = (float) (0.54-0.46*Math.cos((2*Math.PI*i)/(len-1)));
		 }
		 return win;
}

	@Override
	public short[] shortMake()
	{
		 short [] win = new short [len];
			
		 for (int i = 0; i<len; i++)
		 {
			 win[i] = (short)(0.54-0.46*Math.cos( (2*Math.PI*i)/(len-1) ) * 65536);
		 }
		 return win;
	}

}
