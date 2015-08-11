package tfg.gui.view;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import tfg.gui.MainApp;
import trendingTopics.RecuperarInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.TitledPane;

public class LineChartController {
	
	@FXML
	private LineChart grafico;
	@FXML
	private CategoryAxis hora;
	@FXML
	private NumberAxis numTweets;
	@FXML
	private TitledPane titulo;
	
 /*   private ObservableList<String> horas = FXCollections.observableArrayList();
    private ObservableList<Integer> num = FXCollections.observableArrayList();
*/
	
	private String hashtag;
	private String dia;
	private String mes;
	private String annio;
	
	private MainApp mainApp;
	    
	    public MainApp getMainApp() {
			return mainApp;
		}
		public void setMainApp(MainApp mainApp) {
			this.mainApp = mainApp;
		}
	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public LineChartController(/*String hashtag, String dia,String mes,String annio*/) {
	    	this.mainApp = MainApp.getInstance();
	   
	    }
	    @FXML
	    private void initialize() {    	
	    	
	    }
	    @FXML
	    private void boton(){
	    	 this.titulo.setText("Gráfico para "+ this.hashtag);
	    	 this.grafico.setData(getChartData());

	    }

	    private ObservableList<XYChart.Series<String, Integer>> getChartData() {
	    	ObservableList<Series<String, Integer>> answer = FXCollections.observableArrayList();
	        Series<String, Integer> aSeries = new Series<String, Integer>();
	  //      Series<String, Double> cSeries = new Series<String, Double>();
	        aSeries.setName(this.hashtag);
	  //      cSeries.setName("C");
	        
	        try {
				RecuperarInfo.creafichero(dia, mes, annio, hashtag);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        File archivo = null;
		    FileReader fr = null;
		    BufferedReader br = null;
		    System.out.println(this.hashtag);
		//    hora.setCategories(horas);
		    archivo = new File ("datosGrafico"+this.hashtag+".txt");
	           try {
				fr = new FileReader (archivo);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           br = new BufferedReader(fr);
	   
	           // Lectura del fichero
	           String linea;
	           try {
				while((linea=br.readLine())!=null){
				      String []datos = linea.split("-");		
				      System.out.print(Integer.parseInt(datos[1])+"  ");
				      System.out.println(datos[0]);
				      aSeries.getData().add(new XYChart.Data(datos[0]+":00", Integer.parseInt(datos[1])));
				   }
				
		//		hora.setCategories(horas);
		//		this.grafico.setData(num);
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           finally{
	        	   try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	           answer.add(aSeries);
		       return answer;
	    }
	    
		public void initData(String hashtag, String dia, String mes,String annio) {
			this.hashtag=hashtag;
	    	this.dia=dia;
	    	this.mes=mes;
	    	this.annio=annio;
			
		}
	        
	     /*   for (int i = 2011; i < 2021; i++) {
	            aSeries.getData().add(new XYChart.Data(Integer.toString(i), aValue));
	            aValue = aValue + Math.random() - .5;
	            cSeries.getData().add(new XYChart.Data(Integer.toString(i), cValue));
	            cValue = cValue + Math.random() - .5;
	        }*/
	        
	    }

	    	
		
/*
			      
}*/
