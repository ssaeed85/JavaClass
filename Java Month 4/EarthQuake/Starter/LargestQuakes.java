
/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class LargestQuakes {
    private int indexOfLargest(ArrayList<QuakeEntry> quakeData){
        //Returns index of quake entry with highest magnitude
        int maxIndex = 0;
        for(int k=1;k<quakeData.size();k++)
        {
            if(quakeData.get(k).getMagnitude() > quakeData.get(maxIndex).getMagnitude())
                maxIndex =k;
        }
        return maxIndex;
    }    
    private void printQEList(ArrayList<QuakeEntry> quakeData){
        //All this method does is print out the quake data list leveraging the toString() method 
        //for a quake entry
        for(QuakeEntry qe: quakeData){
            System.out.println(qe);
        }
    }
    public ArrayList<QuakeEntry> getLargest (ArrayList<QuakeEntry> quakeData, int howMany)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = quakeData;
        
        for(int j=0;j<howMany && copy!=null;j++)
        {
            int index = indexOfLargest(copy);
            answer.add(copy.get(index));
            copy.remove(index);
        }
        return answer;
    }
    
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        //printQEList(list);
        System.out.println(list.size() + " quakes read");
        
        ArrayList<QuakeEntry> filtered = getLargest(list,5);
        printQEList(filtered);
    }
}
