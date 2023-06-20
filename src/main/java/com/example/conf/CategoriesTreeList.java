package com.example.conf;

import com.example.pojo.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesTreeList {
    //Recursion function to add Category to result in hierarchical order
    public static List<Category> appendCategory(int level, String parentId, List<Category> categories) {
        List<Category> result = new ArrayList<>();
        Category selectedCategory = null;

        for (int i=0; i<categories.size(); i++) {
            if (categories.get(i).getLevel() == level && categories.get(i).getParentId().equals(parentId)) {
                selectedCategory = categories.get(i);
                result.add(selectedCategory);
                categories.remove(i);
                break;
            }
        }
        if(selectedCategory == null) {
            return result;
        }
        while(isAnythingLeft(selectedCategory.getCatId(), categories)) {
            result.addAll(appendCategory(level + 1, selectedCategory.getCatId(), categories));
        }
        return result;
    }
    //Check in "categories" if there is any subcategories left where their parent's id is "parentId"
    public static boolean isAnythingLeft(String parentId, List<Category> categories) {
        for(int i=0; i<categories.size(); i++) {
            if(categories.get(i).getParentId().equals(parentId)) {
                return true;
            }
        }
        return false;
    }
}
