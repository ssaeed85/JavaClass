
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class ThirdRatings {
    private ArrayList<Rater> myRaters = new ArrayList<Rater>();
    public ThirdRatings(){
        // default constructor
        this("ratings.csv");
    }
    public ThirdRatings(String ratingsfile){
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters("data/"+ratingsfile);
    }
    public int getRaterSize(){
        return myRaters.size();
    }
    public double getAverageByID(String mID,int minRaters){
        //Returns avg rating for stated movie
        double avg = 0.0;
        double sum = 0.0;
        int numRaters =0;
        for(Rater r : myRaters){
            if(r.hasRating(mID)){
                numRaters++;
                sum += r.getRating(mID);
            }
        }
        if (numRaters>=minRaters)
            avg = sum / numRaters;
        else{
            //System.out.println(mID + " not rated by enough raters: " + numRaters);
            avg = -1.0;
        }
        return avg;
    }
    public ArrayList<Rating> getAverageRatings(int minRaters){
        //Returns avg rating for all movies in ratings file
        ArrayList<Rating> rList = new ArrayList<Rating>();
        ArrayList<String> movieList = MovieDatabase.filterBy(new TrueFilter());        
        for(String m : movieList){
            double rating = getAverageByID(m,minRaters);            
            if (rating!=-1.0){
                Rating r = new Rating(m,rating);
                rList.add(r);
            }
        }
        System.out.println("Number of movies with " + minRaters + " ratings: " + rList.size());
        return rList;
    }
}
