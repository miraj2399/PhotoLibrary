package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import model.AppState;
import model.Photo;
import model.UserManager;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class EditPhotoController implements Initializable{
	
	public String tempCaption;
	public ArrayList<String[]> tempTagPairs;
	
	
	@FXML
	TextField textfield_caption;
	
	@FXML
	TextField textfield_key;
	
	@FXML
	TextField textfield_value;
	
	@FXML
	FlowPane buttonbar_tags;
	
	@FXML
	Button button_cancel;
	
	@FXML
	Button button_save;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		Photo p = AppState.getPhotoClicked();
		this.tempCaption = p.caption;
		this.tempTagPairs = p.getTagValuePairs();
		textfield_caption.setText(tempCaption);
		for (String[] entry:tempTagPairs) {
			String button_text = "[x] "+entry[0]+":"+entry[1];
			Button b = new Button(button_text);
			b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			     @Override
			     public void handle(MouseEvent event) {
			    	 tempTagPairs.remove(entry);
			    	 buttonbar_tags.getChildren().remove(b);
			     }
			});
			buttonbar_tags.getChildren().add(b);
		}
	}
	/**
	 * Upon clicking on this button, a temporary tag created and displayed
	 * and user can delete the tag value pair clicking on x sign beside the tag pair string
	 * @param event
	 * @throws IOException
	 */
	public void handle_textfield_value(ActionEvent event) throws IOException {
		String tagKey = textfield_key.getText();
		String tagValue = textfield_value.getText();
		if (!tagKey.isEmpty() && !tagValue.isEmpty()) {
			for (String[] entry: tempTagPairs) {
				//tag pair already exist
				if (entry[0].equals(tagKey) & entry[1].equals(tagValue)) {
					return;
					}
				}
			String[] entry = new String[2];
			entry[0]=tagKey;
			entry[1] =tagValue;
			this.tempTagPairs.add(entry);
			String button_text = "[x] "+tagKey+":"+tagValue;
			Button b = new Button(button_text);
			b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			     @Override
			     public void handle(MouseEvent event) {
			    	 tempTagPairs.remove(entry);
			    	 buttonbar_tags.getChildren().remove(b);
			     }
			});
			buttonbar_tags.getChildren().add(b);
			
			
			
		
		/**
		String tagKey = textfield_key.getText();
		String tagValue = textfield_value.getText();
		UserManager manager = new UserManager();
		if (!tagKey.isEmpty() && !tagValue.isEmpty()) {
			Photo p = AppState.getPhotoClicked();
			ArrayList<String> tagValues = new ArrayList<String>();
			tagValues.add(tagValue);
			if (p.haveTag(tagKey)) {
				p.addTagValues(tagKey, tagValues);
			}
			else {
				p.addTag(tagKey, tagValues);
			}
			manager.updateUser(AppState.getUser());
			*/
			
		}
		
	}
	
	/**
	 * Upon clicking on this button, user return to photo details
	 * @param event
	 * @throws IOException
	 */
	public void get_back_to_photo() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/photo.fxml"));
		AnchorPane window = loader.load();
		AppState.getStage().setScene(new Scene(window));
	}
	/**
	 * calls <code>get_back_to_photo</code> function
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_cancel(ActionEvent event) throws IOException {
		get_back_to_photo();
	}
	
	/**
	 * Upon clicking on this button, change on photo details saved
	 * @param event
	 * @throws IOException
	 */
	public void handle_button_save(ActionEvent event) throws IOException{
		//do stuff
		
		UserManager manager = new UserManager();
		tempCaption = textfield_caption.getText();
		Photo p = AppState.getPhotoClicked();
		p.caption = tempCaption;
		p.clearTagDictionary();
		for (String[] entry: this.tempTagPairs) {
			ArrayList<String> tagValues = new ArrayList<String>();
			tagValues.add(entry[1]);
			if (p.haveTag(entry[0])){
				p.addTagValues(entry[0], tagValues);
			}
			else {
				p.addTag(entry[0], tagValues);
			}
		}
		manager.updateUser(AppState.getUser());
		
		get_back_to_photo();
		
	}
	
	

}
