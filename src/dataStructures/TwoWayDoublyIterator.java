package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Two Way Iterator for DLList 
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
class TwoWayDoublyIterator<E> extends DoublyIterator<E>
        implements TwoWayIterator<E> {

    /**
     * Node with the last element in the iteration.
     */
    private DoublyListNode<E> lastNode;
    /**
     * Node with the previous element in the iteration.
     */
    DoublyListNode<E> prevToReturn;

    /**
     * DoublyLLIterator constructor
     *
     * @param first - Node with the first element of the iteration
     * @param last  - Node with the last element of the iteration
     */
    public TwoWayDoublyIterator(DoublyListNode<E> first, DoublyListNode<E> last) {
        super(first);
        //TODO: Left as an exercise.
        this.lastNode = last;
        this.prevToReturn = last;
    }

    /**
     * Returns true if previous would return an element
     * rather than throwing an exception.
     * @return true iff the iteration has more elements in the reverse direction
     */
    public boolean hasPrevious( ) {
        //TODO: Left as an exercise.
        return prevToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next( ){
        //TODO: Left as an exercise.
        return super.next();
    }

    /**
     * Returns the previous element in the iteration.
     * @return previous element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E previous( ) {
        //TODO: Left as an exercise.
        if(!hasPrevious())
            throw new NoSuchElementException();
        E previous = prevToReturn.getElement();
        prevToReturn = prevToReturn.getPrevious();
        return previous;
    }

    /**
     * Restarts the iteration in the reverse direction.
     * After fullForward, if iteration is not empty,
     * previous will return the last element
     */
    public void fullForward() {
        //TODO: Left as an exercise.
        prevToReturn = lastNode;
    }

    /**
     * Restart the iterator
     */
    public void rewind() {
        //TODO: Left as an exercise.
        super.rewind();
        this.prevToReturn = this.lastNode;
    }
}
