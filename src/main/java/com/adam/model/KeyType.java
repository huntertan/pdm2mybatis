package com.adam.model;

public class KeyType {
    int value = 0;

    public KeyType(int value) {
        this.value = value;
    }

    public boolean isAk() {
        return this.value == 1;
    }

    public boolean isPk() {
        return this.value == 0;
    }
}