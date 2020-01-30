package com.kanasuki.game.test.gui;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class OptionSlider extends Table {

    private Slider slider;

    public OptionSlider(String text, int min, int max, int step, int defaultValue, Skin skin) {
        Label optionLabel = new Label(text, skin, "button");
        this.slider = new Slider(min, max, step, false, skin);
        final Label optionNumber = new Label(String.valueOf(defaultValue), skin, "button");
        slider.setValue(defaultValue);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                optionNumber.setText((int) slider.getValue());
            }
        });

        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(skin.getFont("button"), "99");
        float numberWidth = glyphLayout.width;
        glyphLayout.setText(skin.getFont("button"), "enemy number");
        float labelWidth = glyphLayout.width;

        add(optionLabel).padLeft(20).padTop(20).width(labelWidth);
        add(slider).padLeft(20).padTop(20);
        add(optionNumber).padLeft(20).padRight(30).padTop(20).width(numberWidth);
        row();
    }

    public int getValue() {
        return (int) slider.getValue();
    }
}
