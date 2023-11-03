package com.demo.api.model;

public class Input implements Element {

    private final String value;

    public Input(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
