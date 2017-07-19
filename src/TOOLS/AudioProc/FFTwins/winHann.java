package TOOLS.AudioProc.FFTwins;


public class winHann implements FFTwin
{
	private int len;
	
	public winHann (int len)
	{
		this.len = len;
	}

	@Override
	public float[] floatMake()
	{
		 float [] win = new float [len];
			
		 for (int i = 0; i<len; i++)
		 {
			 win[i] = (float) ( 0.5*(1 - Math.cos( (2*Math.PI*i)/(len-1)) ) );
		 }
		 return win;	
		 
	}

	@Override
	public short[] shortMake()
	{
		 short [] win = new short [len];
			
		 for (int i = 0; i<len; i++)
		 {
			 win[i] = (short) ( 0.5*(1 - Math.cos( (2*Math.PI*i)/(len-1)) )*65536 );
		 }
		 return win;	
	}

	
}
