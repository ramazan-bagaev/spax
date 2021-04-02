package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.management.StyleManager;
import com.kanasuki.game.test.texture.TextureManager;

public class CustomizerScreen extends GuiScreen {

    public CustomizerScreen(TextureManager textureManager, GuiFactory guiFactory, StyleManager styleManager) {
        super(guiFactory);
        getGameScreenLayout().getMiddlePlace().
                add(guiFactory.createLevelCustomizer(styleManager.getSkin(), styleManager.getStyleName(), textureManager));
    }
}
