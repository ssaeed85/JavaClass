
/**
 * Write a description of MarkovMOdel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class MarkovModel extends AbstractMarkovModel{
    private int keyLength;
    public MarkovModel(int keyLength){
        this.keyLength = keyLength;
    }
    public String getRandomText(int numChars){    
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-keyLength);
        String key = myText.substring(index, index+keyLength);
        sb.append(key);
        
        for(int k=0; k < numChars-keyLength; k++){
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
        String s = "Markov model of order "+keyLength;
        return s;
    }
}
