package tfg.gui.view;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;
import org.gephi.partition.api.Part;
import org.gephi.partition.api.Partition;

import com.mongodb.DBCursor;

import clasificador.Clasificador;
import busqueda.PopularSearch;
import busqueda.PopularTweet;
import busqueda.SearchPopular;
import Gui.ContenidoEnlazable;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tfg.gui.MainApp;
import tfg.gui.model.HashtagCommunity;
import tfg.gui.model.HashtagName;
import tfg.gui.model.PopularesModel;
import tfg.gui.model.TrendingTopicModel;
import tfg.gui.model.TweetModel;
import tfg.gui.model.TweetTableCell;
import tfg.gui.model.UserModel;
import tfg.gui.model.UserTableCell;
import trendingTopics.RecuperarInfo;
import trendingTopics.TestingGdfs;
import trendingTopics.mongoDBHandler;
import twitter4j.Status;

public class TrendingTopicController {
	
	
	@FXML
	private TextField filterField;
	@FXML
	private TableView<HashtagName> personTable;
	@FXML
	private TableView<HashtagCommunity> ComunidadTable;
	@FXML
	private TableColumn<HashtagName, String> hashtag;
	@FXML
	private TableColumn<HashtagCommunity, String> hashtagCommunity;
	@FXML
	private TableColumn<HashtagCommunity, String> community;
	@FXML
	private TableColumn<HashtagName, String> cultura;
	@FXML
	private TableColumn<HashtagName, String> tecnologia;
	@FXML
	private TableColumn<HashtagName, String> deportes;
	@FXML
	private TableColumn<HashtagName, String> politica;
	@FXML
	private TableColumn<HashtagName, String> entretenimiento;
	@FXML
	private TableColumn<HashtagName, String> otros;
	
	private ObservableList<HashtagName> masterData = FXCollections.observableArrayList();
	private ObservableList<HashtagCommunity> ComunidadData = FXCollections.observableArrayList();
	
	//---------------------------------Atributos tabla TTs------------------------------
    private ObservableList<TrendingTopicModel> trendingTopicsData = FXCollections.observableArrayList();
    @FXML
    private TableView<TrendingTopicModel> tablaTT;
    @FXML
    private TableColumn<TrendingTopicModel, String> trendingTopicColumn;
    @FXML
    private TableColumn<TrendingTopicModel, String> duracionColumn;
    @FXML
    private ComboBox<String> lugar;
    @FXML
    private DatePicker fecha;
    @FXML
    private WebView vistaWeb;
    //Parte de los populares
    //-------------------------------Atributos parte populares
    private ObservableList<PopularesModel> tweetsData = FXCollections.observableArrayList();
    @FXML
    private TableView<PopularesModel> tablaTweets;
    @FXML
    private TableColumn<PopularesModel, UserModel> userColumn; //ojo al parametro de este
    @FXML
    private TableColumn<PopularesModel, TweetModel> tweetColumn;
    @FXML
    private TableColumn<PopularesModel, String> rtColumn;
    @FXML
    private TableColumn<PopularesModel, String> favColumn;
    @FXML
    private TableColumn<PopularesModel, String> followersColumn;
    @FXML
    private Label ttLabel;
    @FXML
    private RadioButton radioRt;
    @FXML
    private RadioButton radioFav;
    @FXML
    private RadioButton radioFollowers;
    @FXML
    private RadioButton radioTwitter;

    @FXML
    private Slider sliderNumPopulares;
    @FXML
    private TextField textFieldNumPopulares;
    
    private ToggleGroup radioButtons;
    private String radioChoose;
    private Partition p2;
    
    //-----------------------------------Pestañas--------------------------------
    @FXML
    private Tab webTab;
    @FXML
    private Tab graficaTab;
    @FXML
    private Tab clasificadorTab;
    @FXML
    private Tab comunidadTab;
    @FXML
    private TabPane tabs;
    //--------------------------------Pestaña web------------------------------
    @FXML
    private TextField dirWeb;
    
