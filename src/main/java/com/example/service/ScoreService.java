package com.example.service;

import com.example.pojo.Score;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    public List<Score> getScores() {
        List<Score> results = new ArrayList<>();
        for (int i=100; i>=0; i-=10) {
            Score s = new Score(i+"%");
            results.add(s);
        }
        return results;
    }
}
