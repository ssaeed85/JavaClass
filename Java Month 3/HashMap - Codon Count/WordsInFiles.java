
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import java.util.*;
public class WordsInFiles {
    private HashMap<String,ArrayList<String>> myMap = new HashMap<String,ArrayList<String>>();
    
    public WordsInFiles(){
        myMap.clear();
    }
    public void addWordsFromFile(File f){
        String fName = f.getName();
        FileResource fr = new FileResource(fName);
        for(String word: fr.words()){
            ArrayList<String> list = new ArrayList<String>();
            //if word currently doesn't exist in the hash map we create a new array list with the current
            //file name and add it to the hash map
            if(!myMap.containsKey(word)){
                list.add(fName);
                myMap.put(word,list);
            }
            //if word already exists in the hash map we check if the current file name exists in list of
            //file names for that particular word's occurence. If it doesn't it gets added
            else{
                list = myMap.get(word);
                if(!list.contains(fName)){
                       list.add(fName);
                       myMap.put(word,list);
                }
            }
        }
    }
    public void buildWordFileMap (){
        myMap.clear();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            addWordsFromFile(f);
        }
    }
    public int maxNumber(){
        ArrayList<String> currentList = new ArrayList<String>();
        int max=0;
        for(String key: myMap.keySet()){
            currentList = myMap.get(key);
            if(currentList.size()>max)
                max = currentList.size();
        }
        return max;
    }
    public ArrayList<String> wordsInNumFiles (int number){
        ArrayList<String> listOfWords = new ArrayList<String>();
        int wordFileOccurenceCount;
        
        for(String key: myMap.keySet()){
            wordFileOccurenceCount = myMap.get(key).size(); //Counts the num of files word occurs in
            if (wordFileOccurenceCount == number)
                listOfWords.add(key);
        }
        
        return listOfWords;
    }
    public void printFilesln(String word){
        for(String key: myMap.keySet()){
            if(key.equals(word)){
                ArrayList<String> currentList = myMap.get(key);
                for(int i=0; i<currentList.size() ; i++){
                    System.out.println(currentList.get(i));
                }
            }
        }
    }
    public void tester(){
        buildWordFileMap();
        
        System.out.println("The maximum number of files any word is in is: " + maxNumber());
        
        System.out.println("The words that have the max occurence are: ");
        ArrayList<String> wordList = wordsInNumFiles(maxNumber());
        for(int i = 0;i<wordList.size();i++){
            System.out.println(wordList.get(i));
        }
    }
}
