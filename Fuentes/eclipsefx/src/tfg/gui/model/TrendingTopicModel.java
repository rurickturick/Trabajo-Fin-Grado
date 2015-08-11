package tfg.gui.model;

import trendingTopics.RecuperarInfo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TrendingTopicModel {

	private final StringProperty trendingTopic;
    private final StringProperty duracion;
    
    public TrendingTopicModel(){
    	this(null,null);
    }
    
    public TrendingTopicModel(String trendingTopic, String duracion){
    	this.trendingTopic = new SimpleStringProperty(trendingTopic);
    	this.duracion = new SimpleStringProperty(RecuperarInfo.tiempoTT(duracion));	
    }
    
    public String getTrendingTopic(){
    	return this.trendingTopic.get();
    }
    public boolean esGraficable(){
    	String hora = this.duracion.get();
    	String[] cifra = hora.split(":");
    	if(Integer.valueOf(cifra[0])>=5) return true;
    	else return false;
    }
    public void setTrendingTopic(String trendingTopic){
    	this.trendingTopic.set(trendingTopic);
    }
    public StringProperty trendingTopicProperty(){
    	return trendingTopic;
    }
    public void setDuracion(String duracion){
    	this.duracion.set(duracion);
    }
    public String getDuracion(){
    	return this.duracion.get();
    }
    public StringProperty duracionProperty(){
    	return duracion;
    }
    @Override
    public String toString(){
    	return "TT: "+this.getTrendingTopic()+" duracion: "+this.getDuracion();
    }
    
}
