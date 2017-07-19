package TOOLS;

public class Pair<K, V> {

    private K lVal;
    private V rVal;

    public static <K, V> Pair<K, V> createPair(K lVal, V rVal)
    {
        return new Pair<K, V>(lVal, rVal);
    }

    public Pair(K lVal, V rVal) {
        this.lVal = lVal;
        this.rVal = rVal;
    }

    public K getLVal() {
        return lVal;
    }

    public V getRVal() {
        return rVal;
    }
    
    public void setLVal (K lVal)
    {
    	this.lVal = lVal;
    }

    public void setRVal (V rVal)
    {
    	this.rVal = rVal;
    }

}
