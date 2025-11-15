package dataStructures;
/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends Tree<E> {

    /**
     * Returns the height of the tree.
     */
    public int getHeight() {
        if(isEmpty())
            return 0;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * @return
     */
    BTNode<E> furtherLeftElement() {
        //TODO: Left as an exercise.
        if(root == null) return null;

        BTNode<E> current = (BTNode<E>) this.root;
        while(current.getLeftChild() != null){
            current = (BTNode<E>) current.getLeftChild();
        }
        return null;
    }

    /**
     * Return the further right node of the tree
     * @return
     */
    BTNode<E> furtherRightElement() {
        //TODO: Left as an exercise.
        if(root == null) return null;

        BTNode<E> current = (BTNode<E>) this.root;
        while(current.getRightChild() != null){
            current = (BTNode<E>) current.getRightChild();
        }
        return null;
    }

   //new methods: Left as an exercise.
}
