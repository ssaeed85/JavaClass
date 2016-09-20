import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        /*
        //Print out original quake entry list
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        System.out.println("\n\n\n");
        */
        
        //Magnitude + Depth filter
        //Filter f = new MinMagFilter(4.0);
        Filter f1 = new MagnitudeFilter(3.5,4.5);
        Filter f2 = new DepthFilter(-55000.0,-20000.0);
        ArrayList<QuakeEntry> m6  = filter(list, f1); 
        ArrayList<QuakeEntry> m7  = filter(m6, f2);
        for (QuakeEntry qe: m7) { 
            System.out.println(qe);
        } 
        
        System.out.println("# quakes filtered: " + m7.size());
        
        
        /*
        //Distance filter
        Location loc = new Location(39.7392, -104.9903); 
        Filter fDist = new DistanceFilter(loc,1000000);
        Filter fPhrase = new PhraseFilter("a","end");
        ArrayList<QuakeEntry> m8  = filter(list, fDist); 
        ArrayList<QuakeEntry> m9  = filter(m8, fPhrase);
        for (QuakeEntry qe: m9) { 
            System.out.println(qe);
        } 
        System.out.println("# quakes filtered: " + m9.size());
        */
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
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
    public void testMatchAllFilter(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        System.out.println("# quakes read: "+list.size());
        
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(1.0,4.0));
        maf.addFilter(new DepthFilter(-180000.0 ,-30000.0));
        maf.addFilter(new PhraseFilter("o","any"));
        
        ArrayList<QuakeEntry> out = filter(list,maf);
        
        for (QuakeEntry qe: out) { 
            System.out.println(qe);
        }
        System.out.println(maf.getName());
        System.out.println("# quakes filtered: " + out.size());
    }
    public void testMatchAllFilter2(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        System.out.println("# quakes read: "+list.size());
        
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0,5.0));
        Location loc = new Location (55.7308, 9.1153);
        maf.addFilter(new DistanceFilter(loc ,3000000 ));
        maf.addFilter(new PhraseFilter("e","any"));
        
        ArrayList<QuakeEntry> out = filter(list,maf);
        
        for (QuakeEntry qe: out) { 
            System.out.println(qe);
        } 
        System.out.println(maf.getName());
        System.out.println("# quakes filtered: " + out.size());
    }
}
