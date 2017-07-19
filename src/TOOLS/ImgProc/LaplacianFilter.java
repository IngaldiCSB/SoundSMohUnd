package TOOLS.ImgProc;

public class LaplacianFilter
{
	private final float [][] kernel =
	{
			{ -1 , -1  , -1},
			{ -1 , 8   , -1},
			{ -1 , -1  , -1}
	};
	
	private SpatialFilter filter;
	
	public LaplacianFilter (int X , int Y , double d)
	{
		filter = new SpatialFilter (kernel , X , Y , d);
	}
	
	public float [][] getFloatFiltered (float [][] inMatrix)
	{
		return filter.getFloatFiltered(inMatrix);
	}
	
	public boolean [][] getBooleanFiltered (float [][] inMatrix)
	{
		return filter.getBooleanFiltered(inMatrix);
	}

}
