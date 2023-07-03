package com.example.service;

import com.example.conf.CategoriesTreeList;
import com.example.conf.JdbcUtils;
import com.example.pojo.Category;
import com.example.pojo.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    //Add information of c to db
    public void addCategory(Category c) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO category(cat_id, name, parent_id, info, level, ques_quant, ques) VALUES(?, ?, ?, ?, ?, ?, ?)");
            stm.setString(1, c.getCatId());
            stm.setString(2, c.getName());
            stm.setString(3, c.getParentId());
            stm.setString(4, c.getInfo());
            stm.setInt(5, c.getLevel());
            stm.setInt(6, c.getQuesQuant());
            stm.setInt(7, c.getQues());
            stm.executeUpdate();

            conn.commit();
        }
    }

    //Get all categories in hierarchical order
    public List<Category> getCategories() throws SQLException {
        List<Category> tmp = new ArrayList<>();
        List<Category> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM category");

            while (rs.next()) {
                Category c = new Category(rs.getString("cat_id"), rs.getString("parent_id"), rs.getString("name"),
                        rs.getString("info"), rs.getInt("level"), rs.getInt("ques_quant"), rs.getInt("ques"));
                tmp.add(c);
            }
        }
        //Add categories to list in hierarchical order by recursion
        while (tmp.size()!=0) {
            results.addAll(CategoriesTreeList.appendCategory(0, "NON", tmp));
            tmp.removeAll(results);
        }
        return results;
    }

    //Get category whose cat_id is "catId"
    public Category getCategory(String catId) throws SQLException {
        Category result = new Category();
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM category WHERE cat_id = ?");
            stm.setString(1, catId);
            ResultSet rs = stm.executeQuery();
            result = new Category(rs.getString("cat_id"), rs.getString("parent_id"), rs.getString("name"),
                    rs.getString("info"), rs.getInt("level"), rs.getInt("ques_quant"), rs.getInt("ques"));
        }
        return result;
    }

    //Update when a question move from old category to new category
    public void quesChangeCategories(String catIdOld, String catIdNew) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("UPDATE category SET ques_quant = (ques_quant - 1) WHERE cat_id = ?");
            stm.setString(1, catIdOld);
            stm.executeUpdate();

            PreparedStatement stm1 = conn.prepareStatement("UPDATE category SET ques_quant = (ques_quant + 1), ques = (ques + 1) WHERE cat_id = ?");
            stm1.setString(1, catIdNew);
            stm1.executeUpdate();
            conn.commit();
        }
    }

    //Get all subcategories of category parent
    public List<Category> getSubCategories(Category parent) throws SQLException {
        List<Category> results = new ArrayList<>();
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM category WHERE parent_id = ?");
            stm.setString(1, parent.getCatId());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Category c = new Category(rs.getString("cat_id"), rs.getString("parent_id"), rs.getString("name"),
                        rs.getString("info"), rs.getInt("level"), rs.getInt("ques_quant"), rs.getInt("ques"));
                results.add(c);
            }
        }
        return results;
    }
}
