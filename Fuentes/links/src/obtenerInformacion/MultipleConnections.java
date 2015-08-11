package obtenerInformacion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



import java.util.concurrent.Callable;
/**
 * Clase que paraleliza el computo de procesamiento de descodificado de URL minimizadas
 * @author esteban, john, oscar
 *
 */
public class MultipleConnections implements Callable<String> {
	private final String url; //url a tratar
	
	/**
	 * Constructora de la clase 
	 * @param url url a tratar
	 */
	public MultipleConnections(String url) {
		this.url = url;	
	}
	
	/**
	 * Metodo que descodifica las url fijandose en los parametros de la conexión HTTP
	 */
	public String call() {
		HttpURLConnection connection;
	    String finalUrl = url;
	    boolean redirect = true;
	    try {
	    		do {
	           
					connection = (HttpURLConnection) new URL(finalUrl)
					.openConnection();
			
					connection.setInstanceFollowRedirects(false);
					connection.setUseCaches(false);
					connection.setConnectTimeout(10000);
	            
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
	                //System.out.println( "redirected url: " + finalUrl);
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
}
