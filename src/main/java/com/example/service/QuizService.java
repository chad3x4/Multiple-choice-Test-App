package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Question;
import com.example.pojo.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuizService {

    public void addQuesToQuiz(List<Question> questions, String quizId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO quiz_ques(qq_id, quiz_id, ques_id) VALUES(?, ?, ?)");
            for (Question question : questions) {
                stm.setString(1, UUID.randomUUID().toString());
                stm.setString(2, quizId);
                stm.setString(3, question.getQuesId());
                stm.executeUpdate();
            }
            conn.commit();
        }
    }

    public void removeQuesFromQuiz(String quesId, String quizId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM quiz_ques WHERE ques_id = ? AND quiz_id = ?");
            stm.setString(1, quesId);
            stm.setString(2, quizId);
            stm.executeUpdate();
            conn.commit();
        }
    }

    public void addQuiz(Quiz q) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO quiz(quiz_id, quiz_name, quiz_des, time_limit, show_des) VALUES(?, ?, ?, ?, ?)");
            stm.setString(1, q.getQuizId());
            stm.setString(2, q.getQuizName());
            stm.setString(3, q.getQuizDes());
            stm.setInt(4, q.getTimeLimit());
            stm.setBoolean(5, q.getShowDes());
            stm.executeUpdate();

            conn.commit();
        }
    }

    //Get quiz whose id = quizId
    public Quiz getQuiz(String quizId) throws SQLException {
        Quiz result;
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM quiz WHERE quiz_id = ?");
            stm.setString(1, quizId);
            ResultSet rs = stm.executeQuery();

            result = new Quiz(rs.getString("quiz_id"), rs.getString("quiz_name"), rs.getString("quiz_des"),
                    rs.getInt("time_limit"), rs.getBoolean("show_des"));
        }
        return result;
    }

    //Get all quizzes from db
    public List<Quiz> getQuizzes() throws SQLException {
        List<Quiz> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM quiz");

            while (rs.next()) {
                Quiz q = new Quiz(rs.getString("quiz_id"), rs.getString("quiz_name"), rs.getString("quiz_des"),
                        rs.getInt("time_limit"), rs.getBoolean("show_des"));
                results.add(q);
            }
        }
        return results;
    }
}
