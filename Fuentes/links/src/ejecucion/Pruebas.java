package ejecucion;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import clasificador.Categorias;
import clasificador.RabinKarp;
import obtenerInformacion.DataStorage;
import obtenerInformacion.MultiplesBusquedas;
import procesarInformacion.Hashtags;
import procesarInformacion.ProcesarInformacion;
import trendingTopics.mongoDBHandler;


public class Pruebas {
	

	
	public static String fetchRedirectURL(String url)
    {
		HttpURLConnection connection;
	    String finalUrl = url;
	    boolean redirect = true;
	    try {
	    		do {
	           
					connection = (HttpURLConnection) new URL(finalUrl)
					.openConnection();
			
					connection.setInstanceFollowRedirects(false);
					connection.setUseCaches(false);
					connection.setConnectTimeout(6000);
	            
					connection.setRequestMethod("GET");
				
	           
					connection.connect();
			
	            int responseCode;
				
					responseCode = connection.getResponseCode();
				
	            if (responseCode >=300 && responseCode <400)
	            {
	                String redirectedUrl =    connection.getHeaderField("Location");
	                if(null== redirectedUrl)
	                    break;
	                finalUrl =redirectedUrl;
	                System.out.println( "redirected url: " + finalUrl);
	            }
	            else
	                redirect = false;
	        }while (connection.getResponseCode() != HttpURLConnection.HTTP_OK && redirect);
	    		connection.disconnect();
    }
	catch (IOException e){
		finalUrl = "";
	}
	    	
	    	
	        
	 
	   
	    return finalUrl;
    }
  
	
	
    public static void main(String[] args)  {
    	/*HashMap<Hashtags, String> aux = new HashMap<Hashtags, String>();
    	
    	aux = mongoDBHandler.recuperarLinksCompletos("7","6","2015");
    	
    	Set<Hashtags> h = aux.keySet();
    	Iterator<Hashtags> it = h.iterator();
    	String a = "";
    	String lista =" ";
    	int totalMatches = 0;
    	while(it.hasNext()){
    		Hashtags hashtag = it.next();
    		a = aux.get(hashtag);
    		RabinKarp rk = new RabinKarp(a, Categorias.getSubCategories());
 		   lista = rk.getMatchesAsString();
 		 
 		   
 		   mongoDBHandler.GuardaClasificacion(hashtag.toString(), "7", "6", "2015", lista );
    		
    	}
    		System.out.println("Finalziado");	*/
    	Hashtags a = new Hashtags("a");
    	Hashtags b = new Hashtags("a");
    	
    	int c = a.compareTo(b);
    	if(c==0)
    		System.out.print("bien");
    }
}
