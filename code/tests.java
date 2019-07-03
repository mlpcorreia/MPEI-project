package BloomFilter;

import java.io.*;
import java.util.*;

/**
 * Class with simple tests
 * @author Miguel Correia
 */
public class tests {

    public static void main(String[] args) {

        // MinHash simple test
        System.out.println();
        System.out.println("Test MinHash");
        MinHash minH = new MinHash();
        int nhf = 200;
        Map<Integer, List<Integer>> set = new TreeMap<>();
        List<int []> set2 = new ArrayList<>();
        int[] a = {1,2,3};
        int[] b = {5,6,7};
        int[] c = {192111, 193111, 142111, 123111};
        List<Integer> list1 = new ArrayList<>();
        for(int k = 0; k < 3; k++)
            list1.add(k);
        
        List<Integer> list2 = new ArrayList<>();
        for(int k = 0; k < 3; k++)
            list2.add(k);
        
        List<Integer> list3 = new ArrayList<>();
        list3.add(192111);
        list3.add(193111);
        list3.add(142111);
        list3.add(123111);
        
        set.put(1,list1);
        set.put(2, list2);
        set.put(3, list3);
        set2.add(a);
        set2.add(b);
        set2.add(c);
        

        Map<Integer, List<Integer>> sig = minH.calcSig2(set, nhf);
        
        double[][] jacc = minH.calcDist2(sig, nhf);
        
        System.out.println("Distances");
        for(int i = 0; i < jacc.length; i++){
            for(int j = 0; j < jacc[i].length; j++)
                System.out.print(" "+jacc[i][j]);
            
            System.out.println();
        }
        
        // Bloom Filter tests
        
        // Test n = 8000
        System.out.println();
        System.out.println("Test n = 8000");
        int n = 8000;
        int k = 2;
        int m = 1000;
        int str_size = 40;
        String[] rands = new String[1000];
        BloomFilter b_filter = new BloomFilter(n);
        
        for(int i = 0; i < m; i++){
            rands[i] = getRandString(str_size);
            b_filter.insert(rands[i], k);
        }
        
        int count = 0;
        for(int j = 0; j < m; j++){
            String tmp = getRandString(str_size);
            if(b_filter.isMember(tmp, k))
                count++;
            //System.out.println("False positive \""+ tmp +"\": "+b_filter.isMember(tmp, k));
        }
        
        System.out.println("False positives: "+count);
         
 
        // Test 1
        System.out.println();
        System.out.println("Test 1");
        BloomFilter b1 = new BloomFilter(10);
        HashX h = new HashX(2,5,17);
        
        String[] array = new String[10];

        for(int i = 0; i < 10; i++){
            array[i] = getRandString(10);
            b1.insert(array[i],1);
            
            System.out.printf("isMember %s: %b \n", array[i], b1.isMember(array[i], 1));
        }
        
        // Test 2
        System.out.println();
        System.out.println("Test 2");
        
        String[] array2 = {"Portugal", "Espanha", "Franca", "Austria", "Belgica", "Suica", "Inglaterra", "Escocia", "Italia", "Russia"};
        
        BloomFilter b2 = new BloomFilter(array2.length);
        
        for(int i = 0; i < array2.length; i++){
            b2.insert(array2[i], 1);
            
            System.out.printf("isMember %s: %b \n", array2[i], b2.isMember(array2[i], 1));
        }
        
        // Test 3
        /*int b_size = 1000;
        BloomFilter b3 = new BloomFilter(b_size);
        String[] array3 = new String[b_size];
        
        for(int i = 0; i < b_size; i++){
            array3[i] = getRandString(20);
            b3.insert(array3[i], 1);
        }
        
        for(int i = 0; i < b_size; i++)
            System.out.printf("isMember %s: %b \n", array3[i], b3.isMember(array3[i], 1));
         */
        
        
        // Test to calculate the jaccard distance using the u.data file (takes to long, creates a error)
        /*Map<String, List<Integer>> jD = new TreeMap<>();
        String filename = "u.data";
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null){
                String[] tmp = line.split("\t");
                if(jD.containsKey(tmp[0]))
                    jD.get(tmp[0]).add(Integer.parseInt(tmp[tmp.length - 1]));
                else if(jD.containsKey(tmp[1]))
                    jD.get(tmp[1]).add(Integer.parseInt(tmp[tmp.length - 1]));
                else{
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(tmp[tmp.length - 1]));
                    jD.put(tmp[0], list);
                    jD.put(tmp[1], list);
                }
            }
        }catch(IOException e){
            
        }
        
        Object[] keys = jD.keySet().toArray();
        double[][] jaccDist = new double[keys.length][keys.length];

        for(int i = 0; i < keys.length; i++){
            List<Integer> u1 = jD.get(keys[i]);
            for(int j = i + 1; j < keys.length; j++){
                int inter = 0, union = 0;
                List<Integer> u2 = jD.get(keys[j]);
                for(int aux : u1)
                    if(u2.contains(aux))
                        inter++;
                u2.addAll(u1);
                union = u2.size();
                
                jaccDist[i][j] = (double)(1 - (double)inter/(double)union);
            }
        }*/
    }

    // Method to create random strings
    public static String getRandString(int str_size) {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        String tmp = "";

        Random rand = new Random();

        for (int i = 0; i < str_size; i++) {
            char c = chars[rand.nextInt(chars.length)];
            tmp = tmp + c;
        }

        return tmp;
    }
}
