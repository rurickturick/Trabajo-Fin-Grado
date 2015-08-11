package procesarInformacion;

import generarRed.Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Gui.Gui;
import Gui.PantallaCarga;
import obtenerInformacion.DataStorage;
import obtenerInformacion.MultipleConnections;
import obtenerInformacion.SearchWithLinks;

/**
 * Clase que procesa la informacion de los enlaces y paraleliza los procesos de cómputo asociados
 * a la decodificación de una URL minimizada
 * @author esteban, john, oscar
 * @reference https://gist.github.com/sanaulla123/3029344
 */

public class ProcesarInformacion {
	
	private static ArrayList<String> webFilter; //filtro para eliminar webs que nos proporcionan poca informacion
	
	/**
	 * Inicializa los filtros, que son las web que no nos interesan.
	 * Añadir en un futuro un parametro para filtrar las web que el usuario quiera.
	 */
	private void inicializeFilter()
	{
		webFilter = new ArrayList<String>();
		webFilter.add("twitter.com");
		webFilter.add("fb.me");
		webFilter.add("instagram.com");
		webFilter.add("facebook.com");
		webFilter.add("vine.co");
		webFilter.add("jobssy.com");
		webFilter.add("youtube.com");
		webFilter.add("api.twitter.com");
		webFilter.add("linkis.com");
		webFilter.add("ow.ly");
		webFilter.add("cur.lv");
		webFilter.add("ebay.es");
	}
	
	
	
	/**
	 * Decodifica los links recibidos de forma paralela.
	 * @param links conjunto de links a decodificar
	 * @return decodificados los links ya decodificados
	 */
	private  ArrayList<String> FastDecode(ArrayList<String> links)
	{
		inicializeFilter();		
		ArrayList<String> decodificados = new ArrayList<String>();
	    ArrayList<Callable<String>> callables = new ArrayList<>(links.size());
	    Iterator<String> itr = links.iterator();
	    while(itr.hasNext())
	    {
	    	String element = itr.next();
	    	MultipleConnections a = new MultipleConnections(element);
	    	Callable<String> callable = a;
	    	callables.add(callable);
	    }
	    //Runtime.getRuntime().availableProcessors()+1
	    ExecutorService executor = Executors.newFixedThreadPool(100);
	    try{
	    	List<Future<String>> futures = executor.invokeAll(callables);
	    	executor.shutdown();
	    	while (!executor.isTerminated()) {	 
			}
	    	for ( Future<String> future : futures){
	            try {
					String linkDecodificado = future.get();
					if(!linkDecodificado.equalsIgnoreCase("") && !webFilter.contains(linkDecodificado))
						decodificados.add(linkDecodificado);
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

	    	}
	    }
	    catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	      } 		
		
		return decodificados;
	
	}
	/**
	 * Recorta los url completos.
	 * @param salidaDecodificados
	 * @return
	 * @throws MalformedURLException 
	 */
	private ArrayList<String> recortarUrl(ArrayList<String> salidaDecodificados) throws MalformedURLException {
		
		Iterator<String> it = salidaDecodificados.iterator();
		ArrayList<String> recortadas = new ArrayList<String>();
		while(it.hasNext())
		{
			String web = it.next();
			if(web != ""){ // Esto es para filtrar solo las paginas que esten disponibles.
				if(!web.startsWith("http"))
					web = "http://" + web;  
				
				URL host= new URL(web);
				web =host.getHost();
				if(web.startsWith("www"))
			        web = web.substring(4, web.length());
				
				if(!webFilter.contains(web))
					recortadas.add(web);
			}
		}
		return recortadas;
	}
	
	
	
	
	/**
	 *  Recibe un tweet y devuelve todos los links que aparecen en el
	 * @param text
	 * @return links
	 * @reference http://stackoverflow.com/questions/9737717/find-url-in-string
	 */
	private  ArrayList<String> pullLinks(String text) {
		 ArrayList<String> links = new ArrayList<String>();

		    String regex = "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
		            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
		            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
		            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
		            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
		            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
		            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
		            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
		            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
		            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b";

		    Pattern p = Pattern.compile(regex);
		    Matcher m = p.matcher(text);
		    while(m.find()) {
		    String urlStr = m.group();
		    if (urlStr.startsWith("(") && urlStr.endsWith(")"))
		    {
		    urlStr = urlStr.substring(1, urlStr.length() - 1);
		    }
		    if(!urlStr.equalsIgnoreCase("http://t.co"))
		    	links.add(urlStr);
		    }
		    return links;
		}
	/**
	 * Recibe un ArrayList de tweets y devuelve todos los links que aparecen en ellos.
	 * @param response
	 * @return misLinks
	 */
	private  ArrayList<String> getLinks(ArrayList<String> response)
	{
		Iterator<String> itTweets = response.iterator();
		
		ArrayList<String> misLinks = new ArrayList<String>();
		while(itTweets.hasNext())
		{
			String tweet = itTweets.next();
			misLinks.addAll(pullLinks(tweet.replaceAll("\n", "")));  // concateno los ArrayList.
		}
		return misLinks;
	}
	
	
	
