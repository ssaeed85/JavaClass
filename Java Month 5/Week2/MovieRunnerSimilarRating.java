
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;
public class MovieRunnerSimilarRating {
    private ArrayList<Rating> rList;
    public MovieRunnerSimilarRating(String movieFile,String ratingFile){
        MovieDatabase.initialize(movieFile);
        RaterDatabase.initialize(ratingFile);
        ArrayList<Rating> rList = new ArrayList<Rating>();
        System.out.println("Read data for " + RaterDatabase.size() + " raters");
        System.out.println("Read data for " + MovieDatabase.size() + " movies");
    }
    public MovieRunnerSimilarRating(){
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.initialize("ratings.csv");
        ArrayList<Rating> rList = new ArrayList<Rating>();
        System.out.println("Read data for " + RaterDatabase.size() + " raters");
        System.out.println("Read data for " + MovieDatabase.size() + " movies");
    }
    public void printAverageRatings(int minRating){
        System.out.println("------ Averaging ------\n"); 
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> rList= fr.getAverageRatings(minRating);
        int i=0;        
        Collections.sort(rList);
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" +
                                MovieDatabase.getTitle(r.getItem()) );
        }
    } 
    public void printAverageRatingsByYearAfterAndGenre (int minRating, int year, String genre){
        AllFilters AF = new AllFilters();
        AF.addFilter(new GenreFilter(genre));
        AF.addFilter(new YearAfterFilter(year));
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> rList = fr.getAverageRatingByFilter(minRating, AF);
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                MovieDatabase.getYear(r.getItem()) + "\t" + 
                                MovieDatabase.getGenres(r.getItem())+ "\n\t" +
                                MovieDatabase.getTitle(r.getItem()) );
        }
    }
    public void printSimilarRatings(String rID, int numSimilarRaters, int minRaters){
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> list = fr.getSimilarRatings(rID, numSimilarRaters, minRaters);
        
        printMovieRecommendations(rID,numSimilarRaters,minRaters,list);
    }
    public void printSimilarRatingsByGenre(String rID, int numSimilarRaters, int minRaters, String genre){
        FourthRatings fr = new FourthRatings();
        Filter f = new GenreFilter(genre);
        ArrayList<Rating> list = fr.getSimilarRatings(rID, numSimilarRaters, minRaters,f);
        
        printMovieRecommendations(rID,numSimilarRaters,minRaters,list);
    }
    public void printSimilarRatingsByDirector (String rID, int numSimilarRaters, int minRaters, String dir){
        FourthRatings fr = new FourthRatings();
        Filter f = new DirectorsFilter(dir);
        ArrayList<Rating> list = fr.getSimilarRatings(rID, numSimilarRaters, minRaters,f);
        
        printMovieRecommendations(rID,numSimilarRaters,minRaters,list);
    }
    public void printSimilarRatingsByGenreAndMinutes 
        (String rID, int numSimilarRaters, int minRaters, String genre, int minMin, int maxMin){
        FourthRatings fr = new FourthRatings();
        AllFilters AF = new AllFilters();
        AF.addFilter(new GenreFilter(genre));
        AF.addFilter(new MinutesFilter(minMin, maxMin));
        ArrayList<Rating> list = fr.getSimilarRatings(rID, numSimilarRaters, minRaters,AF);
        
        printMovieRecommendations(rID,numSimilarRaters,minRaters,list);
    }
    public void printSimilarRatingsByYearAfterAndMinutes 
        (String rID, int numSimilarRaters, int minRaters, int year, int minMin, int maxMin){
        FourthRatings fr = new FourthRatings();
        AllFilters AF = new AllFilters();
        AF.addFilter(new YearAfterFilter(year));
        AF.addFilter(new MinutesFilter(minMin, maxMin));
        ArrayList<Rating> list = fr.getSimilarRatings(rID, numSimilarRaters, minRaters,AF);
        
        printMovieRecommendations(rID,numSimilarRaters,minRaters,list);        
    }
    private void printMovieRecommendations(String rID, int numSimilarRaters, int minRaters,ArrayList<Rating> list){
        System.out.println("\nMost Recommended Movie: " + 
        MovieDatabase.getTitle(list.get(0).getItem())); 
        
        System.out.println( "\nAdditional Info" + 
                            "\nRater ID: " + rID + 
                            "\nNumber of 'Close' Raters used: " + numSimilarRaters +
                            "\nNumber of Minimum Raters: " + minRaters );
                            
        System.out.println( "\nPrinting top 10 of list");
        int i=0;
        for(Rating r : list){
            i++;
            System.out.println((Math.floor(r.getValue()*100)/100) + "\t" +
                                "\t" + MovieDatabase.getTitle(r.getItem()));
            if (i==10) break;
        } 
    }
    
    
    
    public void testSimilarityRatings(String rID){
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> list = fr.getSimilarities(rID);
        
        System.out.println("List size: " + list.size());        
        System.out.println( "\nPrinting top 10 of list");
        int i=0;
        for(Rating r : list){
            i++;
            System.out.println( i + 
                                (Math.floor(r.getValue()*100)/100) + "\t" + 
                                "\t" + MovieDatabase.getTitle(r.getItem()));
            if (i==10)break;
        } 
    }    
    public void testGetSimilarRating(String id, int numSimilarRaters,int minimalRaters){
        FourthRatings fr = new FourthRatings();
        ArrayList<Rating> list = fr.getSimilarRatings(id,numSimilarRaters,minimalRaters);
        System.out.println( "Most Recommended: " + MovieDatabase.getTitle(list.get(0).getItem()));
        System.out.println( "\nPrinting top 10 of list");
        int i=0;
        for(Rating r : list){
            i++;
            System.out.println( i + 
                                (Math.floor(r.getValue()*100)/100) + "\t" + 
                                "\t" + MovieDatabase.getTitle(r.getItem()));
            if (i==10) break;
        } 
    }
}
