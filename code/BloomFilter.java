package BloomFilter;

import java.util.BitSet;

/**
 * Class that implements a Bloom Filter using the BitSet class that implements a bit vector
 * @author Miguel Correia
 */
public class BloomFilter {
    
    /**
     * Size of the Bloom Filter
     */
    private int size;
    
    /**
     * Instantiate the Bloom Filter
     */
    private BitSet bloomFilter;
    
    /**
     * Object that is used to call the hash functions
     */
    private HashX h = new HashX();
    
    /**
     * Constructs a Bloom Filter with a predefined size (2000)
     */
    public BloomFilter(){
        size = 2000;
        bloomFilter = new BitSet(size);
    }
    
    /**
     * Constructs a Bloom Filter
     * @param size  size of the Bloom Filter that is required
     */
    public BloomFilter(int size){
        this.size = size;
        bloomFilter = new BitSet(size);
    }
    
    /**
     * Hash a element and set bloomFilter(h(element)) equal to 1  
     * @param element   the element from the hash is calculated
     * @param k         number of hash functions (in our case only one or two hash functions can be used)
     */
    public void insert(String element, int k){
        int bitIndex;
        if(k == 1){
            bitIndex = h.strHash(element, size);
            bloomFilter.set(bitIndex);
        }
        else{
            bitIndex = h.strHash(element, size);
            bloomFilter.set(bitIndex);
            bitIndex = h.strHashCode(element, size);
            bloomFilter.set(bitIndex);
        }
    }
    
    /**
     * Checks if a certain element belongs to the Bloom Filter
     * @param element   the element to check if belongs to the filter
     * @param k         number of hash functions (in our case only one or two hash functions can be used)
     * @return boolean that indicates if it's in the Bloom Filter or not
     */
    public boolean isMember(String element, int k){
        int bitIndex;
        if(k == 1){
            bitIndex = h.strHash(element, size);
            
            return (bloomFilter.get(bitIndex));  
        }
        else{
            int contador = 0;
            
            bitIndex = h.strHash(element, size);
            if(bloomFilter.get(bitIndex))
                contador++;
            bitIndex = h.strHashCode(element, size);
            if(bloomFilter.get(bitIndex))
                contador++;
            
            return (contador == 2);
        }
    }
}
