
/**
 * Write a description of GeneFinder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
public class GeneFinder {
    public String findProtein(String dna){
        dna = dna.toLowerCase();
        int start = dna.indexOf("atg");
        int stop = dna.indexOf("tga", start+3);
        //System.out.println("start: "+start+", stop: "+stop);
        if ((stop - start)%3 == 0) {
            return dna.substring(start,stop+3);
        }
        else
            return null;
    }
    
    public int findStartCodon(String dna,int loc){
        dna = dna.toLowerCase();
        return dna.indexOf("atg",loc);
    }
    
    public int findEndCodon(String dna,int start){
        dna = dna.toLowerCase();
        int stop1 = dna.indexOf("tag",start+3);
        int stop2 = dna.indexOf("tga",start+3);
        int stop3 = dna.indexOf("taa",start+3);
        
        if (stop1==-1 || (stop1-start)%3!=0){
            stop1 = dna.length();
        }
        if (stop2==-1 || (stop2-start)%3!=0){
            stop2 = dna.length();
        }
        if (stop3==-1 || (stop3-start)%3!=0){
            stop3 = dna.length();
        }
        //System.out.println("Returned stop value: "+Math.min(stop1,Math.min(stop2,stop3)));
        return Math.min(stop1,Math.min(stop2,stop3));
    }
                
    public void testStorageFinder(){
        FileResource file = new FileResource("GRch38dnapart.fa");
        String dna = file.asString();
        StorageResource sr = storeAll(dna);
        printGenes(sr);
        System.out.println("CTG count: "+ctgCount(dna));
    }
    
    public float cgRatio(String dna){
        int cCount=0; 
        int gCount=0;
        int start = 0;
        int pos;
        dna = dna.toLowerCase();
        while(true){
            pos = dna.indexOf("c",start);
            if(pos==-1||pos==dna.length()){
                break;
            }
            if (pos<dna.length()){
                cCount++;
            }
            start=pos+1;
        }
        start=0;
        while(true){
            pos = dna.indexOf("g",start);
            if(pos==-1||pos==dna.length()){
                break;
            }
            if (pos<dna.length()){
                gCount++;
            }
            start=pos+1;
        }
        float cgCount = cCount+gCount;
        float cgRatio = cgCount/dna.length();
        return cgRatio;
    }
    
    public StorageResource storeAll(String dna){
        StorageResource sr = new StorageResource();
        int start = 0;
        int pos;
        while(true){
            pos = findStartCodon(dna,start);
            if(pos==-1||pos==dna.length()){
                break;
            }
            int stop = findEndCodon(dna,pos);
            //System.out.println("start: " + start + ", stop: " + stop);
            if (stop<dna.length()){
                //System.out.println(dna.substring(start,stop+3));
                sr.add(dna.substring(pos,stop+3));
                System.out.println(sr.size()+": "+dna.substring(pos,stop+3));
                start=stop+3;
            }
            else
            {
                start=pos+3;
            }
        }
        return sr;
    }
    public int ctgCount(String dna){
        int count=0;
        int start=0;
        int pos=0;
        dna = dna.toLowerCase();
        while(true){
            pos=dna.indexOf("ctg",start);
            if(pos==-1 || pos>=dna.length()){
                break;
            }
            //if(pos<dna.length()){
            count++;
            start = pos+3;
        }
        return count;
    }
                
    public void printGenes(StorageResource sr){
        int cgRatioQualifiedCount =0;
        int stringLengthQualifiedCount =0;
        int maxLength=0;
        
        for(String gene: sr.data()){
            if((gene.length()>60)||(cgRatio(gene)>0.35)){
                //System.out.println(gene+"    Length: "+gene.length()+"     cg: "+cgRatio(gene));
            }
            if(gene.length()>60){
               stringLengthQualifiedCount++;
               //System.out.println(gene+"     Length: "+gene.length());
            }
            if(cgRatio(gene)>0.35){
                cgRatioQualifiedCount++;
                //System.out.println(gene+"    cg: "+cgRatio(gene));
            }
            if(gene.length()>maxLength){
                maxLength=gene.length();
            }
        }
        System.out.println("Strings longer than 60 nucleotides: "+stringLengthQualifiedCount);
        System.out.println("Strings with a C-G-ratio higher than 0.35: "+cgRatioQualifiedCount);
        System.out.println("Total gene count: "+sr.size());
        System.out.println("Longest gene: "+maxLength);        
    }
}