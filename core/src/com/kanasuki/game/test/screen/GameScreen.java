package com.kanasuki.game.test.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.kanasuki.game.test.GameManager;
import com.kanasuki.game.test.gui.GameScreenLayout;
import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.texture.TextureManager;
import com.kanasuki.game.test.input.PlayerInputProcessor;
import com.kanasuki.game.test.level.LevelConfiguration;

public class GameScreen implements Screen {

    private final Stage gameStage;
    private final Stage uiStage;
    private final TextureManager textureManager;
    private final GameManager gameManager;
    private final PlayerInputProcessor playerInputProcessor;
    private final TestGame testGame;
    private final GameScreenLayout gameScreenLayout;

    private final GuiFactory guiFactory;

    private final Camera camera;

    private float oneSecond = 0;
    private int frames = 0;

    public GameScreen(TestGame testGame, TextureManager textureManager, LevelConfiguration levelConfiguration) {
        this.testGame = testGame;
        this.camera = new OrthographicCamera();
        this.textureManager = textureManager;

        this.gameManager = new GameManager(textureManager, levelConfiguration, testGame);

        this.uiStage = new Stage();
        this.gameStage = new Stage(new FillViewport(1500, 1000, camera));

        gameStage.addActor(gameManager.createGameGroup());

        int squareSize = gameManager.getEnvironment().getSquareSize();

        camera.position.set(levelConfiguration.getWidth()/2 * squareSize, levelConfiguration.getHeight()/2 * squareSize, 0);

        this.playerInputProcessor = new PlayerInputProcessor(gameManager);
        this.guiFactory = new GuiFactory();
        this.gameScreenLayout = guiFactory.createGameScreenLayout();
        uiStage.addActor(gameScreenLayout);
        gameScreenLayout.getTopBar().add(guiFactory.createTopBar(testGame, testGame.getSkin(),
                testGame.getStyle(), textureManager)).growX();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputMultiplexer(uiStage, playerInputProcessor));
    }

    @Override
    public void render(float delta) {
        oneSecond += delta;
        frames++;

        if (oneSecond > 1) {
            oneSecond = oneSecond % 1;
            System.out.println("fps: " + frames);
            frames = 0;
        }

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!gameManager.isGameEnded()) {
            gameManager.makeTurn(delta);

            if (gameManager.isGameLoosed()) {
                Actor actor = guiFactory.createGameOverWindow(testGame, testGame.getSkin(),
                        testGame.getStyle(), textureManager);
                gameScreenLayout.getMiddlePlace().add(actor);
                return;
            }

            if (gameManager.isGameWon()) {
                testGame.getGameProgress().recordScore(testGame.getCurrentLevel().getLevel(), gameManager.getScore());

                if (testGame.getCurrentLevel().getLevel() == testGame.getCurrentMaxLevel()) {
                    testGame.nextLevel();
                }



                Actor actor = guiFactory.createWinWindow(testGame, testGame.getSkin(),
                        testGame.getStyle(), textureManager, gameManager.getScore());
                gameScreenLayout.getMiddlePlace().add(actor);
            }
        }

        gameStage.act();
        uiStage.act();

        gameStage.draw();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameStage.dispose();
        uiStage.dispose();
    }
}
