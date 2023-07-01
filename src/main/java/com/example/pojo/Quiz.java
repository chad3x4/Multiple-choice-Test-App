package com.example.pojo;

import java.util.Date;

public class Quiz {
    private String quizId;
    private String quizName;
    private String quizDes;
    private Date openTime;
    private Date closeTime;
    private int timeLimit;
    private int showDes;
    public Quiz() {
    }
    public Quiz(String quizId, String quizName, String quizDes, Date openTime, Date closeTime, int timeLimit, int showDes) {
        this.quizId = quizId;
        this.quizName = quizName;
        this.quizDes = quizDes;
        this.openTime = openTime;
        this.closeTime = closeTime;
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

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getShowDes() {
        return showDes;
    }

    public void setShowDes(int showDes) {
        this.showDes = showDes;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
}