    //--------------------------------Pestaña grafica--------------------------
    @FXML
    private BorderPane panelGrafica;
    @FXML
	private LineChart grafico;
	@FXML
	private CategoryAxis hora;
	@FXML
	private NumberAxis numTweets;
	@FXML
	private TitledPane titulo;
	@FXML
	private FlowPane cajaCheckBox;
	private ArrayList<CheckBox> listaDeChecks;
    //--------------------------------Pestaña clasificador---------------------
    @FXML
    private BorderPane panelClasificador;
    
    private class MyListener implements ChangeListener<Tab>{
		boolean valido;
		
		public MyListener(boolean valido){
			this.valido = valido;
		
		}
		
		public void setValido(boolean b){
			this.valido = b;
		}
		@Override
		public void changed(ObservableValue<? extends Tab> observable, Tab oldValue,
				Tab newValue) {
			if(!valido){
				
				if(!(newValue.getId().equals("webTab"))){
					@SuppressWarnings({ "deprecation", "unused" })
					Action response = Dialogs.create()
									  .title("Advertencia")
									  .message("Hoy no hay información disponible.\n" + "Espere a mañana para poder verla. \n" 
								      + "Puede revisar información de los días anteriores...")
								      .showWarning();
				
					tabs.getSelectionModel().select(webTab);
				}
			}
		}
	}
    
    
    private MyListener listener = new MyListener(false);
   
    
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
    public TrendingTopicController() {
    	this.mainApp = MainApp.getInstance();
    	
    }
    @FXML
    private void initialize() {
    	 tabs.getSelectionModel().selectedItemProperty().addListener(listener);
    	lugar.getItems().add("España");
		lugar.getItems().add("Barcelona");
		lugar.getItems().add("Bilbao");
		lugar.getItems().add("Las Palmas");
		lugar.getItems().add("Madrid");
		lugar.getItems().add("Malaga");
		lugar.getItems().add("Murcia");
		lugar.getItems().add("Palma");
		lugar.getItems().add("Sevilla");
		lugar.getItems().add("Valencia");
		lugar.getItems().add("Zaragoza");
		lugar.setValue(lugar.getItems().get(0));
		fecha.setValue(LocalDate.now());
		WebEngine webEngine = vistaWeb.getEngine();
		webEngine.load("https://twitter.com/?lang=es");
		this.radioButtons = new ToggleGroup();
		this.radioFav.setToggleGroup(radioButtons);
		this.radioRt.setToggleGroup(radioButtons);
		this.radioFollowers.setToggleGroup(radioButtons);
		this.radioTwitter.setToggleGroup(radioButtons);
	
		addListenerRadioButtons();
		this.radioChoose = "ReTweets";
		this.listaDeChecks = new ArrayList<CheckBox>();
		buscarTrendingTopics();
		
}
    @FXML
    private void buscarTrendingTopics(){
    	
    	//Fecha seleccionada en el programa.
    	String dia=Integer.toString(fecha.getValue().getDayOfMonth());
    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
    	String annio = Integer.toString(fecha.getValue().getYear());
    	
    	//Fecha Actual.
    	Calendar d = Calendar.getInstance();
    	String diaActual = Integer.toString(d.get(Calendar.DATE));
    	String mesActual = Integer.toString(d.get(Calendar.MONTH)+1);
    	String annioActual = Integer.toString(d.get(Calendar.YEAR));
    	
    	
    	boolean valido = !(dia.equals(diaActual) && mes.equals(mesActual) && annio.equals(annioActual));
	    listener.setValido(valido);
    	busqueda();
    	
    	if(valido){
    		listener.setValido(true);
    		clasificar();
        	muestraGrafico();
        	showComunities();	
    	}
    	else{
    		//tabs.getSelectionModel().selectedItemProperty().addListener(listener);
    		if(masterData.size() > 0)
    			masterData.clear();
    		
    		
    		this.grafico.setData(null);
    		
    		if(this.ComunidadData.size() > 0)
        		this.ComunidadData.clear();
    		
    	}
    
    

    	
    	
    	
    	
    	Platform.runLater(new Runnable() {                          
             @Override
             public void run() {
                 try{
                	 mostrarPopulares();
                 }finally{
                	 System.out.println();
                 }
             }
    	 });
  //  	mostrarPopulares();
    	
    	
    }
    @FXML
    public void mostrarGDF(){
    	
    	String dia=Integer.toString(fecha.getValue().getDayOfMonth());
    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
    	String annio = Integer.toString(fecha.getValue().getYear());
    	String fechaResultante = dia +"-" +mes+"-"+ annio;
    	
    	
    	 File path =new File( fechaResultante+"Red.pdf");
         try {
			Desktop.getDesktop().open(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
	}
    
    private void showComunities(){
    	String dia=Integer.toString(fecha.getValue().getDayOfMonth());
    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
    	String annio = Integer.toString(fecha.getValue().getYear());
    	
    
    	
    	
    	this.p2 = TestingGdfs.Generagdf(dia, mes, annio);
		
    	//Limpiamos la informacion de las celdas, para evitar mostrar informacion de otro dia.
    	if(this.ComunidadData.size() > 0)
    		this.ComunidadData.clear();
    	
    	this.ComunidadTable.getColumns().clear();
    	
    	Part[] datos = p2.getParts();
    	int particionMaxima = 0;
    	for(int i = 0; i < datos.length; i++){
    		if(particionMaxima < datos[i].getObjects().length)
    			particionMaxima = datos[i].getObjects().length;
    	}
    	
    	String [][]tablaDeDatos = new String[particionMaxima][datos.length];
    	
    	//Rellenamos los datos;
    	int col = 0;
    	
    	for(Part p : p2.getParts()){
    		
    		Object []todos = p.getObjects();
    		//Relleno los datos para la columna col
    		for (int j = 0; j < todos.length ; j++){
    			tablaDeDatos[j][col] = todos[j].toString();	
    		}
    		
    		//Relleno los huecos.
    		for(int q = todos.length;  q < particionMaxima; q++)
    			tablaDeDatos[q][col] = "";
    		
    		col++;	
    	}
    	
    	String []insertarDatos = new String[datos.length];
    
    	for(int i = 0; i < particionMaxima; i++){
    		for (int j = 0; j < datos.length ; j++){
    				insertarDatos[j] = tablaDeDatos[i][j];
    		}
    		this.ComunidadData.add(new HashtagCommunity(insertarDatos));
    	}
    	 //
    	int numComunidades = p2.getPartsCount();
    	
    	for(int i =0 ; i < numComunidades; i++){
    		int j = i;
    		TableColumn<HashtagCommunity, String> nuevaCol = new TableColumn<HashtagCommunity, String>("Comunidad " + (i+1));
    		nuevaCol.setCellValueFactory(cellData -> cellData.getValue().comunidadProperty()[j]);
    		this.ComunidadTable.getColumns().addAll(nuevaCol);
    	}
    	
    	for (int j = 0; j < this.ComunidadTable.getColumns().size(); j++)
    		System.out.println(this.ComunidadTable.getColumns().get(j).getText());
    
    	
    	FilteredList<HashtagCommunity> filteredData = new FilteredList<>(ComunidadData, p -> true);
		

		
		SortedList<HashtagCommunity> sortedData = new SortedList<>(filteredData);
		
		
		sortedData.comparatorProperty().bind(ComunidadTable.comparatorProperty());
		
		
		ComunidadTable.setItems(sortedData);
    	
		
    }
    
    public String damePorcentaje(String d, int total){
    	if(!d.equals("")&& Integer.parseInt(d)>0){
			 double aux = Integer.parseInt(d) * 100 / total;
			 int aux2 = (int) aux;
			 d = String.valueOf(aux2) + "%";
		 }
    	
    	return d;
    }
    
    public void clasificacionComunidades(String dia, String mes, String annio){
    	if (ComunidadData.size() > 0)
    		this.ComunidadData.clear();
    	
    	
    }
    
public void botonClasificar(String dia, String mes, String annio){
		if(masterData.size() > 0)
			masterData.clear();
		
		String dep = "";
		String cul = "";
		String tec = "";
		String pol = "";
		String ent = "";
		String otr = "";
		
		String l = lugar.getSelectionModel().getSelectedItem();
		if(l.equals("España")){
			l="Spain";
		}
		else if(lugar.equals("Sevilla")){
			l="Seville";
		}
		
		
		
		int total = 0;
		HashMap<String, String> clasificacion = Clasificador.getClasificacion(dia, mes, annio, l);
		DBCursor it = mongoDBHandler.recuperarTT(dia, mes, annio, l);
	
		
		if(clasificacion.size()> 0) {
		 while(it.hasNext()){
			 String trendingTopic = (String) it.next().get("tt");
			 String clase = clasificacion.get(trendingTopic); //Categoria1 n<Categoria2 n2<Categoria3 n3
			 
			 
			 
			 if(clase != null){
				 
				 if(clase.equals("")){
					 otr = "100%";
					 masterData.add(new HashtagName(trendingTopic, cul, tec, dep, pol, ent, otr));
					 dep = tec = cul = pol = ent = otr = "";
				 }else
				 {
					
					 String []strDataStorage = clase.split("<");
					 String []datos;
					 for(String tmp : strDataStorage){
						 datos = tmp.split(" ");
						 
						 if(datos[0].equalsIgnoreCase("Deporte"))
							 dep = datos[1];
				
						 if(datos[0].equalsIgnoreCase("Tecnologia"))
							 tec = datos[1];
				
				 
						 if(datos[0].equalsIgnoreCase("Cultura"))
							 cul = datos[1];
				 
						 if(datos[0].equalsIgnoreCase("Politica"))
							 pol = datos[1];
				 
						 if(datos[0].equalsIgnoreCase("Entretenimiento"))
							 ent = datos[1];
				 
						 if(datos[0].equalsIgnoreCase(""))
							 otr = datos[1];
						 
						 total = total + Integer.parseInt(datos[1]);
					 }
					 cul = damePorcentaje(cul,total);
					 tec = damePorcentaje(tec,total);
					 dep = damePorcentaje(dep,total);
					 pol = damePorcentaje(pol,total);
					 ent = damePorcentaje(ent,total);
					

					 masterData.add(new HashtagName(trendingTopic, cul, tec, dep, pol, ent, otr));
					 dep = tec = cul = pol = ent = otr = "";
					 total = 0;
 
				 }
			 
			 
			 }
		
		}

		
		hashtag.setCellValueFactory(cellData -> cellData.getValue().HashtagProperty());
		hashtag.setStyle("-fx-alignment: CENTER;");
		cultura.setCellValueFactory(cellData -> cellData.getValue().CulturaProperty());
		cultura.setStyle("-fx-alignment: CENTER;");
		tecnologia.setCellValueFactory(cellData -> cellData.getValue().TecnologiaProperty());
		tecnologia.setStyle("-fx-alignment: CENTER;");
		deportes.setCellValueFactory(celldata -> celldata.getValue().deporteProperty());
		deportes.setStyle("-fx-alignment: CENTER;");
		politica.setCellValueFactory(celldata -> celldata.getValue().politicaProperty());
		politica.setStyle("-fx-alignment: CENTER;");
		entretenimiento.setCellValueFactory(celldata -> celldata.getValue().entretenimientoProperty());
		entretenimiento.setStyle("-fx-alignment: CENTER;");
		otros.setCellValueFactory(celldata -> celldata.getValue().otrosProperty());
		otros.setStyle("-fx-alignment: CENTER;");

		
		FilteredList<HashtagName> filteredData = new FilteredList<>(masterData, p -> true);
		
		
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (person.getCultura().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				} else if (person.getTecnologia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (person.getDeporte().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (person.getPolitica().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (person.getEntretenimiento().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if (person.getOtros().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;
				else if(person.getHashtag().toLowerCase().indexOf(lowerCaseFilter)!= -1)
					return true;
				return false;
			});
		});
		
		
		SortedList<HashtagName> sortedData = new SortedList<>(filteredData);
		
		
		sortedData.comparatorProperty().bind(personTable.comparatorProperty());
		
		
		personTable.setItems(sortedData);
		
	}
		
}
    
    
    
    
    private void busqueda(){
    	if(tablaTT.getItems().size()>0);
    	tablaTT.getItems().clear();
    	
    	if (trendingTopicsData.size()> 0)
    		trendingTopicsData.clear();
    	
    	int selectedIndex = lugar.getSelectionModel().getSelectedIndex();
    	String lugar = tratarLugar(this.lugar.getItems().get(selectedIndex));
    	//System.out.println(lugar.getItems().get(selectedIndex));
    	String dia=Integer.toString(fecha.getValue().getDayOfMonth());
    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
    	String annio = Integer.toString(fecha.getValue().getYear());
    	//trendingTopicsData.clear(); //borramos la lista para que cuando se vuelva a ejecutar no los añada al final
   // 	System.out.println("dia: "+dia+" mes: "+mes+" año: "+annio);
    	trendingTopicsData.addAll(RecuperarInfo.obtenerTrendingTopics(RecuperarInfo.obtenerCursor(dia,mes,annio,lugar)));
    	//tablaTT.getItems().clear();
    	tablaTT.setItems(trendingTopicsData);
    	//tablaTT.getSelectionModel().selectFirst();
    	tablaTT.getSelectionModel().selectFirst();
    	//System.out.println(tablaTT.getSelectionModel().getSelectedIndex());
    	
    	//System.out.println(tablaTT.getSelectionModel().getSelectedItem());
    	trendingTopicColumn.setCellValueFactory(cellData -> cellData.getValue().trendingTopicProperty());
        duracionColumn.setCellValueFactory(cellData -> cellData.getValue().duracionProperty());
        linkear();
       
    }
    
	private String tratarLugar(String lugar) {
		if(lugar.equalsIgnoreCase("Sevilla"))
			return "Seville";
		else if (lugar.equalsIgnoreCase("España"))
			return "Spain";
		return lugar;
	}
	private String getTrendingSelected() {
		int indice=this.tablaTT.getSelectionModel().getSelectedIndex();
		//System.out.println(indice);
		ObservableList<TableColumn<TrendingTopicModel, ?>> lista = this.tablaTT.getColumns();
	//	System.out.println(lista.get(0).getCellData(indice));
		
		return (String) lista.get(0).getCellData(indice);
	}
	@FXML
	private void linkear(){		
		String dirWeb=ContenidoEnlazable.tratarTT((getTrendingSelected()));
		this.tabs.getSelectionModel().select((this.webTab));
		this.ttLabel.setText(getTrendingSelected());
        this.tweetsData.clear();
		//https://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm
		WebEngine webEngine = vistaWeb.getEngine();
		webEngine.load(dirWeb);	
		
	}
	private String getUsuarioSelected() {
		int indice=this.tablaTweets.getSelectionModel().getSelectedIndex();
		//System.out.println(indice);
		ObservableList<TableColumn<PopularesModel, ?>> lista = this.tablaTweets.getColumns();
	//	System.out.println(lista.get(0).getCellData(indice));
		UserModel celdaSeleccionada=(UserModel) lista.get(0).getCellData(indice);
	//	UserModel user=(UserModel) celdaSeleccionada.getTweetUser();
		return celdaSeleccionada.getScreenName();
	}
	
	private String getIdTweetSeleccionado() {
		int indice=this.tablaTweets.getSelectionModel().getSelectedIndex();
		//System.out.println(indice);
		ObservableList<TableColumn<PopularesModel, ?>> lista = this.tablaTweets.getColumns();
	//	System.out.println(lista.get(0).getCellData(indice));
		TweetModel celdaSeleccionada=(TweetModel) lista.get(1).getCellData(indice);
	//	UserModel user=(UserModel) celdaSeleccionada.getTweetUser();
		return celdaSeleccionada.getIdTweet();
	}
	private int columnaSeleccionadaPopulares() {
		TablePosition<?, ?> x = tablaTweets.getFocusModel().getFocusedCell();
		return x.getColumn();
	}
	@FXML
	private void mostrarUsuario() {
		this.tabs.getSelectionModel().select((this.webTab));
		WebEngine webEngine = vistaWeb.getEngine();
		if(columnaSeleccionadaPopulares()==0) {
		webEngine.load("https://twitter.com/"+getUsuarioSelected());
		}
		else if(columnaSeleccionadaPopulares()==1) {
	//		System.out.println("Columna del tweet");
			
			webEngine.load("https://twitter.com/"+getUsuarioSelected()+"/status/"+getIdTweetSeleccionado());
		}
	}
	
	@FXML
	private void actualizarSlide() {
		this.textFieldNumPopulares.setText(Integer.toString((int) sliderNumPopulares.getValue()));
	//	this.sliderNumPopulares.setValue(Integer.parseInt(this.textFieldNumPopulares.getText()));
	}
	
	
	
	
	
	@FXML
	private synchronized void mostrarPopulares() {
		
				if(tweetsData.size() > 0)
					tweetsData.clear(); //borramos la lista para que cuando se vuelva a ejecutar no los añada al final
				//   tweetsData.addAll(RecuperarInfo.obtenerTrendingTopics(RecuperarInfo.obtenerCursor(dia,mes,annio,lugar)));
		    	if(this.radioChoose == null)
		    		this.radioChoose = "ReTweets";
				
				String dia=Integer.toString(fecha.getValue().getDayOfMonth());
	    		String mes=Integer.toString(fecha.getValue().getMonth().getValue());
	    		String annio = Integer.toString(fecha.getValue().getYear());
	    		if(this.radioChoose.equals("PopularesTwitter")){
	    			SearchPopular search = new SearchPopular(getTrendingSelected(),1000,annio +"-"+mes+"-"+dia);
	    			List<Status> lista = search.busqueda();
	    			Iterator<Status> it = lista.iterator();
	    			int j=0;
	    			while(it.hasNext()&& j<Integer.parseInt(this.textFieldNumPopulares.getText())){
	    				tweetsData.add(new PopularesModel(it.next()));
	    				j++;
	    			}
	    			
	    		}
	    		else{
				PopularSearch populares = new PopularSearch(getTrendingSelected(), 1000, annio +"-"+mes+"-"+dia, this.radioChoose);
			    List<PopularTweet> listaPopulares=populares.busqueda();
			    Collections.sort(listaPopulares);
			    Iterator<PopularTweet> it = listaPopulares.iterator();
			    int i=0;
			    while(it.hasNext() && i<Integer.parseInt(this.textFieldNumPopulares.getText())){
			    	Status tweetStatus = it.next().getMyStatus();	 
			    	tweetsData.add(new PopularesModel(tweetStatus));
			    	i++;
			    }
	    		}
			    tablaTweets.setItems(tweetsData);
	//		    tweetColumn.setCellValueFactory(cellData -> cellData.getValue().tweetTextProperty());	    
			    rtColumn.setCellValueFactory(cellData -> cellData.getValue().rtCountProperty());
			    favColumn.setCellValueFactory(cellData -> cellData.getValue().favCountProperty());
			    followersColumn.setCellValueFactory(cellData -> cellData.getValue().followersCountProperty());
			    userColumn.setCellValueFactory(cellData -> cellData.getValue().tweetUserProperty());
			    userColumn.setCellFactory(new Callback<TableColumn<PopularesModel,UserModel>,
			    		TableCell<PopularesModel,UserModel>>(){
			    	@Override
			    	public TableCell<PopularesModel,UserModel> call(TableColumn<PopularesModel,UserModel> param){
			    		TableCell<PopularesModel,UserModel> cell = new UserTableCell();// {			    		   		
			    		return cell;
			    	}
			   });
			    tweetColumn.setCellValueFactory(cellData -> cellData.getValue().tweetTextProperty());
			    tweetColumn.setCellFactory(new Callback<TableColumn<PopularesModel,TweetModel>,
			    		TableCell<PopularesModel,TweetModel>>(){
			    	@Override
			    	public TableCell<PopularesModel,TweetModel> call(TableColumn<PopularesModel,TweetModel> param){
			    		TableCell<PopularesModel,TweetModel> cell = new TweetTableCell();// {			    		   		
			    		return cell;
			    	}
			   });
		   }
	
	private void addListenerRadioButtons() {
		this.radioButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
	        @Override
	        public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

	            RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
	            if(chk.getText().equals("RETWEETS")) {
	            	radioChoose="ReTweets";
	            }
	            if(chk.getText().equals("FAVORITOS")) {
	            	radioChoose="Favoritos";	       	
	            }
	            if(chk.getText().equals("FOLLOWERS")) {
	            	radioChoose="Followers";
	            }	
	            if(chk.getText().equals("POPULARES TWITTER")) {
	            	radioChoose="PopularesTwitter";
	            }	
	        }
	    });
	}
	@FXML
	private void muestraGrafico(){
		this.grafico.setData(null);
	//	this.tabs.getSelectionModel().select(graficaTab);
		this.cajaCheckBox.getChildren().removeAll(this.listaDeChecks);
		this.listaDeChecks.clear();
		ArrayList<String> listaGraficas = this.getTrendingConGraficas();
		Iterator<String> it = listaGraficas.iterator();
		while(it.hasNext()){
			CheckBox chk = new CheckBox(it.next().toString());
			this.cajaCheckBox.getChildren().add(chk);
			this.listaDeChecks.add(chk);
			
			chk.setOnAction((event) -> {
			    	this.ejecutarGraficas();
			   	});
			// Register the same filter for two different nodes
		}
		this.cajaCheckBox.setAlignment(Pos.TOP_CENTER);
		
	//	this.mainApp.showLineChart(getTrendingSelected(),dia,mes,annio,this.panelGrafica);
	}
	public void ejecutarGraficas(){
		this.grafico.setData(getChartData(this.listaDeChecks));
		this.grafico.animatedProperty().set(false); //se quita la animacion pero sale la leyenda bien
		Iterator<CheckBox> it = this.listaDeChecks.iterator();
		String txt="";
		while(it.hasNext()){
			CheckBox chk = it.next();
			if(chk.isSelected()){
			txt+=" "+chk.getText();
			}
		}
		this.titulo.setText("Grafico para: "+txt);
	}
	@FXML
	private void clasificar(){
		String dia=Integer.toString(fecha.getValue().getDayOfMonth());
    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
    	String annio = Integer.toString(fecha.getValue().getYear());
    //	this.tabs.getSelectionModel().select(clasificadorTab);
		this.botonClasificar(dia, mes, annio);
    	//this.mainApp.showClasificador(dia,mes,annio,lugar.getSelectionModel().getSelectedItem(),panelClasificador);
		
	}
	@FXML
	private void buscarPag(){
		this.vistaWeb.getEngine().load("https://www.google.es/search?q="+this.dirWeb.getText());
	}
	@FXML
	private void twitterWeb(){
		this.vistaWeb.getEngine().load("https://twitter.com/");
	}
	
