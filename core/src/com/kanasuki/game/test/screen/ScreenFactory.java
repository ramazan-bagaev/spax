package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.GameProgress;
import com.kanasuki.game.test.gui.GameStatisticGui;
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
    private final GameStatisticGui gameStatisticGui;
    private final LevelManager levelManager;
    private final StyleManager styleManager;

    private LevelScreen levelScreen;
    private MenuScreen menuScreen;
    private CustomizerScreen customizerScreen;
    private ChooseGameTypeScreen chooseGameTypeScreen;

    @Inject
    public ScreenFactory(TextureManager textureManager, AnimationManager animationManager,
                         GuiFactory guiFactory, GameProgress gameProgress, GameStatisticGui gameStatisticGui,
                         LevelManager levelManager, StyleManager styleManager) {
        this.textureManager = textureManager;
        this.animationManager = animationManager;
        this.guiFactory = guiFactory;
        this.gameProgress = gameProgress;
        this.gameStatisticGui = gameStatisticGui;
        this.levelManager = levelManager;
        this.styleManager = styleManager;
    }

    public GameScreen newGameScreen(LevelConfiguration levelConfiguration) {
        return new GameScreen(textureManager, animationManager, levelConfiguration, guiFactory, gameProgress, gameStatisticGui, levelManager, styleManager);
    }

    public LevelScreen levelScreen() {
        if (levelScreen == null) {
            this.levelScreen = new LevelScreen(textureManager, guiFactory, styleManager);
        }

        return levelScreen;
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

    public void dispose() {
        if (menuScreen != null) {
            menuScreen.dispose();
        }

        if (customizerScreen != null) {
            customizerScreen.dispose();
        }

        if (chooseGameTypeScreen != null) {
            chooseGameTypeScreen.dispose();
        }

        if (levelScreen != null) {
            levelScreen.dispose();
        }
    }
}
