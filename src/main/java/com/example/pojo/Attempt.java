package com.example.pojo;

public class Attempt {
    private String attId;
    private String attempt;
    private String state;
    private int score;
    private String quizId;
    public Attempt() {
    }
    public Attempt(String attId, String attempt, String state, int score, String quizId) {
        this.attId = attId;
        this.attempt = attempt;
        this.state = state;
        this.score = score;
        this.quizId = quizId;
    }
    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
