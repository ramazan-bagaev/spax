package com.kanasuki.game.test.actor;

import java.util.Collection;
import java.util.HashSet;

public class GameActorManager {

    private Collection<GameActor> gameActors;

    public GameActorManager() {
        this.gameActors = new HashSet<>();
    }

    public void addGameActor(GameActor gameActor) {
        gameActors.add(gameActor);
    }

    public void removeGameActor(GameActor gameActor) {
        gameActors.remove(gameActor);
    }

    public GameActor getGameActor(int x, int y) {
        for (GameActor gameActor: gameActors) {
            if (gameActor.isInField(x, y)) {
                return gameActor;
            }
        }

        return NullActor.instance;
    }
}
