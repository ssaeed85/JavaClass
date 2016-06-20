
/**
 * Write a description of export here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
public class export {
   public void countryInfo(CSVParser parser, String countryOfInterest){
       boolean countryFound=false;
       for (CSVRecord record: parser){
           String country = record.get("Country");
           if (country.contains(countryOfInterest)){
               String exports = record.get("Exports");
               String value = record.get("Value (dollars)");
               
               System.out.print(country + " ");
               System.out.print(exports + " ");
               System.out.println(value);
               countryFound = true;
            }
        }
       if (countryFound == false){
            System.out.println("COUNTRY NOT FOUND");
       }
    }
   public void listExportersTwoProducts(CSVParser parser,String exportItem1,String exportItem2){
       System.out.println("Countries that export both " + exportItem1 + " and " + exportItem2 + ":");
       for (CSVRecord record: parser){
           String exports = record.get("Exports");
           if (exports.contains(exportItem1) && exports.contains(exportItem2)){
               String country = record.get("Country");
               System.out.println(country);
            }
       }   
    }
    public void numberOfExporters(CSVParser parser, String exportItem){
        int count=0;
        for(CSVRecord record: parser){
            String exports = record.get("Exports");
            if(exports.contains(exportItem)){
                count++;
            }
        }
        System.out.println("Number of countries who export " + exportItem + ": " + count);
    }
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record: parser){
            String value = record.get("Value (dollars)");
            if (value.length() > amount.length()){
                System.out.println(record.get("Country") + " " + value);
            }
        }
    }
   public void tester(){
       FileResource fr = new FileResource();
       CSVParser parser = fr.getCSVParser();
       
       //String countryOfInterest = "Nauru";
       String export1 = "cotton";
       String export2 = "flowers";
       //countryInfo(parser, countryOfInterest);
       
       //parser = fr.getCSVParser();
       //listExportersTwoProducts(parser, export1, export2);
       
       parser = fr.getCSVParser();
       numberOfExporters(parser,"cocoa");
       
       parser = fr.getCSVParser();
       String amount = "$999,999,999,999";
       bigExporters(parser, amount);
    }
}

