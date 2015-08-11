package obtenerInformacion;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

	
	public void realizarBusquedas(String date) throws MalformedURLException{
		 Set<Hashtags> keys = datos.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 int i = 1;
		 //int limitador = 10;
		 while(it.hasNext() ){
			 //limitador--;
			 if(i == 150)
			 {
				 try {
					TimeUnit.MINUTES.sleep(15);
					i = 1;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 Hashtags aux = it.next();
			 
			// System.out.print(i + " -> " + aux.toString() + " -> ");
			
			 
			 ProcesarInformacion pi=new ProcesarInformacion();
			 ArrayList<DataStorage> aux2 = pi.getLinksFromHashTagCompleto(aux,100,date);
			 
			 i++; // Incrementamos el numero de consultas realizadas.
			 
		//	 System.out.println(aux2);
			 /*
			 int numTries = 1;
			 while (aux2.size() == 0 && numTries < 2 ){
			 	aux2 = pi.getLinksFromHashTagCompleto(aux, 500, date);
			 	numTries++;
			 	i = i + 5;  // Porque se han realizado 5 consultas mas.
			 }*/
			//System.out.println(i + "->" +  aux.toString() + " : "+aux2);
			if(aux2.size() > 0)
				this.datos.put(aux, aux2 );	
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
