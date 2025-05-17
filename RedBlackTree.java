import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>{

    /**
     * Stores the color for each node in addition to the node's parent, children and data
     * @param <T>
     */
    protected static class RBTNode<T> extends Node<T> {
        public boolean isBlack = false;
        public RBTNode(T data) { super(data); }
        public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
        public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
        public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
    }

    /**
     * Resolves any red property violations that are introduced by inserting a new node
     * into the Red-Black Tree
     * @param newNode
     */
    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
        newNode.isBlack = false; // Setting new node to red

        // Violation for red node with red children
        while (newNode != root && !newNode.isBlack && !newNode.getUp().isBlack) {
            // If parent is a left child
            boolean parentIsLeftChild = newNode.getUp().getUp().getDownLeft() == newNode.getUp();

            // To get aunt
            RBTNode<T> aunt = parentIsLeftChild ? newNode.getUp().getUp().getDownRight()
                    : newNode.getUp().getUp().getDownLeft();

            // Case 1: Red Aunt Case
            if (aunt != null && !aunt.isBlack) {
                newNode.getUp().isBlack = true;
                aunt.isBlack = true;
                newNode.getUp().getUp().isBlack = false;
                newNode = newNode.getUp().getUp(); // Recurring case
            }
            else if (aunt != null && aunt.isBlack) {
                if (parentIsLeftChild) {
                    if (newNode == newNode.getUp().getDownLeft()) {
                        newNode = newNode.getUp();
                        rotate(newNode, newNode.getUp());
                        newNode.getDownRight().isBlack = false;
                        newNode.isBlack = true;
                    }
                    else {
                        rotate(newNode, newNode.getUp());
                        rotate(newNode, newNode.getUp());
                        newNode.isBlack = true;
                    }
                }
                else if (!parentIsLeftChild) {
                    if (newNode == newNode.getUp().getDownRight()) {
                        newNode = newNode.getUp();
                        rotate(newNode, newNode.getUp());
                        newNode.getDownLeft().isBlack = false;
                        newNode.isBlack = true;
                    }
                    else {
                        rotate(newNode, newNode.getUp());
                        rotate(newNode, newNode.getUp());
                        newNode.getDownLeft().isBlack = false;
                        newNode.isBlack = true;
                    }
                }
            }

            // Other Case : No aunt and Parent is left child
            if (parentIsLeftChild && aunt == null) {
                if (newNode == newNode.getUp().getDownRight()) { // New Node is right child
                    newNode.isBlack = true;
                    newNode.getUp().getUp().isBlack = false;
                    rotate(newNode, newNode.getUp());
                    rotate(newNode, newNode.getUp());
                    newNode = newNode.getDownRight();
                }
                else if (newNode == newNode.getUp().getDownLeft()) { // New Node is left child
                    newNode = newNode.getUp();
                    rotate(newNode, newNode.getUp());
                    newNode.getDownRight().isBlack = false;
                    newNode.isBlack = true;
                }
            }
            // Other Case: No aunt and Parent is right child
            if (!parentIsLeftChild && aunt == null) { // New Node is left child
                if (newNode == newNode.getUp().getDownLeft()) {
                    rotate(newNode, newNode.getUp());
                    rotate(newNode, newNode.getUp());
                    newNode.getDownLeft().isBlack = false;
                    newNode.isBlack = true;
                }
                else if (newNode == newNode.getUp().getDownRight()) { // New Node is right child
                    newNode = newNode.getUp();
                    rotate(newNode, newNode.getUp());
                    newNode.getDownLeft().isBlack = false;
                    newNode.isBlack = true;
                }
            }
        }
    }

    /**
     * Ensures insert method in BinarySearchTree class follows RBT properties
     * @param data
     * @return
     */
    @Override
    public boolean insert(T data) {
        RBTNode<T> newNode = new RBTNode<>(data);

        if (insertHelper(newNode)) {
            enforceRBTreePropertiesAfterInsert(newNode);

            if (((RBTNode<T>)root).isBlack == false) {
                ((RBTNode<T>)root).isBlack = true;
            }
            return true;
        }
        return false;
    }

    /**
     * Tests if when inserting only three nodes where parent is right child of grandparent,
     * and new node is the left child of parent, it should rotate to reduce length of RBT while
     * maintaining RBT properties
     */
    @Test
    public void testCaseOne() {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(5);
        redBlackTree.insert(15);
        redBlackTree.insert(10);

        // Checking if root after rotation is black and 10
        assertTrue(((RBTNode<Integer>)redBlackTree.root).isBlack, "Root should be black");
        assertEquals(Integer.valueOf(10), redBlackTree.root.data, "Root should be 10");

        // Assigning left and right child
        RBTNode<Integer> leftChild = ((RBTNode<Integer>)redBlackTree.root).getDownLeft();
        RBTNode<Integer> rightChild = ((RBTNode<Integer>)redBlackTree.root).getDownRight();

        // Checking if left child of root is red and 5
        assertFalse(leftChild.isBlack, "Left child of root should be red");
        assertEquals(Integer.valueOf(5), leftChild.data, "Left child of root should be 5");

        // Checking if right child of root is red and 15
        assertFalse(rightChild.isBlack, "Right child of root should be red");
        assertEquals(Integer.valueOf(15), rightChild.data, "Right child of root should be 5");
    }

    /**
     * Tests if when inserting 4 nodes, child should be red while parent and aunt is black ensuring
     * solution to Red Aunt Case
     */
    @Test
    public void testCaseTwo() {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(10);
        redBlackTree.insert(5);
        redBlackTree.insert(15);
        redBlackTree.insert(3);

        // Checking if root (grandparent) after rotation is black and 10
        assertTrue(((RBTNode<Integer>)redBlackTree.root).isBlack, "Root should be black");
        assertEquals(Integer.valueOf(10), redBlackTree.root.data, "Root should be 10");

        // Assigning left and right parent, and child
        RBTNode<Integer> leftParent = ((RBTNode<Integer>)redBlackTree.root).getDownLeft();
        RBTNode<Integer> rightParent = ((RBTNode<Integer>)redBlackTree.root).getDownRight();
        RBTNode<Integer> child = ((RBTNode<Integer>)redBlackTree.root).getDownLeft().getDownLeft();

        // Checking if left parent is black and 5
        assertTrue(leftParent.isBlack, "Left parent should be black");
        assertEquals(Integer.valueOf(5), leftParent.data, "Left parent should be 5");

        // Checking if right parent is black and 15
        assertTrue(rightParent.isBlack, "Right parent should be black");
        assertEquals(Integer.valueOf(15), rightParent.data, "Right parent should be 5");

        // Checking if child is red and 3
        assertFalse(child.isBlack, "Child should be red");
        assertEquals(Integer.valueOf(3), child.data, "Child should be 3");
    }

    /**
     * Tests if when inserting a larger table, all values are inserted correctly and follow RBT properties
     */
    @Test
    public void testCaseThree() {
        RedBlackTree<Integer> redBlackTree = new RedBlackTree<>();
        redBlackTree.insert(2);
        redBlackTree.insert(1);
        redBlackTree.insert(4);
        redBlackTree.insert(5);
        redBlackTree.insert(9);
        redBlackTree.insert(3);
        redBlackTree.insert(6);
        redBlackTree.insert(7);

        // Checking if root after rotation is black and 10
        assertTrue(((RBTNode<Integer>)redBlackTree.root).isBlack, "Root should be black");
        assertEquals(Integer.valueOf(2), redBlackTree.root.data, "Root should be 10");

        // Assigninging Nodes
        RBTNode<Integer> leftGrandparent = ((RBTNode<Integer>)redBlackTree.root).getDownLeft();
        RBTNode<Integer> rightGrandparent = ((RBTNode<Integer>)redBlackTree.root).getDownRight();
        RBTNode<Integer> leftParent = ((RBTNode<Integer>)redBlackTree.root).getDownRight().getDownLeft();
        RBTNode<Integer> rightParent = ((RBTNode<Integer>)redBlackTree.root).getDownRight().getDownRight();
        RBTNode<Integer> grandchildOne = ((RBTNode<Integer>)redBlackTree.root).getDownRight().getDownLeft().
                getDownLeft();
        RBTNode<Integer> grandchildTwo = ((RBTNode<Integer>)redBlackTree.root).getDownRight().getDownRight().
                getDownLeft();
        RBTNode<Integer> grandchildThree = ((RBTNode<Integer>)redBlackTree.root).getDownRight().getDownRight().
                getDownRight();

        // Checking if leftGrandparent is black and 1
        assertTrue(leftGrandparent.isBlack, "leftGrandparent should be black");
        assertEquals(Integer.valueOf(1), leftGrandparent.data, "LeftGrandparent should be 1");

        // Checking if rightGrandparent is red and 5
        assertFalse(rightGrandparent.isBlack, "rightGrandparent should be red");
        assertEquals(Integer.valueOf(5), rightGrandparent.data, "rightGrandparent should be 5");

        // Checking if leftParent is black and 4
        assertTrue(leftParent.isBlack, "leftParent should be black");
        assertEquals(Integer.valueOf(4), leftParent.data, "leftParent should be 4");

        // Checking if rightParent is black and 7
        assertTrue(rightParent.isBlack, "rightParent should be black");
        assertEquals(Integer.valueOf(7), rightParent.data, "rightParent should be 7");

        // Checking if grandchildOne is red and 3
        assertFalse(grandchildOne.isBlack, "grandchildOne should be red");
        assertEquals(Integer.valueOf(3), grandchildOne.data, "GrandchildOne should be 3");

        // Checking if granchildTwo is red and 6
        assertFalse(grandchildTwo.isBlack, "grandchildTwo should be red");
        assertEquals(Integer.valueOf(6), grandchildTwo.data, "grandchildTwo should be 6");

        // Checking if granchildThree is red and 9
        assertFalse(grandchildThree.isBlack, "grandchildThree should be red");
        assertEquals(Integer.valueOf(9), grandchildThree.data, "grandchildThree should be 9");
    }
}