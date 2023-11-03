package com.demo.api.model;

public class Result<T extends Element> {
    private final String action;
    private final ElementType type;
    private final T data;


    public Result(String action, ElementType type, T data) {
        this.action = action;
        this.type = type;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public ElementType getType() {
        return type;
    }

    public T getData() {
        return data;
    }

}
