package dataStructures;

import dataStructures.exceptions.EmptyMapException;
/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
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
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }

    private BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) {
        //TODO: Left as an exercise.

        if(node == null)
            return null;

        int cmp = key.compareTo(node.getElement().key());

        if(cmp ==0)
            return node;
        else if(cmp < 0)
            return getNode((BTNode<Entry<K,V>>) node.getLeftChild(), key);
        else
            return getNode((BTNode<Entry<K,V>>) node.getRightChild(), key);
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
        //TODO: Left as an exercise.

        if (root == null) {
            root = new BTNode<>(new Map.Entry<>(key, value));
            currentSize++;
            return null;
        }

        BTNode<Entry<K,V>> currrent = (BTNode<Entry<K,V>>) root;

        while (true) {

            int cmp = key.compareTo(currrent.getElement().key());

            if (cmp == 0) {
                V oldValue = currrent.getElement().value();
                currrent.setElement(new Entry<>(key, value));
                return oldValue;
            }

            else if (cmp < 0) {
                if (currrent.getLeftChild() == null) {
                    BTNode<Entry<K,V>> newNode = new BTNode<>(new Map.Entry<>(key, value), currrent);
                    currrent.setLeftChild(newNode);
                    currentSize++;
                    return null;
                }
                currrent = (BTNode<Entry<K,V>>) currrent.getLeftChild();
            }

            else {
                if (currrent.getRightChild() == null) {
                    BTNode<Entry<K,V>> newNode = new BTNode<>(new Map.Entry<>(key, value), currrent);
                    currrent.setRightChild(newNode);
                    currentSize++;
                    return null;
                }
                currrent = (BTNode<Entry<K,V>>) currrent.getRightChild();
            }
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
    @Override
    public V remove(K key) {
        //TODO: Left as an exercise.

        BTNode<Entry<K,V>> node = getNode((BTNode<Entry<K,V>>) root, key);

        if (node == null)
            return null;

        V oldValue = node.getElement().value();

        if (isLeaf(node))  removeLeaf(node);
        else if (hasSingleChild(node))  removeSingleChild(node);
        else  removeTwoChildren(node);

        currentSize--;
        return oldValue;
    }

    //private added methods
    /**
     * Removes a leaf node (node with no children) from the BST.
     * If the node is the root, sets root to null.
     * Otherwise, removes the reference from the parent node.
     *
     * @param node the leaf node to remove
     */
    private void removeLeaf(BTNode<Entry<K,V>> node) {
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) node.getParent();

        if (parent == null) { // removing root
            root = null;
            return;
        }

        if (parent.getLeftChild() == node)
            parent.setLeftChild(null);
        else
            parent.setRightChild(null);
    }

    /**
     * Removes a node that has exactly one child from the BST.
     * Connects the node's parent directly to the node's only child,
     * effectively bypassing the node being removed.
     * If the node is the root, the child becomes the new root.
     *
     * @param node the node with a single child to remove
     */
    private void removeSingleChild(BTNode<Entry<K,V>> node) {

        BTNode<Entry<K,V>> child =
                (node.getLeftChild() != null)
                        ? (BTNode<Entry<K,V>>) node.getLeftChild()
                        : (BTNode<Entry<K,V>>) node.getRightChild();

        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>) node.getParent();

        if (parent == null) {
            root = child;
            child.setParent(null);
            return;
        }

        if (parent.getLeftChild() == node)
            parent.setLeftChild(child);
        else
            parent.setRightChild(child);

        child.setParent(parent);
    }

    /**
     * Removes a node that has two children from the BST.
     * Finds the in-order predecessor (the furthest right element in the left subtree),
     * replaces the node's element with the predecessor's element,
     * and then removes the predecessor node.
     *
     * @param node the node to remove
     */
    private void removeTwoChildren(BTNode<Entry<K,V>> node) {

        BTNode<Entry<K,V>> pred =
                ((BTNode<Entry<K,V>>) node.getLeftChild()).furtherRightElement();

        node.setElement(pred.getElement());

        if (isLeaf(pred))
            removeLeaf(pred);
        else
            removeSingleChild(pred);
    }

    /**
     *
     * @param pred the one to check if it does not have any child
     * @return true if it does have zero child
     */
    private boolean isLeaf(BTNode<Entry<K, V>> pred) {
        return (pred.getRightChild() == null && pred.getLeftChild() == null);
    }

    /**
     *
     * @param node the node to check if it has only one child
     * @return true if it only has one child
     */
    private boolean hasSingleChild(BTNode<Entry<K,V>> node) {
        return (node.getLeftChild() == null) ||  (node.getRightChild() == null);
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
