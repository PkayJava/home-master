package com.angkorteam.home.query;

public class Entry<T> implements java.util.Map.Entry<String, T> {

    private final String key;

    private T value;

    public Entry(String key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public T setValue(T value) {
        return this.value = value;
    }

}