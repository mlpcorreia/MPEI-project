package BloomFilter;

import java.util.*;
import java.io.*;

/**
 * Class to solve the ideia
 * @author Miguel Correia
 */
public class testApp {
    public static void main(String[] args){
        
        int nhf = 100;
        int k = 2;
        String filename = "especie";
        BloomFilter bFilter = new BloomFilter();
        MinHash mH = new MinHash();
        Map<String, List<String>> set = new TreeMap<>();
        Map<String, List<Integer>> minHash = null;
        
        
        Scanner sc = new Scanner(System.in);
        int op = 1;
        do{
            System.out.println("---------------------------------");
            System.out.println("testApp");
            System.out.println("1 - Read data");
            System.out.println("2 - Look in the BloomFilter");
            System.out.println("3 - Create signature to the Set");
            System.out.println("4 - Check Similarities");
            System.out.println("5 - Create random files with random genetic code");
            System.out.println("0 - Exit");
            System.out.print("Insert option: ");
            op = sc.nextInt();
            System.out.println("---------------------------------");
            
            switch(op){
                case 1:
                    System.out.print("Insert the number of files to read: ");
                    int nfiles = sc.nextInt();
                    BufferedReader br = null;
        
                    for(int i = 0; i < nfiles; i++){
                        try{
                            br = new BufferedReader(new FileReader(filename+i));
                            String key = br.readLine();
                            bFilter.insert(key, k);
                            String line;
                            List<String> tmp_list = new ArrayList<>();
                            while((line = br.readLine()) != null)
                                tmp_list.add(line);

                            set.put(key,tmp_list);

                        }catch(IOException e){
                            System.err.println(e);
                        }
                    }
                    System.out.println("Data read!");
                    break;
                    
                case 2:
                    System.out.print("Insert the data to check if belongs to the filter: ");
                    String tmp = sc.next();
                    if(bFilter.isMember(tmp, k))
                        System.out.println("In the filter!");
                    else
                        System.out.println("Not in the filter!");
                    break;
                    
                case 3:
                    minHash = mH.calcSig(set, nhf);
                    System.out.println("Signatures calculated!");
                    break;
                    
                case 4:
                    if(minHash == null){
                        System.out.println("Signatures not calculated!");
                        break;
                    }
                    System.out.print("Insert the threshold wanted: ");
                    double threshold = sc.nextDouble();
                    double[][] distance = mH.calcDist(minHash, nhf);
                    
                    mH.printSimilarities(set, distance, threshold);
                    break;
                    
                case 5:
                    System.out.print("Number of files to create: ");
                    int nf = sc.nextInt();
                    for(int i = 0; i < nf; i++){
                        File file = new File(filename+i);
                        FileWriter fr = null;
                        BufferedWriter bw = null;
                        String data = filename+i;

                        try{
                            fr = new FileWriter(file);
                            bw = new BufferedWriter(fr);
                            bw.write(data + "\n");
                            for(int j = 0; j < 60; j++)
                                bw.write(getRandCode()+"\n");
                        }catch(IOException e){
                            System.err.println("Error writing to file: "+e);
                        }finally{
                            try{
                                bw.close();
                                fr.close();
                            }catch(IOException e){
                                System.err.println("Error closing file: "+e);
                            }
                        }
                    }
                    System.out.println("Files created!");
                    break;
            }
        }while(op != 0);
}
     
    public static String getRandCode(){
        Random rand = new Random();
        String data = "";
        char[] code = "TCAG".toCharArray();
        
        for (int j = 0; j < 60; j++) {
                char c = code[rand.nextInt(code.length)];
                data = data + c;
            }
        
        return data;
    }
}
