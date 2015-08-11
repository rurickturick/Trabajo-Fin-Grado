package trendingTopics;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import twitter4j.Trends;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class mongoDBHandler {
	private static MongoClient mongo;
	private static DB db;
	
	static BDConfiguration bdDetails = new BDConfiguration();
	static String bd = bdDetails.getDatos();
	static{
		try{
			
			String mongoDbUri = bd;
			//String mongoDbUri = "mongodb://ssii1415:dbTFGtweets@hypatia.fdi.ucm.es:27017/admin";
		    mongo = new MongoClient(new MongoClientURI(mongoDbUri));
			db=mongo.getDB("twitter");
		//	mongo = new MongoClient("hypatia.fdi.ucm.es");
		//	mongo = new MongoClient("localhost", 27017);
		//	db = mongo.getDB("admin");
			//db.authenticate("ssii1415", "dbTFGtweets".toCharArray());
		//	db = mongo.getDB("twitter");
		
		 }
		 catch(UnknownHostException e)
		 {
			 e.printStackTrace();
		 }
	}
	
	// Inserta en la BD los TT de un lugar determinado
	public static  void insertarTT(Trends tt,String lugar){
		DBCollection table = db.getCollection("trendingtopic");
		Calendar c = Calendar.getInstance();
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH)+1);
		String annio = Integer.toString(c.get(Calendar.YEAR));
		int hora = c.get(Calendar.HOUR_OF_DAY);
        int minuto = c.get(Calendar.MINUTE);
		 for (int i = 0; i < tt.getTrends().length; i++) {
			   // Compruebo si existe ese tt ya en la BD, en ese dia y en ese lugar
			    BasicDBObject andQuery = new BasicDBObject();
			    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			    obj.add(new BasicDBObject("lugar", lugar));
			    obj.add(new BasicDBObject("tt", tt.getTrends()[i].getName()));
			    obj.add(new BasicDBObject("dia", dia));
			    obj.add(new BasicDBObject("mes", mes));
			    obj.add(new BasicDBObject("annio",annio));
			    andQuery.put("$and", obj);
			    DBCursor cursor = table.find(andQuery);
			   
			   if( cursor.count()==0){ //inserto nuevo tt
	        	BasicDBObject document = new BasicDBObject();
	        	document.put("tt", tt.getTrends()[i].getName());
	        	document.put("dia", dia);
	        	document.put("mes", mes);
	        	document.put("annio", annio);
	        	document.put("hora", hora);
	        	document.put("minuto", minuto);
	        	document.put("lugar", lugar);
	        	document.put("veces", 1);
	        	table.insert(document);
			   }
			   else { // actualizo el nº apariciones de ese tt
				  int veces=(int) cursor.next().get("veces");			 
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.put("veces", veces+1);				 
					BasicDBObject updateObj = new BasicDBObject();
					updateObj.put("$set", newDocument);				 
					table.update(andQuery, updateObj);   
			   }
		  }
	}
	// Recupera de la BD los TT de un determinado dia y lugar
	public static  DBCursor recuperarTT(String dia,String mes,String annio,String lugar){
		DBCollection table = db.getCollection("trendingtopic");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	   
	    obj.add(new BasicDBObject("lugar", lugar));
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    andQuery.put("$and", obj); 
	    DBCursor cursor = table.find(andQuery);
	    BasicDBObject sort = new BasicDBObject();
	    sort=new BasicDBObject("veces",-1);
		return cursor.sort(sort);
	
	}
	
	private static ArrayList<String> borrarNulls(List<String> listaConNulls){
		ArrayList<String> listaSinNulls = new ArrayList<String>();
		Iterator<String> it = listaConNulls.iterator();
		String tmp= "";
		while (it.hasNext())
		{
			tmp = it.next();
			if(tmp != null){
				listaSinNulls.add(tmp);
			}
		}
		
		return listaSinNulls;
	}
	/**
	 * Recuperas todos los trending topics de una determinada fecha
	 * @param dia 
	 * @param mes
	 * @param annio
	 * @return List<String> con los trending Topics de la fecha
	 */
	public static  ArrayList<String> recuperarTT(String dia,String mes,String annio){
		DBCollection table = db.getCollection("trendingtopic");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	   
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    andQuery.put("$and", obj); 
	    @SuppressWarnings("unchecked")
		List <String> l = table.distinct("tt", andQuery);
	   Collections.sort(l);
	    
	   // BasicDBObject sort = new BasicDBObject();
	   // sort=new BasicDBObject("veces",-1);
		return borrarNulls(l); 
	
	}
	public static  DBCursor recuperarLinks(String dia,String mes,String annio,String tt){							
		DBCollection table = db.getCollection("links");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject("tt", tt));
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    andQuery.put("$and", obj); 
	    DBCursor cursor = table.find(andQuery);
	    
		return cursor;
	
	}
	
	public static void GuardaLinks(String tt, String links,String dia,String mes,String annio){
		// Se crea la nueva colecion
		DBCollection table = db.getCollection("links");
		//Guardo la informacion en Mongo
		BasicDBObject document = new BasicDBObject();
		document.put("tt", tt);
		document.put("dia", dia);
    	document.put("mes", mes);
    	document.put("annio", annio);
    	document.put("links", links);
    	table.insert(document);
	}
	
	public static void GuardaGDF(String gdf,String dia,String mes,String annio){
		// Se crea la nueva colecion
		DBCollection table = db.getCollection("gdf");
		//Guardo la informacion en Mongo
		BasicDBObject document = new BasicDBObject();
		document.put("gdf", gdf);
		document.put("dia", dia);
    	document.put("mes", mes);
    	document.put("annio", annio);
    	table.insert(document);
	}
	//27/3/2015
	public static  DBCursor RecuperarGDF(String dia,String mes,String annio){							
		DBCollection table = db.getCollection("gdf");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    andQuery.put("$and", obj); 
	    DBCursor cursor = table.find(andQuery);
	    
		return cursor;
	
	}
	
	public static  DBCursor RecuperarGrafica(String dia,String mes,String annio,String hashtag){							
		DBCollection table = db.getCollection("graficas");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    obj.add(new BasicDBObject("hashtag", hashtag));
	    andQuery.put("$and", obj); 
	    DBCursor cursor = table.find(andQuery);
	    
		return cursor;
	
	}
	
	
	public static  ArrayList<String> recuperarTTporDuracion(String dia,String mes,String annio){
		DBCollection table = db.getCollection("trendingtopic");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	   
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    obj.add(new BasicDBObject("lugar", "Spain"));
	    obj.add(new BasicDBObject("veces", new BasicDBObject("$gte",20) ));

	    andQuery.put("$and", obj); 
	    @SuppressWarnings("unchecked")
		List <String> l = table.distinct("tt", andQuery);
	   Collections.sort(l);
	    
	   // BasicDBObject sort = new BasicDBObject();
	   // sort=new BasicDBObject("veces",-1);
		return borrarNulls(l); 
	
	}
	
	public static  ArrayList<String> recuperarTTporDuracionClasificador(String dia,String mes,String annio,String lugar){
		DBCollection table = db.getCollection("trendingtopic");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	   
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    obj.add(new BasicDBObject("lugar", lugar));
	    obj.add(new BasicDBObject("veces", new BasicDBObject("$gte",1) ));
	    andQuery.put("$and", obj); 
	    @SuppressWarnings("unchecked")
		List <String> l = table.distinct("tt", andQuery);
	   Collections.sort(l);
		return borrarNulls(l); 
	
	}
	
	
	
	
	
	
	
	public static DBCursor recuperarClasificacion(String dia, String mes,
			String annio, String tt) {
		
		
		DBCollection table = db.getCollection("clasificacion");
		BasicDBObject andQuery = new BasicDBObject();
	    List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	   
	    obj.add(new BasicDBObject("dia", dia));
	    obj.add(new BasicDBObject("mes", mes));
	    obj.add(new BasicDBObject("annio", annio));
	    obj.add(new BasicDBObject("tt", tt));
	    andQuery.put("$and", obj); 
	    DBCursor cursor = table.find(andQuery);
	  
		
		return cursor;
	}
}
	
	


