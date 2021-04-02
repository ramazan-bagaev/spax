package com.kanasuki.game.test.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class TextureManager {

    private Map<String, String> fileNames;

    private Map<String, Texture> textures;

    @Inject
    public TextureManager() {
        this.fileNames = new HashMap<>();
        this.textures = new HashMap<>();

        fileNames.put("hero","hero.png");
        fileNames.put("enemy", "enemy.png");
        fileNames.put("square","square.png");
        fileNames.put("wall","wall.png");
        fileNames.put("window", "window.png");
        fileNames.put("dead-enemy", "dead-enemy.png");
        fileNames.put("star", "star.png");
    }

    public void dispose() {
        for (Texture texture: textures.values()) {
            texture.dispose();
        }
    }

    public Texture getTexture(String name) {
        Texture texture = textures.get(name);

        if (texture == null) {
            texture = new Texture(Gdx.files.internal(fileNames.get(name)));
            textures.put(name, texture);
        }

        return texture;
    }
}
