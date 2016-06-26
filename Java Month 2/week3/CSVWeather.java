
/**
 * Write a description of CSVWeather here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVWeather {
    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord lowestTempRecord = null;
        
        for(CSVRecord current : parser){
            lowestTempRecord=lowestOfTwo(current, lowestTempRecord, "TemperatureF");
        }
        return lowestTempRecord;
    }
    public void coldestInManyFiles(){
        CSVRecord lowestTempRecord = null;
        File lowestTempFile = null;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentColdest = coldestHourInFile(fr.getCSVParser());
            lowestTempRecord = lowestOfTwo(currentColdest, lowestTempRecord, "TemperatureF");
            if(lowestTempRecord == currentColdest){
                lowestTempFile = f;
            }
        }
        System.out.println("Coldest day was in file " + lowestTempFile.getName());
        System.out.println("Coldest temperature on the day was " + lowestTempRecord.get("TemperatureF"));
    }
    public double averageTemperatureInFile(CSVParser parser){
        double avgTemp = 0.0;
        int count = 0;
        for (CSVRecord record : parser){
            count++;
            double currentValue = Double.parseDouble(record.get("TemperatureF"));
            avgTemp = (avgTemp*(count-1) + currentValue)/count;
        }
        System.out.println("Average temperature in file is " + avgTemp);
        return avgTemp;
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double avgTemp = 0.0;
        int count = 0;
        for (CSVRecord record : parser){
            double currentHumidityValue = Double.parseDouble(record.get("Humidity"));
            if(currentHumidityValue >= value){
                count++;
                double currentTempValue = Double.parseDouble(record.get("TemperatureF"));
                avgTemp = (avgTemp*(count-1) + currentTempValue)/count;
            }
        }
        if(count!=0){
            System.out.println("Average temperature in file is " + avgTemp);
        }
        else{
            System.out.println("No temperatures with that humidity");
        }
        return avgTemp;
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumidityRecord = null;
        
        for(CSVRecord current : parser){
            String currentHumidity = current.get("Humidity");
            if (!currentHumidity.contains("N/A")){
                
                lowestHumidityRecord=lowestOfTwo(current, lowestHumidityRecord, "Humidity");
            }
        }
        return lowestHumidityRecord;
    }
    public void lowestHumidityInManyFiles(){
        CSVRecord lowestHumidityRecord = null;
        File lowestHumidityFile = null;
        DirectoryResource dr = new DirectoryResource();
        
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord currentLowestHumidity = lowestHumidityInFile(fr.getCSVParser());
            String currentHumidity = currentLowestHumidity.get("Humidity");
            if (!currentHumidity.contains("N/A")){
            lowestHumidityRecord = lowestOfTwo(currentLowestHumidity, lowestHumidityRecord, "Humidity");
        }   
            if(lowestHumidityRecord == currentLowestHumidity){
                lowestHumidityFile = f;
            }
        }
        //System.out.println("Least humid day was in file " + lowestHumidityFile.getName());
        System.out.println("Lowest humidity on that day was " + lowestHumidityRecord.get("Humidity") + 
        " at " + lowestHumidityRecord.get("DateUTC"));
    }
    
    
    public CSVRecord lowestOfTwo(CSVRecord current, CSVRecord lowest, String category){
        if(lowest == null){
            lowest = current;
        }
        else{
            double currentValue = Double.parseDouble(current.get(category));
            double lowestValue = Double.parseDouble(lowest.get(category));
            if((currentValue < lowestValue) && currentValue!=-9999){
                 lowest = current;
            }
        }
        return lowest;
    }
    
    
    public void testColdestInFile(){
        FileResource f = new FileResource();
        CSVParser parser = f.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.print("Coldest Temperature was " + coldest.get("TemperatureF") + " at ");
        System.out.println(coldest.get("DateUTC"));
        
    }
    public void testColdestInManyFiles(){
        coldestInManyFiles();
    }
    public void testlowestHumidityInFile(){
        FileResource f = new FileResource();
        CSVParser parser = f.getCSVParser();
        //CSVRecord coldest = coldestInManyFiles();
        CSVRecord lowest = lowestHumidityInFile(parser);
       //System.out.println(averageTemperatureWithHighHumidityInFile(parser,80));
        System.out.print("Lowest Humidity was " + lowest.get("Humidity") + " at ");
        System.out.println(lowest.get("DateUTC"));
        
    }
    public void testlowestHumidityInManyFiles(){
        lowestHumidityInManyFiles();
    }
    public void testAvgTempInFile(){
        FileResource f = new FileResource();
        CSVParser parser = f.getCSVParser();
        averageTemperatureInFile(parser);        
    }
    public void testTempAndHumidity(){
        FileResource f = new FileResource();
        CSVParser parser = f.getCSVParser();
        averageTemperatureWithHighHumidityInFile(parser, 80);
    }
}
