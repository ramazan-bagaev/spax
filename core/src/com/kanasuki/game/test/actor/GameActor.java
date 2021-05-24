package com.kanasuki.game.test.actor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kanasuki.game.test.utils.Constants;

public abstract class GameActor extends Actor {

    public abstract ActorType getType();

    public abstract boolean isInField(int x, int y);

    public abstract void act(GameActorField gameActorField);

    public void setLifeTime(int lifeTime) {

    }

    public int getLifeTime() {
        return Constants.INFINITE_LIFE_TIME;
    }
}
