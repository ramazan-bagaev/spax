package com.kanasuki.game.test.management.action;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.kanasuki.game.test.actor.ActorType;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.GameActor;
import com.kanasuki.game.test.actor.GameActorField;

public class EnemyActionManager implements GameActorActionManager {

    private final Environment environment;
    private final GameActorField gameActorField;

    public EnemyActionManager(Environment environment, GameActorField gameActorField) {
        this.environment = environment;
        this.gameActorField = gameActorField;
    }

    @Override
    public void act(GameActor actor) {
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

    @Override
    public boolean isApplicable(GameActor gameActor) {
        return gameActor.getType() == ActorType.ENEMY;
    }

    @Override
    public float getActingPeriod() {
        return 0.4f;
    }
}
