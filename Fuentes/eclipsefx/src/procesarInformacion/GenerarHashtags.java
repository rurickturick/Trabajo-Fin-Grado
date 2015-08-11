package procesarInformacion;

import java.util.Iterator;
import java.util.TreeSet;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class GenerarHashtags {

	
	private TreeSet<Hashtags> hashtags;
	
	public GenerarHashtags(){
		this.hashtags=new TreeSet<Hashtags>();
	}
	public GenerarHashtags(TreeSet<Hashtags> hashtags){
		this.hashtags=hashtags;
	}
	
	public TreeSet<Hashtags> getHashtags(){
		return this.hashtags;
	}
	
	public void addrHashtags(String txt){
		TreeSet<Hashtags> aux=pullHashtags(txt);
		Iterator<Hashtags> it=aux.iterator();
		while(it.hasNext()){
			this.hashtags.add(it.next());
		}	
	}
	private TreeSet<Hashtags> pullHashtags(String txt){
		 TreeSet<Hashtags> hashtags = new TreeSet<Hashtags>();
		 String [] aux = txt.split(" ");
		 for(int i=1;i<aux.length;i++){ //igual a 1 por que la primera posicion no tiene hashtags
			 if(aux[i].startsWith("#"))
			 hashtags.add(new Hashtags(aux[i].replace(".","")));		 
		 }
		 return hashtags;			 
	}
	public String toString(){
		Iterator<Hashtags> it = this.hashtags.iterator();
		String aux="";
		while(it.hasNext()){
			aux+=" " + it.next().toString();		
		}
		return aux;
	}
	
}
