package trendingTopics;

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
	
	public static String obtenerListaTT(DBCursor cursor)
	{
		String lista="";
	       while (cursor.hasNext()) {
	    	   DBObject tmp=cursor.next();
	    	   String duracion = tiempoTT(tmp.get("veces").toString());
	    	   lista = lista + tmp.get("tt") + "\t duración: " +duracion  + "\n";
	          // System.out.println("TT: "+ tmp.get("tt") + "   fecha: " +tmp.get("dia")+"-"+tmp.get("mes")+"-"+tmp.get("annio") + "  Veces: " + tmp.get("veces") + "  Lugar: " + tmp.get("lugar") );
	         }
		return lista;
     }
	
	public static DBCursor obtenerCursor(String dia, String mes, String año, String lugar)
	{
		
		 DBCursor cursor=mongoDBHandler.recuperarTT(dia, mes, año,lugar);
	       
		return cursor;
     }
	
	
}
