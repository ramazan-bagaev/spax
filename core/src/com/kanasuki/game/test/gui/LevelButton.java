package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.texture.TextureManager;

public class LevelButton extends Table {

    public LevelButton(boolean active, final TestGame game, int level, Skin skin, TextureManager textureManager) {
        if (active) {
            Actor actor = new TextButton(String.valueOf(level), skin);
            final int finalLevel = level;
            actor.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    LevelConfiguration levelConfiguration = game.getLevelMap().getLevel(finalLevel);
                    game.newGameScreen(levelConfiguration);
                    game.setScreen(game.getGameScreen());
                }
            });
            actor.setColor(0, 1, 0, 0.5f);
            add(actor);
            row();
            StarAwardTable starAwardTable = new StarAwardTable(textureManager,
                    game.getLevelMap().getLevel(level).getStarAmount(game.getGameProgress().getScore(level)), (int) skin.getFont("button").getCapHeight());
            add(starAwardTable);
            row();
        } else {
            Actor actor = new TextButton(String.valueOf(level), skin);
            actor.setColor(1, 0, 0, 0.5f);
            actor.setTouchable(Touchable.disabled);
            add(actor);
            row();
        }
    }
}
