
/**
 * Write a description of MarkovFour here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MarkovFour extends AbstractMarkovModel{
    public String getRandomText(int numChars){        
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-4);
        String key = myText.substring(index, index+4);
        sb.append(key);
        
        for(int k=0; k < numChars-3; k++){
            ArrayList<Character> follows = getFollows(key);
            if (follows.size() == 0){
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index).toString();
            sb.append(next);
            key = key.substring(1)+next;
        }
        return sb.toString();        
    }    
    public String toString(){
        String s = "Markov model of order 4";
        return s;
    }
}
