
/**
 * Write a description of findNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import edu.duke.*;
public class CharactersInPlay {
    private ArrayList<String> charNames;
    private ArrayList<Integer> nameFreq;
    public CharactersInPlay(){
        charNames = new ArrayList<String>();
        nameFreq = new ArrayList<Integer>();
    }
    public void update(String name){
        int index = charNames.indexOf(name);
        if(index==-1){
            charNames.add(name);
            nameFreq.add(1);
        }
        else{
            int value = nameFreq.get(index);
            nameFreq.set(index,value+1);
        }
    }
    public void findAllCharacters (){
        charNames.clear();
        nameFreq.clear();
        FileResource fr = new FileResource();
        String name = "";
        for(String line : fr.lines()){
            if(line.contains(".")){
                name = line.substring(0,line.indexOf("."));
                update(name);
            }
        }
    }
    public void charactersWithNumParts(int num1, int num2){
        //Method filters out names in the charNames ArrayList if count is
        //lies between num1 and num2, both inclusive. Note: num1 < num2 assumed
        
        for(int i =0;i<nameFreq.size();i++){
            if(nameFreq.get(i)>=num1 && nameFreq.get(i)<=num2)
            System.out.println(charNames.get(i) + "\t" + nameFreq.get(i));
        }
    }
    public void tester(){
        findAllCharacters();
        System.out.println("List of Names:");
        for(int i =0;i<nameFreq.size();i++){
            System.out.println(charNames.get(i) + "\t" + nameFreq.get(i));
        }
        System.out.println("\n\nAfter filtering:\n");
        charactersWithNumParts(2,10);
    }
}
