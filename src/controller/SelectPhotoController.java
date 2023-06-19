package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Album;
import model.AppState;
import model.Photo;
import model.UserManager;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class SelectPhotoController implements Initializable{

	@FXML
	Button button_back;
	@FXML
	Text text_album;
	@FXML
	Text text_selected;
	@FXML
	TilePane tile_photos;
	@FXML
	Button button_cut;
	@FXML
	Button button_copy;
	@FXML
	Button button_delete;
	@FXML
	Button button_cancel;
	HashSet<Photo> selected = new HashSet<>();
	HashSet<VBox> selectedContainers = new HashSet<>();
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		Album current = AppState.getAlbumClicked();
		text_album.setText(current.name);
		for(Photo p: current.photos) {
			VBox container = new VBox();
			ImageView img = new ImageView(p.path);
			img.setFitHeight(100);
			img.setFitWidth(100);
			Button status = new Button("select");
			container.getChildren().add(img);
			container.getChildren().add(status);
			
			status.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			     @Override
			     public void handle(MouseEvent event) {
			    	 if (status.getText().equals("select")) {
			    		 status.setText("unselect");
			    		 selected.add(p);
			    		 selectedContainers.add(container);
			    	 }
			    	 else {
			    		 status.setText("select");
			    		 selected.remove(p);
			    		 selectedContainers.remove(container);
			    	 }
			    	 text_selected.setText(selected.size()+" selected");
			     }
			});
			
			tile_photos.getChildren().add(container);
			
		}
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
	 * Upon clicking on this button, photos copied to clip board and deleted from the current album 
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_cut(ActionEvent event) throws IOException {
		AppState.setClipBoard(new ArrayList<Photo>(selected));
		handle_button_delete(event);
	}
	/**
	 * Upon clicking on this button, photos copied to clip board. Doesn't delete from source album
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_copy(ActionEvent event) {
		AppState.setClipBoard(new ArrayList<Photo>(selected));
	
	}
	/**
	 * Upon clicking on this button, selected photos get deleted
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_delete(ActionEvent event) throws IOException {
		UserManager manager = new UserManager();
		if (selected.isEmpty()) return;
		for(Photo p: selected) {
		AppState.getAlbumClicked().deletePhoto(p);
		}
		for(VBox h:selectedContainers) {
			tile_photos.getChildren().remove(h);
		}
		selected.clear();
		manager.updateUser(AppState.getUser());
		
		
	}
	/**
	 * Upon clicking on this button, clip board get empty and all photos get unselected
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_cancel(ActionEvent event) throws IOException {
		this.selected = new HashSet<Photo>();
		this.selectedContainers= new HashSet<VBox>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/selectPhoto.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
		
	}

}
