package captura;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class UpdateUserState {
	//Here we have to get data from any file. or we can do like this, because token values will not change.
    static AccessToken loadAccessToken(){
           String token = "2862224927-e4wN3cPilrNHfZKG8IVkPiE0EY95M6ZqVX9ZRZN";
           String tokenSecret = "S820NF5zTbQfI7ugIGdZckgfehRHSDrpQytagqO8gST5l";
           return new AccessToken(token, tokenSecret);
         }
    
    
     public static void main(String args[]) throws Exception{
           
          
           TwitterFactory factory = new TwitterFactory();
           AccessToken accessToken = loadAccessToken();
           Twitter twitter = factory.getInstance();
           twitter.setOAuthConsumer("SlXeKokxP1MHaHgHol2i6rdA2", "v0Owb4r4AdwCLBgSG1e5avtv40ALFOY1diC3UYHqAPR6BTJXqB");
           twitter.setOAuthAccessToken(accessToken);
           Status status = twitter.updateStatus("Actualizando mi estado desde Java");
           System.out.println("Successfully updated the status to [" + status.getText() + "].");
           System.exit(0);
         }


	public static AccessToken loadAccessTokenForGraphics() {
		return new AccessToken("2862224927-9avBUGPg3hV0ib2ssGwiL1ozpi60M9ovlh4sSah","YpfBAXggbuOr21cxcZsN9OllmCM5ePUrimH1w6PP7zPmO");
	}

}
