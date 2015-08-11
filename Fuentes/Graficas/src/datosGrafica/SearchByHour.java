package datosGrafica;


import java.util.Date;




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
		private int numConsultas;
		private Twitter twitter;
		private int dia;
		
		public SearchByHour(String text,int limiteTweets, String since, int dia){
			this.since = since;
			this.dia = dia;
			this.txt=text;
			this.query=new Query(txt  +" lang:es exclude:retweets");
			if(limiteTweets>100)
				this.query.setCount(100);	
			else 
				this.query.setCount(limiteTweets);
			this.limiteTweets=limiteTweets;
		}
		
		public int getNumConsultas(){
			return this.numConsultas;
		}
		
		public void setNumConsultas(int num){
			this.numConsultas = num;
		}
		
		@SuppressWarnings("deprecation")
		public String busqueda(){
			
			
			
			
			//List<PopularTweet> mytweets = new ArrayList<PopularTweet>();
			int contador = 0;
			this.twitter = ConexionTwitter.conexionApiRestForGraphic();
			int searchResultCount;
			long lowestTweetId = Long.MAX_VALUE;
			int limite = 0;
			int hora = 23;
			//int numConsultas = 0;
			
	        String datos="";
			try {
				

			    do {
			    	QueryResult queryResult;
			    	
					//Para que busque en un determinado dia.
				
					query.setSince(since);
					
					
						queryResult = twitter.search(query);
						numConsultas++;
						if(numConsultas == 160){
						//	System.out.println("estoy dormido");
							numConsultas = 0;
							Thread.sleep(850000);
						}
							
							
				
					searchResultCount = queryResult.getTweets().size();

					 for (Status status : queryResult.getTweets()) {
						 Date fecha_actual =status.getCreatedAt();
						 
						 if(fecha_actual.getDate() == this.dia){
							 
							 if(fecha_actual.getHours() == hora){
								 contador++;
							 }
							 else if(fecha_actual.getHours() < hora){
								// System.out.println("Se han escrito " + contador + " tweets a la hora" + hora + fecha_actual.toString() );
								 
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
					
					// pw.print(datos);
			       
			       
			    }
			    while (searchResultCount != 0 && searchResultCount % 100 == 0 && limite < this.limiteTweets);   	   
 
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
		return datos;
		
		}


	
	


}
