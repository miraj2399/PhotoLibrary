package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
public class AddAlbumController implements Initializable {
	@FXML
	Button button_back;
	@FXML
	TextField textfield_albumName;
	@FXML
	Button button_save;
	@FXML
	Button selectFromComputer;
	
	/**
	 * Upon clicking on this button, user return to album scene
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_back(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.setAlbumListScene(scene1);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on this button, new album created
	 * @param event
	 * @throws IOException
	 */
	
	public void handle_button_save(ActionEvent event) throws IOException {
		String albumName = textfield_albumName.getText();
		User u = (User) AppState.getStage().getUserData();
		Album album = new Album(u,albumName);
		u.addAlbum(album);
		AppState.setAlbumClicked(album);
		UserManager manager = new UserManager();
		manager.updateUser(u);
		manager.save();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		selectFromComputer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

		     @Override
		     public void handle(MouseEvent event) {
		    	 User u = (User) AppState.getStage().getUserData();
		    	 String albumName =textfield_albumName.getText();
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
		    	 FileChooser fileChooser = new FileChooser();
		    	 fileChooser.setTitle("Open Resource File");
		    	 fileChooser.getExtensionFilters().addAll(
		    	         new ExtensionFilter("Image Files","*.bmp","*.png", "*.jpg", "*.gif"));
		    	 List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppState.getStage());
		    	 if (selectedFiles!=null) {
			    	 for(File f: selectedFiles){
			    		 Photo p = new Photo(f.toURI().toString());
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
				AnchorPane mainWindow = null;
				try {
					mainWindow = (AnchorPane)loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Scene scene1 = new Scene(mainWindow);
				AppState.getStage().setScene(scene1);
		         event.consume();
		     }
		});
		
	}
	
	

}
