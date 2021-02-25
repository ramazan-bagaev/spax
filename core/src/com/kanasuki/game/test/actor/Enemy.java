package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Enemy extends AnimatedGameActor {

    public Enemy(TextureAtlas textureAtlas, int fieldX, int fieldY, int squareSize) {
        super(textureAtlas, fieldX, fieldY, squareSize);
    }
}
