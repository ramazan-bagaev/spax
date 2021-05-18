package com.kanasuki.game.test.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameActor extends Actor {

    public abstract ActorType getType();

    public abstract boolean isInField(int x, int y);

    public abstract void act(GameActorField gameActorField);
}
