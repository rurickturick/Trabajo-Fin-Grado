package trendingTopics;

import java.io.FileWriter;

import org.gephi.partition.api.Partition;

import gephi.GephiMethods;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TestingGdfs {
	
	
	public static Partition Generagdf(String dia,String mes,String annio) {
		
		DBCursor cursor = mongoDBHandler.RecuperarGDF(dia,mes,annio);
		
		String gdf="";
	       while (cursor.hasNext()) {
	    	   DBObject tmp=cursor.next();
	    	   String gdfText = tmp.get("gdf").toString();
	    	   gdf = gdf + gdfText;
	          
	         }
		System.out.println(gdf);
		
		
		try{
		      FileWriter fichero = new FileWriter(dia+"-"+mes+"-"+annio+"Red.gdf");		      
		      fichero.write(gdf); //"\r\n"
		      fichero.close();

		    }catch(Exception ex){
		      ex.printStackTrace();
		    }
		
		
		GephiMethods p= new GephiMethods(dia+"-"+mes+"-"+annio+"Red");
		return p.script();// TODO Auto-generated method stub
		
		
	}
	

}
