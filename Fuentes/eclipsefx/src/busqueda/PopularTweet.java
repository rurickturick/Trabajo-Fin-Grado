package busqueda;

import twitter4j.Status;

public class PopularTweet implements Comparable<PopularTweet>{
	private Status miTweet;
	private int followers;
	private String modo;
	
	public PopularTweet(Status s, int numFollowers){
		this.miTweet = s;
		this.followers = numFollowers;
	}
	
	public void setModo(String modo){
		this.modo = modo;
	}
	
	public int getFavoriteCount(){
		return this.miTweet.getFavoriteCount();
	}
	
	public int getRetweetCount(){
		return this.miTweet.getRetweetCount();
	}

	public Status getMyStatus(){
		return this.miTweet;
	}
	
	public int numFollowers(){
		return this.followers;
	}

	@Override
	public int compareTo(PopularTweet arg0) {
		int a = 0;
		int b = 0;
		
		
		if(modo.equals("Followers")){
			a= this.numFollowers();
			b= arg0.numFollowers();
		}
		else if(modo.equals("Favoritos")){
			/* Para evitar q algun tweet que no sea retweeteado o no haya sido favorito sea Popular solo por tener 1 millon de followers. */
			a= this.miTweet.getFavoriteCount();
			b= arg0.getFavoriteCount();
		
			
		}
		else if(modo.equals("ReTweets")){
			a= this.miTweet.getRetweetCount();
			b= arg0.getRetweetCount();
		}
		
		if(a > b)
			return -1;
		else if (a < b)
			return 1;
		else
			return 0;
		
		
	}


	

}
