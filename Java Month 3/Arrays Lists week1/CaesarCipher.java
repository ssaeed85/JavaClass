
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public String encrypt(String input, int key){
        StringBuilder encrypted = new StringBuilder(input);
        //String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
        
        //System.out.println(alphabet);
        //System.out.println(shiftedAlphabet);

        for(int i=0; i < input.length() ; i++){
           char currChar = encrypted.charAt(i);
           char newChar = ' ';
           int idx = alphabet.indexOf(Character.toLowerCase(currChar));
           if (idx!=-1){
               newChar = shiftedAlphabet.charAt(idx);
               if(Character.isUpperCase(currChar)){
                   //System.out.println(newChar);
                   newChar = Character.toUpperCase(newChar);
                   //System.out.println(newChar);
               }
               encrypted.setCharAt(i, newChar);
           }
        }
        // System.out.println(encrypted.toString());
        return encrypted.toString();   
    }
    
    public String encryptTwoKeys(String input, int key1, int key2){
        StringBuilder encrypted = new StringBuilder(input);
        String firstString="";
        String secondString="";
        for(int i=0 ; i < input.length() ; i=i+2){
            firstString = firstString + Character.toString(encrypted.charAt(i));
            //System.out.println(firstString);
        }
        for(int i=1 ; i < input.length() ; i=i+2){
            secondString = secondString + Character.toString(encrypted.charAt(i));
            //System.out.println(secondString);
        }
        String encryptedFirst = encrypt(firstString,key1);
        String encryptedSecond = encrypt(secondString,key2);
        String result = "";
        int length;
        if(encryptedFirst.length()>encryptedSecond.length())
            length = encryptedFirst.length();
        else
            length = encryptedSecond.length();
        String char1 = "";
        String char2 = "";
        for(int i=0; i < length ; i++){
            char1 = Character.toString(encryptedFirst.charAt(i));
            if(i<encryptedSecond.length())
                char2 = Character.toString(encryptedSecond.charAt(i));
            else
                char2 = "";    
            result = result + char1 + char2;
        }
        System.out.println(result);
        return result;
    }
    
    
}
