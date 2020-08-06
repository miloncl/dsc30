
/*
 * Name: Milon Lonappan
 * PID:  A16231106
 */
import java.util.*;

/**
 * Binary search tree implementation.
 * 
 * @author TODO
 * @since  TODO
 */
public class BSTree<T extends Comparable<? super T>> implements Iterable {

    /* * * * * BST Instance Variables * * * * */

    private int nelems; // number of elements stored
    private BSTNode root; // reference to root node

    /* * * * * BST Node Inner Class * * * * */

    protected class BSTNode {

        T key;
        LinkedList<T> dataList;
        BSTNode left;
        BSTNode right;

        /**
         * A constructor that initializes the BSTNode instance variables.
         *
         * @param left     Left child
         * @param right    Right child
         * @param dataList Linked list of related info
         * @param key      Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, LinkedList<T> dataList, T key) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.dataList = dataList;
        }

        /**
         * A constructor that initializes BSTNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty LinkedList for the node.
         *
         * @param left  Left child
         * @param right Right child
         * @param key   Node's key
         */
        public BSTNode(BSTNode left, BSTNode right, T key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.dataList = new LinkedList<T>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public T getKey() {
            return this.key;
        }

        /**
         * Return the left child of the node
         *
         * @return The left child of the node
         */
        public BSTNode getLeft() {
            return this.left;
        }

        /**
         * Return the right child of the node
         *
         * @return The right child of the node
         */
        public BSTNode getRight() {
            return this.right;
        }

        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public LinkedList<T> getDataList() {
            return this.dataList;
        }

        /**
         * Setter for left child of the node
         *
         * @param newleft New left child
         */
        public void setleft(BSTNode newleft) {
            this.left = newleft;
        }

        /**
         * Setter for right child of the node
         *
         * @param newright New right child
         */
        public void setright(BSTNode newright) {
            this.right = newright;
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newData New linked list
         */
        public void setDataList(LinkedList<T> newData) {
            this.dataList = newData;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewInfo(T data) {
            this.dataList.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removeInfo(T data) {
            if (dataList.contains(data)) {
                dataList.remove(data);
                return true;
            } else {
                return false;
            }
        }
    }

    /* * * * * BST Methods * * * * */

    /**
     * 0-arg constructor that initializes root to null and nelems to 0
     */
    public BSTree() {
        this.root = null;
        this.nelems = 0;
    }

    /**
     * Return the root of BSTree. Returns null if the tree is empty
     *
     * @return The root of BSTree, null if the tree is empty
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * Return the BST size
     *
     * @return The BST size
     */
    public int getSize() {
        return this.nelems;
    }

    /**
     * Insert a key into BST
     * 
     * @param key
     * @return true if insertion is successful and false otherwise
     */
    public boolean insert(T key) {

        if (key == null) {
            throw new NullPointerException();
        }
        if (root == null) {
            root = new BSTNode(null, null, key);
            this.nelems++;
        } else {
            return insertHelpFunc(root, key);
        }
        
    }

    /**
     * Helper method for insert
     */
    private boolean insertHelpFunc(BSTNode root, T key){

        if (key.compareTo(root.getKey()) == 0) {
            return false;
        } else if (key.compareTo(root.getKey()) > 0) {
            if (root.getRight() == null) {
                root.setright(new BSTNode(null, null, key));
                this.nelems++;
            } else {
                insertHelpFunc(root.right, key);
            }
        } else if (key.compareTo(root.getKey()) < 0) {
            if (root.getLeft() == null) {
                root.setleft(new BSTNode(null, null, key));
                this.nelems++;
            } else {
                insertHelpFunc(root.left, key);
            }
        }
        return true;
    }

    /**
     * Return true if the 'key' is found in the tree, false otherwise
     *
     * @param key To be searched
     * @return True if the 'key' is found, false otherwise
     * @throws NullPointerException If key is null
     */
    public boolean findKey(T key) {

        if (key == null) {
            throw new NullPointerException();
        }

        return findHelperFunc(this.root, key);
    }

    /**
     * Helper method for find
     */
    private boolean findHelperFunc (BSTNode root, T key){

        if (root == null) {
            return false;
        } else if (root.key == null) {
            return false;
        } else if (root.key.compareTo(key) == 0) {
            return true;
        } else if (key.compareTo(root.key) < 0) {
            return findHelperFunc(root.left, key);
        } else if (key.compareTo(root.key) > 0){
            return findHelperFunc(root.right, key);
        }
        return true;
    }
    /**
     * Insert 'data' into the LinkedList of the node whose key is 'key'
     *
     * @param key  Target key
     * @param data To be added to key's LinkedList
     * @throws NullPointerException     If eaither key or data is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public void insertData(T key, T data) {

        if (key == null || data == null) {
            throw new NullPointerException();
        }
        if (findKey(key) == false) {
            throw new IllegalArgumentException();
        }

        BSTNode newNode = this.root;
        while (key.compareTo(newNode.getKey()) != 0) {
            if (key.compareTo(newNode.getKey()) > 0) {
                BSTNode newRight = newNode.getRight();
                newNode = newRight;
            } else if (key.compareTo(newNode.getKey()) < 0) {
                BSTNode newLeft = newNode.getLeft();
                newNode = newLeft;
            }
        }
        newNode.addNewInfo(data);
    }

    /**
     * Return the LinkedList of the node with key value 'key'
     *
     * @param key Target key
     * @return LinkedList of the node whose key value is 'key'
     * @throws NullPointerException     If key is null
     * @throws IllegalArgumentException If key is not found in the BST
     */
    public LinkedList<T> findDataList(T key) {

        if (key == null){
            throw new NullPointerException();
        }
        if (findKey(key) == false){
            throw new IllegalArgumentException();
        }

        BSTNode nodeCompare = this.root;
        while (nodeCompare.key.compareTo(key) != 0) {
            if (key.compareTo(nodeCompare.key) < 0) {
                nodeCompare = nodeCompare.left;
            } else if (key.compareTo(nodeCompare.key) > 0) {
                nodeCompare = nodeCompare.right;
            }
        }

        return nodeCompare.getDataList();
    }

    /**
     * Return the height of the tree
     *
     * @return The height of the tree, -1 if BST is empty
     */
    public int findHeight() {
        return findHeightHelper(this.getRoot());
    }

    /**
     * Helper for the findHeight method
     *
     * @param root Root node
     * @return The height of the tree, -1 if BST is empty
     */
    private int findHeightHelper(BSTNode root) {

        if (root == null) {
            return -1;
        }

        int left = findHeightHelper(root.left);
        int right = findHeightHelper(root.right);
        return 1 + Math.max(left, right);
    }

    /**
     * Return the number of leaf nodes in the tree
     *
     * @return The number of leaf nodes in the tree
     */
    public int leafCount() {
        return leafCountHelper(this.getRoot());
    }

    /**
     * Helper for the leafCount method
     *
     * @param root Root node
     * @return The number of leaf nodes in the tree
     */
    private int leafCountHelper(BSTNode root) {

        if (root == null) {
            return 0;
        } else if(root.right == null || root.left == null) {
            return 1;
        }

        int left = leafCountHelper(root.left);
        int right = leafCountHelper(root.right);
        return left + right;
    }

    /* * * * * BST Iterator * * * * */

    public class BSTree_Iterator implements Iterator<T> {

        private Stack<BSTNode> stack;
        private BSTNode nodeCompare;

        public BSTree_Iterator() {
            stack = new Stack<BSTNode>();
            nodeCompare = root;

            while (nodeCompare != null) { //goes until compare is null
                stack.push(nodeCompare); //pushes into stack
                nodeCompare = nodeCompare.left; //goes to left
            }
        }

        public boolean hasNext() {
            return stack.size() == 0;
        }

        public T next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            BSTNode pop = stack.pop();
            BSTNode right = pop.right;

            while (right != null) {
                stack.push(right);
                right = right.left;
            }

            return pop.getKey();
        }
    }

    public Iterator<T> iterator() {
        return new BSTree_Iterator();
    }

    /* * * * * Extra Credit Methods * * * * */

    public ArrayList<T> intersection(Iterator<T> iter1, Iterator<T> iter2) {
        /* TODO */
        return null;
    }

    public int levelCount(int level) {
        /* TODO */
        return -1;
    }
}
