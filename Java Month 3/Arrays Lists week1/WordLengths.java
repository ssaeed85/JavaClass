
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts){
        //Method tallies word count for varying lengths into counts[] array
        //counts[k] holds word count of words of length k
        
        //Setup loop to go through each word in the file resource
        for(String word : resource.words() ) {
            //Find length and increment count for said length in counts[] array
            int index = wordLength(word);
            counts[index]++;
        }
    }
    
    public int wordLength(String word){
        int length=0;
        boolean frontCheck=false;
        boolean rearCheck = false;
        //Remove punctuations at beginning and end of word
        System.out.println("Original word:\t"+word);
        while(!frontCheck && !rearCheck && word.length()>1){
            while(word.length()>0 && !Character.isLetter(word.charAt(0))){
                word = word.substring(1);
            }
            frontCheck = true;
            while(word.length()>0 && !Character.isLetter(word.charAt(word.length() - 1))){
                word = word.substring(0,word.length()-1);
            }
            rearCheck = true;
        }
        if(word.length()>1)
            length = word.length();
        if(word.length()==1 && Character.isLetter(word.charAt(0)))
            length = 1;
        if(word.length()==1 && !Character.isLetter(word.charAt(0))){
            length = 0;
        }
        System.out.println("Word:\t" + word + "\t\tLength:\t" + length);
        return length;
    }
    
    public void test(){
        int[] counts = new int[31];
        FileResource data = new FileResource("textForTest.txt");
        countWordLengths(data,counts);
        for(int i=0;i< counts.length;i++){
            if(counts[i]>0){
                System.out.println("Length: " + i + "\t Count: " + counts[i]);
            }
        }
    }
}
