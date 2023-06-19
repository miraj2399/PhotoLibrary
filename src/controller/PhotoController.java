package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.AppState;
import model.Photo;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class PhotoController implements Initializable{
	
	
	
	@FXML
	ImageView imageview_img;
	@FXML
	Button button_back;
	@FXML
	Button button_editPhoto;
	@FXML
	Text text_caption;
	@FXML
	Text text_capturedAt;
	@FXML
	Text text_tags;
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		String tags = "";
		Photo p = AppState.getPhotoClicked();
		
		
		
		for (String[] entry: p.getTagValuePairs()) {
			String oneTag = entry[0]+":"+entry[1]+"; ";
			tags+=oneTag;
			
		}
		imageview_img.setImage(new Image(p.path));
		text_caption.setText(p.caption);
		text_capturedAt.setText(p.dateAdded.toString());
		text_tags.setText(tags);
		
	}
	/**
	 * Upon clicking on this button, user return to album scene
	 * @throws IOException
	 */
	public void handle_button_back(ActionEvent event) throws IOException {
		AppState.getStage().setScene(AppState.getAlbumListScene());
	}
	/**
	 * Upon clicking on this button, scene to edit photo initiated
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_editPhoto(ActionEvent event) throws IOException {
		AppState.setLastScene(button_editPhoto.getScene());
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/editPhoto.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		AppState.getStage().setScene(new Scene(mainWindow));
	}
	

}
