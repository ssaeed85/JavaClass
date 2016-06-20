
/**
 * LinkFinder is a class associated with finding and manipulating various links available on a particular webpage. The following methods are present in this class:
 * 
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
public class LinkFinder 
{
    public StorageResource findURLs(String url){
        StorageResource sr = new StorageResource();
        int start = 0;
        int pos = 0;
        int stop =0;
        int length=url.length();
        while(true){
            pos = url.indexOf("href=\"http",start);
            stop = url.indexOf("\"",pos+7);
            if((pos==-1)||(pos>=length)||(stop==-1)||(stop>=length)){
                break;
            }
            String urlString = url.substring(pos+6,stop);
            sr.add(urlString);
            start=stop+1;
        }
        return sr;
    }
    public boolean existsInStorage(StorageResource sr, String url){
        for(String srData : sr.data()){
            if(url.equalsIgnoreCase(srData)){
                return true;
            }
        }
        return false;
    }
    public void printURLs(StorageResource sr){
        for(String url : sr.data()){
            System.out.println(url);
        }
    }
    public String extractURL(String word){
        int index =0;
        int start=0;
        int stop =0;
        while(index<word.length()){
            start = word.indexOf("href");
            start = word.indexOf("\"",start);
            stop = word.indexOf("\"",start+1);
            System.out.println("URL    : " +word);
            System.out.println("Extract: " +word.substring(start+1,stop)); 
        }
        return word.substring(start+1,stop);
    }   
    public void secureURLCount(StorageResource sr){
        int count=0;
        for (String url : sr.data()){
            if (url.contains("https")){
                count++;
            }
        }
        System.out.println("Number of secure URLs on page: " + count);
    }
    public void dotCount(StorageResource sr){
        int count=0;
        for (String url : sr.data()){
            int index=0;
            while(true){
                index = url.indexOf(".",index);
                if((index==-1) || (index >= url.length())){
                    break;
                }
                else {
                    count++;
                    index++;
                }            
            }
        }
        System.out.println("Number of dots: "+count);
    }
    public void containsComCount(StorageResource sr){
        int count=0;
        for (String url : sr.data()){
            if(url.contains(".com")){
                count++;
            }
        }
        System.out.println("Links containing \".com\": " + count);
    }
    public void endsInComCount(StorageResource sr){
        int count = 0;
        for(String url : sr.data()){
            if((url.endsWith(".com")||url.endsWith(".com/"))){
                count++;
            }
        }
        System.out.println("Links ending in \".com\" or \".com/\": " + count);
    }
    public void testURLExtractionNCounts(){
        StorageResource sr = new StorageResource();
        URLResource url = new URLResource("http://www.dukelearntoprogram.com/course2/data/newyorktimes.html");
        //FileResource url = new FileResource("new.fa");
        String s= url.asString();
        int length=s.length();
        System.out.println(length);
        sr=findURLs(url.asString());
        //printURLs(sr);
        //System.out.println("URL:    " + url);
        System.out.println("Number of URLs on page: " +sr.size());
        secureURLCount(sr);
        dotCount(sr);
        containsComCount(sr);
        endsInComCount(sr);
    }
    }

