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
        twitter.setOAuthConsumer("SlXeKokxP1MHaHgHol2i6rdA2", "v0Owb4r4AdwCLBgSG1e5avtv40ALFOY1diC3UYHqAPR6BTJXqB");
        twitter.setOAuthAccessToken(accessToken);
        
        return twitter;
	}
	
	
public static Twitter conexionApiRestForGraphic(){
		
		TwitterFactory factory = new TwitterFactory();
        AccessToken accessToken = UpdateUserState.loadAccessTokenForGraphics();
        Twitter twitter = factory.getInstance();
        twitter.setOAuthConsumer("gJDFHfBUM6vf7EMzKqSf1cgSi", "eoNKzz0TdOzvtI1yUCCGVnObIsQsfJFYuQhVZg3lIlw8ZMUNB3");
        twitter.setOAuthAccessToken(accessToken);
        
        return twitter;
	}

}
