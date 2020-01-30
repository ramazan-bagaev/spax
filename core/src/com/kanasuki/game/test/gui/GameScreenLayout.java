package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameScreenLayout extends Table {

    private final Table topBar;
    private final Table middlePlace;

    public GameScreenLayout(Table topBar, Table middlePlace) {
        this.topBar = topBar;
        this.middlePlace = middlePlace;

        setFillParent(true);

        add(topBar).growX();
        row();
        add(middlePlace).expandY();
        row();

        top();
    }

    public Table getTopBar() {
        return topBar;
    }

    public Table getMiddlePlace() {
        return middlePlace;
    }
}
