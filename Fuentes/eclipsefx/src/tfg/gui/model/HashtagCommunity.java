package tfg.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HashtagCommunity {
	
	
	
	private StringProperty[] comunidad;
	
	
	public HashtagCommunity(String... comunidades){
		comunidad = new StringProperty[comunidades.length];
		int i = 0;
		for(String comunity: comunidades){
			String a = comunity;
			a = a + a;
			this.comunidad[i] =  new SimpleStringProperty(comunity);
			i++;
		}
	}


	public String[] getComunidad(){
		String  []aux = new String[comunidad.length];
		int i = 0;
		for(StringProperty a: this.comunidad){
			aux[i] = a.get();
			i++;
		}
		
		return aux;
	}
	
	public StringProperty[] comunidadProperty(){
		return this.comunidad;
	}
}
