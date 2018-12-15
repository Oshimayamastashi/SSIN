package application;
	
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class Main extends Application{		
   	private static Stage primaryStage;
   	private FlowPane mainLayout;
    public static void main(String[] args) {
        launch(args);
        }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    	this.primaryStage=primaryStage;
    	this.primaryStage.setTitle("Search Similair Image Now!!");
    	showMainview();
    }
    
    public void showMainview() throws IOException {
    	FXMLLoader loader =new FXMLLoader();
		loader.setLocation(getClass().getResource("/FXML/mainview.fxml"));
		
		mainLayout =loader.load();
		Scene scene =new Scene(mainLayout);	
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
    }
	
	public static void showErrorWindow(String str) throws Exception {	
		//errorStage.showAndWait();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/FXML/errorwindow.fxml"));
		Pane errormsgpane=loader.load();
		Scene scene = new Scene(errormsgpane);
		scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
				
		errorInforControlller errorcontroller=loader.getController();
		errorcontroller.setText(str);
		
		Stage errorStage=new Stage();
		errorStage.initModality(Modality.WINDOW_MODAL);
		
		errorStage.setScene(scene);
		errorStage.setResizable(false);
		errorStage.show();
	}	
	
	public static void showserachingwindow(Stage Loadingstage) throws IOException {
		FXMLLoader loader =new FXMLLoader();
		loader.setLocation(Main.class.getResource("/FXML/serachingview.fxml"));
		
		Pane mainLayout =loader.load();
		Scene scene =new Scene(mainLayout);			
		
		
		Loadingstage.initModality(Modality.WINDOW_MODAL);
		Loadingstage.setScene(scene);		
		Loadingstage.setResizable(false);
		Loadingstage.show(); 
	}
	
	public static void indexwindow() throws IOException {
		FXMLLoader loader =new FXMLLoader();
		loader.setLocation(Main.class.getResource("/FXML/indexview.fxml"));
		
		Pane mainLayout =loader.load();
		Scene scene =new Scene(mainLayout);			
		Stage indexStage=new Stage();
		
		indexStage.initModality(Modality.WINDOW_MODAL);
		indexStage.setScene(scene);		
		indexStage.setResizable(false);
		indexStage.show(); 
	}
}