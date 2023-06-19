
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.*;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class AlbumController implements Initializable{
	@FXML
	ListView<Album> album_list;
	@FXML
	AnchorPane albums;
	@FXML
	Button album_add;
	@FXML
	Button album_delete;
	@FXML
	Button logout;
	@FXML
	TilePane tile_photos;
	@FXML
	Button button_slideshow;
	
	@FXML
	Button button_selectPhotos;
	
	@FXML
	Button button_addPhotos;
	
	@FXML
	Button button_paste;
	@FXML
	Button button_search;
	
	
	public HashMap<Album,ArrayList<Photo>> cache;
	/**
	 * Reading photos again and again from albums reduce performance. This function tried to use already accessed images from cache
	 * @param Album for which gallery view will implement
	 * @return if the operation is successful
	 */
	public boolean setTilePhotosFromCache(Album album) {
		if (cache.containsKey(album)) {
			tile_photos.getChildren().clear();
			ArrayList<Photo> list = cache.get(album);
			for(Photo p: list) {
				ImageView img = new ImageView(p.path);
				img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				     @Override
				     public void handle(MouseEvent event) {
				    	 FXMLLoader loader = new FXMLLoader();
				    	 AppState.setPhotoClicked(p);
				 		loader.setLocation(getClass().getResource("/view/photo.fxml"));
				 		Scene scene1 = null;
						try {
							scene1 = new Scene(loader.load());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 		AppState.getStage().setScene(scene1);
				         event.consume();
				     }
				});
				img.setFitHeight(100);
				img.setFitWidth(100);
				tile_photos.getChildren().add(img);
				
			}
			return true;
		}
		else return false;
		
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
		cache = new HashMap<Album,ArrayList<Photo>>();
		album_delete.disableProperty().bind(
			    Bindings.isNull(album_list.getSelectionModel().selectedItemProperty())
			);
		User u = (User) AppState.getStage().getUserData();
		ObservableList <Album> albums = FXCollections.observableList(u.albums);
		album_list.setItems(albums);
		tile_photos.setHgap(4);
    	tile_photos.setVgap(4);
		album_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null) { 
	        	AppState.setAlbumClicked(newValue);
	        	tile_photos.getChildren().clear();
	        	boolean cached = setTilePhotosFromCache(newValue);
	        	if (!cached) {
	        		ArrayList<Photo> list = new ArrayList<>();
	        		for (Photo p: newValue.photos) {
	        			list.add(p);
	        		}
	        		cache.put(newValue, list);
	        		setTilePhotosFromCache(newValue);
	        	}
	        }
	    
	    });
		if  (AppState.getAlbumClicked()!=null & albums.contains(AppState.getAlbumClicked())) {
			album_list.getSelectionModel().select(AppState.getAlbumClicked());
		}
		else if (albums.size()!=0) {
			album_list.getSelectionModel().select(0);
			AppState.setAlbumClicked(albums.get(0));
		}
	}
	/**
	 * Upon clicking on slide show button, slide show begins
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_slideshow(ActionEvent event) throws IOException {
		if (AppState.getAlbumClicked()==null) return;
		if (AppState.getAlbumClicked().photos.isEmpty()) return;
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/slideShow.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on this button, Scene for adding Album initiates
	 * @param event
	 * @throws IOException
	 */
	public void handle_album_add(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/addAlbum.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on delete button, album get deleted
	 * @param event
	 * @throws IOException
	 */
	public void handle_album_delete(ActionEvent event) throws IOException {
		User u = (User) AppState.getStage().getUserData();
		Album album = album_list.getSelectionModel().getSelectedItem();
		u.deleteAlbum(album);
		album_list.getItems().remove(album);
		UserManager manager = new UserManager();
		manager.updateUser(u);
		System.out.println(u);
		manager.save();
		if(AppState.getUser().albums.isEmpty()) {
			AppState.albumClicked=null;
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.setAlbumListScene(scene1);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on this button, User get logged out
	 * @param event
	 * @throws IOException
	 */
	public void handle_logout(ActionEvent event) throws IOException {
		Stage currentStage = AppState.getStage();
		FXMLLoader loader = new FXMLLoader();
		AppState.reset();
		AppState.setStage(currentStage);
		loader.setLocation(getClass().getResource("/view/start.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		currentStage.setScene(scene1);
		
	}
	
	/**
	 * Upon clicking on this button, a Scene for select photos initiated
	 * @param event
	 * @throws IOException
	 */
	
	public void handle_button_selectPhotos(ActionEvent event) throws IOException {
		if (AppState.getAlbumClicked()==null) return;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selectPhoto.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	
	/**
	 * Upon clicking on this button, file chooser opens and user can choose 1 or multiple files
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_addPhotos(ActionEvent event) throws IOException {
		if (AppState.getAlbumClicked()==null) return;
		FileChooser fileChooser = new FileChooser();
   	 fileChooser.setTitle("Open Resource File");
   	 fileChooser.getExtensionFilters().addAll(
   	         new ExtensionFilter("Image Files","*.bmp","*.png", "*.jpg", "*.gif"));
   	 List<File> selectedFiles = fileChooser.showOpenMultipleDialog(AppState.getStage());
   	 if (selectedFiles!=null) {
	    	 for(File f: selectedFiles){
	    		 Photo p = new Photo(f.getPath());
	    		 AppState.getAlbumClicked().addPhoto(p);
	    	 }
	    	 UserManager manager = new UserManager();
	    	 manager.updateUser(AppState.getUser());
	    	 this.initialize(null, null);
   	 }
	}
	/**
	 * Upon clicking on this button, if clip board is not empty, photo get pasted on the selected album
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_paste() throws IOException {
		if (AppState.getAlbumClicked()==null) return;
		ArrayList<Photo> clipboard = AppState.getClipBoard();
		if (clipboard.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Clipboard!");
			alert.setContentText("Cut or Copy Photos after Seleting Photos");
			alert.showAndWait();
		}
		else {
			UserManager manager = new UserManager();
			for(Photo p:clipboard) {
				AppState.getAlbumClicked().addPhoto(p);
			}
			manager.updateUser(AppState.getUser());
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
			AnchorPane mainWindow = (AnchorPane)loader.load();
			Scene scene1 = new Scene(mainWindow);
			AppState.getStage().setScene(scene1);
			
		}
		
	}
	/**
	 * Upon clicking on this button, search scene initiated
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_search() throws IOException {
		if (AppState.getAlbumClicked()==null) return;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/search.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}

}
