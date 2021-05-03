package com.kanasuki.game.test.management;

import com.badlogic.gdx.Screen;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.event.EventListener;
import com.kanasuki.game.test.event.EventType;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.screen.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ScreenManager implements EventListener {

    private final TestGame testGame;
    private final ScreenFactory screenFactory;
    private final LevelManager levelManager;

    private MenuScreen menuScreen;
    private LevelScreen levelScreen;
    private CustomizerScreen customizerScreen;
    private ChooseGameTypeScreen chooseGameTypeScreen;

    private GameScreen gameScreen;

    @Inject
    public ScreenManager(TestGame testGame, ScreenFactory screenFactory, LevelManager levelManager) {
        this.testGame = testGame;
        this.screenFactory = screenFactory;
        this.levelManager = levelManager;
    }

    public void init() {
        this.menuScreen = screenFactory.menuScreen();
        this.levelScreen = screenFactory.newLevelScreen();
        this.customizerScreen = screenFactory.customizerScreen();
        this.chooseGameTypeScreen = screenFactory.chooseGameTypeScreen();
    }

    public void setScreen(Screen screen) {
        testGame.setScreen(screen);
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public LevelScreen getLevelScreen() {
        return levelScreen;
    }

    public CustomizerScreen getCustomizerScreen() {
        return customizerScreen;
    }

    public ChooseGameTypeScreen getChooseGameTypeScreen() {
        return chooseGameTypeScreen;
    }

    public void nextMaxLevel() {
        if (levelManager.getCurrentLevel() == levelManager.getCurrentMaxLevel()) {
            levelManager.nextMaxLevel();
        }

        updateLevelScreen();
    }

    private void updateGameScreen(LevelConfiguration levelConfiguration) {
        if (gameScreen != null) {
            gameScreen.dispose();
        }

        this.gameScreen = screenFactory.newGameScreen(levelConfiguration);
    }

    private void updateLevelScreen() {
        if (levelScreen != null) {
            levelScreen.dispose();
        }

        levelScreen = screenFactory.newLevelScreen();
    }

    public GameScreen getGameScreen(LevelConfiguration levelConfiguration) {
        updateGameScreen(levelConfiguration);

        return gameScreen;
    }

    public GameScreen getGameScreen() {
        return getGameScreen(levelManager.getCurrentLevelConfiguration());
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

        if (gameScreen != null) {
            gameScreen.dispose();
        }
    }

    @Override
    public void onEvent(EventType eventType) {
        switch (eventType) {

            case MENU_SCREEN_ON:
                setScreen(getMenuScreen());
                break;
            case GAME_SCREEN_ON:
                setScreen(getGameScreen());
                break;
            case LEVEL_SCREEN_ON:
                setScreen(getLevelScreen());
                break;
            case CHOOSE_GAME_TYPE_SCREEN_ON:
                setScreen(getChooseGameTypeScreen());
                break;
            case CUSTOMIZER_SCREEN_ON:
                setScreen(getCustomizerScreen());
                break;
            case NEXT_LEVEL:
                nextLevel();
                break;
            case NEXT_MAX_LEVEL:
                nextMaxLevel();
                break;
            case CUSTOMIZABLE_GAME_ON:
                setScreen(getGameScreen(levelManager.getCustomizableLevelConfiguration()));
                break;
        }
    }

    private void nextLevel() {
        levelManager.nextLevel();
        setScreen(getGameScreen());
    }
}
