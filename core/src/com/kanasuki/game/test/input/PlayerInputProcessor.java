package com.kanasuki.game.test.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.kanasuki.game.test.GameManager;

public class PlayerInputProcessor implements InputProcessor {

    private final GameManager gameManager;

    public PlayerInputProcessor(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                gameManager.addMovingCommand(PlayerInput.UP);
                break;
            case Input.Keys.S:
                gameManager.addMovingCommand(PlayerInput.DOWN);
                break;
            case Input.Keys.A:
                gameManager.addMovingCommand(PlayerInput.LEFT);
                break;
            case Input.Keys.D:
                gameManager.addMovingCommand(PlayerInput.RIGHT);
                break;
            case Input.Keys.UP:
                gameManager.addActionCommand(PlayerInput.ACTION_UP);
                break;
            case Input.Keys.DOWN:
                gameManager.addActionCommand(PlayerInput.ACTION_DOWN);
                break;
            case Input.Keys.LEFT:
                gameManager.addActionCommand(PlayerInput.ACTION_LEFT);
                break;
            case Input.Keys.RIGHT:
                gameManager.addActionCommand(PlayerInput.ACTION_RIGHT);
                break;
            case Input.Keys.ENTER:
                gameManager.addActionCommand(PlayerInput.ACTION);
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                gameManager.removeMovingCommand(PlayerInput.UP);
                break;
            case Input.Keys.S:
                gameManager.removeMovingCommand(PlayerInput.DOWN);
                break;
            case Input.Keys.A:
                gameManager.removeMovingCommand(PlayerInput.LEFT);
                break;
            case Input.Keys.D:
                gameManager.removeMovingCommand(PlayerInput.RIGHT);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
