package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class IndexController {
	@FXML
	public Button btn_index = new Button();
	@FXML
	public Button btn_cancel = new Button();
	@FXML
	public TextField textf_imagedir = new TextField();
	@FXML
	public TextField textf_indexdir = new TextField();

	@FXML
	public void initialize() {

	}

	@FXML
	public void getimagedir(ActionEvent e) {
		DirectoryChooser dirChooser = new DirectoryChooser();
		File file = dirChooser.showDialog(null);
		if (file != null) {
			String ImagePath = file.getAbsolutePath();
			textf_imagedir.setText(ImagePath);
		}
	}

	@FXML
	public void getindexdir(ActionEvent e) {
		DirectoryChooser dirChooser = new DirectoryChooser();
		File recordsDir = new File(System.getProperty("user.dir") + "//index");
		dirChooser.setInitialDirectory(recordsDir);
		File file = dirChooser.showDialog(null);
		if (file != null) {
			String queryImagePath = file.getAbsolutePath();
			textf_indexdir.setText(queryImagePath);
		}
	}

	public void index(ActionEvent e) throws Exception {
		String indexdir=textf_indexdir.getText();
		String imagedir=textf_imagedir.getText();
		if(!(imagedir.equals("")||indexdir.equals(""))) {
			
			LoadingView pForm = new LoadingView();	
			Task<Void> task = new Task<Void>() {
				@Override
				public Void call() throws InterruptedException, IOException {
					LIREapplication.index(indexdir, imagedir);	
					updateProgress(10, 10);
					return null;
				}
			};
			task.setOnSucceeded(event -> {			
				pForm.getDialogStage().close();
				try {
					Main.showErrorWindow("Index Successed!!");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Stage stage = (Stage) btn_index.getScene().getWindow();
				stage.close();
			});
			pForm.getDialogStage().show();
	
			Thread thread = new Thread(task);
			thread.start();
			
			
			
		}
	}

	public void clickcancel(ActionEvent e) {
		Stage stage = (Stage) btn_cancel.getScene().getWindow();
		stage.close();
	}
}
