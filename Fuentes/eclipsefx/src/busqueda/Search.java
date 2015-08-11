package busqueda;

import java.util.ArrayList;

import trendingTopics.ConexionTwitter;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Search {
	
	private Query query;
	private int limiteTweets;
	private String date;
	
	public Search(String txt,int limiteTweets, String date){
		this.date = date;
		this.query=new Query(txt  +" lang:es exclude:retweets");
		if(limiteTweets>100)
			this.query.setCount(100);	
		else 
			this.query.setCount(limiteTweets);
		this.limiteTweets=limiteTweets;
	}
	
	public ArrayList<String> busqueda(){
		ArrayList<String> busquedas = new ArrayList<String>();
		Twitter twitter = ConexionTwitter.conexionApiREST();
		int searchResultCount;
		long lowestTweetId = Long.MAX_VALUE;
		int limite = 0;
		try {
		    do {
		    	QueryResult queryResult;
		    	
				if(!date.equals(""))
					query.setSince(date);
				
					queryResult = twitter.search(query);
			
				searchResultCount = queryResult.getTweets().size();

				 for (Status status : queryResult.getTweets()) {
					    busquedas.add(status.getText());
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
		
		 return busquedas;
		
	}
	

}
