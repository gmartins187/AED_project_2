package dataStructures;

import dataStructures.exceptions.*;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 * 
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

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
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        //TODO: Left as an exercise.
        this.comparator = comparator;

        this.head = null;
        this.tail = null;
        this.currentSize = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        //TODO: Left as an exercise.
        if(size() == 0)
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMax( ) {
        //TODO: Left as an exercise.
        if(size() == 0)
            throw new NoSuchElementException();
        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * @return element in the list or null
     */
    public E get(E element) {
        //TODO: Left as an exercise.
        if(!contains(element))
            return null;
        else
            return (getNode(element)).getElement();
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        //TODO: Left as an exercise.
        return getNode(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     */
    public void add(E element) {
        //TODO: Left as an exercise.
        DoublyListNode<E> newNode = new DoublyListNode<>(element);

        if (isEmpty()) {
            head = tail = newNode;
            currentSize++;
            return;
        }

        DoublyListNode<E> crt = head;

        while (crt != null && comparator.compare(crt.getElement(), element) <= 0) {
            crt = crt.getNext();
        }

        if (crt == null) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        } else if (crt == head) {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else {
            DoublyListNode<E> prev = crt.getPrevious();
            prev.setNext(newNode);
            newNode.setPrevious(prev);
            newNode.setNext(crt);
            crt.setPrevious(newNode);
        }

        currentSize++;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        //TODO: Left as an exercise.
        DoublyListNode<E> toRemove = getNode(element);
        if (toRemove != null) {
            if (toRemove == head) {
                head = head.getNext();
                if (head != null) {
                    head.setPrevious(null);
                } else {
                    tail = null; // List is now empty
                }
            } else if (toRemove == tail) {
                tail = tail.getPrevious();
                if (tail != null) {
                    tail.setNext(null);
                } else {
                    head = null; // List is now empty
                }
            } else {
                toRemove.getPrevious().setNext(toRemove.getNext());
                toRemove.getNext().setPrevious(toRemove.getPrevious());
            }
            currentSize--;
            return toRemove.getElement();
        }
        return null;
    }

    //private methods added

    /**
     * iterates the whole list in search of the element
     */
    private DoublyListNode<E> getNode(E element) {
        DoublyListNode<E> current = head;

        if(current == null) return null;

        while (comparator.compare(current.getElement(), element) != 0) {
            current = current.getNext();
            if(current == null) return null;
        }
        return current;
    }
}
