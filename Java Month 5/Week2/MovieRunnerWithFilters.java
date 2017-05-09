
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;
public class MovieRunnerWithFilters {
    private ThirdRatings tr;
    private ArrayList<Rating> rList;
    public MovieRunnerWithFilters(){
        //tr = new ThirdRatings("ratings.csv");
        tr = new ThirdRatings("ratings.csv");
        ArrayList<Rating> rList = new ArrayList<Rating>();        
        System.out.println("Raters list size: " + tr.getRaterSize());
    }
    public MovieRunnerWithFilters(String movieFile,String ratingFile){
        tr = new ThirdRatings(ratingFile);
        MovieDatabase.initialize(movieFile);
        ArrayList<Rating> rList = new ArrayList<Rating>();
        System.out.println("Read data for " + tr.getRaterSize() + " raters");
        System.out.println("Read data for " + MovieDatabase.size() + " movies");
    }
    public void printAverageRatings(int minRating){
        System.out.println("-----------------------\n");        
        System.out.println("------ Averaging ------\n"); 
        System.out.println("-----------------------\n"); 
        ArrayList<Rating> rList= tr.getAverageRatings(minRating);

        printRatingList(rList);
    }    
    public void printRatingList(ArrayList<Rating> rList){
        Collections.sort(rList);
        int i=0;
        for (Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                MovieDatabase.getTitle(r.getItem()));
        }
    }
    public double getAverageRatingOneMovie(String title){
        //tr2 = new SecondRatings("data/ratedmoviesfull.csv","data/ratings.csv");
        //tr2 = new SecondRatings("data/ratedmovies_short.csv","data/ratings_short.csv");
        ArrayList<Rating> list= tr.getAverageRatings(1);
        double avgRating = 0.0;
        for(Rating r: list){
            if(MovieDatabase.getTitle(r.getItem()).equals(title))
                avgRating = r.getValue();
        }
        System.out.println("\n-----------------------\n");
        System.out.println(avgRating  + "\t-\t" + title);
        return avgRating;
    }
    public ArrayList<Rating> getAverageRatingByFilter(int minRating,Filter  filterCriteria){
        ArrayList<String> mList = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> rList = tr.getAverageRatings(minRating);
        ArrayList<Rating> listToRemove = new ArrayList<Rating>();
        for (Rating r : rList){
            if(!mList.contains(r.getItem()))
                listToRemove.add(r);
        }        
        rList.removeAll(listToRemove);
        //printRatingList(rList);
        return rList;
    }
    public void printAverageRatingByYear(int minRating, int year){
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, new YearAfterFilter(year));
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                MovieDatabase.getYear(r.getItem()) + "\t" +
                                MovieDatabase.getTitle(r.getItem()));
        }
    }
    public void printAverageRatingsByGenre(int minRating, String genre){
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, new GenreFilter(genre));
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                MovieDatabase.getTitle(r.getItem())+ "\n\t" +
                                MovieDatabase.getGenres(r.getItem()) );
        }
    }
    public void printAverageRatingsByMinutes(int minRating,int min, int max){
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, new MinutesFilter(min,max));
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" +
                                "Time: " + MovieDatabase.getMinutes(r.getItem())+ "\t" + 
                                MovieDatabase.getTitle(r.getItem())  );
        }
    }
    public void printAverageRatingsByDirectors(int minRating, String dir){
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, new DirectorsFilter(dir));
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                MovieDatabase.getTitle(r.getItem())+ "\n\t" +
                                MovieDatabase.getDirector(r.getItem()) );
        }
    }
    public void printAverageRatingsByDirectorsAndMinutes(int minRating, String dir, int min, int max){
        AllFilters AF = new AllFilters();
        AF.addFilter(new DirectorsFilter(dir));
        AF.addFilter(new MinutesFilter(min,max));
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, AF);
        int i=0;        
        Collections.sort(rList);
        System.out.println("\nFound " + rList.size() + " movies");
        for(Rating r : rList){
            i++;
            System.out.println (i + "\t" + 
                                (Math.floor(r.getValue()*10000)/10000) + "\t" + 
                                "Time: " + MovieDatabase.getMinutes(r.getItem())+ "\t" + 
                                MovieDatabase.getTitle(r.getItem())+ "\n\t" +
                                MovieDatabase.getDirector(r.getItem()) );
        }
    }
    public void printAverageRatingsByYearAfterAndGenre (int minRating, int year, String genre){
        AllFilters AF = new AllFilters();
        AF.addFilter(new GenreFilter(genre));
        AF.addFilter(new YearAfterFilter(year));
        ArrayList<Rating> rList = getAverageRatingByFilter(minRating, AF);
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
}
