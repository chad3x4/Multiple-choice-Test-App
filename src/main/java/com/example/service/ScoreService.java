package com.example.service;

import com.example.pojo.Choice;
import com.example.pojo.Score;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {

    public int markScore(List<String> choiceIds) throws SQLException {
        ChoiceService cs = new ChoiceService();
        int result = 0;
        for (String id : choiceIds) {
            Choice c = cs.getChoice(id);
            result += c.getScore();
        }
        return result;
    }
    public Score getScore(int value) {
        Score s = new Score(value+"%");
        return s;
    }
    public List<Score> getScores() {
        List<Score> results = new ArrayList<>();
        for (int i=100; i>=0; i-=10) {
            Score s = new Score(i+"%");
            results.add(s);
        }
        return results;
    }
}
