package com.kanasuki.game.test.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {

    private final Map<AnimationProfile, String> fileNames;

    private final Map<AnimationProfile, TextureAtlas> textureAtlasMap;

    private final Map<AnimationProfile, Animation<TextureAtlas.AtlasRegion>> animationMap;

    public AnimationManager() {
        this.fileNames = new HashMap<>();
        this.textureAtlasMap = new HashMap<>();
        this.animationMap = new HashMap<>();

        fileNames.put(AnimationProfile.HERO, "animation/hero/spritesheets/hero-sprite.atlas");
        fileNames.put(AnimationProfile.ENEMY, "animation/enemy/spritesheets/enemy-sprite.atlas");
        fileNames.put(AnimationProfile.WALL_DESTRUCTION, "animation/destroying-wall/spritesheets/enemy-sprite.atlas");
    }

    public void dispose() {
        for (TextureAtlas textureAtlas: textureAtlasMap.values()) {
            textureAtlas.dispose();
        }
    }

    public Animation<TextureAtlas.AtlasRegion> getAnimation(AnimationProfile animationProfile) {
        Animation<TextureAtlas.AtlasRegion> animation = animationMap.get(animationProfile);

        if (animation == null) {
            TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal(fileNames.get(animationProfile)));

            textureAtlasMap.put(animationProfile, textureAtlas);

            animation = new Animation<>(animationProfile.getFrameDuration(), textureAtlas.getRegions(), Animation.PlayMode.LOOP);

            animationMap.put(animationProfile, animation);
        }

        return animation;
    }
}
