package com.kanasuki.game.test.actor;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;

public class Hero extends GameActor {

    private Camera camera;

    public Hero(Texture texture, int fieldX, int fieldY, int squareSize) {
      super(texture, fieldX, fieldY, squareSize);
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
