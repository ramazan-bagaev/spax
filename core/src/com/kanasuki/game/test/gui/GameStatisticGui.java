package com.kanasuki.game.test.gui;

public class GameStatisticGui {

    private CountLabel deadEnemyCountLabel;
    private ScoreLabel scoreLabel;

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
