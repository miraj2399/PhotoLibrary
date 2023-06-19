package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
	public String username;
    public List<Album> albums;


    public User(String username){
        this.username = username;
        this.albums = new ArrayList<Album>();
    }
    /**
     * Add albums to user record
     * @param album
     * @return
     */
    public boolean addAlbum(Album album){
    	for (Album alb: this.albums) {
    		if(alb.name.equals(album.name)) {
    			return false;
    		}
    	}
        this.albums.add(album);
        return true;
    }
    /**
     * Delete album from user record
     * @param album
     * @return
     */

    public boolean deleteAlbum(Album album){
        this.albums.remove(album);
        return true;
    }
    

    @Override
    public String toString() {
        String res = "";
        res = res+username+"\n";
        for (Album a: this.albums){
            res+=a.name+":\n";
            for (Photo p: a.photos){
                res+=p;
            }
            res+="\n";
        }
        return res;
    }
}
