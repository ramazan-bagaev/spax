package com.kanasuki.game.test.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedGameActor extends Actor implements GameActor {

    private float x;
    private float y;

    private float elapsedTime = 0f;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private final int squareSize;

    public AnimatedGameActor(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize) {
        this.x = fieldX;
        this.y = fieldY;

        this.animation = animation;
        this.squareSize = squareSize;

        setX(fieldX * squareSize);
        setY(fieldY * squareSize);
    }

    @Override
    public ActorType getType() {
        return ActorType.VOID;
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
    public void draw(Batch batch, float parentAlpha) {
        this.elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime), x, y, squareSize, squareSize);
    }

    @Override
    protected void positionChanged() {
        this.x = getX();
        this.y = getY();
        super.positionChanged();
    }
}
