package generarRed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import obtenerInformacion.MultiplesBusquedas;
import procesarInformacion.Hashtags;
import trendingTopics.RecuperarInfo;

public class Main {

	public static void RedGephi(String dia, String mes, String año, String lugar)throws NumberFormatException, IOException
	{
		ArrayList<Hashtags> al=new ArrayList<Hashtags>();
		DBCursor cursor = RecuperarInfo.obtenerCursor(dia, mes, año, lugar);
		DBObject tmp;
		while (cursor.hasNext()) {
	    	   tmp=cursor.next();
	    	   al.add(new Hashtags(tmp.get("tt").toString()));
	    }
		
		/*al.add(new Hashtags("#edurne"));
		al.add(new Hashtags("#casillas"));
         */	
        String date = año + "-" +mes +"-" + dia;
		//Creamos el objeto de MultiplesBusquedas pasando como parametro el arrayList
		MultiplesBusquedas s = new MultiplesBusquedas(al);
		//Realizamos las busquedas
		s.realizarBusquedas(date);
		//Vemos por consola el resultado
		System.out.println(s.toString());
		//Creamos la red a partir de los datos de la busqueda
		Red miRed = new Red(s.getDatos());
		
		//File f = new File("");
		//String nombre = dia+"-"+mes+"-"+año+"-"+lugar;
		//miRed.escribeEnBD(dia,mes,año);
		
		
		//Generamos el fichero gdf con los datos almacenados 
		//miRed.generarNodos("miRed");
		miRed.generarGDF(dia, mes, año);
	
	}
	
	//public static void main(String[] args) throws NumberFormatException, IOException {
		
		
		/*Como al buscar los hashtag tarda bastante, otra forma de realizar el proceso es
		 * ir generando ficheros de datos poco a poco, y una vez tenerlos todos unidos en un solo
		 * fichero utilizar los siguientes metodos para generar una red de forma anÃ¡loga*/
	//	MultiplesBusquedas s = new MultiplesBusquedas(al);
	//	s.realizarBusquedas();
	//	System.out.println(s.toString());
	//	HashMap<Hashtags, ArrayList<DataStorage>> datos = s.getDatos();
	//	Red miRedGenerada = new Red(datos);
		//Ir cambiando el nombre de fichero para no sobreescribir, ojo con las rutas en windows \\
		
		
		
		
		/*Ahora una vez tengamos todos los ficheros creados unidos en uno solo hacemos*/
	//	Red miRed3 = new Red();
	//	miRed3.almacenarRed("/home/datosglobales.txt");
	//	miRed3.setSize();
	//	miRed3.generarNodos("/home/fichero.gdf");}
		


}
