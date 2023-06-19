package model;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class AppState {
	public static User  User;
	public static Stage stage;
	public static Scene albumListScene;
	public static Photo photoClicked;
	public static Album albumClicked;
	public static Scene lastScene;
	public static ArrayList<Photo> clipBoard = new ArrayList<Photo>();
	public static ArrayList<Photo> searchResult = new ArrayList<Photo>();
	
	
	/**
	 * Return current user
	 * @return
	 */
	public static User getUser() {
		return User;
	}
	/**
	 * Set currently logged in user. Any other classed can access current user from this class during application runtime
	 * @param user
	 */
	public static void setUser(User user) {
		User = user;
	}
	/**
	 * Return the Stage of the program
	 * @return
	 */
	public static Stage getStage() {
		return stage;
	}
	/**
	 * On start, set Stage. Any other classed can access the Stage from this class during application runtime
	 * @param stage
	 */
	public static void setStage(Stage stage) {
		AppState.stage = stage;
	}
	/**
	 * get album list scene
	 * @return
	 */
	public static Scene getAlbumListScene() {
		return albumListScene;
	}
	/**
	 * set albumListScene for future use in program
	 * @param albumListScene
	 */

	public static void setAlbumListScene(Scene albumListScene) {
		AppState.albumListScene = albumListScene;
	}
	/**
	 * get last clicked phto
	 * @return
	 */
	public static Photo getPhotoClicked() {
		return photoClicked;
	}
/**
 * Set last clicked photo for future use in application
 * @param photoClicked
 */
	public static void setPhotoClicked(Photo photoClicked) {
		AppState.photoClicked = photoClicked;
	}
/**
 * get last clicked album
 * @return
 */

	public static Album getAlbumClicked() {
		return albumClicked;
	}

	/**
	 * Get last clicked album
	 * @param albumClicked
	 */
	public static void setAlbumClicked(Album albumClicked) {
		AppState.albumClicked = albumClicked;
	}
	/**
	 * scene can be stored for future reference
	 * @return
	 */
	public static Scene getLastScene() {
		return lastScene;
	}
	/**
	 * set a scene for future reference
	 * @param scene
	 */
	public static void setLastScene(Scene scene) {
		lastScene = scene;
	}
/**
 * return photos on clip board
 * @return
 */
	public static ArrayList<Photo> getClipBoard() {
		return clipBoard;
	}
	/**
	 * Upon copy and cut, clipboard get updated via this function
	 * @param photos
	 */
	public static void setClipBoard(ArrayList<Photo> photos) {
		clipBoard = photos;
	}
	/**
	 * get result of a search
	 * @return
	 */
	public static ArrayList<Photo> getSearchResult() {
		return searchResult;
	}
	/**
	 * set result of a search
	 * @param photos
	 */
	public static void setSearchResult(ArrayList<Photo> photos) {
		searchResult = photos;
	}
	/**
	 * Reset the AppState
	 */
	public static void reset() {
		User = null;
		stage = null;
		albumListScene = null;
	}
}
