
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class MovieRunnerAverage {
    private SecondRatings sr;
    private ArrayList<Rating> rList;
    public MovieRunnerAverage(){
        sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        //sr = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        ArrayList<Rating> rList = new ArrayList<Rating>();
    }
    public MovieRunnerAverage(String movieFile,String ratingFile){
        sr = new SecondRatings(movieFile,ratingFile);
        ArrayList<Rating> rList = new ArrayList<Rating>();
    }
    public void printAverageRatings(int minRating){
        System.out.println("Movies list size: " + sr.getMovieSize());
        System.out.println("Raters list size: " + sr.getRaterSize());
        System.out.println("\n-----------------------\n");
        
        ArrayList<Rating> rList= sr.getAverageRatings(minRating);
        
        printRatingList(rList);
    }    
    public void printRatingList(ArrayList<Rating> rList){
        Collections.sort(rList);
        int i=0;
        for (Rating r : rList){
            i++;
            System.out.println (i + "\t-\t" + (Math.floor(r.getValue()*10000)/10000) + "\t-\t" + sr.getTitle(r.getItem()));
        }
    }
    public double getAverageRatingOneMovie(String title){
        //sr2 = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        //sr2 = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        ArrayList<Rating> list= sr.getAverageRatings(1);
        double avgRating = 0.0;
        for(Rating r: list){
            if(sr.getTitle(r.getItem()).equals(title))
                avgRating = r.getValue();
        }
        System.out.println("\n-----------------------\n");
        System.out.println(avgRating  + "\t-\t" + title);
        return avgRating;
    }
}
