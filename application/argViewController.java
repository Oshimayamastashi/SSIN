package application;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class argViewController {
	private String imagedir;
	private String indexdir;
	private String searchnum;

	@FXML
	public Button btn_ok = new Button();
	@FXML
	public Button btn_cancel = new Button();
	@FXML
	public TextField textf_imagedir = new TextField();
	@FXML
	public TextField textf_indexdir = new TextField();
	@FXML
	public TextField textf_searchnum = new TextField();

	@FXML
	public void initialize() {
		textf_searchnum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textf_searchnum.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	@FXML
	public void getimagedir(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(null);
		if (file != null) {
			String queryImagePath = file.getAbsolutePath();
			textf_imagedir.setText(queryImagePath);
		}
	}

	@FXML
	public void getindexdir(ActionEvent e) {
		DirectoryChooser dirChooser = new DirectoryChooser();		
		File recordsDir = new File(System.getProperty("user.dir"));
		dirChooser.setInitialDirectory(recordsDir);
		File file = dirChooser.showDialog(null);
		if (file != null) {
			String queryImagePath = file.getAbsolutePath();
			textf_indexdir.setText(queryImagePath);
		}
	}

	public void clickok(ActionEvent e) {
		imagedir = textf_imagedir.getText();
		indexdir = textf_indexdir.getText();
		searchnum = textf_searchnum.getText();

		Stage stage = (Stage) btn_ok.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void clickcancel(ActionEvent e) {
		Stage stage = (Stage) btn_cancel.getScene().getWindow();
		stage.close();
	}

	public String getimagedir() {
		return imagedir;
	}

	public String getindexdir() {
		return indexdir;
	}
	public String getsearchnum() {
		return searchnum;
	}
	
}