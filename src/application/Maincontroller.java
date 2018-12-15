package application;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class Maincontroller {
	@FXML
	private Button btn_pageleft;
	@FXML
	private Button btn_pageright;
	@FXML
	ImageView searchImage = new ImageView();
	@FXML
	ImageView BigImage = new ImageView();

	ImageView[] searchResult = new ImageView[10];
	Pane[] pane_searchResult = new Pane[10];
	@FXML
	HBox hbox_result = new HBox();
	@FXML
	private ComboBox<String> cbox_pagenum;

	Main main;
	private String imagedir;
	private String indexdir;
	private String searchnum;
	private ArrayList<String> search_result = new ArrayList<String>();
	private int page;

	private String[] filename = new String[10];

	@FXML
	public void initialize() throws IOException {
		for (int i = 0; i < 10; i++) {
			searchResult[i] = new ImageView();
			searchResult[i].setFitHeight(250);
			searchResult[i].setFitWidth(250);
			searchResult[i].setLayoutX(5);
			searchResult[i].setLayoutY(5);

			pane_searchResult[i] = new Pane();
			pane_searchResult[i].setMinHeight(260);
			pane_searchResult[i].setMinWidth(260);
			pane_searchResult[i].getStyleClass().add("result-image-view");
			pane_searchResult[i].getChildren().add(searchResult[i]);
			
			final int k=i;
			searchResult[i].setOnMouseClicked(event -> {		
				
				try {
					BufferedImage img =cutImage.scaleImage_pane(500,500,filename[k]);				
					Image image = SwingFXUtils.toFXImage(img, null);
					BigImage.setImage(image);					 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			    	
		         event.consume();
	        });
		}
		;
		btn_pageleft.setDisable(true);
		btn_pageright.setDisable(true);

	}

	public void openArgWindow(ActionEvent e) throws Exception {
		argView a = new argView();
		if (a.getimagedir() != null && !a.getimagedir().equals("")) {
			Image image = SwingFXUtils.toFXImage(cutImage.scaleImage_pane(300, 300, a.getimagedir()), null);
			searchImage.setImage(image);
			imagedir = a.getimagedir();
		}
		if (a.getindexdir() != null && !a.getindexdir().equals("")) {
			indexdir = a.getindexdir();
		}
		if (a.getsearchnum() != null && !a.getsearchnum().equals("")) {
			searchnum = a.getsearchnum();
		}
	}

	public void search(ActionEvent e) throws Exception {
		if (indexdir != null) {
			LoadingView pForm = new LoadingView();

			// In real life this task would do something useful and return
			// some meaningful result:
			Task<ArrayList> task = new Task<ArrayList>() {
				@Override
				public ArrayList call() throws InterruptedException, IOException {
					ArrayList<String> al = LIREapplication.search(imagedir, indexdir, searchnum);
					
					for (int i = 0; i < 10; i++) {
					Image image = SwingFXUtils.toFXImage(cutImage.scaleImage_pane(250, 250, al.get(i)),null);
					searchResult[i].setImage(image);
					}
					updateProgress(10, 10);
					return al;
				}
			};
			task.setOnSucceeded(event -> {
				search_result.clear();
				search_result = task.getValue();

				hbox_result.getChildren().clear();
				for (int i = 0; i < 10; i++) {
					hbox_result.getChildren().add(pane_searchResult[i]);
				}

				btn_pageleft.setDisable(true);
				btn_pageright.setDisable(false);

				int maxpage;
				if (search_result.size() % 10 == 0)
					maxpage = (search_result.size() / 10);
				else
					maxpage = (search_result.size() / 10 + 1);
				cbox_pagenum.getItems().clear();
				for (int i = 1; i <= maxpage; i++) {
					cbox_pagenum.getItems().add(String.valueOf(i));
				}
				cbox_pagenum.getSelectionModel().selectFirst();

				pForm.getDialogStage().close();
			});
			pForm.getDialogStage().show();

			Thread thread = new Thread(task);
			thread.start();
		} else {
			Main.showErrorWindow("No index exist");
		}
	}

	public void index(ActionEvent e) throws IOException {
		Main.indexwindow();
	}	
	// control the view of search result
	public void viewleft(ActionEvent e) throws IOException {
		int maxpage;
		if (search_result.size() % 10 == 0)
			maxpage = (search_result.size() / 10);
		else
			maxpage = (search_result.size() / 10 + 1);

		page--;

		if (page == 0) {
			btn_pageleft.setDisable(true);
		} else {
			btn_pageleft.setDisable(false);
		}

		if (page == maxpage) {
			btn_pageright.setDisable(true);
		} else {
			btn_pageright.setDisable(false);
		}

		cbox_pagenum.getSelectionModel().select(page);
		openallimage(search_result, page);
	}

	public void viewright(ActionEvent e) throws IOException {
		int maxpage;
		if (search_result.size() % 10 == 0)
			maxpage = (search_result.size() / 10 - 1);
		else
			maxpage = (search_result.size() / 10);
		page++;
		if (page == 0) {
			btn_pageleft.setDisable(true);
		} else {
			btn_pageleft.setDisable(false);
		}

		if (page == maxpage) {
			btn_pageright.setDisable(true);
		} else {
			btn_pageright.setDisable(false);
		}

		cbox_pagenum.getSelectionModel().select(page);
		openallimage(search_result, page);
	}
	
	public void printpage(ActionEvent e) throws IOException {
		int select;
		if (cbox_pagenum.getValue() != null) {
			select = Integer.parseInt(cbox_pagenum.getValue()) - 1;
		} else {
			select = 0;
		}

		int maxpage;
		if (search_result.size() % 10 == 0)
			maxpage = (search_result.size() / 10);
		else
			maxpage = (search_result.size() / 10 + 1);

		if (select == 0) {
			btn_pageleft.setDisable(true);
		} else {
			btn_pageleft.setDisable(false);
		}

		if (select == maxpage - 1) {
			btn_pageright.setDisable(true);
		} else {
			btn_pageright.setDisable(false);
		}

		page = select;
		openallimage(search_result, page);
	}
	
	public void openallimage(ArrayList<String> imagename, int page) throws IOException {
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws IOException {
				for (int i = 0; i < 10; i++) {
					if (page * 10 + i < imagename.size()) {
						BufferedImage img = cutImage.scaleImage_pane(250, 250, imagename.get(page * 10 + i));
						Image image = SwingFXUtils.toFXImage(img, null);
						searchResult[i].setImage(image);
						filename[i] = imagename.get(page * 10 + i);
					} else {
		
						searchResult[i].setImage(null);
					}
				}
				return null;
			}
		};
		Thread thread = new Thread(task);
		thread.start();
	}
	
	
}