package captura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class BDConfiguration {
	private String data;
	 	BDConfiguration(){
	 		this.data = "mongodb://";
	 		parseDatosBD();
	 	}
	 	
	 	public String getDatos(){
	 		return this.data;
	 	}
	 	
	   public  void parseDatosBD(){
	     File miDir = new File (".");
	     try {
	       System.out.println ("Directorio actual: " + miDir.getCanonicalPath());
	       
	       String cadena;
	        FileReader f = new FileReader("bd.conf");
	        @SuppressWarnings("resource")
			BufferedReader b = new BufferedReader(f);
	        while((cadena = b.readLine())!=null) {
	            //System.out.println(cadena);
	        	String []datos = cadena.split(":");
	        	if(datos.length < 2)
	        		throw new Exception("Error en el archivo de configuracion");
	        	
	        	if(datos[0].equals("usuario"))
	        		data = data + datos[1]+ ":";
	        	else if(datos[0].equals("contraseña"))
	        		data = data + datos[1];
	        	else if(datos[0].equals("servidor"))
	        		data = data + "@" + datos[1] + ":";
	        	else if(datos[0].equals("puerto"))
	        		data = data + datos[1]+ "/";
	        	else if(datos[0].equals("adminColeccion"))
	        		data = data + datos[1];
	        	else 
	        		throw new Exception("Error en el archivo de configuracion");
	        	
	        }
	        
	        b.close();
	       
	       }
	     catch(Exception e) {
	       e.printStackTrace();
	       }
	     }
	   
	
}
