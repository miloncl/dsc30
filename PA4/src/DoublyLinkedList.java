/*
 * NAME: Milon Lonappan
 * PID: A15231106
 */

import java.util.AbstractList;

/**
 * Doubly-Linked List Implementation
 *
 * @author Milon Lonappan
 * @since
 */
public class DoublyLinkedList<T> extends AbstractList<T> {
    private int nelems;
    private Node head;
    private Node tail;

    /**
     * Node for chaining together to create a linked list
     */
    protected class Node {
        T data;
        Node next;
        Node prev;

        /**
         * Constructor to create singleton Node
         */
        private Node(T element) {
            data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Constructor to create singleton link it between previous and next
         *
         * @param element  Element to add, can be null
         * @param nextNode successor Node, can be null
         * @param prevNode predecessor Node, can be null
         */
        private Node(T element, Node nextNode, Node prevNode) {
            data = element;
            next = nextNode;
            prev = prevNode;
        }

        /**
         * Set the element
         *
         * @param element new element
         */
        public void setElement(T element) {
            data = element;
        }

        /**
         * Accessor to get the Nodes Element
         */
        public T getElement() {
            return data;
        }

        /**
         * Set the next node in the list
         *
         * @param n new next node
         */
        public void setNext(Node n) {
            next = n;
        }

        /**
         * Get the next node in the list
         *
         * @return the successor node
         */
        public Node getNext() {
            return next;
        }

        /**
         * Set the previous node in the list
         *
         * @param p new previous node
         */
        public void setPrev(Node p) {
            prev = p;
        }


        /**
         * Accessor to get the prev Node in the list
         *
         * @return predecessor node
         */
        public Node getPrev() {
            return prev;
        }

        /**
         * Remove this node from the list.
         * Update previous and next nodes
         */
        public void remove() {
            prev.next = next;
            next.prev = prev;
        }
    }

    /**
     * Creates a new, empty doubly-linked list.
     */
    public DoublyLinkedList() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
        nelems = 0;
    }

    /**
     * Add an element to the end of the list
     *
     * @param element data to be added
     * @return whether or not the element was added
     * @throws NullPointerException if data received is null
     */
    @Override
    public boolean add(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException();
        }
        Node newNode = new Node(element);
        if (nelems == 0) {
            newNode.prev = head;
            head.next = newNode;
            newNode.next = tail;
            tail.prev = newNode;
            this.nelems++;
            return true;
        } else {
            tail.prev.next = newNode;
            newNode.prev = tail.prev;
            newNode.next = tail;
            tail.prev = newNode;
            this.nelems++;
            return true;
        }
    }


    /**
     * Adds an element to a certain index in the list, shifting exist elements
     * create room. Does not accept null values.
     *
     *
     */
    @Override
    public void add(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {

        if(element == null) {
            throw new NullPointerException();
        } else if (index < 0 || index > this.size()) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(element);
        Node previousNode;
        Node nextNode;

        if(index == 0) {
            previousNode = head;
            nextNode = head.next;
        } else if (index == this.size() - 1) {
            previousNode = tail.prev;
            nextNode = tail;
        } else {
            previousNode = getNth(index - 1);
            nextNode = getNth(index + 1);
        }

        newNode.next = nextNode;
        newNode.prev = previousNode;
        previousNode.next = newNode;
        nextNode.prev = newNode;
        this.nelems++;

    }

    /**
     * Clear the linked list
     */
    @Override
    public void clear() {
        this.head.next = tail;
        this.tail.prev = head;
        this.nelems = 0;
    }

    /**
     * Determine if the list contains the data element anywhere in the list.
     *
     *
     */
    @Override
    public boolean contains(Object element) {
        T data = (T)element;
        Node newNode = head.next;
        for(int x = 0; x < this.size() - 1; x++) {
            if(newNode.getElement().equals(data)) {
                return true;
            } else {
                newNode = newNode.next;
            }
        }
        return false;
    }

    /**
     * Retrieves the element stored with a given index on the list.
     *
     *
     */
    @Override
    public T get(int index) throws IndexOutOfBoundsException {

        if (index < 0 || index > this.size()-1) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = getNth(index);
        return newNode.getElement();

    }

    /**
     * Helper method to get the Nth node in our list
     *
     * TODO: Javadoc comments
     */
    private Node getNth(int index) {

        if (index == 0){
            return head.next;
        }
        Node newNode = head.next;
        for(int x = 0; x < this.nelems; x++) { //loops through list
            if (x == index-1){ //checks if at end of list
                newNode = newNode.next;
                break;
            }
            else{
                newNode = newNode.next;
            }
        }

        return newNode;
    }

    /**
     * Determine if the list empty
     *
     *
     */
    @Override
    public boolean isEmpty() {
        return nelems == 0;
    }

    /**
     * Remove the element from position index in the list
     *
     *
     */
    @Override
    public T remove(int index) throws IndexOutOfBoundsException {

        if (index < 0 || index > this.size()-1) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = this.getNth(index);
        newNode.remove();
        this.nelems--;
        return newNode.getElement();
    }

    /**
     * Set the value of an element at a certain index in the list.
     *
     *
     */
    @Override
    public T set(int index, T element)
            throws IndexOutOfBoundsException, NullPointerException {

        if (element == null) {
            throw new NullPointerException();
        }
        else if (index < 0 || index > this.size()-1) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = this.getNth(index);
        T returnElem = newNode.getElement();
        newNode.setElement(element);
        return returnElem;
    }

    /**
     * Retrieves the amount of elements that are currently on the list.
     *
     *
     */
    @Override
    public int size() {
        return this.nelems;
    }

    /**
     * String representation of this list in the form of:
     * "[(head) -> elem1 -> elem2 -> ... -> elemN -> (tail)]"
     *
     *
     */
    @Override
    public String toString() {
        String returnString = "[(head) -> "; //returned string
        Node newNode = this.head.next; //temp node
        for (int x = 0; x < nelems; x++){ //loops through list
            returnString += newNode.getElement().toString() + " -> ";
            newNode = newNode.next;
        }
        returnString += "(tail)]"; //adds ending
        return returnString;
    }

    /* ==================== EXTRA CREDIT ==================== */

    /**
     * Inserts another linked list of the same type into this one
     *
     * TODO: javadoc comments
     */
    public void splice(int index, DoublyLinkedList<T> otherList) throws IndexOutOfBoundsException {
        //TODO: Determine if index is valid

        //TODO: Splicing implementation. HINT: remember DoublyLinkedList's  have dummy nodes
    }

    /**
     * Determine the starting indices that match the subSequence
     *
     * TODO: javadoc comments
     */
    public int[] match(DoublyLinkedList<T> subsequence) {

        //A list to hold all the starting indices found
        DoublyLinkedList<Integer> indices = new DoublyLinkedList<>();

        //TODO: Add implementation to find the starting indices

        // Array Conversion
        int[] startingIndices = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            startingIndices[i] = indices.get(i);
        }
        return startingIndices;
    }

}