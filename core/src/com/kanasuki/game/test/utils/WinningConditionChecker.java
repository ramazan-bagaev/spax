package com.kanasuki.game.test.utils;

import com.kanasuki.game.test.actor.GameActorField;
import com.kanasuki.game.test.level.LevelConfiguration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

@Singleton
public class WinningConditionChecker {

    private final int maxSquare;
    private final GameActorField gameActorField;

    @Inject
    public WinningConditionChecker(GameActorField gameActorField, LevelConfiguration levelConfiguration) {
        this.gameActorField = gameActorField;
        this.maxSquare = levelConfiguration.getMaxEnemySquare();
    }

    public int checkEnemyConfinedSquare(int x, int y) {
        Collection<Point> visited = new HashSet<>();
        LinkedList<Point> queue = new LinkedList<>();
        queue.push(new Point(x, y));

        while(!queue.isEmpty()) {
            Point current = queue.pollLast();
            visited.add(current);

            if (visited.size() > maxSquare) {
                return visited.size();
            }

            if (couldAddToQueue(current.getX() + 1, current.getY())) {
                Point point = new Point(current.getX() + 1, current.getY());
                if (!visited.contains(point)) {
                    queue.push(point);
                }
            }

            if (couldAddToQueue(current.getX() - 1, current.getY())) {
                Point point = new Point(current.getX() - 1, current.getY());
                if (!visited.contains(point)) {
                    queue.push(point);
                }
            }

            if (couldAddToQueue(current.getX(), current.getY() + 1)) {
                Point point = new Point(current.getX(), current.getY() + 1);
                if (!visited.contains(point)) {
                    queue.push(point);
                }
            }

            if (couldAddToQueue(current.getX(), current.getY() - 1)) {
                Point point = new Point(current.getX(), current.getY() - 1);
                if (!visited.contains(point)) {
                    queue.push(point);
                }
            }
        }

        return visited.size();
    }

    private boolean couldAddToQueue(int x, int y) {
        boolean inside = gameActorField.isInside(x, y);

        return !gameActorField.isObstruction(x, y) && inside;
    }
}
