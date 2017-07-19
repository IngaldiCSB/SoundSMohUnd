package TOOLS.ImgProc;

import TOOLS.mathTools;

public class DoGFilter
{
	private float sigma1;
	private float sigma2;
	
	private int X;
	private int Y;
	
	private int kerSize;
	private int halfKerSize;
	
	private SpatialFilter filter1;
	private SpatialFilter filter2;
	
	public DoGFilter (int X , int Y , float sigma1 , float sigma2)
	{
		this.X = X;
		this.Y = Y;
		this.kerSize = 7;
		this.halfKerSize = 3;
		
		float [][] ker1 = mathTools.gaussian2D(sigma1, 7);
		float [][] ker2 = mathTools.gaussian2D(sigma2, 7);
		
		filter1 = new SpatialFilter (ker1 , X , Y , 0);
		filter1 = new SpatialFilter (ker2 , X , Y , 0);
	}
	
	public float [][] getFloatFiltered (float [][] inMatrix)
	{
		float [][] outMatrix = new float [X][Y];
		
		for (int y = halfKerSize ; y < Y - halfKerSize ; y++)
		{
			for (int x = halfKerSize ; x < X - halfKerSize ; x++)
			{
				outMatrix[x][y] = Math.max(0 , filter1.conv2D(inMatrix, x, y) - filter2.conv2D(inMatrix, x, y));

			}
		}
		
		return outMatrix;

	}
}
