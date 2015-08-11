package trendingTopics;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class ConexionTwitter {
	
	public ConexionTwitter()
	{
		
	}
	
	/**
	 * 
	 * Realizamos la conexion a la Api REST de Twitter...
	 * 
	 */
	public static Twitter conexionApiREST(){
		
		TwitterFactory factory = new TwitterFactory();
        AccessToken accessToken = UpdateUserState.loadAccessToken();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer("NIl2pMoqhBzgE9tyYs5nmYDhL", "N8Br9C0wfID47dpZCRjT8Myti5oQhOlzQGDQBvQfOtLim2C3CE");
        twitter.setOAuthAccessToken(accessToken);
        
        return twitter;
	}

}
