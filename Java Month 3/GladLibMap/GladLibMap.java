import edu.duke.*;
import java.util.*;
import java.io.*;
public class GladLibMap {
    private ArrayList<String> usedWordList = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> myMap = 
            new HashMap<String, ArrayList<String>>(); //This will replace the above array lists
    private ArrayList<String> usedCategoriesList = new ArrayList<String>();
    private Random myRandom;
    private int wordsReplacedCount = 0;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "datalong";
    
    public GladLibMap(){
        myMap.clear();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
       String[] labels = {"verb","noun","animal","country","color","adjective","name","timeframe","fruit"};
       for(String word: labels){
           ArrayList<String> list = readIt(source + "/" + word + ".txt");
           myMap.put(word,list);
       }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        String sub="";
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        //Use loop to loop through word list until unused word is found and add to growin list of words used
        while(usedWordList.contains(sub) || sub.equals(""))
            sub = getSubstitute(w.substring(first+1,last));
        //Add <category> to used category list
        if(!usedCategoriesList.contains(w.substring(first+1,last)))
            usedCategoriesList.add(w.substring(first+1,last));        
        usedWordList.add(sub);
        wordsReplacedCount++;
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story+"\n\nNumber of words replaced: " + wordsReplacedCount;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    private int totalWordsInMap(){
        int count=0;
        for(String key : myMap.keySet()){
            count = myMap.get(key).size() + count;
        }
        return count;
    }
    private int totalWordsConsidered(){
        int count=0;
        for(String key : myMap.keySet()){
            if(usedCategoriesList.contains(key))
                count = myMap.get(key).size()+count;
        }
        return count;
    }
    
    public void makeStory(){
        System.out.println("\n");
        usedWordList.clear();
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("\nTotal word count available for use: " + totalWordsInMap());
        System.out.println("\nTotal words considered: " + totalWordsConsidered());
    }
}
   
