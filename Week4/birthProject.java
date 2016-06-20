
/**
 * Write a description of birthProject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;
public class birthProject {
    public void totalBirths(FileResource fr){
        int totalBirths=0;
        int totalBoys=0;
        int totalGirls=0;
        int totalUniqueBoyNames=0;
        int totalUniqueGirlNames=0;
        int totalNames=0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            totalNames++;
            if(rec.get(1).equals("M")){
                totalBoys+=numBorn;
                totalUniqueBoyNames++;
            }
            else{
                totalGirls+=numBorn;
                totalUniqueGirlNames++;
            }
        }
        System.out.println("Number of babies born: " + totalBirths);
        System.out.println("Number of boys born: " + totalBoys + " between whom " 
                            + totalUniqueBoyNames +" names were used");
        System.out.println("Number of girls born: " + totalGirls + " between whom " 
                            + totalUniqueGirlNames + " names were used");
        System.out.println("A total of " + totalNames + " names were used");
    }
    public int getRank(int year, String name, String gender){
        int rank = -1;
        int count =0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
             if(f.getName().contains(Integer.toString(year))){
                FileResource fr = new FileResource(f);
                for(CSVRecord rec : fr.getCSVParser(false)){
                    if(rec.get(1).equals(gender)){
                        count++;
                        if(rec.get(0).equals(name)){
                            rank = count;
                        }
                    }
                }
            }
        }
        System.out.println("Rank is: " + rank);
        return rank;
    }
    public String getName(int year, int rank , String gender){
        int count=0;
        String name="";
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
             if(f.getName().contains(Integer.toString(year))){
                FileResource fr = new FileResource(f);
                System.out.println(f.getName());
                for(CSVRecord rec : fr.getCSVParser(false)){
                    if(rec.get(1).equals(gender)){
                        count++;
                        if(count == rank){
                            name = rec.get(0);
                        }
                    }
                }
            }
         }
        if(name.equals("")){
            name = "NO NAME";
        }
        
        return name;
    }
    public void whatIsNameInYear(String name, int year, int newYear , String gender){
        int rank = getRank(year, name, gender);
        String newName = getName(newYear, rank , gender);
        System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
    }
    public int yearOfHighestRank(String name, String gender){
        int yearOfHighestRank = -1;
        int highestRank = -1;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int rank = 0;
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(rec.get(1).equals(gender)){
                    rank++;
                    //System.out.println(rank);
                    if((rank < highestRank) || highestRank==-1){
                        if(rec.get(0).equals(name)){
                            highestRank = rank;
                            String FileName = f.getName();
                            int indexOfYOB = FileName.indexOf("yob");
                            String temp = FileName.substring(indexOfYOB+3,indexOfYOB+7);
                            yearOfHighestRank = Integer.parseInt(temp);
                        }
                    }
                    else
                        break;
                }
            }
        }   
        System.out.println("The name " + name + " has the highest rank of " + highestRank 
                                                    + " in the year " + yearOfHighestRank);
        return yearOfHighestRank;
    }
    public double getAverageRank(String name, String gender){
        double avgRank=-1.0;
        int runningTotal=0;
        int count =0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int rank = 0;
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(rec.get(1).equals(gender)){
                    rank++;
                    if(rec.get(0).equals(name)){
                        runningTotal += rank;
                        count++;
                        break;
                    }
                    System.out.println("Count: " + count + ", Current Total: " + runningTotal);
                }
            }
           
        }
        if(count>0 && runningTotal>0)
            avgRank = (float)runningTotal/count;
        System.out.println("The average rank of " + name + " is " + avgRank);
        return avgRank;
    }
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int totalBirths=0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
             if(f.getName().contains(Integer.toString(year))){
                FileResource fr = new FileResource(f);
                for(CSVRecord rec : fr.getCSVParser(false)){
                    if(rec.get(1).equals(gender)){
                        if(!rec.get(0).equals(name))
                            totalBirths += Integer.parseInt(rec.get(2));  
                        else
                            break;
                    }
                }
            }
        }
        System.out.println("Total births with rank higher than " + name + 
                            "'s rank in the year " + year + ": " + totalBirths);
        return totalBirths;
    }
    public void testTotalBirths(){
        //DirectoryResource dr = new DirectoryResource();
        //File f = dr.selectedFiles();
        FileResource fr = new FileResource("data/yob1905.csv");
        totalBirths(fr);
    }
    public void testTotalBirthsRankedHigher(){
        //whatIsNameInYear("Isabella", 2012, 2014 , "F");
        //int rank=getRank(2012, "Isabella","F");
        //System.out.println(rank);
        //double i = getAverageRank("Jacob", "M");
        //int j = yearOfHighestRank("Mason", "M");
        int k = getTotalBirthsRankedHigher(2012,"Ethan","M");
        //String newName = getName(2014, 3 , "M");
        //System.out.println(newName);
        //DirectoryResource dr = new DirectoryResource();
        //for (File f : dr.selectedFiles()) {
        //    if(f.getName().contains(Integer.toString(2012))){
         //      System.out.println(f.getName());
         //   }
         //}
    }
    public void testgetAverageRank(){
        double i = getAverageRank("Jacob", "M");
    }
    public void testgetRank(){
        int rank=getRank(2012, "Isabella","F");
    }
    public void testwhatIsNameInYear(){
        whatIsNameInYear("Isabella", 2012, 2014 , "F");
    }
    public void testyearOfHighestRank(){
        int j = yearOfHighestRank("Mason", "M");
    }
    public void testgetName(){
        String newName = getName(2014, 3 , "M");
    }
}
