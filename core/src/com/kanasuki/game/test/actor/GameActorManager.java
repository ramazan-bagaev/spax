package com.kanasuki.game.test.actor;

import com.badlogic.gdx.scenes.scene2d.Group;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;

@Singleton
public class GameActorManager {

    private Collection<GameActor> gameActors;
    private Group gameGroup;

    @Inject
    public GameActorManager() {
        this.gameActors = new HashSet<>();
        this.gameGroup = new Group();
    }

    public void addGameActor(GameActor gameActor) {
        gameActors.add(gameActor);
        gameGroup.addActor(gameActor);
    }

    public void removeGameActor(GameActor gameActor) {
        if (gameActors.contains(gameActor)) {
            gameActors.remove(gameActor);
            gameGroup.removeActor(gameActor);
        }
    }

    public GameActor getGameActor(int x, int y) {
        for (GameActor gameActor: gameActors) {
            if (gameActor.isInField(x, y)) {
                return gameActor;
            }
        }

        return NullActor.instance;
    }

    public boolean isActorType(int x, int y, ActorType actorType) {
        for (GameActor gameActor: gameActors) {
            if (gameActor.isInField(x, y)) {
                if (gameActor.getType() == actorType) {
                    return true;
                }
            }
        }

        return false;
    }

    public Group getGameGroup() {
        return gameGroup;
    }

    public int getEnemyAmount() {
        int amount = 0;

        for (GameActor gameActor: gameActors) {
            if (gameActor.getType() == ActorType.ENEMY) {
                amount++;
            }
        }

        return amount;
    }

    public Collection<GameActor> getGameActors() {
        return gameActors;
    }
}
