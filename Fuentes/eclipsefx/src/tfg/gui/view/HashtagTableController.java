package tfg.gui.view;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import tfg.gui.model.HashtagName;
import clasificador.Clasificador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;



public class HashtagTableController {
	
	@FXML
	private TextField filterField;
	@FXML
	private TableView<HashtagName> personTable;
	@FXML
	private TableColumn<HashtagName, String> hashtag;
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
	
	private String dia;
	private String mes;
	private String annio;
	private String lugar;


	private ObservableList<HashtagName> masterData = FXCollections.observableArrayList();

	
	public HashtagTableController() {
		
		

	}
	public void initData(String dia,String mes, String annio, String lugar){
		this.dia=dia;
		this.mes=mes;
		this.annio=annio;
		if(lugar.equals("España")){
			this.lugar="Spain";
		}
		else if(lugar.equals("Sevilla")){
			this.lugar="Seville";
		}
		else
		this.lugar=lugar;
		
	}
	//@FXML
	public void botonClasificar(){
		
		
		String dep = "";
		String cul = "";
		String tec = "";
		String pol = "";
		String ent = "";
		String otr = "";
		
		HashMap<String, String> clasificacion = Clasificador.getClasificacion(dia, mes, annio, lugar);
		
		Set<String> keys = clasificacion.keySet();
		 Iterator<String> it = keys.iterator();
		// String clasi_aux[];
		 while(it.hasNext()){
			 String trendingTopic = it.next();
			 String clase = clasificacion.get(trendingTopic);
		 
			 if(clase.contains("Deporte"))
				 dep = "X";
			
			if(clase.contains("Tecnologia"))
				tec = "X";
			
			 
			 if(clase.contains("Cultura"))
				 cul = "X";
			 
			 if(clase.contains("Politica"))
				 pol = "X";
			 
			 if(clase.contains("Entretenimiento"))
				 ent = "X";
			 
			 if(clase.equals(""))
				 otr = "X";
			 
			 masterData.add(new HashtagName(trendingTopic, cul, tec, dep, pol, ent, otr));
			 
			 dep = tec = cul = pol = ent = otr = "";
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
