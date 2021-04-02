package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.management.StyleManager;
import com.kanasuki.game.test.texture.TextureManager;

public class MenuScreen extends GuiScreen {

    public MenuScreen(TextureManager textureManager, GuiFactory guiFactory, StyleManager styleManager) {
        super(guiFactory);
        getGameScreenLayout().getMiddlePlace().add(guiFactory.createMenu(styleManager.getSkin(), styleManager.getStyleName(), textureManager));
    }
}
