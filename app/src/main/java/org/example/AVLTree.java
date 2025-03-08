package org.example;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class AVLTree {
    private class Node {
        int value, height;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    // Get height of a node
    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    // Get balance factor of a node
    private int getBalance(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Right rotation
    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotation
    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a value into the AVL Tree
    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.value) node.left = insert(node.left, value);
        else if (value > node.value) node.right = insert(node.right, value);
        else return node; // No duplicates allowed

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Balancing the tree
        if (balance > 1 && value < node.left.value) return rotateRight(node);
        if (balance < -1 && value > node.right.value) return rotateLeft(node);
        if (balance > 1 && value > node.left.value) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && value < node.right.value) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Delete a value from the AVL Tree
    public void delete(int value) {
        root = delete(root, value);
    }

    private Node delete(Node node, int value) {
        if (node == null) return null;

        if (value < node.value) node.left = delete(node.left, value);
        else if (value > node.value) node.right = delete(node.right, value);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node temp = getMinValueNode(node.right);
                node.value = temp.value;
                node.right = delete(node.right, temp.value);
            }
        }

        if (node == null) return null;

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Perform necessary rotations
        if (balance > 1 && getBalance(node.left) >= 0) return rotateRight(node);
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private Node getMinValueNode(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Serialize the AVL Tree using BFS
    public String serialize() {
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node == null) {
                sb.append("nil,");
            } else {
                sb.append(node.value).append(",");
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return sb.toString().replaceAll(",+$", "");  // Remove trailing commas
    }

    // Deserialize into an AVL Tree using BFS
    public void deserialize(String data) {
        if (data.isEmpty()) {
            root = null;
            return;
        }

        Queue<String> nodes = new LinkedList<>(Arrays.asList(data.split(",")));
        root = new Node(Integer.parseInt(nodes.poll()));
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!nodes.isEmpty()) {
            Node parent = queue.poll();
            String leftVal = nodes.poll();
            if (!leftVal.equals("nil")) {
                parent.left = new Node(Integer.parseInt(leftVal));
                queue.add(parent.left);
            }

            if (!nodes.isEmpty()) {
                String rightVal = nodes.poll();
                if (!rightVal.equals("nil")) {
                    parent.right = new Node(Integer.parseInt(rightVal));
                    queue.add(parent.right);
                }
            }
        }
    }
}