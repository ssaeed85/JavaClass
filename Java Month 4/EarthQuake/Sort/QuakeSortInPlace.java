
/**
 * Write a description of class QuakeSortInPlace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }
   
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    public int getLargestDepth(ArrayList<QuakeEntry> quakeData , int from) {
        int maxIdx = from;
        for (int i=from+1; i< quakeData.size(); i++) {
            if (quakeData.get(i).getDepth() > quakeData.get(maxIdx).getDepth()) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }
    
    private ArrayList<QuakeEntry> swapQE(ArrayList<QuakeEntry> in, int index1, int index2){
        ArrayList<QuakeEntry> out = new ArrayList<QuakeEntry>();
        QuakeEntry qeTemp = in.get(index1);
        in.set(index1,in.get(index2));
        in.set(index2,qeTemp);
        return in;
    }
    private void printQuakeData(ArrayList<QuakeEntry> in){
        for (QuakeEntry qe: in) { 
            System.out.println(qe);
        } 
    }
    private ArrayList<QuakeEntry> onePassBubbleSort(ArrayList<QuakeEntry> in,int numSorted){
        //One pass bubble sort by magnitude
        for(int i = 0 ; i < (in.size()-numSorted-1); i++){
            if(in.get(i).getMagnitude()>in.get(i+1).getMagnitude())
                swapQE(in,i,i+1);
        }
        return in;
    }
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> in){
        boolean flagSorted = true;
        for(int i = 0 ; i < (in.size()-1); i++){
            if(in.get(i).getMagnitude()>in.get(i+1).getMagnitude()){
                flagSorted = false;
                break;
            }
        }
        return flagSorted;
    }
    public void sortByLargestDepth(ArrayList<QuakeEntry> in) {
       //Sorted in descending order of depth 
       for (int i=0; i< 50; i++) {
            int minIdx = getLargestDepth(in,i);
            in = swapQE(in,i,minIdx);
       }
    }
    
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
       //Sorted in ascending order of magnitude
       for (int i=0; i< in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            in = swapQE(in,i,minIdx);
       }
    }
    public void sortByMagnitudeWithCheck (ArrayList<QuakeEntry> in) {
       int count =0;
       //Sorted in ascending order of magnitude
       for (int i=0; i< in.size(); i++) {
            if(checkInSortedOrder(in))
                break;
            int minIdx = getSmallestMagnitude(in,i);
            in = swapQE(in,i,minIdx);
            count++;
       }
       System.out.println("Number of passes needed: " + count);
       printQuakeData(in);          
    }
    
    public void sortByMagnitudeWithBubbleSort (ArrayList<QuakeEntry> in){
        for(int i=in.size();i>1;i--){
            onePassBubbleSort(in,in.size()-i);
            //System.out.println("\nAfter pass "+(in.size()-i));
            //printQuakeData(in);
        }
    }
    public void sortByMagnitudeWithBubbleSortWithCheck (ArrayList<QuakeEntry> in){
        int count=0;
        for(int i=in.size();i>1;i--){
            if(checkInSortedOrder(in))
                break;
            onePassBubbleSort(in,in.size()-i);
            //System.out.println("\nAfter pass "+(in.size()-i));
            //printQuakeData(in);
            count++;
        }
        System.out.println("Number of passes needed: " + count);
        printQuakeData(in);                    
    }

    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        //String source = "data/nov20quakedatasmall.atom";
        String source = "data/earthQuakeDataDec6sample1.atom";
        //String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
       
        System.out.println("read data for "+list.size()+" quakes");    
        /*
        sortByMagnitude(list);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        */
        //sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
        //sortByMagnitudeWithCheck(list);
        printQuakeData(list);
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                              qe.getMagnitude(),
                              qe.getInfo());
        }
        
    }
}
