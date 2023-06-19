package model;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class Album  implements Serializable{
    private static final long serialVersionUID = 1L;
    
	// to add photo to an album, need to check if photo already exist in any other album by user
    // if it exist, add that photo to the current album
    // it it exist, in same album, operaton cannot be performed
    public List<Photo> photos;
    public User owner;
    public String name;
    public Album(User user,String name){
        this.owner = user;
        this.name = name;
        this.photos = new ArrayList<Photo>();
    }
    /**
     * to add photo to an album, need to check if photo already exist in any other album by user,
     * if it exist, add that photo to the current album,it it exist, in same album, operation cannot be performed, returns <code>false</code> on that case.
     * @param photo to add to the album
     * @return
     */
    public boolean addPhoto(Photo x){
    	if (this.name.equals("stock")) {
    		x.path = Paths.get(x.path).toAbsolutePath().toUri().toString();
    	}
        for (Album album: owner.albums){
            for (Photo p: album.photos){
            	if (album.equals(this) & p.equals(x)) {
            		return false;
            	}
                if (p.equals(x)){
                    //add already existing copy
                    photos.add(p);
                    return true;
                }
            }
        }
        photos.add(x);
        return true;
    }
    /**
     * delete photo from album
     * @param photo to delete
     * @return
     */
    public boolean deletePhoto(Photo p){
        photos.remove(p);
        return true;
    }

   
    /**
     *  search photo in album
     * @param photo to search
     * @return
     */
    public boolean exist(Photo x){
        for(Photo p: photos){
            if (p.equals(x))
            return true;
        }
        return false;
    }
	@Override
	public String toString() {
		return name;
	}
   
}
