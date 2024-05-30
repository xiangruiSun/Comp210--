package assn05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MaxBinHeapER  <V, P extends Comparable<P>> implements BinaryHeap<V, P> {

    private List<Prioritized<V,P>> _heap;

    /**
     * Constructor that creates an empty heap of hospital.Prioritized objects.
     */
    public MaxBinHeapER() {
        _heap = new ArrayList<>();
    }

    @Override
    public int size() {
        return _heap.size();
    }

    public MaxBinHeapER bubbleup(int i) {
        if (i<=0) {
            return this;
        }
        else {
            if (i % 2 == 0) {
                if (_heap.get((i - 2) / 2).getPriority().compareTo(_heap.get(i).getPriority()) < 0) {
                    Prioritized<V,P> parent = _heap.get((i - 2) / 2);
                    _heap.set((i - 2) / 2, _heap.get(i));
                    _heap.set(i, parent);
                    this.bubbleup((i - 2) / 2);
                }
            }
            if (i % 2 == 1) {
                if (_heap.get((i - 1) / 2).getPriority().compareTo(_heap.get(i).getPriority()) < 0) {
                    Prioritized<V,P> parent = _heap.get((i - 1) / 2);
                    _heap.set((i - 1) / 2, _heap.get(i));
                    _heap.set(i, parent);
                    this.bubbleup((i - 1) / 2);
                }
            }
        }
        return this;
    }

    // TODO (Task 2A): enqueue
    public void enqueue(V value) {
        Prioritized<V,P> A = new Patient<>(value);
        _heap.add(A);
        this.bubbleup(this.size()-1);
    }

    // TODO (Task 2A): enqueue
    @Override
    public void enqueue(V value, P priority) {
        Prioritized<V,P> A = new Patient<>(value,priority);
        _heap.add(A);
        this.bubbleup(this.size()-1);
    }

    public MaxBinHeapER bubbledown(int i) {
        if (2*i+1 > _heap.size()-1) { return this;}
        else {
            Prioritized<V,P> parent = _heap.get(i);
            Prioritized<V,P> lChild = _heap.get(2*i+1);
            Prioritized<V,P> rChild = new Patient<>(null, null);
            boolean rChildExist = false;
            if (2*i + 2 <= _heap.size()-1) {
                rChild = _heap.get(2*i+2);
                rChildExist = true;
            }
            Prioritized<V,P> largerChild = lChild;
            int largerindex = 2*i+1;
            if (rChildExist){
                if (rChild.getPriority().compareTo(lChild.getPriority())>0) {
                largerChild = rChild;
                largerindex = 2*i+2;
                }
            }
            if (parent.getPriority().compareTo(largerChild.getPriority()) < 0) {
                _heap.set(i, _heap.get(largerindex));
                _heap.set(largerindex, parent);
                this.bubbledown(largerindex);
            }
        }
        return this;
    }

    // TODO (Task 2A): dequeue
    @Override
    public V dequeue() {
        if (_heap.isEmpty()) {
            return null;
        }
        Prioritized<V,P> dPatient = _heap.get(0);
        V v = dPatient.getValue();
        _heap.remove(0);
        if (!_heap.isEmpty()) {
            _heap.add(0, _heap.remove(_heap.size()-1));
            this.bubbledown(0);
        }
        return v;
    }

    // TODO (Task 2A): getMax
    @Override
    public V getMax() {
    	 if (_heap.isEmpty()){return null;}
         return _heap.get(0).getValue();
    }

    // TODO (part 2B) : updatePriority
    public void updatePriority(V value, P newPriority) {
        int index = 0;
        boolean exist = false;
        for (int i = 0; i <= _heap.size()-1; i++) {
            if (_heap.get(i).getValue().equals(value)) {
                index = i;
                exist = true;
            }
        }
        if (!exist) {return;}
        else {
            P prePriority = _heap.get(index).getPriority();
            Prioritized<V,P> newElement = new Patient<>(value, newPriority);
            _heap.set(index,newElement);
            this.bubbleup(index);
            this.bubbledown(index);
        }
    }

    /**
     * Constructor that builds a heap given an initial array of hospital.Prioritized objects.
     */
    // TODO (Task 3): overloaded constructor
    public MaxBinHeapER(Prioritized<V, P>[] initialEntries ) {
        _heap = new ArrayList<>();
        for (int i = 0; i <initialEntries.length; i ++) {
            _heap.add(initialEntries[i]);
        }
        int lastIndex = _heap.size() - 1;
        int parentIndex = 0;
        if (lastIndex % 2 == 0) { parentIndex = (lastIndex-2)/2; }
        else {parentIndex = (lastIndex-1)/2;}
        while (parentIndex >= 0){
            this.bubbledown(parentIndex);
            parentIndex --;
        }
    }

    @Override
    public Prioritized<V, P>[] getAsArray() {
        Prioritized<V,P>[] result = (Prioritized<V, P>[]) Array.newInstance(Prioritized.class, size());
        return _heap.toArray(result);
    }

}
