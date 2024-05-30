package assn06;

import java.util.ArrayList;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;
    
    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    /**
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */
    private AVLTree<T> rotateLeft() {
        AVLTree<T> newRoot = this._right;
        this._right = newRoot._left;
        newRoot._left = this;
        this.updateHeightandSize(); // Assuming this updates the current node's height
        newRoot.updateHeightandSize(); // And then the new root's
        return newRoot; // Returning the new root of this subtree
    }

    /**
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */
    private void updateHeightandSize() {
        int leftHeight, rightHeight, leftSize, rightSize;

        if (_left == null) {
            leftHeight = -1;
        } else {
            leftHeight = _left._height;
        }

        if (_right == null) {
            rightHeight = -1;
        } else {
            rightHeight = _right._height;
        }

        if (_left == null) {
            leftSize = 0;
        } else {
            leftSize = _left._size;
        }

        if (_right == null) {
            rightSize = 0;
        } else {
            rightSize = _right._size;
        }

        this._height = 1 + Math.max(leftHeight, rightHeight);

        this._size = leftSize + rightSize + 1;
    }


    private AVLTree<T> rotateRight() {
        AVLTree<T> newRoot = this._left;
        this._left = newRoot._right;
        newRoot._right = this;
        this.updateHeightandSize();
        newRoot.updateHeightandSize();
        return newRoot;
    }

    private AVLTree<T> rotateLR() {
        AVLTree<T> z = this;
        AVLTree<T> y = z._left;
        AVLTree<T> x = y._right;
        y._right = x._left;
        y.updateHeightandSize();
        x._left = y;
        x.updateHeightandSize();
        z._left = x;
        z.updateHeightandSize();
        AVLTree<T> tavlTree = z.rotateRight();
        return tavlTree;
    }

    private AVLTree<T> rotateRL() {
        AVLTree<T> z = this;
        AVLTree<T> y = z._right;
        AVLTree<T> x = y._left;
        y._left = x._right;
        y.updateHeightandSize();
        x._right = y;
        x.updateHeightandSize();
        z._right = x;
        z.updateHeightandSize();
        AVLTree<T> tavlTree = z.rotateLeft();
        return tavlTree;
    }

    private int balanceFactor() {
        int leftHeight, rightHeight;

        if (_left == null) {
            leftHeight = -1;
        } else {
            leftHeight = _left._height;
        }

        if (_right == null) {
            rightHeight = -1;
        } else {
            rightHeight = _right._height;
        }

        return leftHeight - rightHeight;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public SelfBalancingBST<T> insert(T element) {
        if (isEmpty()) {
            // Tree is empty; insert the new node at the root
            _value = element;
            _height = 0;
            _size = 1;
            _left = new AVLTree<>();
            _right = new AVLTree<>();
            return this;
        } else if (_value.compareTo(element) > 0) {
            // Insert in the left subtree
            _left = (AVLTree<T>) _left.insert(element);
        } else if (_value.compareTo(element) < 0) {
            // Insert in the right subtree
            _right = (AVLTree<T>) _right.insert(element);
        } else {
            // The element is already in the tree; don't insert duplicates
            return this;
        }

        // Update height and size of this node
        updateHeightandSize();

        // Balance the tree and return the new root if necessary
        return balance();
    }

    private AVLTree<T> balance() {
        int balanceFactor = balanceFactor();

        // Left heavy subtree
        if (balanceFactor > 1) {
            if (_left != null && _left.balanceFactor() < 0) {
                _left = _left.rotateLeft(); // Left-Right case
            }
            return rotateRight(); // Left-Left case
        }

        // Right heavy subtree
        if (balanceFactor < -1) {
            if (_right != null && _right.balanceFactor() > 0) {
                _right = _right.rotateRight(); // Right-Left case
            }
            return rotateLeft(); // Right-Right case
        }

        return this;
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
        if (_value == null) {
            // The tree is empty or the element is not found.
            return this;
        }

        int comparison = element.compareTo(_value);
        if (comparison < 0) {
            // The element to be removed is in the left subtree.
            if (_left != null) _left = (AVLTree<T>) _left.remove(element);
        } else if (comparison > 0) {
            // The element to be removed is in the right subtree.
            if (_right != null) _right = (AVLTree<T>) _right.remove(element);
        } else {
            // Found the node to remove.
            if (_left.isEmpty() && _right.isEmpty()) {
                // Node is a leaf.
                return new AVLTree<>(); // Return an empty tree.
            } else if (!_left.isEmpty() && _right.isEmpty()) {
                // Node has only a left child.
                return _left;
            } else if (_left.isEmpty() && !_right.isEmpty()) {
                // Node has only a right child.
                return _right;
            } else {
                // Node has two children. Use the in-order successor.
                _value = _right.findMin();
                _right = (AVLTree<T>) _right.remove(_value);
            }
        }

        // Update this node's height and size.
        this.updateHeightandSize();

        // Balance this node if necessary and return the root of the balanced subtree.
        return balance();
    }

    @Override
    public T findMin() {
        if (_value == null) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        // Corrected to check if the left subtree is empty
        if (_left.isEmpty()) {
            return _value;
        } else {
            return _left.findMin();
        }
    }

    @Override
    public T findMax() {
        if (_value == null) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        // Corrected to check if the right subtree is empty
        if (_right.isEmpty()) {
            return _value;
        } else {
            return _right.findMax();
        }
    }


    @Override
    public boolean contains(T element) {
    	// TODO
        if (_value == null) {
            return false;
        }

        if(_value.compareTo(element) == 0){
            return true;
        }

        if(_value.compareTo(element) > 0){
            if(_left == null){
                return false;
            }
            else{
                return _left.contains(element);
            }
        }

        if(_value.compareTo(element) < 0){
            if(_right ==null){
                return false;
            }
            else{
                return _right.contains(element);
            }
        }
        return false;
    }


    @Override
    public boolean rangeContain(T start, T end) {
        // TODO
        if (_value == null) {
            return false;
        }

        if(_value.compareTo(end)<=0 && _value.compareTo(start)>=0){
            return true;
        }

        if(_value.compareTo(start)<0){
            if(_right==null){
                return false;
            }
            else{
                return _right.rangeContain(start,end);
            }
        }

        if(_value.compareTo(end)>0){
            if(_left==null){
                return false;
            }
            else{
                return _left.rangeContain(start,end);
            }
        }
        return false;
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }
         return _right;
    }

}

