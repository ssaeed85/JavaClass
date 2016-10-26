
/**
 * Abstract class AbstractMarkovModel - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected String key;
    protected Random myRandom;
    
    public AbstractMarkovModel() {
        myRandom = new Random();
    }
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    public void setTraining(String s) {
        myText = s.trim();
    }
    public ArrayList<Character> getFollows(String key){
        ArrayList<Character> charList = new ArrayList<Character>();
        //int textL = myText.length();
        if(key != null){
            int kL = key.length();
            //System.out.println("key: " + key + "\tKey Length: " + kL);
            
            for(int index = 0;index>=0;index=index+kL){
                index = myText.indexOf(key,index);
                //System.out.println(myText.charAt(index+kL));
                if ((index < myText.length() - kL) && index != -1){
                    //Character c = myText.charAt(index+kL);
                    charList.add(myText.charAt(index+kL));
                }
                else
                    break;
            }
        }
        else
            System.out.println("No key provided");
            
        return charList;
            
    }
    abstract public String toString();
    abstract public String getRandomText(int numChars);
}
