package procesarInformacion;




import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





import clasificador.RabinKarp;
import clasificador.Categorias;
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
	public void inicializeFilter()
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
		webFilter.add("linkshrink");
		webFilter.add("play.google.com");
		webFilter.add("trendinalia.com");
		webFilter.add("t.co");
	}
	
	private boolean isRelevantLink(String link){
		Iterator<String> it = webFilter.iterator();
		
		while(it.hasNext()){
			String linkNoRelevante = it.next();
			if(link.contains(linkNoRelevante))
				return false;
		}
		
		return true;
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
	    	List<Future<String>> futures = executor.invokeAll(callables, 150 , TimeUnit.SECONDS);
	    	executor.shutdown();
	    	
	    	for ( Future<String> future : futures){
	            try {
	            	if(future.isDone() && !future.isCancelled()){
	            		String linkDecodificado = future.get();
	            		if(!linkDecodificado.equalsIgnoreCase("") && isRelevantLink(linkDecodificado))
	            			decodificados.add(linkDecodificado);
	            	}
				} catch (ExecutionException e) {
					//e.printStackTrace();
					//Si alguna tarea falla por excepcion no hace falta tratarla
					//simplemente no se a�adira ese link a la lista de links decodificados.
				}

	    	}
	    }
	    catch (InterruptedException e) {
	        //Thread.currentThread().interrupt();
	      } 
	    catch (CancellationException e){
	    	
	    	
	    }
		
		return decodificados;
	
	}
	/**
	 * Recorta los url completos.
	 * @param salidaDecodificados
	 * @return
	 * @throws MalformedURLException 
	 */
	public static ArrayList<String> recortarUrl(ArrayList<String> salidaDecodificados){
		
		Iterator<String> it = salidaDecodificados.iterator();
		ArrayList<String> recortadas = new ArrayList<String>();
		String recortado = "";
		while(it.hasNext())
		{
			
			String web = it.next();
		
			if(web != ""){ // Esto es para filtrar solo las paginas que esten disponibles.
				if(!web.startsWith("http"))
					web = "http://" + web;  
				
				URL host;
				try {
					
					
					host = new URL(web);
					recortado =host.getHost();
					
					if(recortado.startsWith("www"))
				        recortado = recortado.substring(4, recortado.length());
					
					if(!webFilter.contains(recortado)){
						//Si el link recortado ya trae informacion de la clasificacion, lo agrego 
						if(recortado.contains("politica") || recortado.contains("cultura") || recortado.contains("deport") 
							 || recortado.contains("entretenimiento") || recortado.contains("tecnologia")){
							 	recortadas.add(recortado);
						 }
						 else{//Sino proceso el link entero en busqueda de patrones.
							 RabinKarp rk = new RabinKarp(web, Categorias.getSubCategories());
							 String misDatos = rk.getListMatchesToString();
							 if(!misDatos.equalsIgnoreCase("")){
								 String masPrioridadClasificacion ="";
								 int maxApariciones = -1;
							 
								 String []datos = misDatos.split(">");
							 
								 for (int i = 0; i < datos.length; i++){
									 String []datosConcretos = datos[i].split("-");
									 if(Integer.parseInt(datosConcretos[1]) > maxApariciones){
										 maxApariciones = Integer.parseInt(datosConcretos[1]);
										 masPrioridadClasificacion = datosConcretos[0];
									 }
									 
								 }
								 masPrioridadClasificacion = "." + masPrioridadClasificacion; 
								 recortado = recortado + masPrioridadClasificacion;
								 recortadas.add(recortado);
							 }
							 else
								 recortadas.add(recortado);
						 }
						 
					}
				} catch (MalformedURLException e) {
					//No la agregamos al ArrayList
				}
				
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
	public static ArrayList<DataStorage> cuentaRepeticiones(ArrayList<String> salidaDecodificados)
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

	
	public  ArrayList<DataStorage> getLinksFromHashTagCompleto(Hashtags hashtag, int limit,String date) throws MalformedURLException
	{
		/*
		 * Aqui Definimos los hashtag que queremos usar
		 * El usuario elige los hashtags.
		 */
		
		//Realizo la busqueda para un determinado hashtag.
		SearchWithLinks search1 = new SearchWithLinks(hashtag.toString(),limit,date);
		ArrayList<String> misLinks = new ArrayList<String>();
		
		//obtengo todos los links.
		   ArrayList<String> linksObtenidos = search1.busqueda();
			misLinks = getLinks( linksObtenidos);
		
		
		//decodifico los links
		  ArrayList<String> salidaDecodificados= FastDecode(misLinks);
		  //========================================================================
		  
		  //System.out.println(hashtag.toString() + "-> " + salidaDecodificados);
		  //========================================================================
		  ArrayList<DataStorage> a =  cuentaRepeticiones(salidaDecodificados);
		 
		
		return a;
		
	}	
	

}
