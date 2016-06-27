
/**
 * Write a description of findNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import edu.duke.*;
public class findNameInPlay {
    private ArrayList<String> charNames;
    private ArrayList<Integer> nameFreq;
    public findNameInPlay(){
        charNames = new ArrayList<String>();
        nameFreq = new ArrayList<Integer>();
    }
    public void findNames(){
        charNames.clear();
        nameFreq.clear();
        FileResource fr = new FileResource();
        String name = "";
        for(String line : fr.lines()){
            if(line.contains(".")){
                name = line.substring(0,line.indexOf("."));
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
        }
    }
    public void tester(){
        findNames();
        System.out.println("List of Names:");
        for(int i =0;i<nameFreq.size();i++){
            System.out.println(charNames.get(i) + "\t" + nameFreq.get(i));
        }
        System.out.println("\n\nAfter filtering:\n");
        for(int i =0;i<nameFreq.size();i++){
            if(nameFreq.get(i)>1)
            System.out.println(charNames.get(i) + "\t" + nameFreq.get(i));
        }
    }
}
