package controller;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.AppState;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class Test1Controller implements Initializable{
	@FXML
	TilePane tileview_gallery;
	@FXML
	Button button_add;

	public void initialize(URL arg0, ResourceBundle arg1) {
		File file = new File("/Volumes/Coding/cs_213/Photos79/src/view/img_background_01.jpg");
		for(int i = 0;i<3;i++) {
			ImageView img = new ImageView(file.toURI().toString());
			img.setFitHeight(100);
			img.setFitWidth(100);
			img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			     @Override
			     public void handle(MouseEvent event) {
			         System.out.println("Tile pressed ");
			         event.consume();
			     }
			});
			tileview_gallery.getChildren().add(img);
			
		}
		
		
		button_add.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 FileChooser fileChooser = new FileChooser();
		    	 fileChooser.setTitle("Open Resource File");
		    	 fileChooser.getExtensionFilters().addAll(
		    	         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
		    	         new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"),
		    	         new ExtensionFilter("All Files", "*.*"));
		    	 List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppState.getStage());
		    	 if (selectedFiles!=null) {
			    	 for(File f: selectedFiles){
			    		 System.out.println(f.getAbsolutePath());
			    	 }
		    	 }
		         event.consume();
		     }
		});
	}
	

}
