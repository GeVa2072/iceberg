package com.github.ledoyen;

import java.util.Collections;
import java.util.List;

public abstract class Node {

    private final String description;
    private final List<Node> nodes;

    protected Node(String description, List<Node> nodes) {
        this.description = description;
        this.nodes = nodes;
    }

    public String description() {
        return description;
    }

    public List<Node> nodes() {
        return nodes;
    }

    public boolean leaf() {
        return nodes().isEmpty();
    }

    public static class Branch extends Node {

        public Branch(String description, List<Node> nodes) {
            super(description, nodes);
        }
    }

    public static class Leaf extends Node {
        public Leaf(String description) {
            super(description, Collections.emptyList());
        }
    }
}
