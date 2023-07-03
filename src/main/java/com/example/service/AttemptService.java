package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Attempt;
import com.example.pojo.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttemptService {
    public void addAttempt(Attempt a) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO attempt(att_id, attempt, state, score, quiz_id) VALUES (?, ?, ?, ?, ?)");
            stm1.setString(1, a.getAttId());
            stm1.setString(2, a.getAttempt());
            stm1.setString(3, a.getState());
            stm1.setFloat(4, (float)a.getScore()/100);
            stm1.setString(5, a.getQuizId());
            stm1.executeUpdate();
        }
    }
    public List<Attempt> getAttempts(String quiz_id) throws SQLException {
        List<Attempt> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM attempt WHERE quiz_id = ?");
            stm.setString(1, quiz_id);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attempt a = new Attempt(rs.getString("att_id"), rs.getString("attempt"),
                        rs.getString("state"), rs.getInt("score"), rs.getString("quiz_id"));
                results.add(a);
            }
        }
        return results;
    }
}
