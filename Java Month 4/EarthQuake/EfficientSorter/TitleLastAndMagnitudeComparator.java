
/**
 * Write a description of TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
    public int compare(QuakeEntry qe1, QuakeEntry qe2){
        String q1 = getLastWord(qe1);
        String q2 = getLastWord(qe2);
        int answer = q1.compareTo(q2);
        if(answer ==0)
            answer = Double.compare(qe1.getMagnitude(), qe2.getMagnitude());
        return answer;
    }
    public String getLastWord(QuakeEntry qe){
        String sentence = qe.getInfo();
        return sentence.substring(sentence.lastIndexOf(" ")+1);
    }
}
