package com.kanasuki.game.test.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kanasuki.game.test.GameProgress;
import com.kanasuki.game.test.event.EventManager;
import com.kanasuki.game.test.event.EventType;
import com.kanasuki.game.test.level.LevelMap;
import com.kanasuki.game.test.management.LevelManager;
import com.kanasuki.game.test.texture.TextureManager;

public class LevelButton extends Table {

    public LevelButton(boolean active, int level, Skin skin, TextureManager textureManager,
                       final LevelMap levelMap, GameProgress gameProgress, final LevelManager levelManager, final EventManager eventManager) {
        if (active) {
            Actor actor = new TextButton(String.valueOf(level), skin);
            final int finalLevel = level;
            actor.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    levelManager.setLevel(finalLevel);
                    eventManager.fire(EventType.GAME_SCREEN_ON);
                    //screenManager.setScreen(screenManager.getGameScreen());
                    //LevelConfiguration levelConfiguration = levelMap.getLevel(finalLevel);
                    //game.newGameScreen(levelConfiguration);
                    //game.setScreen(game.getGameScreen());
                }
            });
            actor.setColor(0, 1, 0, 0.5f);
            add(actor);
            row();
            StarAwardTable starAwardTable = new StarAwardTable(textureManager,
                    levelMap.getLevel(level).getStarAmount(gameProgress.getScore(level)), (int) skin.getFont("button").getCapHeight());
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
