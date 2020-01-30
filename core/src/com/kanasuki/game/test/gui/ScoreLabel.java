package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ScoreLabel extends Label {

    private static final String SCORE_TEXT = "Score: ";

    private int score;

    public ScoreLabel(Skin skin, String styleName, int score) {
        super(SCORE_TEXT + score, skin, styleName);
    }

    public void increaseScore(int increase) {
        this.score += increase;
        setText(SCORE_TEXT + score);
    }

    public int getScore() {
        return score;
    }
}
