package com.example.pojo;

public class Choice {
    private String choiceId;//uuid
    private String quesId;
    private String content;
    private int score;
    private String imgLink;

    public Choice() {
    }

    public Choice(String choiceId, String quesId, String content, int score, String imgLink) {
        this.choiceId = choiceId;
        this.quesId = quesId;
        this.content = content;
        this.score = score;
        this.imgLink = imgLink;
    }
    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
