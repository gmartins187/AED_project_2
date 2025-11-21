package dataStructures;

import com.sun.jdi.Value;

/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     *
     * @param key
     * @param value
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public V put(K key, V value) {

        //TODO: Left as an exercise.
        // If exists a entry with this Key, update the node with new element
        // and return the old value of the entry
        // otherwise, insert the newNode, "rebalance" from the insertion position
        // and return value
        Object[] oldValue = new Object[1];

        root = insert((AVLNode<Entry<K, V>>) root, key, value, oldValue);

        if (oldValue[0] == null) {
            currentSize++;
        }

        return (V) oldValue[0];
    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    @SuppressWarnings({"unchecked"})
    public V remove(K key) {
        //TODO: Left as an exercise.
        // If does not exist a entry with this Key, return null
        // otherwise, remove the node where is the element with this key,
        // "rebalance" from the removal position and return value
        if (isEmpty()) return null;

        Object[] oldValue = new Object[1];
        root = delete((AVLNode<Entry<K, V>>) root, key, oldValue);

        if (oldValue[0] != null) {
            currentSize--;
        }

        return (V) oldValue[0];
    }

    //private methods

    private AVLNode<Entry<K, V>> insert(AVLNode<Entry<K, V>> node, K key, V value, Object[] oldValue) {
        if (node == null) {
            return new AVLNode<>(new Entry<>(key, value), null, null, null);
        }

        int cmp = key.compareTo(node.getElement().key());

        if (cmp == 0) {
            oldValue[0] = node.getElement().value();
            node.setElement(new Entry<>(key, value));
            return node;
        }
        else if (cmp < 0) {
            AVLNode<Entry<K, V>> newLeft = insert((AVLNode<Entry<K, V>>) node.getLeftChild(), key, value, oldValue);
            node.setLeftChild(newLeft);
            newLeft.setParent(node);
        }
        else {
            AVLNode<Entry<K, V>> newRight = insert((AVLNode<Entry<K, V>>) node.getRightChild(), key, value, oldValue);
            node.setRightChild(newRight);
            newRight.setParent(node);
        }

        return balanceNode(node);
    }

    private AVLNode<Entry<K, V>> delete(AVLNode<Entry<K, V>> node, K key, Object[] oldValue) {
        if (node == null) return null;

        int cmp = key.compareTo(node.getElement().key());

        if (cmp < 0) {
            AVLNode<Entry<K, V>> newLeft = delete((AVLNode<Entry<K, V>>) node.getLeftChild(), key, oldValue);
            node.setLeftChild(newLeft);
            if (newLeft != null) newLeft.setParent(node);
        }
        else if (cmp > 0) {
            AVLNode<Entry<K, V>> newRight = delete((AVLNode<Entry<K, V>>) node.getRightChild(), key, oldValue);
            node.setRightChild(newRight);
            if (newRight != null) newRight.setParent(node);
        }
        else {
            oldValue[0] = node.getElement().value();

            if (node.getLeftChild() == null) return (AVLNode<Entry<K, V>>) node.getRightChild();
            if (node.getRightChild() == null) return (AVLNode<Entry<K, V>>) node.getLeftChild();

            AVLNode<Entry<K, V>> minNode = (AVLNode<Entry<K, V>>) node.getRightChild();
            while (minNode.getLeftChild() != null) {
                minNode = (AVLNode<Entry<K, V>>) minNode.getLeftChild();
            }

            node.setElement(minNode.getElement());

            Object[] dummy = new Object[1];
            AVLNode<Entry<K, V>> newRight = delete((AVLNode<Entry<K, V>>) node.getRightChild(), minNode.getElement().key(), dummy);
            node.setRightChild(newRight);
            if (newRight != null) newRight.setParent(node);
        }

        return balanceNode(node);
    }

    /**
     * (copied from class board)
     * while(?)
     * update's Z height
     * if(z not balanced)
     *      find x
     *      call restructured(x)
     *      update heights of x,y and z.
     *  z <- parent of root of this subtree;
     */
    private AVLNode<Entry<K, V>> balanceNode(AVLNode<Entry<K, V>> node) {
        node.updateHeight();
        int balanceFactor = node.getBalanceFactor();

        if (Math.abs(balanceFactor) > 1) {

            AVLNode<Entry<K, V>> child;
            AVLNode<Entry<K, V>> grandChild;

            if (balanceFactor > 0) {
                child = (AVLNode<Entry<K, V>>) node.getRightChild();
                if (child.getBalanceFactor() < 0) {
                    grandChild = (AVLNode<Entry<K, V>>) child.getLeftChild();
                } else {
                    grandChild = (AVLNode<Entry<K, V>>) child.getRightChild();
                }
            } else {
                child = (AVLNode<Entry<K, V>>) node.getLeftChild();

                if (child.getBalanceFactor() > 0) {
                    grandChild = (AVLNode<Entry<K, V>>) child.getRightChild();
                } else {
                    grandChild = (AVLNode<Entry<K, V>>) child.getLeftChild();
                }
            }

            return (AVLNode<Entry<K, V>>) restructure(grandChild);
        }

        return node;
    }
}
