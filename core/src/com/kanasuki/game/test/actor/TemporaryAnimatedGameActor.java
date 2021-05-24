package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class TemporaryAnimatedGameActor extends AnimatedGameActor {

    private int lifeTime;

    public TemporaryAnimatedGameActor(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize, int lifeTime) {
        super(animation, fieldX, fieldY, squareSize);
        this.lifeTime = lifeTime;
    }

    @Override
    public int getLifeTime() {
        return lifeTime;
    }

    @Override
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
}
