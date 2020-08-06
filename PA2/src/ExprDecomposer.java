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
public class ExprDecomposer {

    public char[] decompose(String expr) {
        CharStack opr = new CharStack(expr.length());
        CharStack digits = new CharStack(expr.length());
        char[] letters = expr.toCharArray();
        int len = 0;
        int index = 0;
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == ' ' || letters[i] == '(' || letters[i] == ')') {
                continue;
            }
            len++; //counts number of digits and operators
        }
        char[] newExpr = new char[len]; //creates char array of correct length
        for (int i = 0; i < expr.length(); i++) { //loops through String
            char current = expr.charAt(i);
            if (current == ' ') {
                continue; //skips spaces
            }
            if (isDigit(current)) {
                digits.push(current); //adds to digits stack if digit
            } else if (isOperator(current)) {
                opr.push(current); //adds to operators stack if operator
            } else if (current == '(') {
                if (!digits.isEmpty()) {
                    newExpr[index] = digits.pop(); //puts digit value into char array to be
                    // returned if subexpression is found
                    index++;
                }
            } else if (current == ')') {
                if (!digits.isEmpty()) {
                    newExpr[index] = digits.pop(); //adds digit to array
                    index++;
                }
                if (!digits.isEmpty()) {
                    newExpr[index] = digits.pop();
                    char replaced = newExpr[index];
                    newExpr[index] = newExpr[index - 1];
                    newExpr[index - 1] = replaced; //replaces digit in char array
                    index++;
                }
                newExpr[index] = opr.pop();
                index++;
            }
        }
        while (!digits.isEmpty()) {
            if (digits.size() > 1) { //if more than one element pops top two elements of stack
                newExpr[index] = digits.pop();
                index++;
                newExpr[index] = digits.pop();
                char replaced = newExpr[index];
                newExpr[index] = newExpr[index - 1];
                newExpr[index - 1] = replaced; //swaps elements in char array
            } else {
                newExpr[index] = digits.pop(); //if one element left, adds to char array
            }
            index++;
        }
        while (!opr.isEmpty()) {
            newExpr[index] = opr.pop(); //adds operators to char array
            index++;
        }
        return newExpr;
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents a digit
     * @param token to check
     * @return boolean true if token is a digit, false otherwise
     */
    private boolean isDigit(char token) {
        return (token >= '0') && (token <= '9');
    }

    /**
     * UTILITY METHOD, DO NOT MODIFY *
     * Check if the given token represents an operator
     * @param token to check
     * @return boolean true if token is an operator, false otherwise
     */
    private boolean isOperator(char token) {
        return (token == '+') || (token == '-') || (token == '*') || (token == '/');
    }

    /**
     * Inner class CharStack.
     * Note: You can remove methods and variables that you will not use for
     * this question, but you must keep both push() and pop() methods and they
     * should function properly.
     */
    protected class CharStack {

        private static final int    MIN_INIT_CAPACITY = 3;
        private static final int    RESIZE_FACTOR     = 2;
        private static final double DEF_LOAD_FACTOR   = 0.75;
        private static final double MIN_LOAD_FACTOR   = 0.6;
        private static final double DEF_SHRINK_FACTOR = 0.2;
        private static final double MAX_SHRINK_FACTOR = 0.4;

        /* instance variables, feel free to add more if you need */
        private char[] data;
        private int nElems;
        private double loadFactor;
        private double shrinkFactor;
        private int maxCapacity;
        private int originalCap;
        private int head;

        public CharStack(int capacity, double loadF, double shrinkF) {
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
                    data = new char[capacity];
                    loadFactor = loadF;
                    shrinkFactor = shrinkF;
                    nElems = 0;
                    head = -1;
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        public CharStack(int capacity, double loadF) {
            this(capacity, loadF, DEF_SHRINK_FACTOR);
        }

        public CharStack(int capacity) {
            this(capacity, DEF_LOAD_FACTOR, DEF_SHRINK_FACTOR);
        }

        public boolean isEmpty() {
            return nElems == 0;
        }

        public void clear() {
            nElems = 0;
            data = new char[originalCap];
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

        public void push(char element) {
            if ((double) nElems/maxCapacity >= loadFactor) {
                maxCapacity = maxCapacity * RESIZE_FACTOR;
                char[] temp = new char[maxCapacity];
                for(int x = 0; x < data.length; x++){
                    temp[x] = data[x];
                }
                data = temp;
            }
            head++;
            data[head] = element;
            nElems++;
        }

        public char pop() {
            if (this.isEmpty()) {
                throw new EmptyStackException();
            } else {
                char popVal = data[head];
                data[head] = 0;
                nElems--;
                head--;
                if((double)head/maxCapacity <= shrinkFactor) {
                    maxCapacity = maxCapacity / RESIZE_FACTOR;
                    if(maxCapacity < originalCap) {
                        maxCapacity = originalCap;
                    }
                    char[] temp = new char[maxCapacity];
                    for(int x = 0; x < temp.length; x++) {
                        temp[x] = data[x];
                    }
                    data = temp;
                }
                return popVal;
            }
        }
    }
}
