
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;
public class FourthRatings {
    private ArrayList<Rater> myRaters = new ArrayList<Rater>();
    public FourthRatings(){
        myRaters = RaterDatabase.getRaters();
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
    public ArrayList<Rating> getAverageRatingByFilter(int minRaters,Filter  filterCriteria){
        ArrayList<String> mList = MovieDatabase.filterBy(filterCriteria);
        ArrayList<Rating> rList = getAverageRatings(minRaters);
        ArrayList<Rating> listToRemove = new ArrayList<Rating>();
        for (Rating r : rList){
            if(!mList.contains(r.getItem()))
                listToRemove.add(r);
        }        
        rList.removeAll(listToRemove);
        //printRatingList(rList);
        return rList;
    }
    private double dotProduct(Rater me, Rater r){
        double result=0;
        ArrayList<String> CommonMovieList = MovieDatabase.filterBy(new CommonFilter(me,r));
        //if(!CommonMovieList.isEmpty())System.out.println("\nDebuggin dot product\t"+me.getID()+", " +r.getID());
        for(String id : CommonMovieList){
            result = result + (me.getRating(id)-5.0)*(r.getRating(id)-5.0);
            //System.out.println("Movie: " + MovieDatabase.getTitle(id) + "\t R1: " 
            //                    + me.getRating(id) + ", R2: " + r.getRating(id) + "\n");
        }
        //if(result>0.0)System.out.print("\tResult: " + result);
        return result;
    }
    public ArrayList<Rating> getSimilarities(String id){
        //Returns a list of similarity ratings of rater(id) to all raters. Rater|SimilarityRating
        ArrayList<Rating> list = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        for (Rater r : RaterDatabase.getRaters()){
            if(r!=me && dotProduct(me,r) > 0.0){
                list.add(new Rating(r.getID(),dotProduct(me,r)));
            }
        }
        Collections.sort(list,Collections.reverseOrder());
        //for(Rating r : list) System.out.println(r.toString());
        return list;
    }
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters){
        return getSimilarRatings(id,numSimilarRaters,minimalRaters,new TrueFilter());
    }
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters, Filter f){
        //Get Rater similarity ratings list across rater database
        //Truncate list size to numSimilarRaters
        //Take weighted averages for every movie after filterigng for the above rater list
        //Weighted avg : similarity rating for rater * rating of movie by that rater
        ArrayList<Rating> list = getSimilarities(id);
        ArrayList<Rating> ret = new ArrayList<Rating>(); //list of movies:weightedRatings
        if(list.size() > numSimilarRaters){
            //System.out.println("Reducing close raters list from " + list.size() + " to " + numSimilarRaters);
            list.subList(numSimilarRaters, list.size()).clear();
        }
        for(String movie : MovieDatabase.filterBy(f)){
            int numRaters = 0;
            double result=0.0;
            for(Rating rating : list){
                Rater r = RaterDatabase.getRater(rating.getItem());
                if(r.hasRating(movie)){
                    numRaters++;
                    result = result + rating.getValue() * r.getRating(movie);
                }
            }
            if (numRaters>=minimalRaters){
                ret.add(new Rating(movie, result/numRaters));   
            }
        }
        if (ret.isEmpty()){
            System.out.println( "No movie where the number of similar raters are greater than the minimum "+
                                "number of raters needed to make a recommendation");
        }
        else{
            Collections.sort(ret,Collections.reverseOrder());
        }
        //for(Rating r : ret) System.out.println(r.toString());
        return ret;
    }
    private void printStringList(ArrayList<String> list){
        int count =0;
        for(String s : list){
            count++;
            System.out.println(count + ".\t" + s);
        }
    }
    
    public void testDotProduct(String rID1, String rID2){
        System.out.println("Dot Product: " + dotProduct(RaterDatabase.getRater(rID1), RaterDatabase.getRater(rID2)));
    }
}