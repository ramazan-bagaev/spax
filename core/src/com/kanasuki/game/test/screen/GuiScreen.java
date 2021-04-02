package com.kanasuki.game.test.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kanasuki.game.test.gui.GameScreenLayout;
import com.kanasuki.game.test.gui.GuiFactory;

public class GuiScreen implements Screen {

    private final Stage stage;
    private final GameScreenLayout gameScreenLayout;

    GuiScreen(GuiFactory guiFactory) {
        this.stage = new Stage();

        this.gameScreenLayout = guiFactory.createGameScreenLayout();
        stage.addActor(gameScreenLayout);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
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
        stage.dispose();
    }

    public GameScreenLayout getGameScreenLayout() {
        return gameScreenLayout;
    }
}
