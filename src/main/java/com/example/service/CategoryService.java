package com.example.service;

import com.example.conf.CategoriesTreeList;
import com.example.conf.JdbcUtils;
import com.example.pojo.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    //Add information of c to db
    public void addCategory(Category c) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement("INSERT INTO category(cat_id, name, parent, info, level, ques_quant) VALUES(?, ?, ?, ?, ?, ?)");
            stm.setString(1, c.getCatId());
            stm.setString(2, c.getName());
            stm.setString(3, c.getParent());
            stm.setString(4, c.getInfo());
            stm.setInt(5, c.getLevel());
            stm.setInt(6, c.getQuesQuant());
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
                Category c = new Category(rs.getString("cat_id"), rs.getString("parent"), rs.getString("name"),
                        rs.getString("info"), rs.getInt("level"), rs.getInt("ques_quant"));
                tmp.add(c);
            }
        }
        //Add categories to list in hierarchical order by recursion
        while (tmp.size()!=0) {
            results.addAll(CategoriesTreeList.appendCategory(0, "none", tmp));
            tmp.removeAll(results);
        }
        return results;
    }
}
