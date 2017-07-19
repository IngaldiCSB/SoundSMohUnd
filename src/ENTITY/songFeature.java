package ENTITY;

import DATABASE.featureGateway;
import TOOLS.DataClustering.clusterer;


public class songFeature
{
	private int key;
	private byte cluster;
	private double distance;
	

	private float [] components;


	public songFeature(int songID, int timestamp, float[] components)
	{
		Object [] temp = clusterer.getInstance().getCluster(components);
		
		this.key = calcKey(songID , timestamp);
		this.components = components;
		this.cluster = (Byte) temp[clusterer.IND_CLUSTER];
		this.distance = (Double) temp[clusterer.IND_DIST];
	}
	
	public songFeature(int key , byte cluster)
	{
		
		this.key = key;
		this.components = null;
		this.cluster = cluster;
		this.distance = Double.MAX_VALUE;
	}
	
	public int getKey ()
	{
		return key;
	}
	
	
	public int getSongID ()
	{
		int temp = key;
		int mask = 0xffff;

		temp = key >> 16;
		return temp & mask;
	}
	
	public void setValue (byte b)
	{
		cluster = b;
	}
	
	public byte getValue ()
	{
		return cluster;
	}

	public double getDistance()
	{
		return distance;
	}


	public void setDistance(float distance)
	{
		this.distance = distance;
	}
	
	public boolean save ()
	{

		featureGateway fg = new featureGateway ();
		return fg.create(key, cluster);
	}
	
	
	public boolean load (int key)
	{
		featureGateway fg = new featureGateway ();
		this.key = key;
		cluster = fg.read(key);
		
		return true;
	}
	
	//true se esiste un descrittore successivo con cluster dato
	public boolean exists ( int key , byte val)
	{
		featureGateway fg = new featureGateway ();
		boolean found = fg.exists(key, val);
		
		//cerca nel database la coppia [songID:timestamp++]:cluster
		this.key = key;
		this.cluster = val;

		return found;
	}
	
	private int calcKey (int songID, int timestamp)
	{
		int ret = 0;
		
		ret = songID << 16;
		
		ret += timestamp;
		
		return ret;
		
	}

}
