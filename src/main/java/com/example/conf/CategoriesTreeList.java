package com.example.conf;

import com.example.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesTreeList {
    //Recursion function to add Category to result in hierarchical order
    public static List<Category> appendCategory(int level, String parent, List<Category> categories) {
        List<Category> result = new ArrayList<>();
        Category selectedCategory = null;

        for (int i=0; i<categories.size(); i++) {
            if (categories.get(i).getLevel() == level && categories.get(i).getParent().equals(parent)) {
                selectedCategory = categories.get(i);
                result.add(selectedCategory);
                categories.remove(i);
                break;
            }
        }
        if(selectedCategory == null) {
            return result;
        }
        while(isAnythingLeft(selectedCategory.getName(), categories)) {
            result.addAll(appendCategory(level + 1, selectedCategory.getName(), categories));
        }
        return result;
    }
    //Check in "categories" if there is any subcategory left whose parent is "parent"
    public static boolean isAnythingLeft(String parent, List<Category> categories) {
        for(int i=0; i<categories.size(); i++) {
            if(categories.get(i).getParent().equals(parent)) {
                return true;
            }
        }
        return false;
    }
}
