
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies = new ArrayList<Movie>();
    private ArrayList<Rater> myRaters = new ArrayList<Rater>();
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    public SecondRatings(String moviefile, String ratingsfile){
        FirstRatings fr = new FirstRatings(moviefile,ratingsfile);
        myMovies = fr.moviesInfo;
        myRaters = fr.ratersInfo;
    }
    public int getMovieSize(){
        return myMovies.size();
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
        for(Movie m: myMovies){
            double rating = getAverageByID(m.getID(),minRaters);            
            if (rating!=-1.0){
                Rating r = new Rating(m.getID(),rating);
                rList.add(r);
            }
        }
        System.out.println("Number of movies with " + minRaters + " ratings: " + rList.size());
        return rList;
    }
    public String getTitle(String id){
        for(Movie m: myMovies){
            if (m.getID().equals(id))
                return(m.getTitle());
        }
        return("Title not found");
    }
    public String getID(String title){
        for(Movie m: myMovies){
            if(m.getTitle().equals(title))
                return (m.getID());
        }
        return("No such title");
    }
}