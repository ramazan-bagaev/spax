package com.kanasuki.game.test.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AnimatedGameActor extends Actor {

    private static final float FRAME_PER_SECOND = 0.1f;

    private float x;
    private float y;

    private float elapsedTime = 0f;

    private Animation<TextureAtlas.AtlasRegion> animation;
    private final int squareSize;

    public AnimatedGameActor(TextureAtlas textureAtlas, int fieldX, int fieldY, int squareSize) {
        this.x = fieldX;
        this.y = fieldY;

        this.animation = new Animation<>(FRAME_PER_SECOND, textureAtlas.getRegions(), Animation.PlayMode.LOOP);
        this.squareSize = squareSize;
    }

    public boolean isInField(int x, int y) {
        int fieldX = (int) (getX() / squareSize);
        int fieldY = (int) (getY() / squareSize);

        return fieldX == x && fieldY == y;
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
