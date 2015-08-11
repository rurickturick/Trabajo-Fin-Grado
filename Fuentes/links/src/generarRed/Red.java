package generarRed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import clasificador.RabinKarp;
import obtenerInformacion.DataStorage;
import procesarInformacion.Hashtags;
import procesarInformacion.ProcesarInformacion;
import trendingTopics.mongoDBHandler;
import clasificador.Categorias;
/**
 * Clase que genera la red definitiva en formato GDF a partir de los datos dados por un hashmap
 * @author Esteban, Angel, Oscar  
 *
 */
public class Red{
	private int numNodos; //numero de nodos de la red
	private PrintWriter fichero; //fichero en el que se escribira el gdf
	private HashMap<Hashtags, ArrayList<DataStorage>> data; //datos de la red de la forma clave:hashtag valor:lista de enlaces
	private ArrayList<EnlazarNodos> enlaces; //estructura auxiliar para conectar nodos y almacenar información extra
	private HashMap<Hashtags, ArrayList<DataStorage>> dataRecortada;
	private HashMap<Hashtags, ArrayList<DataStorage>> dataRecortadaGDF;
	/**
	 * Constructora por defecto de la clase Red
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Red() throws NumberFormatException, IOException{
		this.data = new HashMap<Hashtags, ArrayList<DataStorage>>();
		this.dataRecortada = new HashMap<Hashtags, ArrayList<DataStorage>>();
		this.numNodos =this.data.size();
		this.enlaces=new ArrayList<EnlazarNodos>();
		this.dataRecortadaGDF = new HashMap<Hashtags, ArrayList<DataStorage>>();
		
	}
	/**
	 * Constructora de la clase Red que recibe unos datos ya precalculados
	 * @param datos
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public Red(HashMap<Hashtags, ArrayList<DataStorage>> datos) throws NumberFormatException, IOException{
		this.data = datos;
		this.dataRecortada = new HashMap<Hashtags, ArrayList<DataStorage>>();
		this.numNodos =this.data.size();
		this.enlaces=new ArrayList<EnlazarNodos>();
		this.dataRecortadaGDF = new HashMap<Hashtags, ArrayList<DataStorage>>();
	}
	
	public HashMap<Hashtags, ArrayList<DataStorage>> getDatosrecortados()
	{
		return this.dataRecortada;
	}
	/**
	 * Metodo que asigna el tamaño del hashmap de los datos al numero de nodos
	 */
	public void setSize() {
		this.numNodos=this.data.size();	
	}
	/**
	 * Clase privada auxiliar para saber si dos nodos estan conectados y guardar información extra
	 * @author esteban, Angel, oscar
	 *
	 */
	private class EnlazarNodos{
		private Hashtags nodo1; //nodo1
		private Hashtags nodo2; //nodo2
		private boolean conectado; //true si nodo1 y nodo2 estan conectados
		private String label; //conjunto de links que comparten ambos nodos
		private int peso; //numero de links que comparten ambos nodos
		/**
		 * Constructora de la clase privada EnlazarNodos
		 * @param nodo1 el primer nodo a conectar
		 * @param nodo2 el segundo nodo que se conecta al primero
		 */
		public EnlazarNodos(Hashtags nodo1, Hashtags nodo2){
			this.nodo1=nodo1;
			this.nodo2=nodo2;
			this.conectado=false;
			this.peso=0;
			this.label="";
		}
		/**
		 * Metodo que enlaza dos nodos de esta clase
		 * @param label link en comun compartido
		 */
		public void enlazar(String label){
			this.conectado=true;
			this.peso++;
			this.label+=" "+label;
		}
		/**
		 * 
		 * @return Devuelve true si los nodos 1 y 2 estan conectados
		 */
		public boolean getConectado(){
			return conectado;
		}
		
		@Override
		public boolean equals(Object o){
			EnlazarNodos aux=(EnlazarNodos) o;
			if(this.nodo1.equals(aux.nodo1) && this.nodo2.equals(aux.nodo2)){
				return true;
			}
			if(this.nodo2.equals(aux.nodo1) && this.nodo1.equals(aux.nodo2)){
				return true;
			}
			return false;
			
		}
		
		
		
		
	}

