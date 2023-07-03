package com.example.pojo;

public class AttemptingQuizSingleton {
    private static final AttemptingQuizSingleton INSTANCE = new AttemptingQuizSingleton();
    private static Quiz attemptingQuiz;

    // Private constructor to avoid client applications to use constructor

    private AttemptingQuizSingleton() {

    }

    public static AttemptingQuizSingleton getInstance() {
        return INSTANCE;
    }

    public Quiz getAttemptingQuiz() {
        return attemptingQuiz;
    }

    public void setAttemptingQuiz(Quiz attemptingQuiz) {
        AttemptingQuizSingleton.attemptingQuiz = attemptingQuiz;
    }
}
