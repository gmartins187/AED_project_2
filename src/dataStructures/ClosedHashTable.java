package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }
    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        //TODO: Left as an exercise.
        int i=0;
        int tableSize = table.length;
        int initialHash = Math.abs(key.hashCode());

        while(i<tableSize){
            int index = (initialHash + i) % tableSize;
            Entry<K,V> entry = table[index];

            if(entry==null) return NOT_FOUND;

            if(entry!=REMOVED_CELL && entry.key().equals(key))
                return index;

            i++;
        }

        return NOT_FOUND; 
    }

    
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        //TODO: Left as an exercise.
        if(key==null) return null;

        int index = searchLinearProving(key);

        if(index==NOT_FOUND) return null;

        return table[index].value();
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        //TODO: Left as an exercise.

        V oldValue = table[searchLinearProving(key)].value();

        table[searchLinearProving(key)] = new Entry<>(key,value);

        return oldValue;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        Entry<K,V>[] oldTable = table;

        int newCapacity = HashTable.nextPrime(table.length * 2);

        table = (Entry<K,V>[]) new Entry[newCapacity];

        currentSize = 0;
        maxSize = (int) (newCapacity * MAX_LOAD_FACTOR);

        for (int i = 0; i < oldTable.length; i++) {
            Entry<K,V> e = oldTable[i];

            if (e != null && e != REMOVED_CELL) {
                insertLegacyEntry(e);
            }
        }
    }

    // Método auxiliar para inserir diretamente sem verificar duplicados (mais rápido)
    private void insertLegacyEntry(Entry<K,V> entry) {
        int i = 0;
        int tableSize = table.length;
        int initialHash = Math.abs(entry.key().hashCode());

        while (true) {
            int index = (initialHash + i) % tableSize;

            if (table[index] == null) {
                table[index] = entry;
                currentSize++;
                break;
            }
            i++;
        }
    }

   
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        if(key == null)
            return null;

        int index = searchLinearProving(key);

        if (index == NOT_FOUND) {
            return null;
        }

        V oldValue = table[index].value();

        table[index] = (Entry<K,V>) REMOVED_CELL;

        currentSize--;

        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
         //TODO: Left as an exercise.
        return new ArrayIterator<>(table, currentSize);
    }

}
