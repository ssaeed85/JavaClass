
/**
 * Write a description of class MarkovWordOne here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordTwo implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int wLength = 2;
    
    public MarkovWordTwo() {
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
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key1,key2);
            if (follows.size() == 0) {
                break;
            }
            //System.out.println(key1 + " " + key2 +"\t"+follows.toString());
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }
        
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(String key1 , String key2){
        ArrayList<String> follows = new ArrayList<String>();
        for(int idx =0 ;idx >=0 && idx < myText.length-wLength; idx+=wLength){
            idx = indexOf(myText, key1, key2,idx);
            if ((idx < myText.length - wLength) && idx != -1){
                if(!follows.contains(myText[idx+wLength]))
                    follows.add(myText[idx+wLength]);
            }
            else
                break;
        }
        return follows;
    }
    private int indexOf(String[] text,String key1, String key2,int start){
        int idx;
        for(idx=start; idx < myText.length-1 && idx != -1; idx++){
            if(myText[idx].equals(key1) && myText[idx+1].equals(key2))
                return idx;
        }
        return -1;
    }
    
    public void testIndexOf(){
        setTraining("this is just a test yes this is a simple test");
        System.out.println("this is: " + indexOf(myText,"this","is",0));
        System.out.println("this is 2: " + indexOf(myText,"this","is",3));
        System.out.println("frog is: " + indexOf(myText,"frog","is",0));
        System.out.println("frog is2: " + indexOf(myText,"frog","is",5));
        System.out.println("a simple: " + indexOf(myText,"a","simple",2));
        System.out.println("simple test: " + indexOf(myText,"simple","test",2));
    }
    
}
