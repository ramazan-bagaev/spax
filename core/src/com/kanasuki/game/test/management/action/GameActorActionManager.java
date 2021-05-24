package com.kanasuki.game.test.management.action;

import com.kanasuki.game.test.actor.GameActor;

public interface GameActorActionManager {

    void act(GameActor gameActor);

    boolean isApplicable(GameActor gameActor);

    float getActingPeriod();
}
