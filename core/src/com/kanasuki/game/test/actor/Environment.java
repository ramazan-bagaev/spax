package com.kanasuki.game.test.actor;

import com.badlogic.gdx.scenes.scene2d.Group;

public class Environment extends Group {

    private final int sizeX;
    private final int sizeY;
    private final int squareSize;

    public Environment(int sizeX, int sizeY, int squareSize) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.squareSize = squareSize;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSquareSize() {
        return squareSize;
    }

    public boolean isInEnvironment(int x, int y) {
        if (x < 0 || x >= sizeX) {
            return false;
        }

        if (y < 0 || y >= sizeY) {
            return false;
        }

        return true;
    }
}
