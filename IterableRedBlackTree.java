import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class IterableRedBlackTree<T extends Comparable<T>>
    extends RedBlackTree<T> implements IterableSortedCollection<T> {

    private Comparable<T> iterationStartPoint = new Comparable<T>() {
        @Override
        public int compareTo(T o) {
            return -1;
        }
    };
    public void setIterationStartPoint(Comparable<T> startPoint) {
        if (startPoint == null) {
            iterationStartPoint = new Comparable<T>() {
                @Override
                public int compareTo(T o) {
                    return 0;
                }
            };
        }
        else {
            iterationStartPoint = startPoint;
        }
    }

    public Iterator<T> iterator() {
        return new RBTIterator<>(root, iterationStartPoint);
    }

    private static class RBTIterator<R extends Comparable<R>> implements Iterator<R> {
        private Stack<Node<R>> stack = new Stack<>();
        private Comparable<R> startPoint;

        public RBTIterator(Node<R> root, Comparable<R> startPoint) {
            this.startPoint = startPoint;
            buildStackHelper(root);
        }

        private void buildStackHelper(Node<R> node) {
            // Base case
            if (node == null) {
                return;
            }

            // Recursive case 1 : When data within node is smaller than start value
            if (startPoint.compareTo(node.data) > 0) {
                buildStackHelper(node.down[1]); // Recursively calls method on nodes right subtree
            }
            // Recursive case 2: When data within node is greater than or equal to start value
            else {
                stack.push(node); // Pushes node to stack
                buildStackHelper(node.down[0]); // Recursively calls method on nodes left subtree
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public R next() {
            // Throws NoSuchElementException if next() is called with empty stack
            if (!hasNext()) {
                throw new NoSuchElementException(); //
            }

            Node<R> nextNode = stack.pop();

            if (nextNode.down[1] != null) {
                buildStackHelper(nextNode.down[1]);
            }
            return nextNode.data;
        }
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * @param newNode the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    @Override
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                //if (compare == 0) {
                  //  return false;
                //}
                if (compare <= 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1];
                    }
                }
            }
        }
    }

    /**
     * Tests if tree iterates correctly when using integers
     */
    @Test
    public void testIntegerIteration() {
        // Input values
        IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
        tree.insert(4);
        tree.insert(2);
        tree.insert(1);
        tree.insert(5);
        tree.insert(3);

        Iterator<Integer> iterator = tree.iterator();

        // Tests if iterator has found values in the tree
        assertTrue(iterator.hasNext(), "Tree is not being iterated through");
        // Tests if values are in order of smallest to biggest
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(4), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
        // Tests if iterator stops iterating at the end of the tree
        assertFalse(iterator.hasNext(), "Should be end of tree");
    }

    @Test
    public void testStringIteration() {
        // Input values
        IterableRedBlackTree<String> tree = new IterableRedBlackTree<>();
        tree.insert("e");
        tree.insert("c");
        tree.insert("a");
        tree.insert("d");
        tree.insert("b");

        Iterator<String> iterator = tree.iterator();

        // Tests if iterator found strings in the tree
        assertTrue(iterator.hasNext());
        // Tests if iterator goes through strings in alphabetical order
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertEquals("c", iterator.next());
        assertEquals("d", iterator.next());
        assertEquals("e", iterator.next());
        // Tests if iterator stops iterating at the end of the tree
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testStartingPointIteration() {
        IterableRedBlackTree<Integer> tree = new IterableRedBlackTree<>();
        tree.insert(4);
        tree.insert(2);
        tree.insert(1);
        tree.insert(5);
        tree.insert(3);

        tree.setIterationStartPoint(3); // Setting starting point
        Iterator<Integer> iterator = tree.iterator();

        // Tests if iterator has found value in the tree
        assertTrue(iterator.hasNext());
        // Tests if iterator starts from 3 and continues in ascending order
        assertEquals(Integer.valueOf(3), iterator.next());
        assertEquals(Integer.valueOf(4), iterator.next());
        assertEquals(Integer.valueOf(5), iterator.next());
        // Tests if iterator stops iterating at end of the tree
        assertFalse(iterator().hasNext());
    }
}
