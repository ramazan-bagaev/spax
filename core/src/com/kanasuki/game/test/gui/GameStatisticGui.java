package com.kanasuki.game.test.gui;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameStatisticGui {

    private CountLabel deadEnemyCountLabel;
    private ScoreLabel scoreLabel;

    @Inject
    public GameStatisticGui() {
    }

    public void setEnemyCountLabel(CountLabel deadEnemyCountLabel) {
        this.deadEnemyCountLabel = deadEnemyCountLabel;
    }

    public void setScoreLabel(ScoreLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void increaseDeadEnemyCount() {
        deadEnemyCountLabel.increaseCount();
    }

    public void increaseScore(int increase) {
        scoreLabel.increaseScore(increase);
    }
}
