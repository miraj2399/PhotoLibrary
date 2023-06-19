package model;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author Miraj Hossain and Stephen French
 */
public class StockGenerator {
	public static final String STOCK_DATA_LOC = "data"+File.separator+"stock";
	/**
	 * The constructor creates stock uer
	 * @throws IOException
	 */
	public StockGenerator() throws IOException {
		UserManager manager = new UserManager();
		String name = "stock";
		if (manager.writeUser(name)) {
			manager.save();
		}
		else {
			
		}
		User u = manager.readUser("stock");
		Album a = new Album(u,"stock");
		File stock_dir = new File(STOCK_DATA_LOC);
		File[] stock_files = stock_dir.listFiles();
		for (File f: stock_files) {
			Photo p = new Photo(f.getPath());
			a.addPhoto(p);
		}
		u.addAlbum(a);
		manager.updateUser(u);
		manager.save();
	}
}
