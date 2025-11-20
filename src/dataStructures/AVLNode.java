package dataStructures;

/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        //TODO: Left as an exercise.
        super(element, parent, left, right);
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setLeftChild(node);
        updateHeight();
    }

    /**
     * Update the right child and height
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        //TODO: Left as an exercise.
        super.setRightChild(node);
        updateHeight();
    }


// others public methods
//TODO: Left as an exercise.

    public void updateHeight(){
        this.height = 1 +
                Math.max(height((AVLNode<E>) getLeftChild()),
                        height((AVLNode<E>) getRightChild()));
    }

    public int getBalanceFactor() {
        int leftHeight = height((AVLNode<E>) getLeftChild());
        int rightHeight = height((AVLNode<E>) getRightChild());
        return rightHeight - leftHeight;
    }
}
