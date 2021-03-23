package com.kanasuki.game.test.actor;

public class GameActorField {

    private final GameActorManager gameActorManager;
    private final Environment environment;

    public GameActorField(GameActorManager gameActorManager, Environment environment) {
        this.gameActorManager = gameActorManager;
        this.environment = environment;
    }

    public boolean isFreeToBuild(int x, int y) {
        GameActor gameActor = gameActorManager.getGameActor(x, y);

        return gameActor == NullActor.instance;
    }

    public boolean isFreeToMove(int x, int y) {
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
}
