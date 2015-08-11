package busqueda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraficoPorHoras extends JFrame {
	
	    JPanel panel;
	    private String hashtag;
	    private Date fecha;
	    private ArrayList<Integer> datos;
	    /**
	     * 
	     * @param hashTag : nombre del hastag.
	     * @param fecha   : la fecha.
	     * @param datos   : el numero de tweets por hora.
	     */
	    public GraficoPorHoras(String hashTag, Date fecha, ArrayList<Integer> datos){
	    	this.datos = datos;
	    	this.fecha = fecha;
	    	this.hashtag = hashTag;
	        setTitle(this.hashtag + " grafico.");
	        setSize(800,600);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setVisible(true);
	        init();
	    }
	 
	    private void init() {
	    	 panel = new JPanel();
		     getContentPane().add(panel);
	    	
		     DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
	    	File archivo = null;
	        FileReader fr = null;
	        BufferedReader br = null;
	   
	        try {
	           // Apertura del fichero y creacion de BufferedReader para poder
	           // hacer una lectura comoda (disponer del metodo readLine()).
	           archivo = new File ("datosGrafico"+this.hashtag+".txt");
	           fr = new FileReader (archivo);
	           br = new BufferedReader(fr);
	   
	           // Lectura del fichero
	           String linea;
	           while((linea=br.readLine())!=null){
	              String []datos = linea.split("-");
	              line_chart_dataset.addValue( Integer.parseInt(datos[1]), "Numero Tweets", datos[0]); 
	           }
	        }
	        catch(Exception e){
	           e.printStackTrace();
	        }finally{
	           // En el finally cerramos el fichero, para asegurarnos
	           // que se cierra tanto si todo va bien como si salta 
	           // una excepcion.
	           try{                    
	              if( null != fr ){   
	                 fr.close();     
	              }                  
	           }catch (Exception e2){ 
	              e2.printStackTrace();
	           }
	        }
	    	

	        // Creando el Grafico
	        JFreeChart chart=ChartFactory.createLineChart("Trafico "+this.hashtag,
	                "Hora","Numero tweets",line_chart_dataset,PlotOrientation.VERTICAL,
	                true,true,false);  
	        
	        // Mostrar Grafico
	        ChartPanel chartPanel = new ChartPanel(chart);
	        panel.add(chartPanel);
	    }
	   
	    
	    public static void main(String args[]){
	        new GraficoPorHoras("#JuevesSanto", null, null).setVisible(true);
	    }
	
/**
 * ChartUtilities.saveChartAsJPEG(new File("grafico.jpg"), chart, 500, 500);
 

grafico.jpg -> Nombre del Archivo
chart -> Objeto que contiene el Grafico
500,500 -> Dimension de la Imagen


Mas Información en: http://jonathanmelgoza.com/blog/como-hacer-graficos-con-java/#ixzz3WWvNezGm
 */

	
}
