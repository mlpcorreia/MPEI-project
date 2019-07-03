package BloomFilter;

/**
 * HashX is the class that has the methods different hash functions
 * @author Miguel Correia
 */
public class HashX {
    
    /**
     * Random integer
     */
    private int a;
    
    /**
     * Random integer
     */
    private int b;
    
    /**
     * Prime number (it is recommended to use a large prime number)
     */
    private int p;
    
    /**
     * Constructs a HashX object with a,b and p predefined
     */
    public HashX(){
        a = (int)(Math.random()+1234567);
        b = (int)(Math.random()+1234567);
        p = 7919;
    }
    
    /**
     * Constructs a HashX object
     * @param a     random integer
     * @param b     random integer
     * @param p     prime number
     */
    public HashX(int a, int b, int p){
        this.a = a;
        this.b = b;
        this.p = p;
    }
    
    /**
     * Universal Hash function method
     * @param element   the element to hash
     * @param randA     random integer
     * @param randB     random integer
     * @return hash calculated from element
     */
    public int uniHash(int element,int randA, int randB){
        return Math.abs(((randA * element + randB) % p));
    }
    
    /**
     * Universal Hash function method for a String
     * @param element   the element to hash
     * @param randA     random integer
     * @param randB     random integer
     * @return hash calculated from element
     */
    public int strUniHash(String element, int randA, int randB){
        int hash = 0;
        
        for(int i = 0; i < element.length(); i++){
            int tmp = Character.getNumericValue(element.charAt(i));
            hash = hash + (randA * tmp + randB);
        }
        
        return Math.abs(hash % p);
    }
    
    /**
     * Method to create the hash from a string
     * @param element   the element to hash
     * @param m         size 
     * @return hash calculated from element
     */
    public int strHash(String element, int m){
        int tmp;
        int hash = 0;
        
        for(int i = 0; i < element.length(); i++){
            tmp = Character.getNumericValue(element.charAt(i));
            hash = hash + (a * tmp % p);
        }
        
        return Math.abs(hash) % m;
    }
    
    /**
     * Method to create the hash from a string (using the hashCode() from the String class)
     * @param element   the element to hash
     * @param m         size
     * @return hash calculated from element
     */
    public int strHashCode(String element, int m){
        return Math.abs(element.hashCode()) % m;
    }
}
