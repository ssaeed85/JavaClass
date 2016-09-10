  import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //Starting with whichSlice append every totalSlice-th char to result in returned string
        StringBuilder slicedString = new StringBuilder();
        for(int i = whichSlice ; i < message.length()  ; i=i+totalSlices){
            char c = message.charAt(i);
            //System.out.println(c);
            slicedString = slicedString.append(c);
        }
        //System.out.println(slicedString.toString());
        return slicedString.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        String[] encryptedStrings = new String[klength];
        
        for(int i = 0 ;i<klength;i++){
            encryptedStrings[i] = sliceString(encrypted, i, klength);
            //System.out.println(encryptedStrings[i]);
        }
        
        for(int i = 0 ; i<klength; i++){
            CaesarCracker cc = new CaesarCracker(mostCommon); 
            key[i]=cc.getKey(encryptedStrings[i]);
            //System.out.println(key[i]);
        }
        
        //System.out.println("The Key is: \n");
        //System.out.println(Arrays.toString(key));
        //System.out.println("\n\n\n\n");
        return key;
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> set = new HashSet<String>();
        
        for(String line : fr.lines()){
            line = line.toLowerCase();
            set.add(line);
        }
        
        return set;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        char mostCommon='e';
        int[] letterFreq = new int[26];
        
        CaesarCracker cc = new CaesarCracker();
        for(String word:dictionary){
            letterFreq = cc.countLetters(word);
        }
        int maxIndex = cc.maxIndex(letterFreq);
        mostCommon = alph.charAt(maxIndex);
        return mostCommon;
    }
    
    public int countWords(String message, HashSet<String> dictionary){
        int count=0;
        String[] wordSet = message.split("\\W+");
        for(int i=0;i<wordSet.length;i++){
            String testWord = wordSet[i].toLowerCase();
            if (dictionary.contains(testWord))
                count++;
        }
        
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dictionary){
        //Language is known (English). Therefore, most common letter = 'e'. Unknown key length
        //Brute force for keylengths 1 through 100. Check against dictionary for most correct keylength
        int maxCorrectWordCount = 0;
        int bestKeyLength = 0;
        //int[] bestKey = new int[];
        for(int keyLength=1;keyLength<=100;keyLength++){
            int correctWordCount = 0;
            int[] key = tryKeyLength(encrypted, keyLength,mostCommonCharIn(dictionary));
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            correctWordCount = countWords(decrypted,dictionary);
            if(correctWordCount>maxCorrectWordCount){
                maxCorrectWordCount=correctWordCount;
                bestKeyLength=keyLength;
                //bestKey = key;
            }
        }
        
        float pct = (float)maxCorrectWordCount/dictionary.size() * 100;
        System.out.println("The best key length is: " + bestKeyLength
                                  + " with a max correct word count of " + maxCorrectWordCount
                                   + " out of " + dictionary.size() + "\t(" + pct + "%)");
        
        
        int[] bestKey = tryKeyLength(encrypted, bestKeyLength,mostCommonCharIn(dictionary));
        VigenereCipher vc = new VigenereCipher(bestKey);
        String decrypted = vc.decrypt(encrypted);
        System.out.println("Test:\t"+countWords(decrypted,dictionary));
        //System.out.println(decrypted);
        return decrypted;
    }
    
    public String breakForAllLanguages(String encrypted, HashMap<String,HashSet<String>> languages){
        int maxCorrectWordCount=0;
        String bestLang="";
        for(String key: languages.keySet()){
            System.out.println("Testing for language: " + key);
            String decrypted = breakForLanguage(encrypted,languages.get(key));
            //correctWordCount.put(key,countWords(decrypted,languages.get(key)));
            int correctWordCount = countWords(decrypted,languages.get(key));
            //System.out.print("\t"+correctWordCount);
            if (correctWordCount>maxCorrectWordCount){
                maxCorrectWordCount = correctWordCount;
                bestLang = key;
            }
        }
        
        System.out.println("\n\n\n\nThe best language by correct word count is : " + bestLang);
        String decrypted=breakForLanguage(encrypted,languages.get(bestLang));    
        //System.out.println("The decrypted message is: \n");
        //System.out.println(decrypted);
        return decrypted;        
    }
    
    public void breakVigenere(){
        FileResource file = new FileResource();
        String encrypted = file.asString();
        
        HashSet<String> dictDA = readDictionary(new FileResource("dictionaries/Danish"));
        HashSet<String> dictDU = readDictionary(new FileResource("dictionaries/Dutch"));
        HashSet<String> dictEG = readDictionary(new FileResource("dictionaries/English"));
        HashSet<String> dictFR = readDictionary(new FileResource("dictionaries/French"));
        HashSet<String> dictGE = readDictionary(new FileResource("dictionaries/German"));
        HashSet<String> dictIT = readDictionary(new FileResource("dictionaries/Italian"));
        HashSet<String> dictPO = readDictionary(new FileResource("dictionaries/Portuguese"));
        HashSet<String> dictSP = readDictionary(new FileResource("dictionaries/Spanish"));
        HashMap<String,HashSet<String>> languages = new HashMap<String,HashSet<String>>();
        
        languages.put("Danish",dictDA);
        languages.put("Dutch",dictDU);
        languages.put("English",dictEG);
        languages.put("French",dictFR);
        languages.put("German",dictGE);
        languages.put("Italian",dictIT);
        languages.put("Portuguese",dictPO);
        languages.put("Spanish",dictSP);
        
        
        String decrypted = breakForAllLanguages(encrypted,languages);
        
        System.out.println("The decrypted message is: \n");
        System.out.println(decrypted);
        //VigenereCipher vc = new VigenereCipher(key);
        //String decrypted = vc.decrypt(encrypted);
        //System.out.println(decrypted);
        
        //breakForLanguage(encrypted, languages);
    }
    
    public void testTryKeyLength(int klength, char mostCommon){
        FileResource file = new FileResource();
        String encrypted = file.asString();
        
        int[] key = tryKeyLength(encrypted, klength, mostCommon); //This is to test for a known key length   
        System.out.println(Arrays.toString(key)+ "\n\n");
    }
   
    public void testWordCountOnDecryption(int testKeyLength, char mostCommon){
        FileResource file = new FileResource();
        FileResource dictFR = new FileResource("dictionaries/English");
        String encrypted = file.asString();
        HashSet<String> dictionary = readDictionary(dictFR);
        int[] key = tryKeyLength(encrypted, testKeyLength, mostCommon);
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        int correctWordCount = countWords(decrypted,dictionary);
        System.out.println("The number of correct words for a key length of " + testKeyLength + " is " + 
                        + correctWordCount + " out of " + dictionary.size());
    }
    
    public void testCommonLetterDictionary(){
        FileResource dictFR = new FileResource("dictionaries/English");
        HashSet<String> dictionary = readDictionary(dictFR);
        char mostCommonChar = mostCommonCharIn(dictionary);
        System.out.println("The most common letter in the English alphabet is \'"
                            + mostCommonChar + "\'");
    }
}
