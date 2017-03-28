
/**
 * Write a description of MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MovieRunnerAverage {
    public void printAverageRatings(){
        //SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        System.out.println("Movies list size: " + sr.getMovieSize());
        System.out.println("Raters list size: " + sr.getRaterSize());
        System.out.println("\n\n-----------------------");
        
        System.out.println(sr.getAverageRatings(3));
    }
}
