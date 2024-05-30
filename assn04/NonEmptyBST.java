package assn04;
import javax.lang.model.type.NullType;
import java.util.LinkedList;
import java.util.Queue;

public class NonEmptyBST<T extends Comparable<T>> implements BST<T> {
	private T _element;
	private BST<T> _left;
	private BST<T> _right;
	
	public NonEmptyBST(T element) {
		_left = new EmptyBST<T>();
		_right = new EmptyBST<T>();
		_element = element;
	}

	// TODO: insert
	@Override
	public BST<T> insert(T element){
		if (_element.compareTo(element) > 0) {
			if (this._left.isEmpty()) {
				this._left = new NonEmptyBST<T>(element);
			}
			else {
				this._left.insert(element);
			}
		}

		if (_element.compareTo(element) < 0) {
			if (this._right.isEmpty()){
				this._right = new NonEmptyBST<T>(element);
			}
			else {
				this._right.insert(element);
			}
		}

		return this;
		}
	
	// TODO: remove
	public BST<T> successor() {
		BST<T> p = this._right;
		while (!p.getLeft().isEmpty()) {
			p = p.getLeft();
		}
		return p;
	}
	@Override
	public BST<T> remove(T element) {
		if (_element.equals(element)) {
			if (_left.isEmpty() && _right.isEmpty()) {
				return new EmptyBST<T>();
			}
			else {
				if (_left.isEmpty()) {
					return this._right;
				}
				if (_right.isEmpty()) {
					return this._left;
				}
				BST<T> successorNode = this.successor();
				this._element = successorNode.getElement();
				this._right = this._right.remove(this._element);
			}
		}
		else {
			if (_left.isEmpty() && _right.isEmpty()) {
				return this;
			}
			if (_element.compareTo(element) > 0) {
				this._left = this._left.remove(element);
			}
			else {
				this._right = this._right.remove(element);
			}
		}
		return this;
	}
	
	// TODO: remove all in range (inclusive)
	@Override
	public BST<T> remove_range(T start, T end) {
		if (this.isEmpty()) {
			return new EmptyBST<T>();
		}
		if (start.compareTo(end) < 0) {
				if (this.getElement().compareTo(start) >= 0 && this.getElement().compareTo(end) <= 0) {
					return this.remove(this.getElement()).remove_range(start, end);
				}

				if (this.getElement().compareTo(start) > 0) {
					if (this._left.isEmpty()) { return this;}
					this._left = this.getLeft().remove_range(start, end);
				}

				if (this.getElement().compareTo(end) < 0) {
					if (this._right.isEmpty()) { return this;}
					this._right = this.getRight().remove_range(start, end);
				}

				return this;
			}

		if (start.compareTo(end) > 0) {
			this.remove_range(end, start);
		}

		if (start.compareTo(end) == 0) {
			this.remove(start);
		}
		return this;
	}
	// TODO: printPreOrderTraversal
	@Override
	public void printPreOrderTraversal() {
		if (_element == null) {
			return;
		}
		System.out.print(this._element + " ");
		this._left.printPreOrderTraversal();
		this._right.printPreOrderTraversal();
	}

	// TODO: printPostOrderTraversal
	@Override
	public void printPostOrderTraversal() {
		if (_element == null) {
			return;
		}
		this._left.printPostOrderTraversal();
		this._right.printPostOrderTraversal();
		System.out.print(this._element + " ");
	}

	// The findMin method returns the minimum value in the tree.
	@Override
	public T findMin() {
		if(_left.isEmpty()) {
			return _element;
		}
		return _left.findMin();
	}

	@Override
	public int getHeight() {
		   return Math.max(_left.getHeight(), _right.getHeight())+1;
	}

	@Override
	public BST<T> getLeft() {
		return _left;
	}

	@Override
	public BST<T> getRight() {
		return _right;
	}

	@Override
	public T getElement() {
		return _element;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
