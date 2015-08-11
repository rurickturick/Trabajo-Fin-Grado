package busqueda;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import trendingTopics.ConexionTwitter;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class SearchByHour {
        private String txt;
		private Query query;
		private int limiteTweets;
		private String since;
		private String until;
		private Twitter twitter;
		private Date dia;
		
		public SearchByHour(String txt,int limiteTweets, String since, String until, Date dia){
			this.since = since;
			this.until = until;
			this.dia = dia;
			this.txt=txt;
			this.query=new Query(txt  +" lang:es exclude:retweets");
			if(limiteTweets>100)
				this.query.setCount(100);	
			else 
				this.query.setCount(limiteTweets);
			this.limiteTweets=limiteTweets;
		}
		
		
		@SuppressWarnings("deprecation")
		public void busqueda(){
			
			
			
			
			//List<PopularTweet> mytweets = new ArrayList<PopularTweet>();
			int contador = 0;
			this.twitter = ConexionTwitter.conexionApiRestForGraphic();
			int searchResultCount;
			long lowestTweetId = Long.MAX_VALUE;
			int limite = 0;
			int hora = 23;
			int numConsultas = 0;
			FileWriter fichero = null;
	        PrintWriter pw = null;
	        String datos="";
			try {
				
		        fichero = new FileWriter("datosGrafico"+txt+".txt",true);
		        pw = new PrintWriter(fichero);
				
				
				
				
			    do {
			    	QueryResult queryResult;
			    	
					//Para que busque en un determinado dia.
					query.setUntil(until);
					query.setSince(since);
					
						queryResult = twitter.search(query);
						numConsultas++;
						if(numConsultas == 160){
							numConsultas = 0;
							Thread.sleep(850000);
						}
							
							
				
					searchResultCount = queryResult.getTweets().size();

					 for (Status status : queryResult.getTweets()) {
						 Date fecha_actual =status.getCreatedAt();
						 
						 if(fecha_actual.getDate() == this.dia.getDate()){
							 if(fecha_actual.getHours() == hora){
								 contador++;
							 }
							 else if(fecha_actual.getHours() < hora){
								 System.out.println("Se han escrito " + contador + " tweets a la hora" + hora );
								 datos=hora + "-" + contador+"\n" +datos;
								
								 contador = 1;
								 hora--;
							 }
						 }
						 	        
						    if (status.getId() < lowestTweetId) {
						          lowestTweetId = status.getId();
						          query.setMaxId(lowestTweetId);
						    }		            
						   limite++;
					}
					
					 pw.print(datos);
			       
			       
			    }
			    while (searchResultCount != 0 && searchResultCount % 100 == 0 && limite < this.limiteTweets);   
			   
			   
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
		        try {
		            // Nuevamente aprovechamos el finally para 
		            // asegurarnos que se cierra el fichero.
		            if (null != fichero)
		               pw.close();
		            } catch (Exception e2) {
		               e2.printStackTrace();
		            }
		         }
			
			
		
		
		}


	
	
	
	public static void main(String[] args) {
		
		Date dia = new Date();
		dia.setDate(2);
		
		
		SearchByHour searching = new SearchByHour("#JuevesSanto", 70000,"2015-04-02", "2015-04-03", dia);
		searching.busqueda();
	}

}
