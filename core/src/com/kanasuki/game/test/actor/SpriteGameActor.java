package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteGameActor extends Actor implements GameActor {

    private final int squareSize;

    private final Sprite sprite;

    SpriteGameActor(Texture texture, int fieldX, int fieldY, int squareSize) {
        this.sprite = new Sprite(texture);
        this.squareSize = squareSize;

        sprite.setSize(squareSize, squareSize);

        setX(fieldX * squareSize);
        setY(fieldY * squareSize);
    }

    @Override
    public ActorType getType() {
        return ActorType.SOFT;
    }

    @Override
    public boolean isInField(int x, int y) {
        int fieldX = (int) (getX() / squareSize);
        int fieldY = (int) (getY() / squareSize);

        return fieldX == x && fieldY == y;
    }

    @Override
    public void act(GameActorField gameActorField) {

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
