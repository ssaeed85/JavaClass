
/**
 * Write a description of WordFrequencies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import java.util.ArrayList;
public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    private int maxIndex;
    
    public WordFrequencies(){
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
        maxIndex= -1;
    }
    public void findUnique(){
        myWords.clear();
        myFreqs.clear();
        
        FileResource fr = new FileResource(); //Unspecified: allows selection from window
        
        for(String word : fr.words()){
            word = word.toLowerCase();
            int index = myWords.indexOf(word);
            if(index==-1){
                myWords.add(word);
                myFreqs.add(1);
            }
            else{
                int count = myFreqs.get(index);
                myFreqs.set(index,count+1);
            }
        }
    }
    public int findIndexOfMax(){
        int maxValue =0;
        for(int i = 0 ; i<myFreqs.size();i++){
            int value = myFreqs.get(i);
            if(value>maxValue){
                maxValue = value;
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    public void tester(){
        findUnique();
        System.out.println("Number of unique words: " + myWords.size());
        for(int i = 0; i<myWords.size();i++){
            System.out.println(myFreqs.get(i) + "\t" + myWords.get(i));
        }
        int maxIdx = findIndexOfMax();
        System.out.println("The word that occurs the most often and its count are:\t"
                                          + myWords.get(maxIdx) + "\t" + myFreqs.get(maxIdx));
    }
}
