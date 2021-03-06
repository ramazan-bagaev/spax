package com.kanasuki.game.test.management;

import com.badlogic.gdx.Gdx;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.level.LevelMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LevelManager {

    private int currentMaxLevel = 1;
    private int currentLevel = 1;

    private final LevelMap levelMap;

    private LevelConfiguration customizableLevelConfiguration;

    @Inject
    public LevelManager(LevelMap levelMap) {
        Gdx.app.log("debug", "created level manager");
        this.levelMap = levelMap;
    }

    public LevelConfiguration getCurrentLevelConfiguration() {
        return levelMap.getLevel(currentLevel);
    }

    public int getCurrentMaxLevel() {
        return currentMaxLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void nextMaxLevel() {
        currentMaxLevel++;
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }

    public void nextLevel() {
        this.currentLevel++;
    }

    public void setCustomizableLevel(LevelConfiguration levelConfiguration) {
        this.customizableLevelConfiguration = levelConfiguration;
    }

    public LevelConfiguration getCustomizableLevelConfiguration() {
        return customizableLevelConfiguration;
    }
}
