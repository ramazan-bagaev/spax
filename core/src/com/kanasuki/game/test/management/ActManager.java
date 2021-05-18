package com.kanasuki.game.test.management;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.kanasuki.game.test.actor.*;

import javax.inject.Singleton;

@Singleton
public class ActManager {

    private final GameActorField gameActorField;
    private final GameActorManager gameActorManager;
    private final Environment environment;

    public ActManager(GameActorField gameActorField, GameActorManager gameActorManager, Environment environment) {
        this.gameActorField = gameActorField;
        this.gameActorManager = gameActorManager;
        this.environment = environment;
    }

    public void actEnemies() {
        for (GameActor gameActor: gameActorManager.getGameActors()) {
            if (gameActor.getType() == ActorType.ENEMY) {
                actEnemy(gameActor);
            }
        }
    }

    private void actEnemy(GameActor actor) {
        int squareSize = environment.getSquareSize();
        float x = actor.getX();
        float y = actor.getY();

        int fieldX = (int) (x / squareSize);
        int fieldY = (int) (y / squareSize);

        switch (MathUtils.random(4)) {
            case 0:
                if (gameActorField.isFreeToMove(fieldX, fieldY + 1, ActorType.ENEMY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 1:
                if (gameActorField.isFreeToMove(fieldX, fieldY - 1, ActorType.ENEMY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(0, -squareSize);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 2:
                if (gameActorField.isFreeToMove(fieldX + 1, fieldY, ActorType.ENEMY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
            case 3:
                if (gameActorField.isFreeToMove(fieldX - 1, fieldY, ActorType.ENEMY)) {
                    MoveByAction moveByAction = new MoveByAction();
                    moveByAction.setAmount(-squareSize, 0);
                    //moveByAction.setDuration(0.2f);
                    actor.addAction(moveByAction);
                }
                break;
        }
    }
}
