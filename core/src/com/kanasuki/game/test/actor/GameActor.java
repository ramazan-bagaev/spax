package com.kanasuki.game.test.actor;

public interface GameActor {

    ActorType getType();

    boolean isInField(int x, int y);

    void act(GameActorField gameActorField);
}
