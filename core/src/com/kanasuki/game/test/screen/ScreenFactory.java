package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.GameProgress;
import com.kanasuki.game.test.di.SpaxContext;
import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.management.LevelManager;
import com.kanasuki.game.test.management.StyleManager;
import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.TextureManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScreenFactory {

    private final TextureManager textureManager;
    private final AnimationManager animationManager;
    private final GuiFactory guiFactory;
    private final GameProgress gameProgress;
    private final LevelManager levelManager;
    private final StyleManager styleManager;
    private final SpaxContext spaxContext;

    private MenuScreen menuScreen;
    private CustomizerScreen customizerScreen;
    private ChooseGameTypeScreen chooseGameTypeScreen;

    @Inject
    public ScreenFactory(TextureManager textureManager, AnimationManager animationManager,
                         GuiFactory guiFactory, GameProgress gameProgress,
                         LevelManager levelManager, StyleManager styleManager, SpaxContext spaxContext) {
        this.textureManager = textureManager;
        this.animationManager = animationManager;
        this.guiFactory = guiFactory;
        this.gameProgress = gameProgress;
        this.levelManager = levelManager;
        this.styleManager = styleManager;
        this.spaxContext = spaxContext;
    }

    public GameScreen newGameScreen(LevelConfiguration levelConfiguration) {
        return new GameScreen(spaxContext, textureManager, animationManager, levelConfiguration, guiFactory, gameProgress, levelManager, styleManager);
    }

    public LevelScreen newLevelScreen() {
        return new LevelScreen(textureManager, guiFactory, styleManager);
    }

    public MenuScreen menuScreen() {
        if (menuScreen == null) {
            this.menuScreen = new MenuScreen(textureManager, guiFactory, styleManager);
        }

        return menuScreen;
    }

    public CustomizerScreen customizerScreen() {
        if (customizerScreen == null) {
            this.customizerScreen = new CustomizerScreen(textureManager, guiFactory, styleManager);
        }

        return customizerScreen;
    }

    public ChooseGameTypeScreen chooseGameTypeScreen() {
        if (chooseGameTypeScreen == null) {
            this.chooseGameTypeScreen = new ChooseGameTypeScreen(textureManager, guiFactory, styleManager);
        }

        return chooseGameTypeScreen;
    }
}
