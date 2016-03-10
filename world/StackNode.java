package world;

/**
 * Stack node class.
 *
 * @author Devon Harker, Josh Haskins, Vincent Tennant
 * @version 2013-04-03
 */
public class StackNode {

    private Thread thread;
    private StackNode nextNode;

    /**
     * Basic constructor. Creates an empty node.
     */
    public StackNode() {
        thread = null ;
        nextNode = null;
    }

    /**
     * Second constructor. Note that this constructor does not net the next
     * node, this must be done manually(Using set next).
     *
     * @param thread The thread to put into the newly created node.
     *
     */
    public StackNode(Thread thread) {
        this.thread = thread;
        nextNode = null;
    }

    public void insert(StackNode node) {
        node.setNext(this.getNext());
        this.setNext(node);
    }

    /**
     * The getters for the node class
     *
     * @return The thread in this node.
     * @return nextNode The node after this one.
     */
    public Thread getThread() {
        return thread;
    }

    public StackNode getNext() {
        return nextNode;
    }

    /**
     * The setters for the node class. Set the Value and the nextNode.
     *
     * @param thread the
     * @param nextNode
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setNext(StackNode next) {
        nextNode = next;
    }
}
