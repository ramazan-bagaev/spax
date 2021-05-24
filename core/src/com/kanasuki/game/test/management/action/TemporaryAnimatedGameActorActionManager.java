package com.kanasuki.game.test.management.action;

import com.kanasuki.game.test.actor.GameActor;
import com.kanasuki.game.test.actor.GameActorManager;
import com.kanasuki.game.test.utils.Constants;

public class TemporaryAnimatedGameActorActionManager implements GameActorActionManager {

    private final GameActorManager gameActorManager;

    public TemporaryAnimatedGameActorActionManager(GameActorManager gameActorManager) {
        this.gameActorManager = gameActorManager;
    }

    @Override
    public void act(GameActor gameActor) {
        int lifeTime = gameActor.getLifeTime();

        if (lifeTime == 0) {
            gameActorManager.removeGameActor(gameActor);
        } else {
            gameActor.setLifeTime(lifeTime - 1);
        }
    }

    @Override
    public boolean isApplicable(GameActor gameActor) {
        return gameActor.getLifeTime() != Constants.INFINITE_LIFE_TIME;
    }

    @Override
    public float getActingPeriod() {
        return 0.05f;
    }
}
