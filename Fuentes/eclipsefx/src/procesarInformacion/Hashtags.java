package procesarInformacion;

import java.text.Collator;
/**
 * Clase que crea objetos de tipo hashtags
 * @author esteban, john, oscar
 *
 */
public class Hashtags implements Comparable<Hashtags> {
	
	private String hashtag; //el hashtag a representar
	/**
	 * Constructora de la clase hashtag
	 * @param hashtag String asociado al hashtag
	 */
	public Hashtags(String hashtag){
		this.hashtag=hashtag;
	}
	@Override
	public String toString(){
		return this.hashtag;
	}
	
	@Override
	public int compareTo(Hashtags o) {
		Collator c=Collator.getInstance(); //segun el locale considera unas maneras u otras
		//Primary=>Considera iguales mayusculas y tildes
		//Secondary=>Considera iguales mayusculas
		//Tertiary=>Considera diferentes mayusculas y tildes
		c.setStrength(Collator.PRIMARY);
		return c.compare(this.hashtag, o.hashtag);
	}
	
	

}
