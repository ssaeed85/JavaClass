
/**
 * Write a description of class Rater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Rater {
    private String myID;
    private ArrayList<Rating> myRatings;

    public Rater(String id) {
        myID = id;
        myRatings = new ArrayList<Rating>();
    }
    public void addRating(String item, double rating) {
        myRatings.add(new Rating(item,rating));
    }
    public boolean hasRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return true; 
            }
        }        
        return false;
    }
    public String getID() {
        return myID;
    }
    public double getRating(String item) {
        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        return -1;
    }
    public int numRatings() {
        return myRatings.size();
    }
    public boolean equals(Rater r){
        boolean flag = false;   //true means equal IDs
        if(myID.equals(r.getID()))
            flag = true;
        return flag;
    }
    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        return list;
    }
    public String toString(){
        String s = "Rater: " + myID;
        int i=0;
        for(Rating r: myRatings){
            i++;
            s = s+ "\n" + r.toString();
        }
        return s;
    }
}
