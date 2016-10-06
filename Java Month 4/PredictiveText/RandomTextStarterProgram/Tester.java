
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * 
 */
import java.util.*;
import edu.duke.*;
public class Tester {
    private MarkovOne markov = new MarkovOne();
    private String key="";
    private String testStr = "this is a test yes this is a test.";
    public Tester(){
        markov.setTraining(testStr);
    }
    public void testGetFollows(String key){
        this.key = key;

        ArrayList<Character> list = markov.getFollows(key);
        printFollowResult(list,key);
    }
    public void testGetFollowsWithFile(String key){
        FileResource fr = new FileResource();
        this.key = key;
        testStr = fr.asString();
		testStr = testStr.replace('\n', ' ');
        markov.setTraining(testStr);
        //System.out.println(testStr);
        ArrayList<Character> list = markov.getFollows(key);
        //printFollowResult(list,key);
        System.out.println("Number of characters that follow \"" + key + "\" are " + list.size());
    }
    private void printFollowResult(ArrayList<Character> list, String key){
        System.out.println("Msg: "+testStr);
        System.out.println("Key: "+key);
        for(Character c : list){
            System.out.println(c);
        }
    }
}
