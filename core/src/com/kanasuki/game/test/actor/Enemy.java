package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Enemy extends AnimatedGameActor {

    public Enemy(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize) {
        super(animation, fieldX, fieldY, squareSize);
    }

    @Override
    public ActorType getType() {
        return ActorType.ENEMY;
    }
}
