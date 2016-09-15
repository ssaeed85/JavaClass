import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    private void printQEList(ArrayList<QuakeEntry> quakeData){
        //All this method does is print out the quake data list leveraging the toString() method 
        //for a quake entry
        for(QuakeEntry qe: quakeData){
            System.out.println(qe);
        }
    }
    
    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qe : quakeData)
        {
            if(qe.getMagnitude()> magMin)
                answer.add(qe);
        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for(QuakeEntry qe : quakeData)
        {
            Location loc = qe.getLocation();
            if(loc.distanceTo(from)/1000 < distMax)
                answer.add(qe);
        }
        
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, 
                                                double minDepth,
                                                double maxDepth)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            if(qe.getDepth()>minDepth && qe.getDepth()<maxDepth)
                answer.add(qe);
        }        
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String named, String phrase)
    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe: quakeData){
            String title = qe.getInfo();            
            if(named == "start" && title.startsWith(phrase) ||
               named == "end" && title.endsWith(phrase) ||
               named == "any" && title.contains(phrase) )
            {
                    answer.add(qe);
            }
        }        
        return answer;
    }

    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> bigQuakes = filterByMagnitude(list, 5.0);
        
        int quakeCount =0;
        printQEList(bigQuakes);
        System.out.println("Found " + bigQuakes.size() + " quakes that match the criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
         Location city =  new Location(38.17, -118.82);

        ArrayList<QuakeEntry> answer = filterByDistanceFrom(list, 1000,city);
       
        for(QuakeEntry qe: answer){
            System.out.println(qe.getLocation().distanceTo(city)/1000 + " km, " + qe.getInfo());
        }
        System.out.println(answer.size() + " quakes found");        
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        
        printQEList(list);
    }
    
    public void quakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        ArrayList<QuakeEntry> answer = filterByDepth(list, -10000,-8000);
        printQEList(answer);
        System.out.println("Found " + answer.size() + " quakes that match the criteria");
    }
    
    public void quakesByPhrase(String phrase, String phraseLoc){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        
        //String phrase = "California";
        //String phraseLoc = "end";
        ArrayList<QuakeEntry> answer = filterByPhrase(list, phraseLoc,phrase);
        printQEList(answer);
        System.out.println("Found " + answer.size() + " quakes that match " + phrase + " at " + phraseLoc);
    }
}
