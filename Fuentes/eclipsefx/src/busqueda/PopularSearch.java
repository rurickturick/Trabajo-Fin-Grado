package busqueda;

import gephi.GephiMethods;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import trendingTopics.ConexionTwitter;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class PopularSearch {
	private Query query;
	private int limiteTweets;
	private String date;
	private Twitter twitter;
	private String modo;
	
	public PopularSearch(String txt,int limiteTweets, String date, String modo){
		this.date = date;
		this.query=new Query(txt  +" lang:es exclude:retweets");
		if(limiteTweets>100)
			this.query.setCount(100);	
		else 
			this.query.setCount(limiteTweets);
		this.limiteTweets=limiteTweets;
		this.modo = modo;
	}
	
	
	/**
	 * Suma la cantidad de followers que tienen los followers de idUser.   //Imposible de implementar debido a que estamos limitados por la PAi de twitter a 180 consultas.
	 * @param idUser  
	 * @return
	 * @throws TwitterException
	 *//*
	public int getFollowersInfo(long idUser) throws TwitterException{
		int followers = 0;
		long  cursor = -1;
			IDs ids = twitter.getFollowersIDs(idUser,cursor);
			
			for (long id : ids.getIDs()) {
				  followers++;
	              //System.out.println(id);
	              User user = twitter.showUser(id);
	              followers = followers + user.getFollowersCount();
	              //System.out.println(user.getName());
	          }
			
		return followers;
	}
	*/
	public List<PopularTweet> busqueda(){
		List<PopularTweet> mytweets = new ArrayList<PopularTweet>();
		this.twitter = ConexionTwitter.conexionApiREST();
		int searchResultCount;
		long lowestTweetId = Long.MAX_VALUE;
		int limite = 0;
		
		try {
		    do {
		    	QueryResult queryResult;
		    	
				if(!date.equals(""))
					query.setUntil(date);
				
					queryResult = twitter.search(query);
			
				searchResultCount = queryResult.getTweets().size();

				 for (Status status : queryResult.getTweets()) {
					 
					 //Comprobar si es un tweet relevante.
					 if(esPopular(status)){
						  PopularTweet nuevo = new PopularTweet(status,status.getUser().getFollowersCount());
						  nuevo.setModo(modo);
						  mytweets.add(nuevo);
					 }
					 
					
					  
					 
					        //De momento solo los imprimo, el siguiente paso seria Clasificarlos.		        
					    if (status.getId() < lowestTweetId) {
					          lowestTweetId = status.getId();
					          query.setMaxId(lowestTweetId);
					    }		            
					   limite++;
				}
				
		    	
		       
		       
		    }
		    while (searchResultCount != 0 && searchResultCount % 100 == 0 && limite < this.limiteTweets);   // El limite sera 2000 tweets.
		   
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		return mytweets;
	}
	/**
	 * Se considerara candidato a ser tweet popular si al menos tiene 500 followers, es mas de 10 veces favorito y tiene mas de 10 retweets.
	 * @param status
	 * @return
	 */
	private boolean esPopular(Status status) {
		return true;
		//return ((status.getUser().getFollowersCount() > 500 )  && (status.getFavoriteCount() > 5)  && (status.getRetweetCount() > 5));
	}


	public static void main(String[] args) {
		//Limite de tweets a mostrar 
		int limite = 10;
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("tweets.txt",true);
            pw = new PrintWriter(fichero);
		
		//Tratamos de obtener la mayor cantidad posible de tweets. para luego procesarlos, ver si son tweets candidatos a ser populares, y mostrar "limite" tweets con mejores puntuaciones.
		PopularSearch searching = new PopularSearch("#FelizLunes", 5000, "2015-04-13", "ReTweets");
		List<PopularTweet> populares = searching.busqueda();
		Collections.sort(populares);
		
		Iterator<PopularTweet> it = populares.iterator();
		int i = 1;
		//Solo mostraremos los 10 mejores tweets obtenidos.
		while(it.hasNext() && i < limite){
			PopularTweet status = it.next();
			  pw.println(i + "->" + status.getMyStatus().getText() + " number of favorites " +  status.getMyStatus().getFavoriteCount() + " number of retweets: " + status.getMyStatus().getRetweetCount()+  " number of followers: " + status.numFollowers());
			  System.out.println(i + "->" + status.getMyStatus().getText() + " number of favorites " +  status.getMyStatus().getFavoriteCount() + " number of retweets: " + status.getMyStatus().getRetweetCount()+  " number of followers: " + status.numFollowers());		
			  i++;
		}
        
	 } catch (Exception e) {
         e.printStackTrace();
     } finally {
        try {
        // Nuevamente aprovechamos el finally para 
        // asegurarnos que se cierra el fichero.
        if (null != fichero)
           fichero.close();
        } catch (Exception e2) {
           e2.printStackTrace();
        }
     }
	}

	
	
	
	

}
