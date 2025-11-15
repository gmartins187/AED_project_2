package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

import javax.swing.text.html.HTMLEditorKit;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    //TODO: Left as exercise
    private final Map<K,V>[] table;

    private int tableIndex;

    private Iterator<Map.Entry<K,V>> bucketIterator;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        //TODO: Left as exercise

        this.table = table;
        this.tableIndex = 0;
        bucketIterator = null;
        findNextIterator();
    }

    //private method added
    /**
     * finds the next iterator to use
     */
    private void findNextIterator() {
        while (tableIndex < table.length && (table[tableIndex] == null || table[tableIndex].isEmpty())) {
            tableIndex++;
        }

        if (tableIndex < table.length)
            bucketIterator = table[tableIndex].iterator();
        else
            bucketIterator = null;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
	//TODO: Left as exercise
        if(bucketIterator == null || !bucketIterator.hasNext()){
            tableIndex++;
            findNextIterator();
        }

        return bucketIterator != null && bucketIterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        //TODO: Left as exercise
        if (!hasNext())
            throw new NoSuchElementException();

        return bucketIterator.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        //TODO: Left as exercise
        this.tableIndex = 0;
        this.bucketIterator = null;
        findNextIterator();
    }
}

