package busqueda;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import trendingTopics.ConexionTwitter;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
//import twitter4j.URLEntity;

public class SearchPopular {
	private Query query;
	private int limiteTweets;
	private String date;
	
	public SearchPopular(String txt,int limiteTweets, String date){
		this.date = date;
		
		this.query=new Query(txt  +" lang:es exclude:retweets");
		this.query.resultType(ResultType.popular);
		if(limiteTweets>100)
			this.query.setCount(100);		
		else 
			this.query.setCount(limiteTweets);
		this.limiteTweets=limiteTweets;
	}
	
	public List<Status> busqueda(){
		
	
		List<Status> busquedas = new ArrayList<Status>();
		Twitter twitter = ConexionTwitter.conexionApiREST();
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
					    busquedas.add(status);
					    //URLEntity[] urlEntity=status.getURLEntities();
					    //if(urlEntity.length > 0)
					    //System.out.println(urlEntity[0].getURL());
					    
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
	
/*	public static void main(String args[]){
		SearchPopular populares = new SearchPopular("#JuevesSanto",10,"2015-04-04");
		Iterator<String> it = populares.busqueda().iterator();
		int i = 1;
		while(it.hasNext()){
			System.out.println(i + " -> " + it.next().toString());
			i++;
		}

	}
	*/
}
