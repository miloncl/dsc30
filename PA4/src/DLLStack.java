/**
 * Stack implementation using Doubly-linked list.
 * @param <T> generic container
 */
public class DLLStack<T> {

    private DoublyLinkedList<T> stack;

    public DLLStack() {
        stack = new DoublyLinkedList<>();
    }

    public int size() {
        return stack.size();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void push(T data) {
        stack.add(data);
    }

    public T pop() {
        if(stack.isEmpty()) {
            return null;
        } else {
            return stack.remove(stack.size() - 1);
        }
    }

    public T peek() {
        if(stack.isEmpty()) {
            return null;
        } else {
            return stack.get(stack.size() - 1);
        }
    }

}
