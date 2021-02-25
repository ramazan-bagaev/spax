package com.kanasuki.game.test.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class TextureAtlasManager {

    private final Map<String, String> fileNames;

    private final Map<String, TextureAtlas> textureAtlasMap;

    public TextureAtlasManager() {
        this.fileNames = new HashMap<>();
        this.textureAtlasMap = new HashMap<>();

        fileNames.put("hero", "animation/hero/spritesheets/hero-sprite.atlas");
        fileNames.put("enemy", "animation/enemy/spritesheets/enemy-sprite.atlas");
    }

    public void dispose() {
        for (TextureAtlas textureAtlas: textureAtlasMap.values()) {
            textureAtlas.dispose();
        }
    }

    public TextureAtlas getTextureAtlas(String name) {
        TextureAtlas textureAtlas = textureAtlasMap.get(name);

        if (textureAtlas == null) {
            textureAtlas = new TextureAtlas(Gdx.files.internal(fileNames.get(name)));
            textureAtlasMap.put(name, textureAtlas);
        }

        return textureAtlas;
    }
}
