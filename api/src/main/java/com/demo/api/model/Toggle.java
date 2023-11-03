package com.demo.api.model;

public class Toggle implements Element {

    private final Boolean value;

    public Toggle(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}
