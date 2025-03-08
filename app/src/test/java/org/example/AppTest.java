package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void testAVLInsertion() {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        assertEquals("4,3,5,nil,nil,nil,nil", tree.serialize());
    }

    @Test
    void testAVLDeletion() {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.delete(4);
        assertEquals("5,3,nil,nil,nil", tree.serialize());
    }

    @Test
    void testAVLSerializationAndDeserialization() {
        AVLTree tree = new AVLTree();
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        String serialized = tree.serialize();

        AVLTree newTree = new AVLTree();
        newTree.deserialize(serialized);
        assertEquals(serialized, newTree.serialize());
    }
}