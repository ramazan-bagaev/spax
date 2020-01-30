package com.kanasuki.game.test.texture;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

public class TextureDrawable extends BaseDrawable {

    private final Texture texture;
    private final float alpha;

    public TextureDrawable(Texture texture, float alpha) {
        this.texture = texture;
        this.alpha = alpha;
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        Color color = batch.getColor();
        batch.setColor(color.r, color.g, color.b, alpha);
        batch.draw(texture, x, y, width, height);
        batch.setColor(color);
    }
}
