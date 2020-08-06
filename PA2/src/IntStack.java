/*
    Name: Milon Lonappan
    PID:  A15231106
 */

import java.util.EmptyStackException;

/**
 *
 * @author Milon Lonappan
 * @since
 */
public class IntStack {

    /* static constants, feel free to add more if you need */
    private static final int    MIN_INIT_CAPACITY = 3;
    private static final int    RESIZE_FACTOR     = 2;
    private static final double DEF_LOAD_FACTOR   = 0.75;
    private static final double MIN_LOAD_FACTOR   = 0.6;
    private static final double DEF_SHRINK_FACTOR = 0.2;
    private static final double MAX_SHRINK_FACTOR = 0.4;

    /* instance variables, feel free to add more if you need */
    private int[] data;
    private int nElems;
    private double loadFactor;
    private double shrinkFactor;
    private int maxCapacity;
    private int originalCap;
    private int head;

    public IntStack(int capacity, double loadF, double shrinkF) {
        try{
            if(capacity < MIN_INIT_CAPACITY) {
                throw new IllegalArgumentException("Capacity is out of valid range");
            } else if (loadF < MIN_LOAD_FACTOR || loadF > 1) {
                throw new IllegalArgumentException("Load factor is out of valid range");
            } else if (shrinkF <= 0 || shrinkF > MAX_SHRINK_FACTOR) {
                throw new IllegalArgumentException("Shrink factor is out of valid range");
            } else {
                maxCapacity = capacity;
                originalCap = capacity;
                data = new int[capacity];
                loadFactor = loadF;
                shrinkFactor = shrinkF;
                nElems = 0;
                head = -1;
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public IntStack(int capacity, double loadF) {
        this(capacity, loadF, DEF_SHRINK_FACTOR);
    }

    public IntStack(int capacity) {
        this(capacity, DEF_LOAD_FACTOR, DEF_SHRINK_FACTOR);
    }

    public boolean isEmpty() {
        if (nElems == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        nElems = 0;
        data = new int[originalCap];
        head = -1;

    }

    public int size() {
        return nElems;
    }

    public int capacity() {
        return maxCapacity;
    }

    public int peek() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        } else {
            return data[head];
        }

    }

    public void push(int element) {
        if ((double) nElems/maxCapacity >= loadFactor) {
            maxCapacity = maxCapacity * RESIZE_FACTOR;
            int[] temp = new int[maxCapacity];
            for(int x = 0; x < data.length; x++){
                temp[x] = data[x];
            }
            data = temp;
        }
        head++;
        data[head] = element;
        nElems++;

    }

    public int pop() {
        if (this.isEmpty()) {
            throw new EmptyStackException();
        } else {
            int popVal = data[head];
            data[head] = 0;
            nElems--;
            head--;
            if((double)head/maxCapacity <= shrinkFactor) {
                maxCapacity = maxCapacity / RESIZE_FACTOR;
                if(maxCapacity < originalCap) {
                    maxCapacity = originalCap;
                }
                int[] temp = new int[maxCapacity];
                for(int x = 0; x < temp.length; x++) {
                    temp[x] = data[x];
                }
                data = temp;
            }
            return popVal;
        }

    }

    public void multiPush(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        } else {
            for(int x = 0; x < elements.length; x++) {
                this.push(elements[x]);
            }
        }
    }

    public int[] multiPop(int amount) {
        if(amount < 0) {
            throw new IllegalArgumentException();
        } else {
            if (amount > nElems) {
                amount = nElems - 1;
            }
            int[] returnPops = new int[amount];
            for(int x = 0; x < amount; x++) {
                returnPops[x] = this.pop();
            }
            return returnPops;
        }

    }
}
