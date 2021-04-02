package com.kanasuki.game.test.screen;

import com.kanasuki.game.test.gui.GuiFactory;
import com.kanasuki.game.test.management.StyleManager;
import com.kanasuki.game.test.texture.TextureManager;

public class ChooseGameTypeScreen extends GuiScreen {

    public ChooseGameTypeScreen(TextureManager textureManager, GuiFactory guiFactory, StyleManager styleManager) {
        super(guiFactory);
        getGameScreenLayout().getMiddlePlace()
                .add(guiFactory.createChooseGameType(styleManager.getSkin(), styleManager.getStyleName(), textureManager));
    }
}
