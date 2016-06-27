
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int dKey;
    
    public CaesarCipher(int key){
        //Constructor for class. Creates shifted alphabet using provided key
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key) +
                            alphabet.substring(0,key);
        dKey = 26-key;
    }
    
    public String encrypt(String msg){
        StringBuilder encrypted = new StringBuilder(msg);

        for(int i=0; i < msg.length() ; i++){
           char currChar = encrypted.charAt(i);
           int idx = alphabet.indexOf(Character.toLowerCase(currChar));
           if (idx!=-1){
               char newChar = shiftedAlphabet.charAt(idx);
               if(Character.isUpperCase(currChar))
                   newChar = Character.toUpperCase(newChar);
               encrypted.setCharAt(i, newChar);
           }
        }
        
        return encrypted.toString();   
    }

    public String decrypt(String encrypted){
        CaesarCipher cc = new CaesarCipher(dKey);
        return cc.encrypt(encrypted);
    }
    
    public void testEncrypt(String msg,int key){
        CaesarCipher cc = new CaesarCipher(key);
        System.out.println(cc.encrypt(msg));
    }
}
