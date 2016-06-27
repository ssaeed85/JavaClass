
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
public class CaesarBreaker {
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
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
    
    public String decrypt(String encrypted){
        int[] letterFreq = countLetters(encrypted);
        int maxDex = maxIndex(letterFreq);
        int dkey = decryptionKey(maxDex);
        
        System.out.println("Decryption key determined: " +dkey);
        CaesarCipher cc = new CaesarCipher();
        String message = cc.encrypt(encrypted, 26 - dkey);
        return message;
    }
    
    public int maxIndex(int[] array){
        //Method to find index in integer array with highest value
        int maxIndex=0;
        int max=0;
        
        for(int i=0 ; i<array.length ; i++){
            if(array[i] > max){
                maxIndex = i;
                max = array[i];
                //System.out.println("Index: " + i + "\tValue: " + array[i] + "\tCurrent max index: " + maxIndex + "\tCurrent max: " + max);
            }
        }
        //System.out.println("Index of highest value: " + maxIndex);
        return maxIndex;
    }
    public int decryptionKey(int maxIndex){
        int dkey = maxIndex-4;
        if (maxIndex<4){
            dkey = 26 - (4-maxIndex);
        }
        return dkey;
    }
    public String halfOfString(String st, int start){
        //Method to create a new string from start postion and appending every other character from given string
        String result="";
        for(int i=start; i<st.length(); i+=2){
            result = result + st.charAt(i);
        }
        return result;
    }
    public String decryptTwoKeys(String encrypted){
        //Method to decrypt a string that has been encrypted using two keys
        //System.out.println("Encrypted string: \n" + encrypted);
        String[] st = new String[2];
        int[][] letterFreq = new int[2][26];
        int[] maxIdx = new int[2];
        int[] dkey = new int[2];
        String[] decryptHalf = new String[2];
        for(int i=0;i<2;i++){
            //Split the encrypted strings into two separate strings. Store as new strings   
            st[i] = halfOfString(encrypted,i);
            
            //System.out.println("\n\n" + i + " half of string:\t" + st[i].substring(0,20)); //For debugging
            
            //For each individual string half find the decryption key and print it
            letterFreq[i]=countLetters(st[i]);
            maxIdx[i] = maxIndex(letterFreq[i]);
            dkey[i] = decryptionKey(maxIdx[i]);
            System.out.println("Decription key " + i + ":\t" + dkey[i]);
            
            /* //Additional code for debugging
            CaesarCipher cc = new CaesarCipher();
            decryptHalf[i] = cc.encrypt(st[i],dkey[i]);   
            System.out.println("\n\n" + i + " decrypted string: \t" + decryptHalf[i].substring(0,20));
            */
        }
        //Use the original cipher encryption with two keys method using the newly found decryption keys to decrypt message
        CaesarCipher cc = new CaesarCipher();
        String decrypted = cc.encryptTwoKeys(encrypted, 26-dkey[0],26-dkey[1]);
        return decrypted;
    }
    public void testDecryptTwoKeys(){
        FileResource fr = new FileResource("test.txt");
        String text = fr.asString();
        System.out.println("Original text: \n" + text);
        
        
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encryptTwoKeys(text,10,23);
        System.out.println("\n\n\n\n\n\nEncrypted text: \n" + encrypted);
        
        System.out.println("\n\n\n\n\n\nDecrypted text: \n");
        String decrypted = decryptTwoKeys(encrypted);
        //System.out.println(decrypted);
        
        
    }
    public void testDecrypt(){
        FileResource fr = new FileResource("test.txt");
        String text = fr.asString();
        System.out.println("Original text: \n" + text);
        
        
        int[] letterFreq = countLetters(text);
        System.out.println("\n\nFreq of E: " + letterFreq[4]);
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encrypt(text,10);
        System.out.println("\n\n\n\n\n\nEncrypted text: \n" + encrypted);
        int[] encryptedLetterFreq = countLetters(encrypted);
        
        int max=0;
        for(int i=0;i<encryptedLetterFreq.length;i++){
            if(encryptedLetterFreq[i]>max){
                max = encryptedLetterFreq[i];
            }
        }
        System.out.println("\n\nHighest letter count: " + max);
        
        System.out.println("\n\n\n\n\n\nDecrypted text: \n");
        String decrypted = decrypt(encrypted);
        System.out.println(decrypted);
    }
    
}
