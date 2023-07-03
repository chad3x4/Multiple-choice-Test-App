package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Category;
import com.example.pojo.Choice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChoiceService {
    public List<Choice> getChoices(String quesId) throws SQLException {
        List<Choice> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM choice WHERE ques_id = ?");
            stm.setString(1, quesId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Choice ch = new Choice(rs.getString("choice_id"), rs.getString("ques_id"),
                        rs.getString("content"), rs.getInt("score"), rs.getString("img_link"));
                results.add(ch);
            }
        }

        return results;
    }
}
