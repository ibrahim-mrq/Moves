package com.moves.Model.Filter;

public abstract class Filter<T> {
    T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
