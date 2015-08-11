package obtenerInformacion;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import procesarInformacion.Hashtags;
import procesarInformacion.ProcesarInformacion;
/**
 * Clase que realiza varias busquedas a la vez a traves de la clase Procesar Informacion
 * @author esteban, john, oscar
 *
 */
public class MultiplesBusquedas {
	private HashMap<Hashtags, ArrayList<DataStorage>> datos; //datos a analizar
	/**
	 * Constructor por defecto de la clase MultiplesBusquedas
	 * @param query conjunto de hashtags sobre los que se realizara la busqueda
	 */
	public MultiplesBusquedas(ArrayList<Hashtags> query)
	{
		this.datos = new HashMap<Hashtags, ArrayList<DataStorage>>();
		Iterator<Hashtags> it  = query.iterator();
		while(it.hasNext())
		{
			datos.put(it.next(), new ArrayList<DataStorage>());
		}
		
	}
	/**
	 * 
	 * @return el hashmap con los datos
	 */
	public HashMap<Hashtags, ArrayList<DataStorage>> getDatos()
	{
		return this.datos;
	}
	
	/**
	 * Realiza las busquedas sobre los diferentes hashtags del hashmap y almacena sus links asociados
	 * @throws MalformedURLException 
	 */
	public void realizarBusquedas() throws MalformedURLException{
		 Set<Hashtags> keys = datos.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext()){
			 Hashtags aux = it.next();
			 ProcesarInformacion pi=new ProcesarInformacion();
			 this.datos.put(aux,  pi.getLinksFromHashTagRecortada(aux,40));	
		 }
		
	}
	
	public void realizarBusquedas(String date) throws MalformedURLException{
		 Set<Hashtags> keys = datos.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext()){
			 Hashtags aux = it.next();
			 ProcesarInformacion pi=new ProcesarInformacion();
			 this.datos.put(aux,  pi.getLinksFromHashTagCompleto(aux,40,date));	
		 }
		
	}
	
	
	@Override
	public String toString(){
		 Set<Hashtags> keys = datos.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 String ret="";
		 while(it.hasNext()){
			 Hashtags aux = it.next();
			 ret+="Hashgtag->"+aux+"\t";
			 ret+=this.datos.get(aux);
			 ret+="\n";	 
		 }
		return ret;
		
		
	}

	

}
