package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Category;
import com.example.pojo.Choice;
import com.example.pojo.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionService {
    //Add information of q and choices to db
    public void addQuestion(Question q, List<Choice> choices) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm1 = conn.prepareStatement("INSERT INTO question(ques_id, cat_id, ques_name, ques_text, img_link) VALUES(?, ?, ?, ?, ?)");
            stm1.setString(1, q.getQuesId());
            stm1.setString(2, q.getCatId());
            stm1.setString(3, q.getQuesName());
            stm1.setString(4, q.getQuesText());
            stm1.setString(5, q.getImgLink());
            stm1.executeUpdate();

            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO choice(choice_id, ques_id, content, score, img_link) VALUES(?, ?, ?, ?, ?)");
            for (Choice c:choices) {
                stm2.setString(1, c.getChoiceId());
                stm2.setString(2, c.getQuesId());
                stm2.setString(3, c.getContent());
                stm2.setInt(4, c.getScore());
                stm2.setString(5, c.getImgLink());

                stm2.executeUpdate();
            }
            PreparedStatement stm3 = conn.prepareStatement("UPDATE category SET ques_quant = ques_quant+1, ques = ques+1 WHERE cat_id = ?");
            stm3.setString(1, q.getCatId());
            stm3.executeUpdate();
            //Avoid commit data to db if any statements fail
            conn.commit();
        }
    }

    //*Get all questions have category c
    public List<Question> getQuestions(Category c) {
        List<Question> results = new ArrayList<>();
        return results;
    }
}