	/**
	 * Recibe un ArrayList de URL decodificados y cuenta cuantas veces aparece cada uno
	 * @param salidaDecodificados
	 * @return salidaDecodificados
	 */
	private ArrayList<DataStorage> cuentaRepeticiones(ArrayList<String> salidaDecodificados)
	{
		Iterator<String> it = salidaDecodificados.iterator();
		   
	    
		   ArrayList<DataStorage> cuentaLinks =new ArrayList<DataStorage>();
		    
		    	while(it.hasNext())
		    	{
		    		String link = it.next();
		    		DataStorage milink = new DataStorage(link);
		    		if(cuentaLinks.contains((DataStorage)milink)){
		    	         DataStorage datos = cuentaLinks.get(cuentaLinks.indexOf(milink));
		    	         datos.incrementa();
		    		}
		    		else cuentaLinks.add(milink);
		    		
		    	}
		    return cuentaLinks;
		
	}
	
	/**
	 *  Obtiene los links decodificados y el numero de veces que aparece de un 
	 *  maximo de "limit" tweets, relacionados por un "hastag"
	 * @param hashtag
	 * @param limit
	 * @return
	 * @throws MalformedURLException 
	 */
	public  ArrayList<DataStorage> getLinksFromHashTagRecortada(Hashtags hashtag, int limit) throws MalformedURLException
	{
		/*
		 * Aqui Definimos los hashtag que queremos usar
		 * El usuario elige los hashtags.
		 */
		
		//Realizo la busqueda para un determinado hashtag.
		SearchWithLinks search1 = new SearchWithLinks(hashtag.toString(),limit, "");
		ArrayList<String> misLinks = new ArrayList<String>();
		
		//obtengo todos los links.
			misLinks = getLinks( search1.busqueda());
		
		
		//decodifico los links
		  ArrayList<String> salidaDecodificados= FastDecode(misLinks);
		    
		//Acorto los URL
		  ArrayList<String> decodificadosCortos  = recortarUrl(salidaDecodificados); 
		
		return cuentaRepeticiones(decodificadosCortos);
		
	}
	
	public  ArrayList<DataStorage> getLinksFromHashTagCompleto(Hashtags hashtag, int limit,String date) throws MalformedURLException
	{
		/*
		 * Aqui Definimos los hashtag que queremos usar
		 * El usuario elige los hashtags.
		 */
		
		//Realizo la busqueda para un determinado hashtag.
		SearchWithLinks search1 = new SearchWithLinks(hashtag.toString(),40,date);
		ArrayList<String> misLinks = new ArrayList<String>();
		
		//obtengo todos los links.
			misLinks = getLinks( search1.busqueda());
		
		
		//decodifico los links
		  ArrayList<String> salidaDecodificados= FastDecode(misLinks);
		    
		
		return cuentaRepeticiones(salidaDecodificados);
		
	}	
	
	



	/*public static void main(String[] args) throws NumberFormatException, IOException {
		Main.RedGephi("11","03", "2015", "ninguno");
	}*/
}
