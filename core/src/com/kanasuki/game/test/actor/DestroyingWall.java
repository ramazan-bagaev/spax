package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DestroyingWall extends TemporaryAnimatedGameActor {

    private final static int DESTROYING_WALL_LIFE_TIME = 5;

    public DestroyingWall(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize) {
        super(animation, fieldX, fieldY, squareSize, DESTROYING_WALL_LIFE_TIME);
    }
}
