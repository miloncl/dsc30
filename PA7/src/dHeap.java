/*
 * Name: Milon Lonappan
 * PID:  Milon Lonappan
 */

import java.util.*;

/**
 *
 * 
 * @param <T> Generic type
 */
public class dHeap<T extends Comparable<? super T>> implements dHeapInterface<T> {

    private T[] heap; // heap array
    private int d; // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // boolean to indicate whether heap is max or min
    private int DEFAULT_SIZE = 6;
    private int doublingVal = 2;

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        heap = (T[]) new Comparable[DEFAULT_SIZE];
        d = doublingVal;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        heap = (T[]) new Comparable[heapSize];
        d = doublingVal;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        heap = (T[]) new Comparable[heapSize];
        this.d = d;
        nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    @Override
    public int size() {
        return this.nelems;
    }

    @Override
    public void add(T data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }

        if(this.heap.length == this.size()) {
            this.resize();
        }

        this.heap[nelems] = data;
        bubbleUp(nelems);
        nelems++;
    }

    @Override
    public T remove() throws NoSuchElementException {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }

        swap(0, nelems-1);
        T toReturn = this.heap[nelems-1];
        nelems--;
        trickleDown(0);
        return toReturn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        T[] array = (T[]) new Comparable[this.heap.length];
        this.heap = array;
        this.nelems = 0;
    }

    public T element() throws NoSuchElementException {
        if (this.size() == 0) {
            throw new NoSuchElementException();
        }
        else {
            return this.heap[0];
        }
    }

    private void trickleDown(int index) {

        int child = this.d * index + 1;
        T pValue = this.heap[index];

        while (child < this.size()) {
            T maxVal = pValue;
            int maxIndex = -1;

            for (int x = 0; x < d && x + child < this.size(); x++){
                if(compareFuncTrickle(x + child, maxVal)) {
                    maxVal = this.heap[x + child];
                    maxIndex = x + child;
                }

            }
            if (maxVal.compareTo(pValue) == 0) {
                return;
            } else {
                swap(index, maxIndex);
                index = maxIndex;
                child = this.d * index + 1;
            }
        }
    }

    private boolean compareFuncTrickle(int child, T max) {

        if (isMaxHeap) {
            if (this.heap[child].compareTo(max) > 0 ) {
                return true;
            } else {
                return false;
            }
        } else {
            if (this.heap[child].compareTo(max) < 0 ) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void bubbleUp(int index) {

        while (index > 0) {
            int parentIndex = parent(index);
            if (compareFuncBubble(index, parentIndex)) {
                return;
            } else {
                swap(index, parentIndex);
                index = parentIndex;
            }
        }
    }

    private boolean compareFuncBubble(int child, int parentIndex){
        if (this.isMaxHeap) {
            return this.heap[child].compareTo(this.heap[parentIndex]) <= 0;
        } else {
            return this.heap[child].compareTo(this.heap[parentIndex]) >= 0;
        }
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] arr = (T[]) new Comparable[this.heap.length * doublingVal];
        System.arraycopy(this.heap, 0, arr, 0, this.heap.length);
        this.heap = arr;
    }

    private int parent(int index) {
        if(index - d <= 0) {
            return 0;
        } else {
            return (index-1)/d;
        }
    }


    private void swap(int index1, int index2){
        T swap = this.heap[index1];  //stores index
        this.heap[index1] = this.heap[index2];
        this.heap[index2] = swap;
    }
}
