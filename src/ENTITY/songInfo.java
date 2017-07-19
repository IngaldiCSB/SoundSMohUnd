package ENTITY;

import DATABASE.songGateway;

public class songInfo
{
	private String autore;
	private String titolo;
	private int  anno;
	private String youtube;
	
	private int ID;

	public songInfo(String autore, String titolo, int anno, String youtube)
	{
		super();
		this.autore = autore;
		this.titolo = titolo;
		this.anno = anno;
		this.youtube = youtube;
		ID = 0;
	}
	
	
	public songInfo()
	{
		
	}


	public boolean save ()
	{
		songGateway g = new songGateway ();
		
		ID = g.create(titolo, autore, anno, youtube);
		
		//debug
		//System.out.print(ID);
		
		return true;
	}
	
	public boolean load (int ID)
	{
		songGateway g = new songGateway ();
		
		this.ID = ID;
		
		Object [] result = g.read(ID);
		
		titolo = (String) result[songGateway.IND_TITOLO];
		autore = (String) result[songGateway.IND_AUTORE];
		anno = (Integer) result[songGateway.IND_ANNO];
		youtube = (String) result[songGateway.IND_YOUTUBE];
		
		//debug
		//System.out.print(titolo);
		
		return true;

	}


	public String getAutore()
	{
		return autore;
	}


	public String getTitolo()
	{
		return titolo;
	}


	public int getAnno()
	{
		return anno;
	}


	public String getYoutube()
	{
		return youtube;
	}


	public int getID()
	{
		return ID;
	}
}
