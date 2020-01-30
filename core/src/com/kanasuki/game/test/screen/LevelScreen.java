package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.texture.TextureManager;

public class LevelScreen extends GuiScreen {

    public LevelScreen(TestGame testGame, TextureManager textureManager) {
        GuiFactory guiFactory = new GuiFactory();


        getGameScreenLayout().
                getMiddlePlace().add(guiFactory.createScrollableLevels(testGame, testGame.getSkin(), testGame.getStyle(), textureManager));
    }
}
