package world;

/**
 * A stack that is used to hold all of the newly created people. This stack
 * allows for easy starting of the people threads.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
public class Stack {

    private StackNode top;
    private StackNode base;
    private int size;

    /**
     * Creates a new, empty stack.
     */
    public Stack() {
        top = null;
        base = null;
        size = 0;
    }

    /**
     * Pushes a thread to the top of the stack list.
     *
     * @param thread The thread to push.
     */
    public void push(Thread thread) {
        StackNode node = new StackNode(thread);

        if (top == null) {
            node.setNext(null);
            top = node;
            base = node;
        } else {
            top.insert(node);
            top = node;
        }
    }

    /**
     * Pops the top element off of the stack list.
     *
     * @return Returns the thread in the stack node deleted.
     */
    public Thread pop() {
        StackNode pointer = base;
        Thread t1 = top.getThread();

        if (base == top) {
            top = null;
            base = null;
        } else {

            while (pointer.getNext().getNext() != null) {
                pointer = pointer.getNext();
            }
            //Sets the top element to the pointer node(Secsond node)
            top = pointer;
            pointer.setNext(null);
        }
        return t1;
    }

    /**
     * Returns the top of the stack.
     *
     * @return The thread that is at the top of the stack.
     */
    public Thread top() {
        return top.getThread();
    }

    /**
     * Returns true or false based on whether the stack is full or not.
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (base == null) {
            return true;
        } else {
            return false;
        }
    }
}
