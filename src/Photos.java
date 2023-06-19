/**
 * @author Miraj M Hossain and Steven A French
 * 
 */


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

public class Photos extends Application{
	
	public void start(Stage PrimaryStage) throws Exception{
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/start.fxml"));
		AnchorPane mainWindow = (AnchorPane)loader.load();
		AppState.setStage(PrimaryStage);
		Scene scene1 = new Scene(mainWindow);
		PrimaryStage.setScene(scene1);
		PrimaryStage.setTitle("PHOTOS");
		PrimaryStage.setWidth(1200);
		PrimaryStage.setHeight(800);
		PrimaryStage.show();
		
		
		PrimaryStage.setOnCloseRequest(e -> {
			
			

		});
		
		
	}
	public static void main(String[] args) throws IOException {
		StockGenerator g = new StockGenerator();
		launch(args);
	}
}
