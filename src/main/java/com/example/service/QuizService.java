package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Question;
import com.example.pojo.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizService {
    public void addQuiz(Quiz q) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO quiz(quiz_id, quiz_name, quiz_des, open_time, close_time, time_limit, show_des) VALUES(?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, q.getQuizId());
            stm.setString(2, q.getQuizName());
            stm.setString(3, q.getQuizDes());
//            stm.setDate(4, q.getOpenTime());
//            stm.setDate(5, q.getCloseTime());
            stm.setInt(6, q.getTimeLimit());
            stm.setInt(7, q.getShowDes());
            stm.executeUpdate();

            conn.commit();
        }
    }

    public List<Quiz> getQuizzes() {
        List<Quiz> results = new ArrayList<>();
        return results;
    }
}
