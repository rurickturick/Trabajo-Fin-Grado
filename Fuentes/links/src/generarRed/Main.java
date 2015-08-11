package generarRed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import obtenerInformacion.MultiplesBusquedas;
import procesarInformacion.Hashtags;
import trendingTopics.mongoDBHandler;

public class Main {

	public static void RedGephi(String dia, String mes, String año, String lugar)throws NumberFormatException, IOException
	{
		ArrayList<Hashtags> al=new ArrayList<Hashtags>();
		ArrayList<String> l = mongoDBHandler.recuperarTT(dia, mes, año);
		 Iterator<String> it = l.iterator();
		 String tmp = "";
		while (it.hasNext()) {
	    	   tmp=it.next();
	    	   al.add(new Hashtags(tmp));
	    }
		
		
		
	
         
        String date = año + "-" +mes +"-" + dia;
		//Creamos el objeto de MultiplesBusquedas pasando como parametro el arrayList
		MultiplesBusquedas s = new MultiplesBusquedas(al);
		//Realizamos las busquedas
		s.realizarBusquedas(date);
		//Vemos por consola el resultado
		//System.out.println(s.toString());
		
		//Creamos la red a partir de los datos de la busqueda
		Red miRed = new Red(s.getDatos());
	
		
	
		
	    miRed.escribeEnBD(dia,mes,año);
		
		miRed.recortarURLs();
		
		miRed.imprimeURLsRecortadas();
		//Generamos el fichero gdf con los datos almacenados 
		//miRed.generarNodos("miRed");
		miRed.generarGDF(dia, mes, año);
		
		//System.out.println(miRed.getDatosrecortados());
	
	}
	


}
