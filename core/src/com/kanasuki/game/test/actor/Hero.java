package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Hero extends AnimatedGameActor {

    private Camera camera;

    public Hero(Animation<TextureAtlas.AtlasRegion> animation, int fieldX, int fieldY, int squareSize) {
      super(animation, fieldX, fieldY, squareSize);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        if (camera != null) {
            camera.position.set(getX(), getY(), 0);
            camera.update();
        }
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
        positionChanged();
    }
}
