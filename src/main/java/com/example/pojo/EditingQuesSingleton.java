package com.example.pojo;

public class EditingQuesSingleton {

    private static final EditingQuesSingleton INSTANCE = new EditingQuesSingleton();
    private static Question editingQues;

    // Private constructor to avoid client applications to use constructor

    private EditingQuesSingleton() {

    }

    public static EditingQuesSingleton getInstance() {
        return INSTANCE;
    }

    public Question getEditingQues() {
        return editingQues;
    }

    public void setEditingQues(Question editingQues) {
        EditingQuesSingleton.editingQues = editingQues;
    }
}
