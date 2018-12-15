package application;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class argView {
	private final Stage argStage;
	private final argViewController argcontroller;
	public argView() throws IOException {
		argStage = new Stage();
		argStage.setTitle("");
		argStage.setResizable(false);		
		argStage.initModality(Modality.WINDOW_MODAL);		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/FXML/argument.fxml"));		
		BorderPane argumentpane = loader.load();
		argcontroller=loader.getController();		
		Scene scene = new Scene(argumentpane);
		argStage.setScene(scene);
		argStage.showAndWait();
	}
	
	public Stage getStage() {
        return argStage;
    }
	public String getindexdir() {
		return argcontroller.getindexdir();
	}
	public String getimagedir() {
		return argcontroller.getimagedir();
	}
	public String getsearchnum() {
		return argcontroller.getsearchnum();
	}
}
