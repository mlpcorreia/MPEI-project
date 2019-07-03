package BloomFilter;

import java.util.*;

/**
 * Class that implements the MinHash module
 * @author Miguel Correia
 */
public class MinHash {
    
    /**
     * Method to create the signatures for a given set
     * @param set   Data set
     * @param nhf   number of hash functions
     * @return the signatures for the different keys
     */
    public Map<String, List<Integer>> calcSig(Map<String, List<String>> set, int nhf){
        HashX h = new HashX();
        Map<String, List<Integer>> minHash = new TreeMap<>();
        int[] randA = new int[nhf];
        int[] randB = new int[nhf];
        
        for(int k = 0; k < randA.length; k++){
            randA[k] = (int)(Math.random()*(nhf-1));
            randB[k] = (int)(Math.random()*(nhf-1));
        }   
        
        for(Map.Entry<String, List<String>> entry : set.entrySet()){
            List<String> tmp = entry.getValue();
            List<Integer> sig = new ArrayList<>();
            for(int j = 0; j < nhf; j++){
                int i = 0;
                int minimo = h.strUniHash(tmp.get(i),randA[j], randB[j]);
                for(i = 1; i < tmp.size(); i++){
                    int aux = h.strUniHash(tmp.get(i),randA[j], randB[j]);
                       //System.out.println("Minimo: "+minimo+"  aux: "+aux+ " tmp.length: "+tmp.size());
                    if(aux < minimo)
                        minimo = aux;
                       
                }
              sig.add(minimo);
            }
            
            minHash.put(entry.getKey(), sig);
        }
        
        return minHash;
    }
    
    /**
     * Method to create the signatures for a given set(uses different input arguments)
     * @param set   Data set
     * @param nhf   number of hash functions
     * @return the signatures for the different keys
     */
    public Map<Integer, List<Integer>> calcSig2(Map<Integer, List<Integer>> set, int nhf){
        HashX h = new HashX();
        Map<Integer, List<Integer>> minHash = new TreeMap<>();
        int[] randA = new int[nhf];
        int[] randB = new int[nhf];
        
        for(int k = 0; k < randA.length; k++){
            randA[k] = (int)(Math.random()*(nhf-1));
            randB[k] = (int)(Math.random()*(nhf-1));
        }   
        
        for(Map.Entry<Integer, List<Integer>> entry : set.entrySet()){
            List<Integer> tmp = entry.getValue();
            List<Integer> sig = new ArrayList<>();
            for(int j = 0; j < nhf; j++){
                int i = 0;
                int minimo = h.uniHash(tmp.get(i),randA[j], randB[j]);
                for(i = 1; i < tmp.size(); i++){
                    int aux = h.uniHash(tmp.get(i),randA[j], randB[j]);
                       //System.out.println("Minimo: "+minimo+"  aux: "+aux+ " tmp.length: "+tmp.size());
                    if(aux < minimo)
                        minimo = aux;
                       
                }
              sig.add(minimo);
            }
            
            minHash.put(entry.getKey(), sig);
        }
        
        return minHash;
    }
    
    /**
     * Method to calculate the jaccard distances
     * @param minHash   Signatures of the set
     * @param nhf       number of hash functions
     * @return the distances between the different elements from the set
     */
    public double[][] calcDist(Map<String, List<Integer>> minHash, int nhf){    
        Object[] key_array = minHash.keySet().toArray();
        int nu = key_array.length;
        double[][] minDist = new double[nu][nu];
        
        for(int i = 0; i < nu; i++){
            String key1 = (String)key_array[i];
            for(int j = i+1; j < nu; j++){
                int countInter = 0;
                String key2 = (String)key_array[j];
                List<Integer> min1 = minHash.get(key1);
                List<Integer> min2 = minHash.get(key2);
                
                for(int n = 0; n < min1.size(); n++)
                    if(min1.get(n).equals(min2.get(n)))
                        countInter++;
                
                minDist[i][j] = (double)(1 - (double)countInter/(double)nhf);
            }
        }
        
        return minDist;
    }
    
    /**
     * Method to calculate the jaccard distances(uses different input arguments)
     * @param minHash   Signatures of the set
     * @param nhf       number of hash functions
     * @return the distances between the different elements from the set
     */
    public double[][] calcDist2(Map<Integer, List<Integer>> minHash, int nhf){
        
        Object[] key_array = minHash.keySet().toArray();
        int nu = key_array.length;
        double[][] minDist = new double[nu][nu];
        
        for(int i = 0; i < nu; i++){
            int key1 = (int)key_array[i];
            for(int j = i+1; j < nu; j++){
                int countInter = 0;
                int key2 = (int)key_array[j];
                List<Integer> min1 = minHash.get(key1);
                List<Integer> min2 = minHash.get(key2);
                
                for(int n = 0; n < min1.size(); n++)
                    if(min1.get(n).equals(min2.get(n)))
                        countInter++;
                
                //System.out.println("calcDist: "+countInter);
                minDist[i][j] = (double)(1 - (double)countInter/(double)nhf);
            }
        }
        
        return minDist;
    }
    
    /**
     * Method to print the similarities in the set
     * @param set           Data set
     * @param minDist       Distances between the different elements from the set
     * @param threshold
     */
    public void printSimilarities(Map<String, List<String>> set, double[][] minDist, double threshold){
        Object[] keys = set.keySet().toArray();
        System.out.println("Similarities:");
        for(int i = 0; i < keys.length; i++){
            String key1 = (String)keys[i];
            for(int j = i + 1; j < keys.length; j++){
                String key2 = (String)keys[j];
                if(minDist[i][j] < threshold)
                    System.out.println(key1+" and "+key2+": "+(1.0 - minDist[i][j]));
            }
        }
    }
}
