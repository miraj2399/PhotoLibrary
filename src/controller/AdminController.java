package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.*;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */

public class AdminController implements Initializable {
	
	@FXML
	ListView<String> listview_users;
	@FXML
	Button button_add;
	@FXML
	Button button_delete;
	@FXML
	TextField username;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		button_delete.disableProperty().bind(
			    Bindings.isNull(listview_users.getSelectionModel().selectedItemProperty())
			);
		
		UserManager manager = null;
		try {
			manager = new UserManager();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<String> users = new ArrayList<>();
		users.addAll(manager.userNameIdMap.keySet());
		ObservableList <String> userList = FXCollections.observableList(users);
		listview_users.setItems(userList);
		
	}
	
	/**
	 * Upon clicking on this button, new user added
	 * @param event
	 * @throws IOException
	 */
	public void handle_add(ActionEvent event) throws IOException {
		UserManager manager = new UserManager();
		String name = username.getText();
		if (manager.writeUser(name)) {
			manager.save();
			listview_users.getItems().add(name);
			username.setText("");
		}
		else {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Cannot add user");
			error.show();
		}
	}
	
	/**
	 * Upon clicking on this button, selected user deleted
	 * @param event
	 * @throws IOException
	 */
	public void handle_delete(ActionEvent event) throws IOException {
		UserManager manager = new UserManager();
		int idx = listview_users.getSelectionModel().getSelectedIndex();
		manager.deleteUser(listview_users.getSelectionModel().getSelectedItem());
		manager.save();
		listview_users.getItems().remove(idx);
		
		
	}
	
	/**
	 * Upon clicking on this button, admin logged out
	 * @param event
	 * @throws IOException
	 */
	public void handle_logout(ActionEvent event) throws IOException {
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/start.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		stage.setScene(scene1);
	}
	

}
