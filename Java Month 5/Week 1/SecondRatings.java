
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
        FileResource movief = new FileResource(moviefile);
        FileResource ratingsf = new FileResource(ratingsfile);
        for(CSVRecord rec : movief.getCSVParser()){
            String id = rec.get("id");
            String title = rec.get("title");
            int year = Integer.parseInt(rec.get("year"));
            String country = rec.get("country");
            String genre = rec.get("genre");
            String director = rec.get("director");
            int minutes = Integer.parseInt(rec.get("minutes"));
            String poster = rec.get("poster");
            Movie currentMovie = new Movie(id,title,year,genre,director,country,poster,minutes);
            myMovies.add(currentMovie);
        }   
        for(CSVRecord rec : ratingsf.getCSVParser()){
            Rater cRater = new Rater(rec.get("rater_id"));
            String mID = rec.get("movie_id");
            double rating = Double.parseDouble(rec.get("rating"));    
            boolean bSkip = false;
            for(Rater r: myRaters){
                String rID = r.getID();
                if (rID.equals(cRater.getID())){
                    r.addRating(mID,rating);
                    bSkip = true;
                    break;                    
                }
            }
            if (bSkip==false){
                cRater.addRating(mID,rating);
                myRaters.add(cRater);                
            }
        }
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
            System.out.println(mID + " not rated by enough raters: " + numRaters);
            avg = 0.0;
        }
        return avg;
    }
    public ArrayList<Rating> getAverageRatings(int minRaters){
        //Returns avg rating for all movies in ratings file
        ArrayList<Rating> rList = new ArrayList<Rating>();
        for(Movie m: myMovies){
            double rating = getAverageByID(m.getID(),minRaters);            
            if (rating>0.0){
                Rating r = new Rating(m.getID(),rating);
                rList.add(r);
            }
        }
        return rList;
    }
    public String getTitle(String id){
        for(Movie m: myMovies){
            if (m.getID().equals(id))
                return(m.getTitle());
        }
        return("Title not found");
    }
    
}