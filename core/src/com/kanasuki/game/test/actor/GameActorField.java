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

    public boolean isFreeToMove(int x, int y) {
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor == NullActor.instance || gameActor.getType() == ActorType.SOFT;
    }

    public boolean isHard(int x, int y) {
        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor != NullActor.instance && gameActor.getType() == ActorType.HARD;
    }

    public int getSquareSize() {
        return environment.getSquareSize();
    }

    public boolean isInside(int x, int y) {
        return environment.isInEnvironment(x, y);
    }
}
