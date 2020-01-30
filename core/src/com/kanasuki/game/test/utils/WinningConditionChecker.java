package com.kanasuki.game.test.utils;

import com.kanasuki.game.test.GameManager;
import com.kanasuki.game.test.actor.Environment;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

public class WinningConditionChecker {

    private final int maxSquare;

    private final GameManager gameManager;
    private final Environment environment;

    public WinningConditionChecker(GameManager gameManager) {
        this.gameManager = gameManager;
        this.environment = gameManager.getEnvironment();
        this.maxSquare = gameManager.getLevelConfiguration().getMaxEnemySquare();
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
        if (!environment.isInEnvironment(x, y)) {
            return false;
        }

        return !gameManager.isInWall(x, y);
    }
}
