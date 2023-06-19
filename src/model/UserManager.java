package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = 1L;
	public Integer last_used_id;
    public HashMap<String,Integer> userNameIdMap;
    
    public static final String USER_DATA_LOC = "data"+File.separator;
    public static final String USER_LIST_LOC = "data"+File.separator+"users.ser";
    
    




    /**
     * try to retrieve info from user.ser
     * If no information found, creates a user.ser
     * user.ser have id for each user
     * that allows to store each user individually which makes program efficient as all user data doesn't need to be retrieved when a single user use the application
     * @throws IOException
     */
    public UserManager() throws IOException{
        FileInputStream fis;
        ObjectInputStream ois;
    
        
        //try to read from uses.ser
        try{
            fis = new FileInputStream(USER_LIST_LOC);
            ois = new ObjectInputStream(fis);
            UserManager usermanager = (UserManager) ois.readObject();
            this.last_used_id = usermanager.last_used_id;
            this.userNameIdMap = usermanager.userNameIdMap;
            fis.close();
            ois.close();
        }
        //else  initialize
        catch(Exception e){
            this.last_used_id=1;
            this.userNameIdMap = new HashMap<String,Integer>();
        }
    }


    /**
     * Save user details on local machine on .ser file
     * @param user
     * @return
     */
    public boolean writeUser(String user){
        try{

            //user already exist
            if (userNameIdMap.containsKey(user)){
                return false;
            }

            //add user to userlist
            else{
                userNameIdMap.put(user,last_used_id);
            }

            //create a individual .ser file for user
            FileOutputStream fos1 = new FileOutputStream(USER_DATA_LOC+File.separator+Integer.toString(last_used_id)+".ser");
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(new User(user));
            oos1.close();
            fos1.close();

            // increment the last_used_id
            this.last_used_id+=1;

            return true;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
    }
    /**
     * Retrieve previously saved user from local machine
     * @param username
     * @return
     */
    public User readUser(String username){
        try{

            // user not in userlist, cannot readUser
            if (!this.userNameIdMap.containsKey(username)){
                return null;
            }
            
            // get user id from userlist
            Integer id = this.userNameIdMap.get(username);

            //read from .ser file for individual user
            FileInputStream fis1 = new FileInputStream(USER_DATA_LOC+File.separator+Integer.toString(id)+".ser");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            User u = (User) ois1.readObject();
            ois1.close();
            fis1.close();

            //return user
            return u;
            
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    /**
     * if user details change, can update the changes on local machine using this function
     * @param u
     * @return
     */
    public boolean updateUser(User u) {
    	try {
    		if (!userNameIdMap.containsKey(u.username)){
                return false;
            }
    		
    		Integer id = this.userNameIdMap.get(u.username);

    		FileOutputStream fos1 = new FileOutputStream(USER_DATA_LOC+File.separator+Integer.toString(id)+".ser");
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(u);
            oos1.close();
            fos1.close();
    		return true;

    	}
    	catch(Exception e) {
    	return false;
    	}
    }
    /**
     * save current state of users
     * @return
     */
    public boolean save(){
        try{
            FileOutputStream fos;
            ObjectOutputStream oos;
            fos = new FileOutputStream(USER_LIST_LOC);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    /**
     * Delete user data from local machine
     * @param user
     * @return
     */
    public boolean deleteUser(String user) {
    	try {
    		if (!this.userNameIdMap.containsKey(user)){
                return false;
            }
            // get user id from userlist
            Integer id = this.userNameIdMap.get(user);
            this.userNameIdMap.remove(user);
            File userData = new File(USER_DATA_LOC+File.separator+Integer.toString(id)+".ser");
            if (userData.exists()){
            	if (userData.delete()) {
            		System.out.println("Deleted");
            	return true;
            	}
            }
            return false;

    	}
    	catch(Exception e) {
    		
    		return false;
    	}
    }
    


}
