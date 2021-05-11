package com.kanasuki.game.test.actor;

public class NullActor implements GameActor {

    public static final GameActor instance = new NullActor();

    @Override
    public ActorType getType() {
        return ActorType.VOID;
    }

    @Override
    public boolean isInField(int x, int y) {
        return false;
    }

    @Override
    public void act(GameActorField gameActorField) {

    }
}
