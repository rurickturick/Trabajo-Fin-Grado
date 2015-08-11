package trendingTopics;

import java.io.FileWriter;

import tfg.gui.model.TrendingTopicModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import Gui.ContenidoEnlazable;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RecuperarInfo {

	public static String tiempoTT(String veces){
		int tiempo;
		tiempo =Integer.parseInt(veces);
		tiempo=tiempo*15;
		int hora=tiempo/60;
		int minutos  = (tiempo%60);
		
		String resultado= hora+":"+minutos;
		
		
		return resultado ;
	}
	public static ArrayList<TrendingTopicModel> obtenerTrendingTopics(DBCursor cursor){
		ArrayList<TrendingTopicModel> listaTT = new ArrayList<TrendingTopicModel>();
		while (cursor.hasNext()) {
	    	   DBObject tmp=cursor.next();
	    	   TrendingTopicModel tt = new TrendingTopicModel(tmp.get("tt").toString(),tmp.get("veces").toString());
	    	   listaTT.add(tt);
		}
		return listaTT;
	}
	
	@Deprecated
	public static String obtenerListaTT(DBCursor cursor)
	{
		String lista="";
	       while (cursor.hasNext()) {
	    	   DBObject tmp=cursor.next();
	    	   String duracion = tiempoTT(tmp.get("veces").toString());
	    	   lista = lista + ContenidoEnlazable.tratarTT(tmp.get("tt").toString()) + "\t duración: " +duracion  + "<br>";
	          // System.out.println("TT: "+ tmp.get("tt") + "   fecha: " +tmp.get("dia")+"-"+tmp.get("mes")+"-"+tmp.get("annio") + "  Veces: " + tmp.get("veces") + "  Lugar: " + tmp.get("lugar") );
	         }
		return lista;
     }
	
	public static DBCursor obtenerCursor(String dia, String mes, String año, String lugar)
	{
		
		 DBCursor cursor=mongoDBHandler.recuperarTT(dia, mes, año,lugar);
	       
		return cursor;
     }
	
	public static String obtenerLinks(String dia, String mes, String año, String lugar){
		DBCursor tmp = mongoDBHandler.recuperarTT(dia,  mes,  año,  lugar);
		String lista="";
		while(tmp.hasNext()){
			DBObject aux=tmp.next();
	    	   String tt = (String) aux.get("tt");
	    	   DBCursor tmp2=mongoDBHandler.recuperarLinks(dia,  mes,  año,  tt);
	    	   while(tmp2.hasNext()){
	    		   DBObject aux2=tmp2.next();
	    		   lista = lista + tt + "\t links: " + aux2.get("links") + "\n";
	    	   }
	    	  
		}
		
		return lista;
	}
	
	 public static void creafichero(String dia,String mes,String annio,String hashtag) throws IOException{
		   DBCursor tmp=mongoDBHandler.RecuperarGrafica( dia, mes, annio,hashtag);
		   DBObject aux=tmp.next();
    	   String datos = (String) aux.get("datos");
		    FileWriter fichero = null;
	        PrintWriter pw = null;
	    	fichero = new FileWriter("datosGrafico"+hashtag+".txt",true);
	        pw = new PrintWriter(fichero);
	        pw.print(datos);
	        pw.close();
	    }
	
}
