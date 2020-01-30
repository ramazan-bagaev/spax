package com.kanasuki.game.test;

import java.util.HashMap;
import java.util.Map;

public class GameProgress {

    private Map<Integer, Integer> scoreMap;

    public GameProgress() {
        this.scoreMap = new HashMap<>();
    }

    public void recordScore(int level, int score) {
        Integer lastScore = scoreMap.get(level);

        if (lastScore == null) {
            scoreMap.put(level, score);
        } else {
            if (lastScore < score) {
                scoreMap.put(level, score);
            }
        }
    }

    public int getScore(int level) {
        Integer score = scoreMap.get(level);

        if (score == null) {
            return 0;
        } else {
            return score;
        }
    }
}
