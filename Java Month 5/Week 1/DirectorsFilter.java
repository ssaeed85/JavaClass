
/**
 * Write a description of DirectorsFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import org.apache.commons.csv.*;
import edu.duke.FileResource;
public class DirectorsFilter implements Filter{
    private ArrayList<String> dir = new ArrayList<String>();
    public DirectorsFilter(String dir){
        for(String s : dir.split(",")){
            this.dir.add(s);
        }
    }
    public boolean satisfies(String id){
        String directors = MovieDatabase.getDirector(id);
        for(String s : directors.split(",")){
            if (dir.contains(s))
                return true;
        }        
        return false;
    }
}
