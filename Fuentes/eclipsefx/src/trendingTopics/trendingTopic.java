package trendingTopics;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import twitter4j.Trends;
import twitter4j.Twitter;


public class trendingTopic {
	
	 public static void main(String args[]) throws Exception{
		 	//Realizamos la conexion a la Api REST de Twitter...
	        Twitter twitter = ConexionTwitter.conexionApiREST();
	        
	       //Agregamos las ciudades, que nos interesan para buscar los trending topics.
	        Map<String,Integer> ciudades = new HashMap<String,Integer>();
	        ciudades.put("Spain", 23424950);
	        ciudades.put("Barcelona", 753692);
	        ciudades.put("Bilbao", 754542);
	        ciudades.put("Las Palmas", 764814);
	        ciudades.put("Madrid", 766273);
	        ciudades.put("Malaga", 766356);
	        ciudades.put("Murcia", 768026);
	        ciudades.put("Palma", 769293);
	        ciudades.put("Sevilla", 774508);
	        ciudades.put("Valencia", 776688);
	        ciudades.put("Zaragoza", 779063);

	       //Actualizamos la base de datos cada 15 minutos...
	     while(true){
	    	 Iterator<String> it = ciudades.keySet().iterator();
	    	  while(it.hasNext()){
	    		  String key = (String) it.next();
	    		  int idTrendLocation =(int) ciudades.get(key) ; 
	    		  Trends trends = twitter.getPlaceTrends(idTrendLocation);
	    		  mongoDBHandler.insertarTT(trends,trends.getLocation().getName());
	    		 
	    		
	    	  }
	    	  TimeUnit.MINUTES.sleep(15);
	      }
	        
	       }
	        
}

	       

