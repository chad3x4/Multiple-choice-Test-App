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

    public int getValue() {
        String r = value.substring(0, value.length()-1);
        return Integer.parseInt(r);
    }

    public void setValue(String score) {
        this.value = value;
    }
}
