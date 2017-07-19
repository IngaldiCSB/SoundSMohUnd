package ENTITY;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import TOOLS.mathTools;

import DATABASE.clusteringGateway;


public class clusteringInfo
{
	private float [][] centroids; //float[k][dim]
	
	private int k;
	private int dim;
	
	public clusteringInfo ()
	{
		//TODO leggere da file
		this.k = 16;
		this.dim = 12;
		this.centroids = new float[k][dim];
		

	}
	
	public void setCentroid (int i , float [] val)
	{
		centroids[i] = val;
		
	}
	
	public float [] getCentroid (int i)
	{
		return centroids [i];
	}
	
	public int getK()
	{
		return k;
	}
	
	//TODO STUB
	public boolean load ()
	{
		float[][]temp =
		{
				{
					11.315682f , 
					7.2230763f , 
					6.2953815f , 
					6.2699103f , 
					4.947489f , 
					6.70275f , 
					9.919909f , 
					9.820971f , 
					7.5606136f , 
					9.503241f , 
					6.3151026f , 
					19.847002f
				},
				{
					1.7498046f , 
					4.82068f , 
					5.9654737f , 
					1.3410779f , 
					2.0972292f , 
					4.714407f , 
					5.832865f , 
					2.8466468f , 
					3.582339f , 
					2.1882865f , 
					2.1119914f , 
					1.5121168f 
				},
				{
					0.6977123f , 
					0.9167847f , 
					0.0f , 
					0.6471909f , 
					1.387025f , 
					1.6471542f , 
					1.939458f , 
					1.1312279f , 
					0.0f , 
					4.0391088f , 
					1.4868232f , 
					0.07383641f 
				},
				{
					3.6643736f , 
					0.76595455f , 
					0.42446446f , 
					0.7494506f , 
					1.0169818f , 
					0.9277486f , 
					0.0f , 
					3.6340609f , 
					1.3763545f , 
					2.685804f , 
					3.3474758f , 
					5.730044f 
				},
				{
					0.0f , 
					0.0f , 
					0.026038185f , 
					0.0f , 
					0.08581205f , 
					0.0f , 
					0.0f , 
					0.2019424f , 
					0.21517119f , 
					1.166049f , 
					0.3810945f , 
					0.0f
				},
				{					
					5.1238217f , 
					1.6178213f , 
					3.0748594f , 
					4.740808f , 
					4.6008697f , 
					2.7664957f , 
					3.1229014f , 
					4.612692f , 
					1.8925f , 
					0.99220574f , 
					0.95128834f , 
					5.5472865f 
				},
				{
					3.7605479f , 
					3.6680098f , 
					2.4337351f , 
					3.0788298f , 
					5.0251436f , 
					8.254962f , 
					5.692381f , 
					4.9816833f , 
					1.8268578f , 
					3.8185644f , 
					7.206366f , 
					9.047475f 
				},
				{
					0.5580074f , 
					0.0f , 
					0.0f , 
					0.0f , 
					0.0f , 
					2.1413743f , 
					17.877678f , 
					0.3923486f , 
					0.0f , 
					0.0f , 
					0.75873125f , 
					7.838735f 
				},
				{
					4.9954944f , 
					2.524295f , 
					4.3934746f , 
					0.9292955f , 
					0.0f , 
					0.35990486f , 
					8.805307f , 
					1.3171049f , 
					1.6855346f , 
					0.6767016f , 
					0.3349484f , 
					11.173172f 
				},
				{
					1.6736641f , 
					0.6520274f , 
					10.489166f , 
					5.9957137f , 
					2.9177423f , 
					5.2376966f , 
					5.710087f , 
					5.401481f , 
					5.0552063f , 
					5.8071775f , 
					3.049677f , 
					2.424466f 
				},
				{
					5.5350733f , 
					4.401218f, 
					4.224165f , 
					2.49509f , 
					5.633466f , 
					6.8418355f , 
					8.028506f , 
					6.9655194f , 
					3.047121f , 
					2.7874548f , 
					3.8009102f , 
					4.1782284f
				},
				{
					5.2920947f , 
					1.863968f , 
					1.2709898f , 
					0.6211976f , 
					0.05354666f , 
					0.45679608f , 
					1.5590432f , 
					0.59282917f , 
					0.9596782f , 
					3.8650897f , 
					4.3080354f , 
					3.881059f 
				},
				{
					1.271985f , 
					4.278352f , 
					9.579844f , 
					4.142421f , 
					3.9940972f , 
					3.7878833f , 
					5.6769466f , 
					2.7133398f , 
					1.6925703f , 
					2.2327774f , 
					2.3738074f , 
					2.052448f 
				},
				{
					1.4682823f , 
					0.8914062f , 
					0.0f , 
					0.0f , 
					0.63662434f , 
					0.46363193f , 
					0.6346423f , 
					0.03054532f , 
					0.47858873f , 
					0.6118105f , 
					0.36704248f , 
					0.8188304f
				},
				{
					10.59269f , 
					4.667237f , 
					3.9935622f , 
					3.862065f , 
					3.4102664f , 
					3.2235153f , 
					6.6445427f , 
					7.1615567f , 
					3.108614f , 
					5.436955f , 
					3.4381163f , 
					7.678816f
				},
				{
					0.9597226f , 
					15.118167f , 
					1.1774223f , 
					0.29126358f , 
					0.57039744f , 
					0.50634557f , 
					0.44589806f , 
					0.014607877f , 
					2.2842607f , 
					0.383653f , 
					0.3341199f , 
					0.69670105f
				}
		};
		
		for (int i = 0 ; i < k ; i++)
		{
			centroids[i] = mathTools.normalize(temp[i] , 100);
		}
		
		return true;
	}

	//TODO STUB
	public boolean save ()
	{
		return true;
	}
	

}
