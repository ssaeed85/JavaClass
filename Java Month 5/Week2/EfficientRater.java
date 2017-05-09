
/**
 * Write a description of EfficientRater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */import java.util.*;

public class EfficientRater implements Rater{
    private String myID;
    private HashMap<String,Rating> myRatings;
    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }
    public void addRating(String item, double rating) {
        myRatings.put(item,new Rating(item, rating));
    }
    public boolean hasRating(String item) {
        if(myRatings.containsKey(item))
            return true;
        return false;
    }
    public String getID() {
        return myID;
    }
    public double getRating(String item) {
        //Returns rating of the rater for a particular movie
        if (hasRating(item)){
            Rating r = myRatings.get(item);
            return r.getValue();
        }            
        return -1;
    }
    public int numRatings() {
        return myRatings.size();
    }
    public ArrayList<String> getItemsRated() {
        //Returns list of movies rated by Rater
        ArrayList<String> list = new ArrayList<String>();
        for(String key: myRatings.keySet()){
            list.add(myRatings.get(key).getItem());
        }
        return list;
    }
    public String toString(){
        //Lists all of the ratings by a rater
        String s = "Rater: " + myID;
        int i=0;
        for(String key: myRatings.keySet()){
            i++;
            Rating r = myRatings.get(key);
            s = s+ "\n" + r.toString();
        }
        return s;
    }
}