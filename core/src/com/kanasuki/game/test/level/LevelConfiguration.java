package com.kanasuki.game.test.level;

public class LevelConfiguration {

    private final int level;
    private final int enemyNumber;
    private final int width;
    private final int height;
    private final int maxEnemySquare;

    private final int oneStarScore;
    private final int twoStarScore;
    private final int threeStarScore;

    public LevelConfiguration(int level, int enemyNumber, int width, int height, int maxEnemySquare) {
        this.level = level;
        this.enemyNumber = enemyNumber;
        this.width = width;
        this.height = height;
        this.maxEnemySquare = maxEnemySquare;
        this.oneStarScore = enemyNumber;
        this.twoStarScore = enemyNumber * (maxEnemySquare / 2);
        this.threeStarScore = enemyNumber * maxEnemySquare;
    }

    public int getLevel() {
        return level;
    }

    public int getEnemyNumber() {
        return enemyNumber;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxEnemySquare() {
        return maxEnemySquare;
    }

    public int getStarAmount(int score) {
        if (score >= threeStarScore) {
            return 3;
        }

        if (score >= twoStarScore) {
            return 2;
        }

        if (score >= oneStarScore) {
            return 1;
        }

        return 0;
    }

    public static class LevelConfigurationBuilder {

        private int level;
        private int enemyNumber;
        private int width;
        private int height;
        private int maxEnemySquare;

        public LevelConfigurationBuilder() {

        }

        public LevelConfigurationBuilder level(int level) {
            LevelConfigurationBuilder.this.level = level;
            return this;
        }

        public LevelConfigurationBuilder enemyNumber(int enemyNumber) {
            LevelConfigurationBuilder.this.enemyNumber = enemyNumber;
            return this;
        }

        public LevelConfigurationBuilder width(int width) {
            LevelConfigurationBuilder.this.width = width;
            return this;
        }

        public LevelConfigurationBuilder height(int height) {
            LevelConfigurationBuilder.this.height = height;
            return this;
        }

        public LevelConfigurationBuilder deadEnemySquare(int deadEnemySquare) {
            LevelConfigurationBuilder.this.maxEnemySquare = deadEnemySquare;
            return this;
        }

        public LevelConfiguration build() {
            return new LevelConfiguration(level, enemyNumber, width, height, maxEnemySquare);
        }
    }
}
