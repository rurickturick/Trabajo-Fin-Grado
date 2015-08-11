package tfg.gui.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class HashtagName {

	private final StringProperty cultura;
	private final StringProperty tecnologia;
	private final StringProperty deporte;
	private final StringProperty politica;
	private final StringProperty entretenimiento;
	private final StringProperty otros;
	private final StringProperty hashtag;

	public HashtagName(String hashtag, String cultu, String tecno, String deporte, String pol, String ent, String o) {
		this.hashtag = new SimpleStringProperty(hashtag);
		this.cultura = new SimpleStringProperty(cultu);
		this.tecnologia = new SimpleStringProperty(tecno);
		this.deporte = new SimpleStringProperty(deporte);
		this.politica = new SimpleStringProperty(pol);
		this.entretenimiento = new SimpleStringProperty(ent);
		this.otros = new SimpleStringProperty(o);
	}
	
	public String getHashtag(){
		return  this.hashtag.get();
	}
	
	public void setHashtag(String h){
		this.hashtag.set(h);
	}
	
	public StringProperty HashtagProperty(){
		return hashtag;
	}
	
	public String getDeporte(){
		return deporte.get();
	}
	public String getCultura() {
		return cultura.get();
	}
	
	public String getPolitica(){
		return politica.get();
	}
	
	public String getEntretenimiento(){
		return entretenimiento.get();
	}
	public String getOtros(){
		return otros.get();
	}
	
	public void setPolitica(String Pol){
		this.politica.set(Pol);
	}
	
	public void setEntretenimiento(String value){
		this.entretenimiento.set(value);
	}
	
	public void setOtros(String value){
		this.otros.set(value);
	}

	public void setCultura(String firstName) {
		this.tecnologia.set(firstName);
	}
	
	public void setDeporte(String deporte){
		this.deporte.set(deporte);
	}
	
	public StringProperty CulturaProperty() {
		return cultura;
	}
	
	public StringProperty deporteProperty(){
		return deporte;
	}
	
	public StringProperty politicaProperty(){
		return politica;
	}
	
	public StringProperty entretenimientoProperty(){
		return entretenimiento;
	}
	
	public StringProperty otrosProperty(){
		return otros;
	}

	public String getTecnologia() {
		return tecnologia.get();
	}

	public void setTecnologia(String lastName) {
		this.cultura.set(lastName);
	}
	
	public StringProperty TecnologiaProperty() {
		return tecnologia;
	}
}