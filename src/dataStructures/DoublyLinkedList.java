package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

/**
 * Implementation of Doubly Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class DoublyLinkedList<E> implements TwoWayList<E> {
    /**
     *  Node at the head of the list.
     */
    private DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private int currentSize;

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        //TODO: Left as an exercise.
        this.currentSize = 0;
        this.tail = null;
        this.head = null;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        //TODO: Left as an exercise.
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        //TODO: Left as an exercise.
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     */
    public void addFirst( E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> node = new DoublyListNode<>(element);
        if(isEmpty()){
            this.head = node;
            this.tail = node;
            this.currentSize++;
        } else{
            node.setNext(this.head);
            this.head.setPrevious(node);
            this.head = node;
            this.currentSize++;
        }
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     */
    public void addLast( E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> node = new DoublyListNode<>(element);
        if(isEmpty()){
            this.head = node;
            this.tail = node;
            this.currentSize++;
        } else{
            node.setPrevious(this.tail);
            this.tail.setNext(node);
            this.tail = node;
            this.currentSize++;
        }
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst( ) {
        //TODO: Left as an exercise.
        return this.head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast( ) {
        //TODO: Left as an exercise.
        return this.tail.getElement();
    }

   

     /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get( int position ) {
        //TODO: Left as an exercise.
        if(position > size()) {
            throw new InvalidPositionException();
        } else {
            return getNode(position).getElement();
        }
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf( E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> node = this.head;
        for (int i=0; i<size(); i++) {
            if(node.getElement() == element){
                return i;
            } else{
                node = node.getNext();
            }
        }
        return -1;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add( int position, E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> newNode = null;
        if(position>size() || position<0){
            throw new InvalidPositionException();
        } else if(position==0){
            addFirst(element);
        } else if(position==size()){
            addLast(element);
        } else{
            newNode = new DoublyListNode<>(element, getNode(position).getPrevious(), getNode(position));
            getNode(position).setPrevious(newNode);
            getNode(position).getPrevious().setNext(newNode);
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst( ) {
        //TODO: Left as an exercise.
        if(size() == 0){
            throw new NoSuchElementException();
        } else{
            E node = this.head.getElement();
            if (this.currentSize == 1) {
                this.currentSize = 0;
                this.tail = null;
                this.head = null;
            }else{
                this.head.getNext().setPrevious(null);
                this.head = this.head.getNext();
                this.currentSize--;
            }
            return node;
        }
    }

    /**
     * Removes and returns the element at the last position in the list.
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast( ) {
        //TODO: Left as an exercise.
        if(size() == 0){
            throw new NoSuchElementException();
        } else{
            E node = this.tail.getElement();
            if(currentSize > 1) {
                this.tail.getPrevious().setNext(null);
                this.tail = this.tail.getPrevious();
            }
            return node;
        }
    }

    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove( int position ) {
        //TODO: Left as an exercise.
        if(position > this.size())
            throw new InvalidPositionException();
        DoublyListNode<E> node = getNode(position);
        node.getPrevious().setNext(node.getNext());
        node.getNext().setPrevious(node.getPrevious());
        return node.getElement();
    }

    /**
     * This method iterates the whole doublyLinkedList and return the node
     * based on the position.
     * @param position the position searching
     * @return the node from the position
     */
    private DoublyListNode<E> getNode(int position){
        //TODO
        if(size()/2 > position){
            DoublyListNode<E> node = this.head;
            for(int i=0; i<position; i++)  node = node.getNext();

            return node;
        }else{
            DoublyListNode<E> node = this.tail;
            for(int i=size(); i>position; i--)  node = node.getPrevious();

            return node;
        }
    }
}
