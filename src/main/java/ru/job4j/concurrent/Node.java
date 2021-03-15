package ru.job4j.concurrent;

public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(T t, Node<T> nextNode) {
        next = nextNode;
        value = t;
    }

    public Node<T> getNext() {
        return next;
    }


    public T getValue() {
        return value;
    }

}
