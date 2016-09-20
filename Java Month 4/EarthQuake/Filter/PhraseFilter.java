
/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter {
    private String phrase;
    private String loc;
    
    public PhraseFilter(String ph, String l){
        phrase = ph;
        loc = l;
    }
    
    public boolean satisfies(QuakeEntry qe){
        String title = qe.getInfo();
        return( 
            loc == "start" && title.startsWith(phrase) ||
            loc == "end" && title.endsWith(phrase) ||
            loc == "any" && title.contains(phrase)
            );
    }
    public String getName(){
        return "Phrase";
    }
}
