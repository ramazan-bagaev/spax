package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CountLabel extends Label {

    private int count;
    private int all;

    public CountLabel(Skin skin, String styleName, int count, int all) {
        super(count + "/" + all, skin, styleName);
        this.count = count;
        this.all = all;
    }

    public void increaseCount() {
        count++;
        setText(count + "/" + all);
    }
}
