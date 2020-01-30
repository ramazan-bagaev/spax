package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.texture.TextureManager;

public class MenuScreen extends GuiScreen {

    public MenuScreen(TestGame game, TextureManager textureManager) {
        GuiFactory guiFactory = new GuiFactory();

        getGameScreenLayout().getMiddlePlace().add(guiFactory.createMenu(game, game.getSkin(), game.getStyle(), textureManager));
    }
}
