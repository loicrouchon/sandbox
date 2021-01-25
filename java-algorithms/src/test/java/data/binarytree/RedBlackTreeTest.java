package data.binarytree;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RedBlackTreeTest {

    @Test
    public void testEmptyTree() {
        RedBlackTree tree = new RedBlackTree();
        assertEquals("{NIL}", tree.toString());
        assertEquals(0, tree.getRotationCounter());
    }

    @Test
    public void testInsert1() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(1);
        assertEquals("{key=1,color=BLACK,left={NIL},right={NIL}}", tree.toString());
        assertEquals(0, tree.getRotationCounter());
    }

    @Test
    public void testInsert2() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(1);
        tree.insert(2);
        assertEquals("{key=1,color=BLACK,left={NIL},right={key=2,color=RED,left={NIL},right={NIL}}}", tree.toString());
        assertEquals(0, tree.getRotationCounter());
    }

    @Test
    public void testInsert3() {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        assertEquals(
                "{key=2,color=BLACK,left={key=1,color=RED,left={NIL},right={NIL}},right={key=3,color=RED,left={NIL},right={NIL}}}",
                tree.toString());
        assertEquals(1, tree.getRotationCounter());
    }

    @Test
    public void testInsert4() {
        RedBlackTree tree = new RedBlackTree();
        for (int i = 1; i < 8; i++) {
            tree.insert(i);
        }
        assertEquals(
                "{key=4,color=BLACK,left="
                        + "{key=2,color=BLACK,left={key=1,color=RED,left={NIL},right={NIL}},right={key=3,color=RED,left={NIL},right={NIL}}}"
                        + "right="
                        + "{key=6,color=BLACK,left={key=5,color=RED,left={NIL},right={NIL}},right={key=7,color=RED,left={NIL},right={NIL}}}"
                        + "}", tree.toString());
        assertEquals(4, tree.getRotationCounter());
    }
}
