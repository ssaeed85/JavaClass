
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
    
    public boolean isVowel(char ch){
        String vowels = "AEIOUaeiou";
        boolean result=false;
        if (vowels.contains(Character.toString(ch)))
            result = true;
        return result;
    }
    
    public String replaceVowels(String phrase, char ch){
        String vowels = "AEIOUaeiou";
        StringBuilder result = new StringBuilder(phrase);
        for (int i=0 ; i < phrase.length() ; i++){
            if(isVowel(phrase.charAt(i)))
                result.setCharAt(i, ch);
            else
                result.setCharAt(i, phrase.charAt(i));             
        }
        return result.toString();
    }
    
    public String emphasize(String phrase, char ch){
        StringBuilder result = new StringBuilder(phrase);
        for( int i=0 ; i < phrase.length() ; i++){
            if(phrase.charAt(i)==Character.toLowerCase(ch) || phrase.charAt(i)==Character.toUpperCase(ch)){
                if(i%2==0)
                    result.setCharAt(i,'*');
                else
                    result.setCharAt(i,'+');
                }
            }
        return result.toString();
    }
}
