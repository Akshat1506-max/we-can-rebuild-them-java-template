package org.example;

public class App {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insert values into AVL tree
        tree.insert(3);
        tree.insert(4);
        tree.insert(5);
        tree.insert(6);

        // Serialize and print the tree
        System.out.println("Serialized AVL Tree: " + tree.serialize());

        // Delete a node
        tree.delete(6);
        System.out.println("After deletion: " + tree.serialize());

        // Serialize and deserialize
        String serialized = tree.serialize();
        AVLTree newTree = new AVLTree();
        newTree.deserialize(serialized);
        System.out.println("Deserialized Tree: " + newTree.serialize());
    }
}