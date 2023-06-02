package com.example.service;

import com.example.conf.CategoriesTreeList;
import com.example.conf.JdbcUtils;
import com.example.pojo.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
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



    public void addCategory() {

    }
}
