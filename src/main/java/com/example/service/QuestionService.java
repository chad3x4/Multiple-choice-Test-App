package com.example.service;

import com.example.conf.JdbcUtils;
import com.example.pojo.Category;
import com.example.pojo.Choice;
import com.example.pojo.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public void updateQuestion(Question q, List<Choice> choices) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("DELETE FROM question WHERE ques_id = ?;");
            System.out.println("Editing in question "+q.getQuesId());
            stm.setString(1, q.getQuesId());
            stm.executeUpdate();

            PreparedStatement stm_ = conn.prepareStatement("DELETE FROM choice WHERE ques_id = ?;");
            stm_.setString(1, q.getQuesId());
            stm_.executeUpdate();

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
            //Avoid commit data to db if any statements fail
            conn.commit();
        }
    }

    //Get question has quesId
    public Question getQuestion(String quesId) throws SQLException {
        Question result;
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE ques_id = ?");
            stm.setString(1, quesId);

            ResultSet rs = stm.executeQuery();
            result = new Question(rs.getString("ques_id"), rs.getString("cat_id"),
                    rs.getString("ques_name"), rs.getString("ques_text"), rs.getString("img_link"));
        }
        return result;
    }

    //*Get all questions have category c
    public List<Question> getQuestions(Category c) throws SQLException{
        List<Question> results = new ArrayList<>();

        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM question WHERE cat_id = ?");
            stm.setString(1, c.getCatId());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question(rs.getString("ques_id"), rs.getString("cat_id"),
                        rs.getString("ques_name"), rs.getString("ques_text"), rs.getString("img_link"));
                results.add(q);
            }
        }
        return results;
    }

    public List<Question> getQuestions(String quizId) throws SQLException {
        List<Question> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT qq.qq_id, qq.quiz_id, ques.ques_id, ques.cat_id, ques.ques_name, ques.ques_text, ques.img_link" +
                    " FROM quiz_ques AS qq INNER JOIN question AS ques USING (ques_id) WHERE qq.quiz_id = ?;");
            stm.setString(1, quizId);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Question q = new Question(rs.getString("ques_id"), rs.getString("cat_id"),
                        rs.getString("ques_name"), rs.getString("ques_text"), rs.getString("img_link"));
                results.add(q);
            }
        }
        return results;
    }

    public boolean isMultipleAnswers(String quesId) throws SQLException {
        ChoiceService cs = new ChoiceService();
        List<Choice> choices = cs.getChoices(quesId);
        for (Choice c: choices) {
            if(c.getScore() == 100) return false;
            if(c.getScore()>0) return true;
        }
        return false;
    }
}
