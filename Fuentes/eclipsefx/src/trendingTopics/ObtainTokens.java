package trendingTopics;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
 
/**
 *
 * @author Oscar
 *
 *consumer key (API key)     SlXeKokxP1MHaHgHol2i6rdA2
 *consumer secret(API secret)     v0Owb4r4AdwCLBgSG1e5avtv40ALFOY1diC3UYHqAPR6BTJXqB
 *token             2862224927-e4wN3cPilrNHfZKG8IVkPiE0EY95M6ZqVX9ZRZN
 *tokenSecret     S820NF5zTbQfI7ugIGdZckgfehRHSDrpQytagqO8gST5l
 */
 
public class ObtainTokens {
     
     
    //Para guardar los tokens obtenidos. pero en este caso solo los imprimo
     private static  void storeAccessToken(double l, AccessToken accessToken){
         String token = accessToken.getToken();
         String tokenSecret = accessToken.getTokenSecret();
          
         System.out.println("el token es " + token);
         System.out.println("El token secret es " + tokenSecret);
 
          }
      
     public static void main(String args[]) throws Exception{
             
            Twitter twitter = TwitterFactory.getSingleton();
             
            twitter.setOAuthConsumer("SlXeKokxP1MHaHgHol2i6rdA2", "v0Owb4r4AdwCLBgSG1e5avtv40ALFOY1diC3UYHqAPR6BTJXqB");
            RequestToken requestToken = twitter.getOAuthRequestToken();
            AccessToken accessToken = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
              System.out.println("Open the following URL and grant access to your account:");
              System.out.println(requestToken.getAuthorizationURL());
              System.out.print("Enter the PIN(if available) or just hit enter.[PIN]:");
              String pin = br.readLine();
              try{
                 if(pin.length() > 0){
                   accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                 }else{
                   accessToken = twitter.getOAuthAccessToken();
                 }
              } catch (TwitterException te) {
                if(401 == te.getStatusCode()){
                  System.out.println("Unable to get the access token.");
                }else{
                  te.printStackTrace();
                }
              }
            }
            //Guardamos los token para usarlos luego.
            storeAccessToken(twitter.verifyCredentials().getId() , accessToken);
            System.exit(0);
          }
 
}