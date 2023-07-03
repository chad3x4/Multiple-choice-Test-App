package com.example.pojo;

public class Quiz {
    private String quizId;
    private String quizName;
    private String quizDes;
    private int timeLimit;
    private boolean showDes;
    public Quiz() {
    }
    public Quiz(String quizId, String quizName, String quizDes, int timeLimit, boolean showDes) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.quizDes = quizDes;
        this.timeLimit = timeLimit;
        this.showDes = showDes;
    }
    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizDes() {
        return quizDes;
    }

    public void setQuizDes(String quizDes) {
        this.quizDes = quizDes;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public boolean getShowDes() {
        return showDes;
    }

    public void setShowDes(boolean showDes) {
        this.showDes = showDes;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
