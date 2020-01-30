package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameActor extends Actor {

    private final int squareSize;

    private final Sprite sprite;

    GameActor(Texture texture, int fieldX, int fieldY, int squareSize) {
        this.sprite = new Sprite(texture);
        this.squareSize = squareSize;

        sprite.setSize(squareSize, squareSize);

        setX(fieldX * squareSize);
        setY(fieldY * squareSize);
    }

    public boolean isInField(int x, int y) {
        int fieldX = (int) (getX() / squareSize);
        int fieldY = (int) (getY() / squareSize);

        return fieldX == x && fieldY == y;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        sprite.draw(batch);
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(), getY());
        super.positionChanged();
    }
}
