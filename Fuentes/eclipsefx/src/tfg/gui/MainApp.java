package tfg.gui;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	

    private Stage primaryStage;
    private BorderPane rootLayout;
    private static MainApp aplicacion;
    
    public MainApp() {
    	aplicacion = this;
    }
    public static MainApp getInstance() {
    	return aplicacion;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TrendSpy");

        initRootLayout(); //inicializa el layout de fondo el menú con edit file etc
        this.primaryStage.getIcons().add(new Image("file:images/iconApp.png"));
        showTrendingTopicView(); //muestra la primera pantalla el trending topic view
      //  this.showPopularesView();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setMaximized(true);

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Shows the person overview inside the root layout.
     */
    public void showTrendingTopicView() {
    	try {
			replaceSceneContent("view/TrendingTopicView.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
    public void showPopularesView() {
    	try {
			replaceSceneContent("view/PopularesView.fxml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
     }
    //http://stackoverflow.com/questions/13003323/javafx-how-to-change-stage
    private void replaceSceneContent(String fxml) throws Exception {
        Parent page = (Parent) FXMLLoader.load(MainApp.class.getResource(fxml), null, new JavaFXBuilderFactory());
        rootLayout.setCenter(page);
        
   
    }
    public boolean showLineChart(BorderPane panel){
    try {
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/LineChart.fxml"));
  
        AnchorPane page = (AnchorPane) loader.load();
    
        panel.setCenter(page);
     

        return true;
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	public static void main(String[] args) {
		launch(args);
	}
	public boolean showClasificador(String dia, String mes, String annio, String lugar,BorderPane panel) {
		  try {
		        // Load the fxml file and create a new stage for the popup dialog.
		        FXMLLoader loader = new FXMLLoader();
		        loader.setLocation(MainApp.class.getResource("view/ClasificadorTable.fxml"));
		    
		        AnchorPane page = (AnchorPane) loader.load();
		        panel.setCenter(page);
		       
		        
		        return true;
		    } catch (IOException e) {
		        e.printStackTrace();
		        return false;
		    }
		// TODO Auto-generated method stub
		
	}
}
