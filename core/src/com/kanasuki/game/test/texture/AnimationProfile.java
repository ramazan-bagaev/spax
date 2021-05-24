package com.kanasuki.game.test.texture;

public enum  AnimationProfile {

    HERO(0.4f),
    ENEMY(0.25f),
    WALL_DESTRUCTION(0.05f),
    DYING_ENEMY(0.1f);

    private final float frameDuration;

    AnimationProfile(float frameDuration) {
        this.frameDuration = frameDuration;
    }

    public float getFrameDuration() {
        return frameDuration;
    }
}
