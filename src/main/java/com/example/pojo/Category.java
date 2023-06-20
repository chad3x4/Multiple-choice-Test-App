package com.example.pojo;

public class Category {
    private String catId;
    private String parentId;
    private String name;
    private String info;
    private int level;
    private int quesQuant;
    private int ques;

    public Category() {
    }

    public Category(String catId, String parentId, String name, String info, int level, int quesQuant, int ques) {
        this.catId = catId;
        this.parentId = parentId;
        this.name = name;
        this.info = info;
        this.level = level;
        this.quesQuant = quesQuant;
        this.ques = ques;
    }

    @Override
    public String toString() {
        String prefix = "";
        for (int i = 0; i<this.level; i++) {
            prefix += "    ";
        }
        if (this.quesQuant == 0) return prefix + this.name;
        else return prefix + this.name + " (" + this.quesQuant + ")";
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuesQuant() {
        return quesQuant;
    }

    public void setQuesQuant(int quesQuant) {
        this.quesQuant = quesQuant;
    }

    public int getQues() {
        return ques;
    }

    public void setQues(int ques) {
        this.ques = ques;
    }
}
