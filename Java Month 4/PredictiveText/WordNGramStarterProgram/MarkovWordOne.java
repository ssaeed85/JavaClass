
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int wLength = 1;
    
    public MarkovWordOne() {
        myRandom = new Random();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        System.out.println(myText);
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-wLength);  // random word to start with
        String key = myText[index];
        sb.append(key);
        sb.append(" ");
        for(int k=0; k < numWords-wLength; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = next;
        }
        
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(String key){
        ArrayList<String> follows = new ArrayList<String>();
        for(int idx =0 ;idx >=0 && idx < myText.length-wLength; idx+=wLength){
            idx = indexOf(myText, key,idx);
            if ((idx < myText.length - wLength) && idx != -1){
                if(!follows.contains(myText[idx+wLength]))
                    follows.add(myText[idx+wLength]);
            }
            else
                break;
        }
        return follows;
    }
    private int indexOf(String[] text,String key,int start){
        int idx;
        for(idx=start; idx < myText.length  && idx != -1 ; idx++){
            if(myText[idx].equals(key))
                return idx;
        }
        return -1;
    }
    public void testIndexOf(){
        setTraining("this is just a test yes this is a simple test");
        System.out.println("this: " + indexOf(myText,"this",0));
        System.out.println("this2: " + indexOf(myText,"this",3));
        System.out.println("frog: " + indexOf(myText,"frog",0));
        System.out.println("frog2: " + indexOf(myText,"frog",5));
        System.out.println("simple: " + indexOf(myText,"simple",2));
        System.out.println("test: " + indexOf(myText,"test",5));
    }
}
