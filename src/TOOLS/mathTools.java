package TOOLS;

public class mathTools
{
	public static float max (float [] v)
	{
		float max = Float.MIN_VALUE;
		
		for (int i = 0 ; i < v.length ; i++)
		{
			if (v[i] > max)
				max = v[i];
		}
		
		return max;

	}
	

	
	public static int maxPoint (float [] v)
	{
		float max = Float.MIN_VALUE;
		int maxP = 0;
		
		for (int i = 0 ; i < v.length ; i++)
		{
			if (v[i] > max)
			{
				max = v[i];
				maxP = i;				
			}

		}
		
		return maxP;

	}
	
	public static float min (float [] v)
	{
		float min = Float.MAX_VALUE;
		
		for (int i = 0 ; i < v.length ; i++)
		{
			if (v[i] < min)
				min = v[i];
		}
		
		return min;

	}

	
	public static float distanceL1 (float [] v1 , float [] v2)
	{
		float temp = 0;
		
		if (v1.length == v2.length)
		{
			for (int i = 0 ; i < v1.length ; i++)
			{
				temp += Math.abs((v1[i] - v2[i]));
				
			}
			
			return temp;
		}
		else
		{
			return -1;
		}
	}
	
	public static float distanceL2 (float [] v1 , float [] v2)
	{
		float temp = 0;
		
		if (v1.length == v2.length)
		{
			for (int i = 0 ; i < v1.length ; i++)
			{
				temp += (v1[i] - v2[i])*(v1[i] - v2[i]);
				
			}
			
			return (float)Math.sqrt(temp);
		}
		else
		{
			return -1;
		}
	}
	
	public static float distanceLinf (float [] v1 , float[] v2)
	{
		float temp = Integer.MAX_VALUE;
		
		if (v1.length == v2.length)
		{
			for (int i = 0 ; i < v1.length ; i++)
			{
				if (v1[i] - v2[i] < temp)
				{
					temp = Math.abs((v1[i] - v2[i]));
				}
				
			}
			
			return temp;
		}
		else return -1;
		
	}
	
	public static float distanceCos (float [] v1 , float[] v2)
	{
		float temp1 = 0;
		float temp2 = 0;
		float temp = 0;
		
		if (v1.length == v2.length)
		{
			for (int i = 0 ; i < v1.length ; i++)
			{
				temp1 += v1[i]*v1[i];
				temp2 += v2[i]*v2[i];
				
				temp += v1[i]*v2[i];
				
			}
			
			temp1 = (float) Math.sqrt(temp1);
			temp2 = (float) Math.sqrt(temp2);
			
			return (float) Math.cos(temp);
		}
		else return -1;

	}
	
	public static float distanceStatCorr (float [] v1 , float[] v2)
	{
		if (v1.length == v2.length)
		{
			float v1mean = mean (v1);
			float v2mean = mean (v2);
			
			float tempC = 0;
			float tempS = 0;
			
			for (int i = 0 ; i < v1.length; i++)
			{
				tempC += (v1[i] - v1mean)*(v2[i] - v2mean);
				tempS += (v1[i] - v1mean)*(v1[i] - v1mean)*(v2[i] - v2mean)*(v2[i] - v2mean);

			}
			
			return 1 - (float)Math.abs(tempC/Math.sqrt(tempS));
		}
		else return -1;
	}
	
	//TODO modifica qui per cambiare metrica di distanza
	//----------------------------------------------------------
	public static float defaultMetric (float [] v1 , float [] v2)
	{
		return mathTools.distanceL2 (v1 , v2);
	}
	//-----------------------------------------------------------

	public static float module (float a , float b)
	{
		return (float) Math.sqrt(a*a + b*b);
	}

	public static float mean (float[] v)
	{
		float temp = 0;
		int N = v.length;
		
		for (int i = 0 ; i < N ; i++)
		{
			temp += v[i];
		}
		
		return temp / N;
	}
	
	public static float [][] gaussian2D (float sigma , int dim)
	{
		float [][] ret = new float [dim][dim];
		
		float coeff = (float) (1 / 2*Math.PI*sigma);
		
		for (int y = 0 ; y < dim ; y++)
		{
			for (int x = 0 ; x < dim ; x++)
			{
				ret[x][y] = (float) (coeff*Math.exp( -(x*x+y*y)/(sigma*2)));
				//System.out.println (ret[x][y]);
			}
		}
		
		
		return ret;
	}
	
	public static float [] normalize (float [] v , float range)
	{
		float[] ret = new float [v.length];
		float max = max(v);
		
		for (int i = 0 ; i < v.length ; i++)
		{
			ret[i] = (v[i] / max)*range; 
		}
		
		return ret;
	}
	
	public static float [] autoCorr (float[] v)
	{
		int size = v.length;
		
		float temp = 0;
		
		float [] ret = new float [size];
		
		for (int i = 0 ; i < size ; i++)
		{
			temp = 0;
			for (int j = 0 ; j < size - i ; j++)
			{
				temp += v[i]*v[i+j];
			}
			
			ret[i] = temp;

		}

		return ret;
	}
}
