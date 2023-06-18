package com.example.pojo;

public class Category {
    private String catId;
    private String parent;
    private String name;
    private String info;
    private int level;
    private int quesQuant;

    public Category() {
    }

    public Category(String catId, String parent, String name, String info, int level, int quesQuant) {
        this.catId = catId;
        this.parent = parent;
        this.name = name;
        this.info = info;
        this.level = level;
        this.quesQuant = quesQuant;
    }

    @Override
    public String toString() {
        String prefix = "";
        for (int i = 0; i<this.level; i++) {
            prefix += "      ";
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
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
}
