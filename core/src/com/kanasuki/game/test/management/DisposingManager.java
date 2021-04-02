package com.kanasuki.game.test.management;

import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.TextureManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DisposingManager {

    private final TextureManager textureManager;
    private final AnimationManager animationManager;
    private final ScreenManager screenManager;
    private final StyleManager styleManager;

    @Inject
    public DisposingManager(TextureManager textureManager, AnimationManager animationManager, ScreenManager screenManager, StyleManager styleManager) {
        this.textureManager = textureManager;
        this.animationManager = animationManager;
        this.screenManager = screenManager;
        this.styleManager = styleManager;
    }

    public void dispose() {
        textureManager.dispose();
        animationManager.dispose();
        screenManager.dispose();
        styleManager.dispose();
    }
}
