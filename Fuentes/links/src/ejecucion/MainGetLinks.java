package ejecucion;
import generarRed.Main;

import java.io.IOException;
import java.util.Calendar;



public class MainGetLinks {

	public static void main(String[] args) throws InterruptedException, NumberFormatException, IOException {
		ejecutar();

	}

	private static void ejecutar() throws InterruptedException, NumberFormatException, IOException {
		int hora, minuto,segundo,horaS, minutoS, miliSegSleep;
		String  dia, mes, annio;
		Calendar c,d;
        
       
        
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
        		Thread.sleep(1000);
        		d=Calendar.getInstance();
        		d.add(Calendar.DATE,-1);
        	    dia = Integer.toString(d.get(Calendar.DATE));
        	    mes = Integer.toString(d.get(Calendar.MONTH)+1);
        	    annio = Integer.toString(d.get(Calendar.YEAR));
        		Main.RedGephi( dia,  mes, annio,"");
        		
        		c = Calendar.getInstance();
        		horaS = c.get(Calendar.HOUR_OF_DAY);
                minutoS = c.get(Calendar.MINUTE);
                
                System.out.print("Finalizado");
                miliSegSleep = ((24 - horaS -1)*3600000) + (60-minutoS-1)*60000 ;
                Thread.sleep(miliSegSleep);	 
        	}
        	
           
            
        	
        }
	}

	
}
