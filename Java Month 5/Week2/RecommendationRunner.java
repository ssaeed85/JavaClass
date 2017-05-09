
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class RecommendationRunner implements Recommender{
    private Random randomGenerator = new Random();
    public RecommendationRunner(){
    }
    public ArrayList<String> getItemsToRate (){
        FourthRatings fr = new FourthRatings();
        ArrayList<String> mList = MovieDatabase.filterBy(new YearAfterFilter(2014));
        ArrayList<String> ret = new ArrayList<String>();
        for(int i=0; i<10;i++){
            int index = randomGenerator.nextInt(mList.size());
            ret.add(mList.get(index));
        }
        return ret;
    }
    public void printRecommendationsFor (String webRaterID){
        FourthRatings FR = new FourthRatings();
        int numSimilarRaters = 20;
        int minRaters = 2;
        ArrayList<Rating> list = FR.getSimilarRatings(webRaterID, numSimilarRaters, minRaters);
        
        if (list.size()>0){
            System.out.println( "<br />Here are the movies we recommend<br />");
            int i = 0;
            System.out.println("<table border=\"5\"><tr><th>Poster</th><th>Movie</th><th>Year</th></tr>");
            for(Rating r : list){
                i++;
                String p = MovieDatabase.getPoster(r.getItem());
                String pString = p.replaceAll("http:","data");
                System.out.println("<tr><td><img src = " + pString + " height=\"80\" align=\"left\"></td>"
                                    + "<td>" + MovieDatabase.getTitle(r.getItem()) + "</td>"
                                    + "<td>" + MovieDatabase.getYear(r.getItem()) + "</td></tr>");
                if (i == 10)
                    break;
            }
            System.out.println("</table>");
        }            
        else{
            System.out.print( "<br />No movies can be recommended on the basis of your ratings<br />");
            System.out.print( "<br />Please go back and refresh the page. The movies loaded are picked"
                                + " randomly from a pool. Rerate and try again.<br />");
        }
    }
}
