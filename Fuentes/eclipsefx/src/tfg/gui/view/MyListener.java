package tfg.gui.view;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;

@SuppressWarnings({ "unused", "deprecation" })
public class MyListener implements ChangeListener<Tab>{
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
			
			Action response = Dialogs.create()
					.title("Advertencia")
					.message("Hoy no hay información disponible.\n"
							+ "Espere a mañana para poder verla. \n" 
							+ "Puede revisar información de los días anteriores...")
							.showWarning();
			
			
				
			}
		}
			
	
	
	
		}
		
	

}
