package com.example.pojo;

import java.time.LocalDateTime;

public class AttemptingQuizSingleton {
    private static final AttemptingQuizSingleton INSTANCE = new AttemptingQuizSingleton();
    private static Quiz attemptingQuiz;
    private static LocalDateTime startDate;
    private static LocalDateTime completeDate;
    private static boolean isShuffle;

    // Private constructor to avoid client applications to use constructor

    private AttemptingQuizSingleton() {

    }

    public static AttemptingQuizSingleton getInstance() {
        return INSTANCE;
    }

    public static LocalDateTime getStartDate() {
        return startDate;
    }

    public static void setStartDate(LocalDateTime startDate) {
        AttemptingQuizSingleton.startDate = startDate;
    }

    public static LocalDateTime getCompleteDate() {
        return completeDate;
    }

    public static void setCompleteDate(LocalDateTime completeDate) {
        AttemptingQuizSingleton.completeDate = completeDate;
    }

    public Quiz getAttemptingQuiz() {
        return attemptingQuiz;
    }

    public void setAttemptingQuiz(Quiz attemptingQuiz) {
        AttemptingQuizSingleton.attemptingQuiz = attemptingQuiz;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public void setShuffle(boolean shuffle) {
        isShuffle = shuffle;
    }
}
