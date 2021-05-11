package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.Texture;

public class DeadEnemy extends SpriteGameActor {

    public DeadEnemy(Texture texture, int fieldX, int fieldY, int squareSize) {
        super(texture, fieldX, fieldY, squareSize);
    }

    @Override
    public ActorType getType() {
        return ActorType.OBSTRUCTION;
    }
}
