
/**
 * Write a description of MarkovFour here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovFour {
    private String myText;
    private Random myRandom;
    public MarkovFour() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
    }
    
    public ArrayList<Character> getFollows(String key){
        ArrayList<Character> charList = new ArrayList<Character>();
        //int textL = myText.length();
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
        return charList;
    }
    
    public String getRandomText(int numChars){        
        /*StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-4);
        String key = myText.substring(index, index+4);
        sb.append(key);
        
        for(int k=0; k < numChars-4; k++){
            ArrayList<Character> follows = getFollows(key);
            if (follows.size() == 0){
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index).toString();
            sb.append(next);
            key = myText.substring(index, index+4);
        }
        String test = sb.toString();
        return sb.toString();*/
        
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-4);
        String key = myText.substring(index, index+4);
        sb.append(key);
        
        for(int k=0; k < numChars-4; k++){
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
}