package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kanasuki.game.test.texture.TextureManager;

public class StarAwardTable extends Table {

    private static final int ALL_STAR = 3;

    public StarAwardTable(TextureManager textureManager, int amount, int size) {
        for (int i = 0; i < ALL_STAR; i++) {
            Image star = new Image(textureManager.getTexture("star"));
            if (i >= amount) {
                star.setColor(1f, 0f, 0f, 0.3f);
            }
            add(star).width(size).height(size);
        }
    }
}
