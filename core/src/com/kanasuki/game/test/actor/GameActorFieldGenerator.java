package com.kanasuki.game.test.actor;

import com.badlogic.gdx.Gdx;
import com.kanasuki.game.test.level.LevelConfiguration;

public class GameActorFieldGenerator {

    private static int DEFAULT_SQUARE_SIZE = 50;

    private GameActorManager gameActorManager;

    public GameActorFieldGenerator(GameActorManager gameActorManager) {
        this.gameActorManager = gameActorManager;
    }

    public GameActorField generate(LevelConfiguration levelConfiguration) {
        int squareSize = Math.min(Math.min(Gdx.graphics.getWidth()/levelConfiguration.getWidth(),
                Gdx.graphics.getHeight()/levelConfiguration.getHeight()), DEFAULT_SQUARE_SIZE);

        Environment environment = new Environment(levelConfiguration.getWidth(), levelConfiguration.getHeight(), squareSize);
        environment.setZIndex(0);

        GameActorField gameActorField = new GameActorField(gameActorManager, environment);

        return gameActorField;
    }
}
