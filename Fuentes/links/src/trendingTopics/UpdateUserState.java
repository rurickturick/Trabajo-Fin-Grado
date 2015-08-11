package trendingTopics;


import twitter4j.auth.AccessToken;

public class UpdateUserState {
	//Here we have to get data from any file. or we can do like this, because token values will not change.
    static AccessToken loadAccessToken(){
           String token = "2862224927-jxlnODdoBH6zrIiTrA8v7gknkr1zEvhtwE2D5Es";
           String tokenSecret = "2HGK1cjgLUcXFEtUEa5TPGkcf9s4zXlKSsesuY1455tS7";
           return new AccessToken(token, tokenSecret);
         }
    
    
     
}
