

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
    //private HashMap<String,Rater> ratersInfo = new HashMap<String,Rater>();
    private ArrayList<Rater> ratersInfo = new ArrayList<Rater>();
    public void loadMovies(String fileString){
        FileResource fr = new FileResource(fileString);
        int recordCount = 0;
        for(CSVRecord rec : fr.getCSVParser()){
            recordCount++;
            String id = rec.get("id");
            String title = rec.get("title");
            int year = Integer.parseInt(rec.get("year"));
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
            boolean bSkip = false;
           
            for(Rater r: ratersInfo){
                String rID = r.getID();
                if (rID.equals(cRater.getID())){
                    r.addRating(mID,rating);
                    bSkip = true;
                    break;                    
                }
            }
            if (bSkip==false){
                cRater.addRating(mID,rating);
                ratersInfo.add(cRater);                
            }
            //System.out.println(cRater.getID() +": " + mID + ", " + rating);
        }
        System.out.println("Number of records in file: " + recordCount);
        System.out.println("Number of raters in file: " + ratersInfo.size());
    }
    public int getRatingCount(String rID){
        for(Rater r : ratersInfo){
            if(rID.equals(r.getID())){
                return r.numRatings();
            }            
        }
        System.out.println("List doesn't contain rater");
        return -1;
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
        //Prints all loaded raters info
        for(Rater r: ratersInfo){
                r.toString();
        }
    }
    public int maxRatings(){
        //Return max number of ratings by any rater
        int max = 0;
        for(Rater r: ratersInfo){
            if(r.numRatings()>max)
                max = r.numRatings();
        }
        System.out.println("Max number of ratings by any rater: " + max);
        return max;
    }
    public void ratersWithRating(int num){
        //Prints out all raters (ID) that have the same number of ratings as argument
        int i = 0;
        for(Rater r: ratersInfo){
            //System.out.println(r.getID()+ ": " +r.numRatings());
            if(r.numRatings()==num){
                i++;
                //System.out.println(r.toString());
            }
        }
        System.out.println("Number of raters with " + num +" rating(s) : " + i );         
    }
    public int numRatingsMovie(String mID){
        //Returns number of ratings a particular movie has received across all raters
        int i = 0;
        for(Rater r: ratersInfo){
            if(r.hasRating(mID)){
                i++;
            }
        }
        return i;
    }
    public ArrayList<String> numRatedMovies(){
        //Returns the total number of UNIQUE movies rated by all raters combined
        ArrayList<String> mList = new ArrayList<String>();
        for(Rater r: ratersInfo){
            ArrayList<String> l = r.getItemsRated();
            for(String s : l){
                if(!mList.contains(s))
                    mList.add(s);
            }
        }
        System.out.println(mList.size());
        return mList;
    }
    public void testLoadMovies(){
        String filePath = "data/ratedmoviesfull.csv";
        //String filePath = "data/ratedmovies_short.csv";
        loadMovies(filePath);
    }
    public void testmovieCounts(){
        System.out.println("Movies longer than 150 minutes: " + getMovieCountLongerThan(150));
        System.out.println("Movies of the comedy genre: " + getMovieCountGenre("Comedy"));
        System.out.println("With a movie count of " + getMovieCountDirector() + " each");
    }
    public void testLoadRaters(){
        String filePath = "data/ratings.csv";
        //String filePath = "data/ratings_short.csv";
        loadRaters(filePath);
        printRatersInfo();
    }
}