
/**
 * Write a description of DistanceFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter
{
    private Location from;
    private double maxDist;
    
    public DistanceFilter(Location loc, double dist){
        from = loc;
        maxDist = dist;
    }

    public boolean satisfies(QuakeEntry qe) { 
        Location loc = qe.getLocation();        
        double dist= loc.distanceTo(from);        
        return dist<maxDist;
    } 
    public String getName(){
        return "Distance";
    }
}