package com.example.pojo;

public class Choice {
    private String choiceId;//uuid
    private String content;
    private boolean isCorrect;

    public Choice() {
    }

    public Choice(String choiceId, String content, boolean isCorrect) {
        this.choiceId = choiceId;
        this.content = content;
        this.isCorrect = isCorrect;
    }
    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
