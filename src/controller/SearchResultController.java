package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import model.Album;
import model.AppState;
import model.Photo;
import model.User;
import model.UserManager;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class SearchResultController implements Initializable{
	@FXML
	Button button_back;
	@FXML
	Button button_save;
	@FXML
	TextField textfield_album;
	@FXML
	TilePane tile_photos;
	
	

	public void initialize(URL arg0, ResourceBundle arg1) {
		for (Photo p: AppState.getSearchResult()) {
			ImageView img = new ImageView(p.path);
			img.setFitHeight(100);
			img.setFitWidth(100);
			tile_photos.getChildren().add(img);
		}
	}
	/**
	 * Upon clicking on this button, new album created based on search result
	 * @param event
	 * @throws IOException
	 */
	public  void handle_button_save() throws IOException {
		 User u = (User) AppState.getStage().getUserData();
    	 String albumName =textfield_album.getText();
    	 if (albumName.isEmpty()) {
    		 Alert alert = new Alert(AlertType.ERROR);
    		 alert.setTitle("Empty album Name!");
    		 alert.show();
    		 return;
    	 }
    	 Album album = new Album(u, albumName);
    	 if(!u.addAlbum(album)) {
    		 Alert alert = new Alert(AlertType.ERROR);
    		 alert.setTitle("Album alread exist or invalid input");
    		 alert.show();
    		 return;
    	 }
    	 if (AppState.getSearchResult().isEmpty()) {
    		 Alert alert = new Alert(AlertType.ERROR);
    		 alert.setTitle("Cannot Create Album with empty Search Result!");
    		 alert.show();
    		 return;
    	 }
    	 
    	 if (!AppState.getSearchResult().isEmpty()) {
	    	 for(Photo p: AppState.getSearchResult()){
	    		 album.addPhoto(p);
	    	 }
    	 }
    	 UserManager manager = null;
		try {
			manager = new UserManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		manager.updateUser(u);
 		AppState.setAlbumClicked(album);
 		manager.save();
 		
 		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on this button, user go back to album scene
	 * @param event
	 * @throws IOException
	 */
	public  void  handle_button_back() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}

}
