package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.AppState;
import model.Photo;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class SlideShowController implements Initializable{
	
	@FXML
	ImageView imageView_current;
	@FXML
	Button button_back;
	@FXML
	Button button_prev;
	@FXML
	Button button_next;
	@FXML 
	Label label_status;
	int currentIndex = 0;
	List<Photo> photos;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.photos = AppState.getAlbumClicked().photos;
		int numOfPhotos = photos.size();
		label_status.setText("1"+"/"+numOfPhotos);
		Image firstImage = new Image(photos.get(0).path);
		imageView_current.setImage(firstImage);
	}
	/**
	 * Upon clicking on this button, user return to album scene
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
		
	}
	/**
	 * Upon clicking on this button, user go to previous photo on slide show
	 * @param event
	 * @throws IOException
	 */
    public void handle_button_prev(ActionEvent event) {
    	this.currentIndex -=1;
    	if(currentIndex<0) currentIndex = photos.size()-1;
    	imageView_current.setImage(new Image(photos.get(currentIndex).path));
    	label_status.setText((currentIndex+1)+"/"+photos.size());
    }
    /**
	 * Upon clicking on this button, user go to next photo on slide show
	 * @param event
	 * @throws IOException
	 */
    public void handle_button_next(ActionEvent event) {
    	this.currentIndex = (this.currentIndex+1)%photos.size();
    	imageView_current.setImage(new Image(photos.get(currentIndex).path));
    	label_status.setText((currentIndex+1)+"/"+photos.size());
}
	

}

