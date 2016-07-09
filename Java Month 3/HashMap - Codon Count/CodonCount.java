
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import java.util.*;
public class CodonCount {
    private HashMap<String,Integer> myMap = new HashMap<String,Integer>();
    
    public CodonCount(){
        myMap.clear();
    }
    
    public void buildCodonMap(int start, String dna){
        myMap.clear();
        int loopCount = (int)((dna.length() - start)/3);
        
        String codon;
        for(int i = 0 ; i< loopCount; i++){
            int st = start+i*3;
            int end = st + 3;
            
            codon = dna.substring(st,end);
            if (myMap.containsKey(codon)){
                myMap.put(codon,myMap.get(codon)+1);            
            }
            else{
                myMap.put(codon,1);
            }
        }   
    }
    
    public void printCodonCount(int min,int max){
        System.out.println("Counts of codons between " +min+ " & " +max+ " are:");
        for(String key : myMap.keySet()){
            Integer value = myMap.get(key);
            if((value >= min) && (value <= max))
                System.out.println(key+"\t" + value);
            //}
        }
    }
    
    public void getMostCommonCodon(){
        int maxCount=0;
        String result="";
        
        for(String key: myMap.keySet()){
            if(myMap.get(key)>maxCount){
                maxCount = myMap.get(key);
                result=key;
            }
        }
        System.out.println("Most common count codon: " + result + "\tCount: " + maxCount);
    }
    
    public void tester(){
        FileResource fr = new FileResource();
        for(String word : fr.words()){
            //System.out.println(word +"\tLength: " + word.length());
            buildCodonMap(0,word);
            printCodonCount(1,5);
            getMostCommonCodon();
            
            buildCodonMap(1,word);
            printCodonCount(1,5);
            getMostCommonCodon();
            
            buildCodonMap(2,word);
            printCodonCount(1,5);
            getMostCommonCodon();
        }
    }
}
