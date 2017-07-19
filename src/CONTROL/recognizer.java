package CONTROL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import TOOLS.Pair;
import TOOLS.DataClustering.clusterer;

import BOUNDARY.Connect;
import DATABASE.featureGateway;
import ENTITY.songFeature;
import ENTITY.songInfo;

public class recognizer
{
	private final int initTTL = 1;
	
	public void recognize (ArrayList<Float> in, Connect conn)
	{
		System.out.println("guard:entra in recognizer");


		ArrayList <Pair <songFeature , Integer> > candidati = null;
		ArrayList <Pair <songFeature , Integer> > candidatiOut = null; 
		byte[] inputClusters = new byte [in.size()/12];

		float [] subArray = new  float [12];       //12 è la dim del feature vector

		for (int i = 0 ; i < inputClusters.length ; i ++)
		{
			for (int j = 0 ; j < 12 ; j++)
			{
				subArray[j] = in.get(i*12 + j);

				//DEBUG
				//System.out.println(subArray[j]);
			}

			inputClusters[i] = (Byte) clusterer.getInstance().getCluster(subArray)[clusterer.IND_CLUSTER];

			//DEBUG
			//System.out.println(inputClusters[i]);
		}

		//TODO GRANDISSIMO INGUACCHIO

		featureGateway fg = new featureGateway ();

		ArrayList <songFeature> tempCandidati = fg.readAll(inputClusters[0]);
		candidati = new ArrayList <Pair <songFeature , Integer> > ();

		//inizializza lista candidati
		for (int i = 0 ; i < tempCandidati.size() ; i++)
		{
			candidati.add(new Pair <songFeature , Integer> (tempCandidati.get(i) , initTTL) );
		}


		boolean rimossi = true;
		int i = 0;

		songFeature tempFeat = null;
		int tempTTL = 0;
		int tempKey = 0;

		//esci dal ciclo se non ci sono rimozioni o se la lista è finita
		while (rimossi == true && i < inputClusters.length)
		{
			//inizializzazioni
			i++;
			rimossi = false;
			candidatiOut = new ArrayList <Pair <songFeature , Integer> > ();

			//DEBUG
			System.out.println ("");
			for (int j = 0 ; j < candidati.size () ; j++)
			{
				//prendi il j-esimo candidato
				tempFeat = candidati.get(j).getLVal();
				tempTTL = candidati.get(j).getRVal();

				tempKey = tempFeat.getKey();

				//se esiste il timestamp successivo, aggiungi candidato alla lista
				if (tempFeat.exists(tempKey + 1 , inputClusters[i]))
				{
					candidatiOut.add(new Pair <songFeature , Integer> (tempFeat , tempTTL) );
				}
				//se non esiste il timestamp successivo, decrementa il ttl, comunica tentativo rimozione e poi...
				else
				{
					tempTTL--;
					rimossi = true;
					
					//..se il ttl non è negativo, aggiungi lo stesso candidato alla lista
					if (tempTTL > -1)
					{
						candidatiOut.add(new Pair <songFeature , Integer> (tempFeat , tempTTL) );
					}

				}
				
				//DEBUG
				if (tempTTL > -1)System.out.println (tempFeat.getKey() +"-" + tempFeat.getValue() + "-" + tempTTL);


			}



			//sostituisci la nuova lista alla vecchia
			candidati = candidatiOut;


		}

		int ret = 0;

		/*for (int k = 0 ; i < candidati.size() ; i++)
			  System.out.println(candidati.get(k).getKey());*/

		if (candidati.size() == 0)
		{
			System.out.println("nessun risultato trovato");
			return;
		}
		else if (candidati.size() == 1)
		{
			ret = candidati.get(0).getLVal().getSongID();
		}
		else
		{	
			ArrayList <songFeature> parm = new ArrayList <songFeature> ();

			for (int k = 0 ; k < candidati.size() ; k++)
			{
				parm.add(candidati.get(k).getLVal());
			}
			ret = this.ResolveCollision(parm);

		}

		retrieveTopResult (ret , conn);
		return;



	}
	
	private int ResolveCollision (List <songFeature> candidati)
	{
		  HashMap <Integer , Integer> cand = new HashMap <Integer , Integer> ();
		  int tempCount = 0;
		  int tempKey = 0;
		  
		  for (int j = 0 ; j<candidati.size() ; j++)
		  {
			  tempKey = candidati.get(j).getSongID();
			  System.out.println (tempKey);
			  
			  if (cand.containsKey(tempKey))
			  {
				  tempCount = cand.get(tempKey);
				  tempCount++;
			  }
			  else
			  {
				  tempCount = 1;
			  }
			  
			  cand.put(tempKey, tempCount);
		  }
		  
		  int max = 0;
		  int ret = 0;
		  
		  Iterator<Entry<Integer , Integer>> it = cand.entrySet().iterator();
		  Entry<Integer , Integer> temp;
		  
		  while (it.hasNext())
		  {
			  temp = (Entry<Integer , Integer>) it.next();
			  
			  if (temp.getValue() > max)
			  {
				  max = temp.getValue();
				  ret = temp.getKey();
				  
			  }
		  }
		  
		  
		  return ret;
		  
		  //print (i) per test;


		  //Miglioramenti: inserire nella struttura "candidati" un campo TTL per ogni candidato iniziale
		  // (e.s TTL iniziale = 4)
		  // decrementare TTL ogni volta che non viene trovato (songID,timestamp++,c[i])
		  // if (candidati[i].TTL == 0) then (candidati[i].remove
		  //
		  //serve a migliorare la recall


		  //alogritmo di scoring basato sulla relazione
		  //
		  //dati due punti a,b e il centroide C
		  //
		  //	|d(a,C) - d(b,C| <= d(a,b) <= d(a,C) + d(b,C)


	}
	
	private void retrieveTopResult (int songID , Connect conn)
	{
		songInfo retrieve = new songInfo ();
		retrieve.load(songID);
		
		conn.send(retrieve.getTitolo(), retrieve.getAutore(), retrieve.getAnno(), retrieve.getYoutube());
		
	}
}
