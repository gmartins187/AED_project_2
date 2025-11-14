package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    private Iterator<E> iterator;

    /**
     *  Filter.
     */
    private Predicate<E> criterion;

    /**
     * Node with the next element in the iteration.
     */
    private E nextToReturn;


    /**
     *
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        //TODO: Left as an exercise.
        this.iterator = list;
        this.criterion = criterion;
        this.nextToReturn = findNext();
    }

    /**
     * Returns true if next would return an element
     *
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        //TODO: Left as an exercise.
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        //TODO: Left as an exercise.
        if(nextToReturn == null)
            throw new NoSuchElementException();

        E current = nextToReturn;
        nextToReturn = findNext();  // find the next element that matches
        return current;
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        //TODO: Left as an exercise.
        iterator.rewind();
        nextToReturn = findNext();
    }


    //added private methods

    /**
     * @return the next to return by the filter
     */
    private E findNext() {
        while (iterator.hasNext()) {
            E candidate = iterator.next();
            if (criterion.check(candidate))
                return candidate;
        }
        return null; // no more matching elements
    }
}
