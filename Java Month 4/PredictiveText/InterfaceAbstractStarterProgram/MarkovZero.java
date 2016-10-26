
/**
 * Write a description of MarkovZero here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovZero extends AbstractMarkovModel{
    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int k=0; k < numChars; k++){
            int index = myRandom.nextInt(myText.length());
            sb.append(myText.charAt(index));
        }
        
        return sb.toString();
    }    
    public String toString(){
        String s = "Markov model of order 0";
        return s;
    }
}
