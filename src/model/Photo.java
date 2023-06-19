package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 
 * @author Miraj Hossain and Steven "Drew" French
 *
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;
	public String path;
    public String caption;
    public Map<String,ArrayList<String>> tagDictionary;
    public Date dateAdded;

    public Photo(String path){
    	//this.path = Paths.get(path).toAbsolutePath().toUri().toString();
    	this.path = path;
        this.caption = "";

        this.tagDictionary = new HashMap<String,ArrayList<String>>();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.MILLISECOND,0);
        this.dateAdded = today.getTime();
        
    }

    public Photo(String path,String caption){
        this(path);
        this.caption = caption;
    }

    
    /**
     * * first check if tagKey already exists for the photo by using haveTag
     * if the tag doesn't exist, use addTag() to create new tag and add values to it
     * if tag exist, use addTagValues() to push new tagValues to tag, in case duplicate tagValues inserted, the function take
     * union of existing tag values and the parameter pushed, thus, duplicate is avoided by the function and no error message given
     * to delete tagValues, use deleteTagValues(), if the tag doesn't exist, the function ignores that and delete the tag values that exists
     * After deleting the tagValues, if the tagKey become empty, deleteTagValues delete tagKey from tagDictionary
     * @param tagKey
     * @return
     */
    public boolean haveTag(String tagKey){
        return tagDictionary.containsKey(tagKey);
    }
    /**
     * add tag values for a tag key, if tag key doesn't exist
     * @param tagKey
     * @param tagValues
     * @return
     */
    public boolean addTag(String tagKey, ArrayList<String>tagValues){
        try{
            if (!tagDictionary.containsKey(tagKey)) tagDictionary.put(tagKey,tagValues);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

/**
 * If tag key exist, add tag values to that tag key
 * @param tagKey
 * @param tagValues
 * @return
 */
    public boolean addTagValues(String tagKey, ArrayList<String>tagValues){
        try{
            if (this.haveTag(tagKey)){
                HashSet<String> set = new HashSet<>(); 
                set.addAll(this.tagDictionary.get(tagKey));
                set.addAll(tagValues);
                ArrayList<String> union = new ArrayList<>();
                union.addAll(set);
                this.tagDictionary.put(tagKey,union);
                return true;
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }
    /**
     * delete tag values, doesn't return error if tag values to be deleted doesn't exist on values
     * @param tagKey
     * @param deleteValues
     * @return
     */
    public boolean deleteTagValues(String tagKey,ArrayList<String>deleteValues){
        try{
            if (this.haveTag(tagKey)){
                HashSet<String> set = new HashSet<>(); 
                set.addAll(this.tagDictionary.get(tagKey));
                set.removeAll(deleteValues);
                ArrayList<String> union = new ArrayList<>();
                union.addAll(set);
                if (union.isEmpty()) {
                	tagDictionary.remove(tagKey);
                }
                this.tagDictionary.put(tagKey,union);
                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
    
    /**
     * deconstruct list to tag value pairs
     * @return
     */
    public ArrayList<String[]> getTagValuePairs(){
    	ArrayList<String[]> res = new ArrayList<String[]>();
    	for (String tagKey:tagDictionary.keySet()) {
    		for (String tagValue:tagDictionary.get(tagKey)){
    			String[] entry = new String[2];
    			entry[0] = tagKey;
    			entry[1] = tagValue;
    			res.add(entry);
    	}
    	}
    	return res;
    }
    /***
     * compare photo using path value
     * @param other
     * @return
     */
    public boolean equals(Photo other){
        return other.path.equals(this.path);
    }
    /**
     * Clear tag dictionary
     * @return
     */
    public boolean clearTagDictionary() {
    	this.tagDictionary = new HashMap<String,ArrayList<String>>();
    	return true;
    }

	@Override
	public String toString() {
		return "Photo [path=" + path + ", caption=" + caption + ", tagDictionary=" + tagDictionary + ", dateAdded="
				+ dateAdded + "]";
	}
}
