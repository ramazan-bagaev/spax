package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class DyingEnemy extends TemporaryAnimatedGameActor {

    private static final int DYING_ENEMY_LIFE_TIME = 7;

    public DyingEnemy(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize) {
        super(animation, fieldX, fieldY, squareSize, DYING_ENEMY_LIFE_TIME);
    }
}
