package trendingTopics;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Twitter;


public class getWoeid {
	public static void main(String args[]) throws Exception{
		 
		//Realizamos la conexion a la Api REST de Twitter...
        Twitter twitter = ConexionTwitter.conexionApiREST();
        
        ResponseList<Location> locations;
        locations = twitter.getAvailableTrends();
        System.out.println("Showing available trends");
        for (Location location : locations) {
            System.out.println(location.getName() + " (woeid:" + location.getWoeid() + ")");
      }
	}  
}