	/**
	 * Metodo que escribe en un fichero pasado por <ruta> la informacion de la red de forma
	 * hashtag>link1<link2<...<linkN<
	 * @param ruta ruta donde se almacenara el fichero
	 * @throws IOException
	 */
	public void escribeEnFichero(String ruta) throws IOException{
		FileWriter f= new FileWriter(ruta);
		PrintWriter ficheroTmp = new PrintWriter(f);
		Set<Hashtags> keys = data.keySet();
		Iterator<Hashtags> it = keys.iterator();
		while(it.hasNext()){
			Hashtags h= it.next();
			ficheroTmp.print(h);
			ficheroTmp.print(">");
			ArrayList<DataStorage> links=this.data.get(h);
			Iterator<DataStorage> itLinks=links.iterator();
			while(itLinks.hasNext()){
				DataStorage l=itLinks.next();
				ficheroTmp.print(l.getLink()+"<");
			}
			ficheroTmp.println();
		//	this.fichero.println(this.data.get(h));			
		}
		ficheroTmp.close();
		
		
	}
	/**
	 * Metodo  que guarda los links asociados a un determinado Hashtag en la bd
	 * @param dia
	 * @param mes
	 * @param annio
	 * @throws IOException
	 */
	public void escribeEnBD(String dia,String mes,String annio) throws IOException{
		
		Set<Hashtags> keys = data.keySet();
		Iterator<Hashtags> it = keys.iterator();
		String lista = "";
		while(it.hasNext()){
			String tmp="";
			Hashtags h= it.next();		
			ArrayList<DataStorage> links=this.data.get(h);
			
			//No guardamos hashtags que no tengan links.
			if(links.size() > 0){
				Iterator<DataStorage> itLinks=links.iterator();
						while(itLinks.hasNext()){
							DataStorage l=itLinks.next();
							tmp=tmp+l.getLink()+"<";
							
							RabinKarp rk = new RabinKarp(tmp, Categorias.getSubCategories());
				    		   lista = rk.getMatchesAsString();
						}
			
				mongoDBHandler.GuardaLinks(h.toString(),tmp,dia,mes,annio);
				mongoDBHandler.GuardaClasificacion(h.toString(), dia,mes,annio,lista);
			}
		}
		
		
		
	}
	/*Metodo obsoleto que concatenaba dos ficheros que se creaban con escribeEnFichero
	 * Se dejo de usar porque es mas rapido copiar y pegar a mano el contenido de todos 
	 * los ficheros que guardaban datos parciales de la red
	public void concatenaFicheros(String ruta1, String ruta2) throws IOException{
		File f1 = new File (ruta1);
		Scanner scan1 = new Scanner(f1);
		String s=null;
		String s1 = null;
		String s2=null;
		while(scan1.hasNext()){
			s=scan1.nextLine();
		}
		scan1.close();
		File f2 = new File (ruta2);
		Scanner scan2 = new Scanner(f2);
		while(scan2.hasNext()){
			s1=scan2.nextLine();
		}
		scan2.close();
		s2=s+"\n"+s1;
		FileWriter fw = new FileWriter (new File ("DatosConcatenados.txt"));
		fw.write(s2);
		fw.close(); 	
	}*/
	/**
	 * Metodo que carga en datos una red que se encuentra en un fichero
	 * @param ruta1 ruta donde se encuentra el fichero
	 * @throws FileNotFoundException
	 */
	public void almacenarRed(String ruta1) throws FileNotFoundException{
		HashMap<Hashtags, ArrayList<DataStorage>> aux=new HashMap<Hashtags, ArrayList<DataStorage>>();
		File f1 = new File (ruta1);
		Scanner scan1 = new Scanner(f1);
		while(scan1.hasNext()){
			String s=scan1.nextLine();
			String[] h=s.split(">");
			Hashtags hashtag = new Hashtags(h[0]);
			String[] links=h[1].split("<");
			ArrayList<DataStorage> enlaces=new ArrayList<DataStorage>();
			for(int i=0;i<links.length;i++){
				enlaces.add(new DataStorage(links[i]));
			}
			aux.put(hashtag, enlaces);
			
		}
		scan1.close();
		this.data=aux;			
	}
	/**
	 * Metodo principal que genera un archivo gdf con los datos almacenados en Datos
	 * @param nombre nombre que queramos dar al fichero resultante
	 * 
	 */
	public void generarNodos(String nombre) throws IOException{
		FileWriter f= new FileWriter(nombre+".gdf");
		this.fichero = new PrintWriter(f);
		fichero.println("nodedef>name VARCHAR,label VARCHAR");		
		 Set<Hashtags> keys = data.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext())
		{
			String nodo=it.next().toString();
			fichero.println(nodo+","+nodo); 
		}
		 fichero.println("edgedef>node1 VARCHAR,node2 VARCHAR,weight INTEGER,label VARCHAR,links VARCHAR");
		 for (int a = 1; a < numNodos; a++)
			{
				for (int b = a + 1; b <= numNodos; b++)
				{	 
					compartenLinks(a,b);
				}	
			}
		 for(int z=0;z<this.enlaces.size();z++){
		 EnlazarNodos link = this.enlaces.get(z);
		 if(link.getConectado()){
			fichero.println(link.nodo1+","+link.nodo2+","+link.peso+","+link.peso+","+link.label);
		 }
		 }
		 fichero.close();
		
	}
	
	/**
	 * Metodo que genera los datos de un fichero gdf y los almacena en la bd
	 *  
	 */
	public void DiscriminarNodos(){
		String dia,mes,annio = "";
		Calendar d=Calendar.getInstance();
		d.add(Calendar.DATE,-1);
	    dia = Integer.toString(d.get(Calendar.DATE));
	    mes = Integer.toString(d.get(Calendar.MONTH)+1);
	    annio = Integer.toString(d.get(Calendar.YEAR));
	    
	    ArrayList<String> tts = mongoDBHandler.recuperarTTporDuracion(dia, mes, annio);
	    ArrayList<Hashtags> tt2 = new ArrayList<Hashtags>();
	    for(String s: tts){
	    	tt2.add(new Hashtags(s));
	    }
	    
	    Set<Hashtags> keys = dataRecortada.keySet();
		Iterator<Hashtags> it = keys.iterator();
		
		while(it.hasNext()){
			Hashtags h= it.next();
			if(tts.contains(h.toString()))
				this.dataRecortadaGDF.put(h,dataRecortada.get(h));
		}
			dataRecortada = this.dataRecortadaGDF;
	    this.numNodos = this.dataRecortadaGDF.size();
	    
	}
	
	public void generarGDF(String dia,String mes, String annio) throws IOException{
		String gdf="";
		
		DiscriminarNodos();
	    
	   
	    
		gdf="nodedef>name VARCHAR,label VARCHAR"+"\n";		
		 Set<Hashtags> keys = dataRecortada.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext())
		{
			String nodo=it.next().toString();
			gdf=gdf+nodo +","+nodo + "\n";
		}
		 gdf=gdf+"edgedef>node1 VARCHAR,node2 VARCHAR,weight INTEGER,label VARCHAR,links VARCHAR" +"\n";
		 for (int a = 1; a < numNodos; a++)
			{
				for (int b = a + 1; b <= numNodos; b++)
				{	 
					compartenLinks(a,b);
				}	
			}
		 for(int z=0;z<this.enlaces.size();z++){
		 EnlazarNodos link = this.enlaces.get(z);
		 if(link.getConectado()){
			 gdf=gdf+link.nodo1+","+link.nodo2+","+link.peso+","+link.peso+","+link.label+"\n";
			
		 }
		 }
		 mongoDBHandler.GuardaGDF(gdf, dia, mes, annio);
		//System.out.print(gdf);
	}
	
	
	/**
	 * Metodo privado auxiliar a generarNodos() que crea estructuras de tipo enlazar nodos para saber si dos nodos se conectan
	 * @param i nodo i
	 * @param j nodo j
	 */
	private void compartenLinks(int i, int j) {		
		 Set<Hashtags> keys = dataRecortada.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 int u = 1;
		 int v = 2;	
		 while(it.hasNext() && u < i){
			 u++;
			 v++;
			it.next();		 
		 }
		 Hashtags h1=it.next();	 
		 ArrayList<DataStorage> linksI = dataRecortada.get(h1);		 
		 while(it.hasNext() && v < j) {
			v++;
			it.next(); 
		 } 
		 Hashtags h2=it.next();
		 ArrayList<DataStorage> linksJ = dataRecortada.get(h2); 
		 //La idea de esto es iterar el array que tenga menos elementos
		 //porque asi es mas eficiente.
		 if(linksI.size() > linksJ.size())
		 {
			 Iterator<DataStorage> itData = linksJ.iterator();
			 EnlazarNodos enlace=new EnlazarNodos(h1,h2);
			 while(itData.hasNext())
			 {
				 DataStorage aux=itData.next();
				 for(int w=0;w<linksI.size();w++){
					 if(linksI.get(w).equals(aux)){
						 enlace.enlazar(linksI.get(w).getLink());
					 }
				 }
			if(!this.enlaces.contains(enlace)){
			 this.enlaces.add(enlace);
			}
			 }
		 }
		 else //la busqueda se hace al reves
		 {
			 Iterator<DataStorage> itData = linksI.iterator();
			 EnlazarNodos enlace=new EnlazarNodos(h2,h1);
			 while(itData.hasNext())
			 { 
				 DataStorage aux=itData.next();
				 for(int z=0;z<linksJ.size();z++){
					 if(linksJ.get(z).equals(aux)){
						 enlace.enlazar(linksJ.get(z).getLink());
					 }
				 }
				 if(!this.enlaces.contains(enlace)){
				 this.enlaces.add(enlace);
				 }
			 }
			 
		 } 
	}
	
	
	public void recortarURLs() {
		Set<Hashtags> keys = data.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext()){
			 Hashtags ht = it.next();
			 ArrayList<DataStorage> aux = data.get(ht);
			 
			 if(aux.size() > 0){
				 aux = Red.procesarUrls(data.get(ht));
				dataRecortada.put(ht, aux);
			 }
		 }
		this.numNodos = dataRecortada.size();
	}
	
	
	public void imprimeURLsRecortadas(){
		Set<Hashtags> keys = dataRecortada.keySet();
		 Iterator<Hashtags> it = keys.iterator();
		 while(it.hasNext()){
			 Hashtags ht = it.next();
			 ArrayList<DataStorage> aux = dataRecortada.get(ht);
			 System.out.println(ht.toString() + aux.toString());
		 }
	}
	
	
	
	
	
	
	private static ArrayList<DataStorage> procesarUrls(ArrayList<DataStorage> dt) {
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<Integer> apariciones= new ArrayList<Integer>();
		ArrayList<DataStorage> nuevo =  new ArrayList<DataStorage>();
		Iterator<DataStorage> it = dt.iterator();
		while(it.hasNext()){
			DataStorage a = it.next();
			links.add(a.getLink());
			apariciones.add(a.getApariciones());

		}
		//
		links = ProcesarInformacion.recortarUrl(links);
		nuevo = ProcesarInformacion.cuentaRepeticiones(links);
		
		return nuevo;
	}

	public static void main(String args[]) throws NumberFormatException, IOException{
		ProcesarInformacion pi = new ProcesarInformacion();
		pi.inicializeFilter();
		HashMap<Hashtags, ArrayList<DataStorage>> s = new HashMap<Hashtags, ArrayList<DataStorage>>();
		ArrayList<DataStorage> aux = new ArrayList<DataStorage>();
		aux.add(new DataStorage("http://www.html5quintus.com/#demo", 5));
		s.put(new Hashtags("#Quintus"), aux);
		
		Red miRed = new Red(s);
		miRed.recortarURLs();
		System.out.println(miRed.getDatosrecortados());
		
	}
	
}