	private ArrayList<String> getTrendingConGraficas(){
		ArrayList<String> listaGraficas = new ArrayList<String>();
		for(int i=0; i < this.trendingTopicsData.size(); i++ ){
			if(this.trendingTopicsData.get(i).esGraficable()){
				listaGraficas.add(this.trendingTopicsData.get(i).getTrendingTopic());				
			}
		}
		return listaGraficas;
	}
	
	//Grafica
	 private ObservableList<XYChart.Series<String, Integer>> getChartData(ArrayList<CheckBox> listaChecks) {
		 	
		 	String dia=Integer.toString(fecha.getValue().getDayOfMonth());
	    	String mes=Integer.toString(fecha.getValue().getMonth().getValue());
	    	String annio = Integer.toString(fecha.getValue().getYear());
	    	ObservableList<Series<String, Integer>> answer = FXCollections.observableArrayList();
	    	Iterator<CheckBox> it = listaChecks.iterator();
	    	while(it.hasNext()){
	    		CheckBox chk = it.next();
	    		if(chk.isSelected()){
	    			Series<String, Integer> aSeries = new Series<String, Integer>();
	    			aSeries.setName(chk.getText());
	    			String hashtag=chk.getText();
	    			 try {
	    					RecuperarInfo.creafichero(dia, mes, annio, hashtag);
	    				} catch (IOException e1) {
	    					// TODO Auto-generated catch block
	    					e1.printStackTrace();
	    				}
	    		        File archivo = null;
	    			    FileReader fr = null;
	    			    BufferedReader br = null;
	    			//    hora.setCategories(horas);
	    			    archivo = new File ("datosGrafico"+hashtag+".txt");
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
	    						archivo.delete(); //borramos el archivo al final para evitar concatenaciones
	    						
	    					} catch (IOException e) {
	    						// TODO Auto-generated catch block
	    						e.printStackTrace();
	    					}
	    		           }
	    		           answer.add(aSeries);
	    		}
	    	}
			return answer;
	    
	       
	    }
}
    
    

