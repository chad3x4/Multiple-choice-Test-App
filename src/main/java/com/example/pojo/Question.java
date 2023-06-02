package com.example.pojo;

public class Question {
    private String quesId;
    private String category;
    private String content;

    public Question() {
    }

    public Question(String quesId, String category, String content) {
        this.quesId = quesId;
        this.category = category;
        this.content = content;
    }

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
