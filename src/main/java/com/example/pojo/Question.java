package com.example.pojo;

public class Question {
    private String quesId;
    private String catId;
    private String quesName;
    private String quesText;
    private String imgLink;

    public Question() {
    }

    public Question(String quesId, String catId, String quesName, String quesText, String imgLink) {
        this.quesId = quesId;
        this.catId = catId;
        this.quesName = quesName;
        this.quesText = quesText;
        this.imgLink = imgLink;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getQuesName() {
        return quesName;
    }

    public void setQuesName(String quesName) {
        this.quesName = quesName;
    }

    public String getQuesText() {
        return quesText;
    }

    public void setQuesText(String quesText) {
        this.quesText = quesText;
    }
}
