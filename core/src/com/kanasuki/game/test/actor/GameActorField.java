package com.kanasuki.game.test.actor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameActorField {

    private final GameActorManager gameActorManager;
    private final Environment environment;

    @Inject
    public GameActorField(GameActorManager gameActorManager, Environment environment) {
        this.gameActorManager = gameActorManager;
        this.environment = environment;
    }

    public boolean isFreeToBuild(int x, int y) {
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor == NullActor.instance;
    }

    public boolean isFreeToMove(int x, int y, ActorType movingActorType) {
        if (!isInside(x, y)) {
            return false;
        }

        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor == NullActor.instance ||
              (gameActor.getType() == ActorType.ENEMY && movingActorType == ActorType.HERO) ||
              (gameActor.getType() == ActorType.HERO && movingActorType == ActorType.ENEMY);
    }

    public boolean isObstruction(int x, int y) {
        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor.getType() == ActorType.OBSTRUCTION_NON_REMOVABLE || gameActor.getType() == ActorType.OBSTRUCTION_REMOVABLE;
    }

    public int getSquareSize() {
        return environment.getSquareSize();
    }

    public boolean isInside(int x, int y) {
        return environment.isInEnvironment(x, y);
    }
}
