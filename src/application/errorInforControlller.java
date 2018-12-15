package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class errorInforControlller {
	
	@FXML
	Label ErrorInfor=new Label();
	@FXML
	Button close_btn=new Button();
	
	@FXML
	public void closeWindow(ActionEvent e){
		Stage stage = (Stage) close_btn.getScene().getWindow();
	    stage.close();
	}
	
	
	public void setText(String str){
		ErrorInfor.setText(str);
		ErrorInfor.getStyleClass().add("error-text");
	}

}
