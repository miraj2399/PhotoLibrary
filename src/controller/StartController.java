package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.*;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class StartController implements Initializable {
	@FXML
	public TextField username;
	@FXML
	public Button login;
	@FXML
	public AnchorPane startWindow;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	/**
	 * Upon clicking on this button, user logged in if verification completes
	 * @param event
	 * @throws IOException
	 */
	public void handle_login(ActionEvent event) throws IOException {
		String user = username.getText();
		UserManager manager = new UserManager();
		User u = manager.readUser(user);
		//handle Admin
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		if (user.equals("admin")){
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/admin.fxml"));
			AnchorPane mainWindow = (AnchorPane)loader.load();
			Scene scene1 = new Scene(mainWindow);
			stage.setScene(scene1);
			
		}
		
		else if (!Objects.isNull(u)) {
			stage.setUserData(u);
			AppState.setUser(u);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
			AnchorPane mainWindow = (AnchorPane)loader.load();
			Scene scene1 = new Scene(mainWindow);
			AppState.setAlbumListScene(scene1);
			stage.setScene(scene1);
		}
		else {
			Alert fail = new Alert(AlertType.ERROR);
			fail.setTitle("User "+user+" doesn't exist in system!" );
			fail.show();
		}
		
		
				
	}

}
