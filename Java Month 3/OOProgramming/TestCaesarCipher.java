
/**
 * Write a description of TestCaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class TestCaesarCipher {
    private String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public int[] countLetters(String st){
         //Counts letter frequency and stores into integer array
        int[] letterFreq = new int[26];
        for(int i=0; i<st.length() ; i++){
            char ch = Character.toLowerCase(st.charAt(i));
            int idx = alphabet.indexOf(ch);
            if(idx!=-1){
                //Index has to be a positive value. -1 implies not in alphabet string
                letterFreq[idx]++;
            }
        }
        return letterFreq;
    }
    public int maxIndex(int[] array){
        //Method to find index in integer array with highest value
        int maxIndex=0;
        int max=0;
        for(int i=0 ; i<array.length ; i++){
            if(array[i] > max){
                maxIndex = i;
                max = array[i];
            }
        }
        return maxIndex;
    }
    public int decryptionKey(int maxIndex){
        int dkey = maxIndex-4;
        if (maxIndex<4){
            dkey = 26 - (4-maxIndex);
        }
        return dkey;
    }
    public String breakCaesarCipher(String input){
        int[] letterFreq = countLetters(input);
        int maxDex = maxIndex(letterFreq);
        int dkey = decryptionKey(maxDex);
        
        System.out.println("Decryption key determined: " +dkey);
        CaesarCipher c2 = new CaesarCipher(dkey);
        String message = c2.decrypt(input);
        return message;
    }
    public void simpleTests(){
        FileResource fr = new FileResource("simpleTestFile.txt");
        String input = fr.asString();
        System.out.println(input);
        CaesarCipher c1 = new CaesarCipher(18);
        String encrypted = c1.encrypt(input);
        System.out.println(encrypted);        
        //System.out.println(c1.decrypt(encrypted));        
        System.out.println(breakCaesarCipher(encrypted));
    }
}
