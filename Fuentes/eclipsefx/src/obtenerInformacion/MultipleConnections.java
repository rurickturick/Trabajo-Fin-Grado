package obtenerInformacion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;


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
	public String call() throws MalformedURLException {
		HttpURLConnection con;
		String web = "";
		
		try {
			con = (HttpURLConnection) new URL( url ).openConnection();
			con.setInstanceFollowRedirects(false);

			con.connect();
			
			    //Mientras se pueda redireccionar.
				while(con.getHeaderField("Location") != null)
				{
					web = con.getHeaderField("Location");
					con.disconnect();
					con = (HttpURLConnection) new URL( web ).openConnection();
					con.setInstanceFollowRedirects(false);
					con.connect(); // da una  excepcion si no se puede conectar al host.
				}
				con.disconnect();
		} 
	
		catch (UnknownHostException e) {
			//Entrara aqui si algun host no existe, o no se puede conectar a el.
			//No necesitamos esta url, asi q no hace falta tratarla.
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
		return web;
	}
}
