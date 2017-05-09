
/**
 * Write a description of MinutesFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MinutesFilter implements Filter{
    private int min;
    private int max;
    public MinutesFilter(int min, int max){
        if(min<=max){
            this.min = min;
            this.max = max;
        }
        else{
            //Added code to ensure that the smaller of the two is used as min
            this.min = max;
            this.max = min;
        }
    }
    public boolean satisfies(String id){
        return (MovieDatabase.getMinutes(id) >= min && MovieDatabase.getMinutes(id) <= max);
    }
}
