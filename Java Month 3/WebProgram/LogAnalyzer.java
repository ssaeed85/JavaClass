
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
    private HashMap<String, ArrayList<String>> iPsByDayMap;
    private HashMap<String,Integer> IPCountMap;
    private ArrayList<String> IPAddressList;
    private ArrayList<String> maxCountIPsList;
    private ArrayList<LogEntry> records;
    private ArrayList<String> uniqueIpListForDay;
    private ArrayList<String> filteredIPList; //used for various different filtering methods
    
    private String IPAddr; //used to hold Ip address string for current Log Entry
     
    public LogAnalyzer() {
        iPsByDayMap = new HashMap<String, ArrayList<String>>();
        IPCountMap = new HashMap<String,Integer>();
        IPAddressList = new ArrayList<String>();
        maxCountIPsList = new ArrayList<String>();
        records = new ArrayList<LogEntry>();
        uniqueIpListForDay = new ArrayList<String>();
        filteredIPList = new ArrayList<String>();
        IPAddr = "";        
    }public void printStringArrayList(ArrayList<String> list){
        int count =0;
        for(String listItem : list){
            count++;
            System.out.println(count+".\t\t"+listItem);
        }        
    }
    public void readFile(String filename) {
        FileResource fr = new FileResource(filename);
        WebLogParser wParser = new WebLogParser();
        for(String line : fr.lines()){
            records.add(wParser.parseEntry(line));
        }
    }
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
    public int countUniqueIps(){
        filteredIPList.clear();        
        for(LogEntry le : records){
            IPAddr = le.getIpAddress();
            if(!filteredIPList.contains(IPAddr)){
                //System.out.println(IPAddr);
                filteredIPList.add(IPAddr);
            }
        }
        int count = filteredIPList.size();  
        System.out.println("The number of unique IP Addresses: " + count);
        return count;
    }
    public void printAllHigherThanNum(int num){
        System.out.println("Entries with a status code higher than " + num + " are: ");
        for(LogEntry le : records){
            if(le.getStatusCode()>num)
                System.out.println(le);            
        }
    }
    public int countUniqueIPsInRange (int low, int high){
        ///Returns count of unique IPAddress within a given range
        int StatusCode = 0;

        for(LogEntry le : records){
            IPAddr = le.getIpAddress();
            StatusCode = le.getStatusCode();
            if(!filteredIPList.contains(IPAddr)){
                if(StatusCode >= low && StatusCode <= high){
                    //System.out.println(IPAddr);
                    filteredIPList.add(IPAddr);
                }
            }
        }
        int count = filteredIPList.size(); 
        return count;
    }
    public ArrayList<String> uniqueIPVisitsOnDay(String date){
        //Returns list of unique IPAddress that visited on a day
        uniqueIpListForDay.clear();
        String strLogEntry="";
        String dateLC = date.toLowerCase();
        
        for(LogEntry le : records){
            strLogEntry = le.toString();
            strLogEntry = strLogEntry.toLowerCase();
            if(strLogEntry.contains(dateLC)){
                IPAddr = le.getIpAddress();
                if(!uniqueIpListForDay.contains(IPAddr)){
                    uniqueIpListForDay.add(IPAddr);
                    System.out.println(strLogEntry);
                }
            }
        }
        return uniqueIpListForDay;
    }
    public HashMap<String,Integer> countVisitsPerIP(){
        //Returns a HashMap. key: IPAddress, value: count of visits
        IPCountMap.clear();
        
        for(LogEntry le:records){
            IPAddr = le.getIpAddress();
            if(IPCountMap.containsKey(IPAddr))
                IPCountMap.put(IPAddr,IPCountMap.get(IPAddr)+1);
            else
                IPCountMap.put(IPAddr,1);
        }
        return IPCountMap;
    }
    public int mostNumberVisitsByIP(){
        //Returns the highest number of times any IpAdress visited
        countVisitsPerIP();
        int max = 0;
        for(String key : IPCountMap.keySet()){
            if(IPCountMap.get(key)>max){
                max = IPCountMap.get(key);
            }
        }
        return max;
    }
    public ArrayList<String> iPsMostVisits(){
        //Retiurns a list of IP Address with the max visit count
        countVisitsPerIP();
        maxCountIPsList.clear();
        int max = mostNumberVisitsByIP();
            
        for(String key : IPCountMap.keySet()){
            if(IPCountMap.get(key) == max){
                maxCountIPsList.add(key);
                System.out.println(key);
            }
        }
        return maxCountIPsList;
    }
    public HashMap<String,ArrayList<String>> iPsForDays(){
        //Returns a HashMap. key: Date, Value: List of IPAdress visits for that Date. 
        //IP addresses should be recorded for every occurence of it even if multiple occurences exist.
        iPsByDayMap.clear();
        Date TimeStamp=null;
        String Date = ""; //Date String got from default toString() method for above TimeStamp
        
        for(LogEntry le : records){
           IPAddr = le.getIpAddress();
           TimeStamp = le.getAccessTime();
           Date = TimeStamp.toString().substring(4,10);
           IPAddressList.clear();
           
           if(iPsByDayMap.containsKey(Date)){
               //If HashMap contains Date: retrieve current IP list stored for that Date
               iPsByDayMap.get(Date).add(IPAddr);
           }
           else{
               
               IPAddressList.add(IPAddr);
               iPsByDayMap.put(Date,IPAddressList);  
            }
        }    
        
        //print out HashMap. Can be commented out once tested
        System.out.println("IP addresses organized by date are as follows:");
        for(String key: iPsByDayMap.keySet()){
            System.out.println("Date:\t" + key );
            printStringArrayList(iPsByDayMap.get(key));
            System.out.println("\n\n");
        }
        return iPsByDayMap;            
    }
    public String dayWithMostIPVisits(){
        //Returns day with the most unique IPAddress (user) visits
        iPsForDays();
        String DateOfMostVisits="";
        int maxCount = 0;
        
        for(String Date : iPsByDayMap.keySet()){
            if(iPsByDayMap.get(Date).size() > maxCount){
                maxCount = iPsByDayMap.get(Date).size();
                DateOfMostVisits = Date;
            }
        }
        System.out.println("Date of most IP hits: " + DateOfMostVisits);
        return DateOfMostVisits;
    }
    public ArrayList<String> iPsWithMostVisitsOnDay(String Date){   
        //Returns ArrayList of IP Addresses that had the most access on the given date
        //An ArrayList ensures it returns all IP addresses if there are multiple that accessed the 
        //website on a given Date
        //Note: Date format MMM DD
        iPsForDays();  
        ArrayList<String> iPwithHighestAccessCount = new ArrayList<String>();
        filteredIPList = iPsByDayMap.get(Date);
        HashMap<String,Integer> tempMap = new HashMap<String,Integer>();
        int max = 0;
        
        for(String IPAddress: filteredIPList){
            if(tempMap.containsKey(IPAddress))
                tempMap.put(IPAddress,IPCountMap.get(IPAddress)+1);
            else
                tempMap.put(IPAddress,1);
                
            if(tempMap.get(IPAddress)>max)
                max = tempMap.get(IPAddress);
        }
        filteredIPList.clear();
        for(String key:tempMap.keySet()){
            if(tempMap.get(key)==max){
                filteredIPList.add(key);
                System.out.println(key);
            }
        }
        return filteredIPList;
    }
}

