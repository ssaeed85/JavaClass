/**
 * Write a description of EfficientMarkovMode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class EfficientMarkovModel extends AbstractMarkovModel{
    private int keyLength;
    private HashMap<String,ArrayList<Character>> getFollowsMap = new HashMap<String,ArrayList<Character>>();
    public EfficientMarkovModel(int keyLength){
        this.keyLength = keyLength;
    }
    public String getRandomText(int numChars){    
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-keyLength);
        key = myText.substring(index, index+keyLength);
        sb.append(key);
        buildMap();
        
        for(int k=0; k < numChars-keyLength; k++){
            ArrayList<Character> follows = getFollowsMap.get(key);
            if(follows.isEmpty())
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index).toString();
            sb.append(next);
            key = key.substring(1)+next;
        }
        
        return sb.toString();        
    }    
    public void buildMap(){
        for(int idx=0; (idx < myText.length()-keyLength+1) && (idx != -1); idx++){
            String key = myText.substring(idx, idx + keyLength); //local variable
            if(!getFollowsMap.containsKey(key)){
                ArrayList<Character> follows = getFollows(key);
                if(follows == null){
                    follows = new ArrayList<Character>();
                }
                getFollowsMap.put(key,follows);
            }
        }
        printMapStatus();
    }
    public String toString(){
        String s = "Efficient Markov model of order "+keyLength;
        return s;
    }
    public void printMapStatus(){
        System.out.println("The hashMap has " + getFollowsMap.size() + " key/value pairs");
        
        //Print the hash Map if its not too big: not more than 30 key/value pairs
        if(getFollowsMap.size()<30){
            System.out.println("\nPrinting HashMap");
            printMap();
        }
        
        System.out.println("The key(s) \'" + keysWithMaxValue().toString() 
                            + "\' has the largest follows list with a size of " + findMaxValue());
    }
    public void printMap(){
        //Method to print the full hashMap
        System.out.println("\tKey\t\tValue");
        int count=0;
        for(String keyString:getFollowsMap.keySet()){
            count++;
            System.out.println(count+".\t"+keyString+"\t\t"+getFollowsMap.get(keyString).size());
        }
    }
    public ArrayList<String> keysWithMaxValue(){
        //Method to find the keys with the max size of arraylist associated with it
        int max=findMaxValue();
        ArrayList<String> keys = new ArrayList<String>();
        for(String keyString : getFollowsMap.keySet()){
            if(getFollowsMap.get(keyString).size()==max){
                keys.add(keyString);
            }
        }
        return keys;
    }
    public int findMaxValue(){
        //Find the max possible size of the associated arraylists for all keys in the hashMap
        int max=0;
        for(String keyString : getFollowsMap.keySet()){
            if(getFollowsMap.get(keyString).size()>max){
                max = getFollowsMap.get(keyString).size();
            }
        }
        return max;
    }
}