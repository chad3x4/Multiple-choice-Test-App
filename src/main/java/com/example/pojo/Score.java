package com.example.pojo;

public class Score {
    private String value;
    public Score() {
    }
    public Score(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String score) {
        this.value = value;
    }
}
