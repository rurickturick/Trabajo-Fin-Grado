package obtenerInformacion;

import java.util.ArrayList;



import trendingTopics.ConexionTwitter;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
/**
 * Clase que realiza busquedas y obtiene datos de Twitter
 * @author esteban, john, oscar
 *
 */
public class SearchWithLinks {
	
	private Query query; //la busqueda a realizar
	private int limiteTweets; //el limite de tweets que buscaremos
	private String date;
	/**
	 * Constructor de la clase Search 
	 * @param txt texto a buscar, normalmente un hashtag
	 * @param limiteTweets limite de tweets como maximo que nos devolvera la búsqueda
	 */
	public SearchWithLinks(String txt,int limiteTweets,String date){
		//inicializamos la fecha si es que existe
		this.date = date;

		//Filtramos aquellos tweets que tengan links, en español y que no sean retweets
		this.query=new Query(txt  +" filter:links lang:es exclude:retweets");
		//Por defecto twitter solo nos permite realizar una busqueda de 100 tweets
		if(limiteTweets>100)
			this.query.setCount(100);		
		else 
			this.query.setCount(limiteTweets);
		this.limiteTweets=limiteTweets;
		
		if (!this.date.equals("")){
			this.query.setSince(date);
			
		}
	}
	/**
	 * Metodo que realiza la busqueda en Twitter
	 * @return un ArrayList de resultados
	 */
	public ArrayList<String> busqueda(){
		ArrayList<String> busquedas = new ArrayList<String>();
		Twitter twitter = ConexionTwitter.conexionApiREST();
		int searchResultCount;
		long lowestTweetId = Long.MAX_VALUE;
		int limite = 0;
		try {
		    do {
		    	QueryResult queryResult;
				
				queryResult = twitter.search(query);
				searchResultCount = queryResult.getTweets().size();

				 for (Status status : queryResult.getTweets()) {
					    busquedas.add(status.getText());
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
		
		 return busquedas;
		
	}
	

}
