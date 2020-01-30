package com.kanasuki.game.test.level;

import java.util.HashMap;
import java.util.Map;

public class LevelMap {

    private final Map<Integer, LevelConfiguration> levelMap;

    public LevelMap() {
        this.levelMap = new HashMap<>();
        init();
    }

    private void init() {
        levelMap.put(1, new LevelConfiguration.LevelConfigurationBuilder().
                level(1).
                enemyNumber(2).
                height(10).
                width(20).
                deadEnemySquare(12).
                build());

        levelMap.put(2, new LevelConfiguration.LevelConfigurationBuilder().
                level(2).
                enemyNumber(3).
                height(11).
                width(21).
                deadEnemySquare(12).
                build());

        levelMap.put(3, new LevelConfiguration.LevelConfigurationBuilder().
                level(3).
                enemyNumber(4).
                height(12).
                width(22).
                deadEnemySquare(12).
                build());

        levelMap.put(4, new LevelConfiguration.LevelConfigurationBuilder().
                level(4).
                enemyNumber(5).
                height(13).
                width(23).
                deadEnemySquare(12).
                build());

        levelMap.put(5, new LevelConfiguration.LevelConfigurationBuilder().
                level(5).
                enemyNumber(6).
                height(14).
                width(24).
                deadEnemySquare(9).
                build());

        levelMap.put(6, new LevelConfiguration.LevelConfigurationBuilder().
                level(6).
                enemyNumber(7).
                height(15).
                width(25).
                deadEnemySquare(9).
                build());

        levelMap.put(7, new LevelConfiguration.LevelConfigurationBuilder().
                level(7).
                enemyNumber(8).
                height(16).
                width(26).
                deadEnemySquare(9).
                build());

        levelMap.put(8, new LevelConfiguration.LevelConfigurationBuilder().
                level(8).
                enemyNumber(9).
                height(17).
                width(27).
                deadEnemySquare(9).
                build());

        levelMap.put(9, new LevelConfiguration.LevelConfigurationBuilder().
                level(9).
                enemyNumber(10).
                height(18).
                width(19).
                deadEnemySquare(9).
                build());
    }

    public LevelConfiguration getLevel(int level) {
        return levelMap.get(level);
    }

    public int getLevelNumbers() {
        return levelMap.size();
    }
}
