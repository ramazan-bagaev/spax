package com.kanasuki.game.test.management.action;

import com.kanasuki.game.test.actor.GameActor;
import com.kanasuki.game.test.actor.GameActorManager;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Singleton
public class ActionManager {

    private final GameActorManager gameActorManager;

    private final Collection<GameActorActionManager> gameActorActionManagers;

    private final Map<GameActorActionManager, Float> deltas;

    public ActionManager(GameActorManager gameActorManager) {
        this.gameActorManager = gameActorManager;
        this.gameActorActionManagers = new HashSet<>();
        this.deltas = new HashMap<>();
    }

    public void addGameActorActionManager(GameActorActionManager gameActorActionManager) {
        gameActorActionManagers.add(gameActorActionManager);
        deltas.put(gameActorActionManager, 0f);
    }

    public void act(float delta) {
        Collection<GameActor> gameActors = new HashSet<>(gameActorManager.getGameActors());

        for (GameActorActionManager gameActorActionManager: gameActorActionManagers) {
            deltas.put(gameActorActionManager, deltas.get(gameActorActionManager) + delta);

            if (deltas.get(gameActorActionManager) > gameActorActionManager.getActingPeriod()) {
                deltas.put(gameActorActionManager, 0f);

                for (GameActor gameActor : gameActors) {
                    if (gameActorActionManager.isApplicable(gameActor)) {
                        gameActorActionManager.act(gameActor);
                    }
                }
            }
        }
    }
}
