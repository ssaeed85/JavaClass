

/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    private ArrayList<Movie> moviesInfo = new ArrayList<Movie>();
    private HashMap<String,Rater> ratersInfo = new HashMap<String,Rater>();
    public void loadMovies(String fileString){
        FileResource fr = new FileResource(fileString);
        int recordCount = 0;
        for(CSVRecord rec : fr.getCSVParser()){
            recordCount++;
            String id = rec.get("id");
            String title = rec.get("title");
            String year = rec.get("year");
            String country = rec.get("country");
            String genre = rec.get("genre");
            String director = rec.get("director");
            int minutes = Integer.parseInt(rec.get("minutes"));
            String poster = rec.get("poster");
            
            Movie currentMovie = new Movie(id,title,year,genre,director,country,poster,minutes);
            //System.out.println(currentMovie.toString());
            moviesInfo.add(currentMovie);
        }            
        System.out.println("Number of movies in file: " + recordCount);
    }
    public void loadRaters(String fileString){
        FileResource fr = new FileResource(fileString);
        int recordCount = 0;
        for(CSVRecord rec : fr.getCSVParser()){
            recordCount++;      
            Rater cRater = new Rater(rec.get("rater_id"));
            String mID = rec.get("movie_id");
            double rating = Double.parseDouble(rec.get("rating")); 
            //double time = Double.parseDouble(rec.get("time"));      
            if (!ratersInfo.containsKey(cRater.getID())){
                cRater.addRating(mID,rating);
                ratersInfo.put(cRater.getID(),cRater);
            }
            else{                
                for(Rater r: ratersInfo){
                    if(cRater.equals(r)){
                        r.addRating(mID,rating);
                        break;
                    }
                }
            }            
            System.out.println(cRater.getID() +": " + mID);
        }
        System.out.println("Number of records in file: " + recordCount);
        System.out.println("Number of raters in file: " + ratersInfo.size());
    }
    public int getMovieCountLongerThan(int minMinutes){
        int count =0;
        if(moviesInfo.isEmpty())
            System.out.println("No movies in list");
        else{
            for(Movie cMovie: moviesInfo){
                if(cMovie.getMinutes()>minMinutes)
                    count++;
            }
        }        
        return count;
    }
    public int getMovieCountGenre(String genre){
        int count =0;
        if(moviesInfo.isEmpty())
            System.out.println("No movies in list");
        else{
            for(Movie cMovie: moviesInfo){
                String cGenre = cMovie.getGenres().toLowerCase();
                if(cGenre.contains(genre.toLowerCase()))
                    count++;
            }
        }        
        return count;
    }
    public int getMovieCountDirector(){
        int max =0;
        HashMap<String,Integer> dirCount = new HashMap<String,Integer>();
        if(moviesInfo.isEmpty())
            System.out.println("No movies in list");
        else{
            for(Movie cMovie: moviesInfo){
                String cDir = cMovie.getDirector();
                if(!dirCount.containsKey(cDir))
                    dirCount.put(cDir,1);
                else{
                    int count = dirCount.get(cDir);
                    dirCount.put(cDir,count+1);
                }
            }
            for(String key : dirCount.keySet()){
                if(max < dirCount.get(key))
                    max = dirCount.get(key);
            } 
            System.out.println("\nMost prolific directors: ");
            for(String key: dirCount.keySet()){
                if(dirCount.get(key) == max)
                    System.out.println(key);
            }
        }        
        return max;
    }
    public void printRatersInfo(){
        for(String rID: ratersInfo.keySet()){
            System.out.println(ratersInfo.get(rID).getID());
            ArrayList<String> ratings = ratersInfo.get(rID).getItemsRated();
            
            System.out.println(ratings.toString());            
        }
    }
    public void testLoadMovies(){
        //String filePath = "data/ratedmoviesfull.csv";
        String filePath = "data/ratedmovies_short.csv";
        loadMovies(filePath);
    }
    public void testmovieCounts(){
        System.out.println("Movies longer than 150 minutes: " + getMovieCountLongerThan(150));
        System.out.println("Movies of the comedy genre: " + getMovieCountGenre("Comedy"));
        System.out.println("With a movie count of " + getMovieCountDirector() + " each");
    }
    public void testLoadRaters(){
        String filePath = "data/ratings_short.csv";
        loadRaters(filePath);
        printRatersInfo();
    }
    public void testRatersEquality(){
        FileResource fr = new FileResource("data/ratings_short.csv");
        for(CSVRecord rec : fr.getCSVParser()){
            Rater cRater = new Rater(rec.get("rater_id"));
            String mID = rec.get("movie_id");
            double rating = Double.parseDouble(rec.get("rating")); 
            //double time = Double.parseDouble(rec.get("time"));  
            cRater.addRating(mID,rating);
            ratersInfo.add(cRater);         
            System.out.println(cRater.getID() +": " + mID);
        }
        System.out.println("Number of raters in file: " + ratersInfo.size());
        Rater c1 = ratersInfo.get(0);
        Rater c2 = ratersInfo.get(1);
        Rater c3 = ratersInfo.get(2);
        System.out.println("First Rater: " + c1.toString());
        System.out.println("Second Rater: " + c2.toString());
        System.out.println("Third Rater: " + c3.toString());
        System.out.println("Are first and second raters the same?\n" + c1.equals(c2));
        System.out.println("Are first and third raters the same?\n" + c1.equals(c3));
        
        System.out.println("c1 and c2 are the same rater. \n Does the list recognize this? Does the list contain a rater with id '1'?");
        Rater newRater = new Rater("1");
        System.out.println(ratersInfo.contains(newRater));
    }
}