package application;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;

public class loadingcontroller {
	@FXML
	ProgressBar pb = new ProgressBar();
	
	@FXML
	public void initialize() {
		 pb.setProgress(ProgressBar.INDETERMINATE_PROGRESS);		
	}
}
