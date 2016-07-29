
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    private String fName = "weblog3-short_log";
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    public void printStringArrayList(ArrayList<String> list){
        int count =0;
        for(String listItem : list){
            count++;
            System.out.println(count+". "+listItem);
        }        
    }
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        la.printAll();
    }    
    public void testUniqueIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        System.out.println("Number of unique IP Adresses: " + la.countUniqueIps());
    }
    public void testPrintAllHigherThanStatusCode(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        la.printAllHigherThanNum (400);    
    }
    public void testCountUniqueIPsInRange(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        System.out.println("\nUnique IP Addresses between 200 and 299 are: " + 
                                la.countUniqueIPsInRange(200,299)); 
        System.out.println("Unique IP Addresses between 300 and 399 are: " + 
                                la.countUniqueIPsInRange(300,399));
    }
    public void testUniqueIPInDay(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        ArrayList<String> list = la.uniqueIPVisitsOnDay("Mar 24");
        System.out.println("\n\tNumber of unique IP Visits on selected date: " + 
                        list.size());
    }
    public void testCountVisitsPerIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        HashMap<String,Integer> IPCountMap = new HashMap<String,Integer>();
        IPCountMap = la.countVisitsPerIP();
        System.out.println("\n\tIP Addresses and corresponding visit count:");
        for(String key : IPCountMap.keySet()){
            System.out.println(key + ":\t" + IPCountMap.get(key));
        }   
    }
    public void testmostNumberVisitsByIP(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        int max = la.mostNumberVisitsByIP();
        System.out.println("\n\tHighest number of times any IP Address visited:\t" + max);
    }
    public void testiPsMostVisits(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        System.out.println("\n\tList of IP Addresses with max visit count:");
        la.iPsMostVisits();
    }
    public void testiPsForDays(){
        LogAnalyzer la = new LogAnalyzer();
        la.readFile(fName);
        la.iPsForDays();
    }
}
