package org.client.gui.models;

import java.util.Stack;

public class SizedStack<E> extends Stack<E> {
    private final int maxSize;

    public SizedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public E push(E element) {
        if (size() >= maxSize) {
            remove(0);
        }
        return super.push(element);
    }
}
