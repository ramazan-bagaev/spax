package com.kanasuki.game.test.management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StyleManager {

    private static final String DEFAULT_STYLE = "default";

    private final Skin skin;

    @Inject
    public StyleManager() {
        this.skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
    }

    public Skin getSkin() {
        return skin;
    }

    public String getStyleName() {
        return DEFAULT_STYLE;
    }

    public void dispose() {
        skin.dispose();
    }
}
