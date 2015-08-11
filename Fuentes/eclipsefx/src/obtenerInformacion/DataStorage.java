package obtenerInformacion;

/**
 * Clase que almacena los links y el numero de veces que aparece
 * @author esteban, john, oscar
 *
 */
public class DataStorage {
      private String link; //el link que se estudia
      private int apariciones; //el numero de apariciones del mismo
      
     public DataStorage(String link)
      {
    	 this.link=link;
    	 this.apariciones = 1;
      }

	public void incrementa(){
		this.apariciones++;
	}
	
	@Override
	public boolean equals(Object o){
		DataStorage aux = (DataStorage)o;
		return this.link.equals(aux.link);
		
	}
	public String getLink(){
		return this.link;
	}

	public int getApariciones() {
		return apariciones;
	}

	public void setApariciones(int apariciones) {
		this.apariciones = apariciones;
	}
	@Override
	public String toString(){
		return this.link+" "+this.apariciones;
		
	}

	
}
