package datosGrafica;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;



public class Ejecucion {

	public static void main(String[] args) throws InterruptedException{
		
		Calendar c,d;
		int hora,minuto,segundo,horaS,minutoS,miliSegSleep;
		String dia,mes,annio;
		
		 while(true){
	        	c = Calendar.getInstance();
	    		hora = c.get(Calendar.HOUR_OF_DAY);
	            minuto = c.get(Calendar.MINUTE);
	            segundo = c.get(Calendar.SECOND);
	        	/*hora = 0;
	        	minuto = 0;
	        	segundo = 0;*/
	           
	        	if (hora == 0 && minuto == 0 && segundo == 0)
	        	{
	        		 d=Calendar.getInstance();
	         		d.add(Calendar.DATE,-1);
	         		dia = Integer.toString(d.get(Calendar.DATE));
	        	    mes = Integer.toString(d.get(Calendar.MONTH)+1);
	        	    annio = Integer.toString(d.get(Calendar.YEAR));
	         		
	     
	        		
	        		ArrayList<String> t = mongoDBHandler.recuperarTTporDuracion(dia,mes,annio);
	        		Iterator<String> it = t.iterator();
	        		int numConsultas = 0;
	        		while(it.hasNext()){
	        			String hashtag = it.next();
	        			
	        			SearchByHour searching = new SearchByHour(hashtag, 70000,annio+"-"+mes+"-"+dia, d.get(Calendar.DATE));
	        			searching.setNumConsultas(numConsultas);
	        			mongoDBHandler.GuardarGrafica(dia, mes, annio, hashtag, searching.busqueda());
	        			numConsultas = searching.getNumConsultas();
	        			
	        		}
	        		
	        		c = Calendar.getInstance();
	        		horaS = c.get(Calendar.HOUR_OF_DAY);
	                minutoS = c.get(Calendar.MINUTE);
	                
	                miliSegSleep = ((24 - horaS -1)*3600000) + (60-minutoS-1)*60000 ;
	                Thread.sleep(miliSegSleep);	 
	        		
	        		
	        		
	        	}
		 }
		

		
		
		
	}
	
	
}
