package TOOLS.ImgProc;



public class SpatialFilter
{
	private float [][] kernel;
	private int kerSize;
	private int halfKerSize;
	private int X;
	private int Y;
	private float matrixMean;
	private double param;



	public SpatialFilter (float [][] kernel , int X , int Y , double d)
	{
		this.kernel = kernel;
		this.kerSize = kernel.length;
		this.halfKerSize = (kerSize - 1) / 2;
		this.X = X;
		this.Y = Y;
		this.param = d;
		this.matrixMean = 0;
		
						
	}
	
	public float  [][] getFloatFiltered(float [][] inMatrix)
	{
		float temp = 0;
		
		float [][] ret = new float [X][Y];
		
		long tempMean = 0;
		int area = X*Y;
		
				
		for (int y = halfKerSize ; y < Y - halfKerSize ; y++)
		{
			for (int x = halfKerSize ; x < X - halfKerSize ; x++)
			{
				ret[x][y] = pointOperator( conv2D(inMatrix , x , y) );
				
				
			}
		}
		
		SpecRender s = new SpecRender ();
		//s.render(debugMatrix, X, Y);
		
		matrixMean = tempMean/area;
		return ret;

	}
	
	public float conv2D ( float [][] inMatrix , int x , int y)
	{
		float temp = 0;
		
		for (int i = 0 ; i < kerSize ; i++)
		{
			for (int j = 0 ; j < kerSize ; j++)
			{
				temp += kernel[i][j]*inMatrix[x - halfKerSize + i][y - halfKerSize + j];
			}
		}

		return temp;
		
		
	}
	public boolean  [][] getBooleanFiltered(float [][] inMatrix)
	{
		boolean[][] outMatrix = new boolean [X][Y];
		float temp = 0;
		int halfKerSize = (kerSize - 1) / 2;
		
		long tempMean = 0;
		int area = X*Y;
		
				
		for (int y = halfKerSize ; y < Y - halfKerSize ; y++)
		{
			for (int x = halfKerSize ; x < X - halfKerSize ; x++)
			{
				temp = 0;
				
				for (int i = 0 ; i < kerSize ; i++)
				{
					for (int j = 0 ; j < kerSize ; j++)
					{
						temp += kernel[i][j]*inMatrix[x - halfKerSize + i][y - halfKerSize + j];
					}
				}
				
				if ( temp > param)
				{
					outMatrix[x][y] =  true;
				}
				else 
				{
					outMatrix[x][y] = false;
				}
				
				tempMean += inMatrix[x][y];
			}
		}
		
		SpecRender s = new SpecRender ();
		//s.render(debugMatrix, X, Y);
		
		matrixMean = tempMean/area;
		return outMatrix;
		
	}
		
	//qui si cambia la point-to-point transformation
	//power law
	public float pointOperator (float f)
	{
		return (float)Math.max(0, f-param);
	}


	public float getMean ()
	{
		return matrixMean;
	}

}
