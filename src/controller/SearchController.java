package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import model.Album;
import model.AppState;
import model.Photo;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class SearchController implements Initializable{
	@FXML
	Button button_back;
	@FXML
	DatePicker datepicker_from;
	@FXML
	DatePicker datepicker_to;
	@FXML
	Button button_search_date;
	@FXML
	Button button_search_tag;
	@FXML
	TextField textfield_tag1;
	@FXML
	TextField textfield_value1;
	@FXML
	TextField textfield_tag2;
	@FXML
	TextField textfield_value2;
	@FXML 
	public TextField textfield_option;
	
	/**
	 * Upon clicking on this button, user return to album scene
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_back() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/albums.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	/**
	 * Upon clicking on this button,search in date range performed
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_search_date() throws IOException {
		LocalDate start = datepicker_from.getValue();
		LocalDate end = datepicker_to.getValue();
		if (start== null|| end==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty Date!");
			alert.showAndWait();
		}
		if(!start.equals(end)&&start.isAfter(end)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Start Date must be less than End date");
			alert.showAndWait();
		}
		else {
			User u = AppState.getUser();
			ArrayList<Photo> list= new ArrayList<Photo>();
			for (Album album: u.albums) {
				for(Photo photo:album.photos) {
					LocalDate photoDate = photo.dateAdded.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				if (!(photoDate.isAfter(end)|| photoDate.isBefore(start)) && !list.contains(photo)){
						list.add(photo);
					}
				}
			}
			AppState.setSearchResult(list);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/searchResult.fxml"));
			AnchorPane mainWindow = (AnchorPane)loader.load();
			Scene scene1 = new Scene(mainWindow);
			AppState.getStage().setScene(scene1);
		}
		
	}
	/**
	 * Upon clicking on this button, search based on tag parameter performed
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_search_tag() throws IOException {
		String junction = "";
		junction+=textfield_option.getText();
		ArrayList<Photo> list = new ArrayList<Photo>();
		for(Album album: AppState.getUser().albums)
			for(Photo p: album.photos) {
				String tag1 = textfield_tag1.getText();
				String tag2 = textfield_tag2.getText();
				String value1 = textfield_value1.getText();
				String value2 = textfield_value2.getText();
				if (junction.isEmpty()||junction.equals("OR")) {
					if (!list.contains(p) &&(p.haveTag(tag1)&&p.tagDictionary.get(tag1).contains(value1)) || (p.haveTag(tag2)&&p.tagDictionary.get(tag2).contains(value2))) {
						list.add(p);
					}
				}
				else if  (junction.equals("AND")) {
					if (!list.contains(p) &&(p.haveTag(tag1)&&p.tagDictionary.get(tag1).contains(value1)) && (p.haveTag(tag2)&&p.tagDictionary.get(tag2).contains(value2))) {
						list.add(p);
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("INVALID CONJUNCTION");
					alert.showAndWait();
					return;
				}
		}
		AppState.setSearchResult(list);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/searchResult.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		Scene scene1 = new Scene(mainWindow);
		AppState.getStage().setScene(scene1);
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
