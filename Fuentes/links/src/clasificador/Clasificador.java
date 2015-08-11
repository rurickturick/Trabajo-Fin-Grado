package clasificador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import trendingTopics.mongoDBHandler;


import com.mongodb.DBCursor;
import com.mongodb.DBObject;


public class Clasificador {
	
	
	public static String Clasificar(String dia, String mes, String a�o, String lugar){
		DBCursor tmp = mongoDBHandler.recuperarTT(dia,  mes,  a�o,  lugar);
		String lista="";
		while(tmp.hasNext()){
			DBObject aux=tmp.next();
	    	   String tt = (String) aux.get("tt");
	    	   DBCursor tmp2=mongoDBHandler.recuperarLinks(dia,  mes,  a�o,  tt);
	    	   while(tmp2.hasNext()){
	    		   DBObject aux2=tmp2.next();
	    		   RabinKarp rk = new RabinKarp(aux2.get("links").toString(), Categorias.getSubCategories());
	    		   lista =lista + tt + ": " +  rk.getListMatches() + "\n";
	    	   }
	    	  
		}
		
		
		return lista;
	}
	
	
	
	public static void main(String[] args) throws IOException{
		
		String l;
		l=Clasificar("19","3","2015","Espa�a");
		System.out.print("Resultado : \n" + l.toString());
		
	}
}
