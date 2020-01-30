package com.kanasuki.game.test.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.texture.TextureDrawable;
import com.kanasuki.game.test.texture.TextureManager;

public class GuiFactory {

    public GameScreenLayout createGameScreenLayout() {
        Table topBar = new Table();
        Table middlePlace = new Table();

        return new GameScreenLayout(topBar, middlePlace);
    }

    public Actor createMenu(final TestGame game, Skin skin, String name, TextureManager textureManager) {
        Table table = new Table();

        TextButton playButton = new TextButton("play", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getChooseGameTypeScreen());
            }
        });

        TextButton exitButton = new TextButton("exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(playButton).pad(30).padLeft(70).padRight(70);
        table.row();
        table.add(exitButton).pad(30).padLeft(70).padRight(70);
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 1));

        Container<Table> tableContainer = new Container<>();
        tableContainer.setActor(table);

        Table mainLayout = new Table();
        mainLayout.setFillParent(true);

        mainLayout.add(tableContainer);
        mainLayout.row();

        return mainLayout;
    }

    public Actor createGameOverWindow(final TestGame game, Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        Label gameOverLabel = new Label("game over", skin, "title");
        TextButton againButton = new TextButton("again", skin);
        againButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.newGameScreen(game.getCurrentLevel());
                game.setScreen(game.getGameScreen());
            }
        });

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getMenuScreen());
            }
        });

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.addActor(againButton);
        horizontalGroup.space(10);
        horizontalGroup.addActor(exitButton);

        table.add(gameOverLabel).pad(20);
        table.row();
        table.add(horizontalGroup).padBottom(20).padLeft(10).padRight(10);
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 1));

        return table;
    }

    public Actor createWinWindow(final TestGame game, Skin skin, String styleName, TextureManager textureManager, int score) {
        Table table = new Table();

        Label gameOverLabel = new Label("you win", skin, "title");
        TextButton againButton = new TextButton("again", skin);
        againButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.newGameScreen(game.getCurrentLevel());
                game.setScreen(game.getGameScreen());
            }
        });

        StarAwardTable starAwardTable = new StarAwardTable(textureManager, game.getCurrentLevel().getStarAmount(score),
                (int) gameOverLabel.getHeight());

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getMenuScreen());
            }
        });

        TextButton nextLevel = new TextButton("next", skin);
        nextLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                LevelConfiguration levelConfiguration = game.getLevelMap().getLevel(game.getCurrentLevel().getLevel() + 1);
                game.newGameScreen(levelConfiguration);
                game.setScreen(game.getGameScreen());
            }
        });

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.addActor(againButton);
        horizontalGroup.space(10);
        horizontalGroup.addActor(exitButton);

        if (game.getCurrentLevel().getLevel() != 10) {
            horizontalGroup.addActor(nextLevel);
        }

        table.add(gameOverLabel).pad(10);
        table.row();
        table.add(starAwardTable).pad(10);
        table.row();
        table.add(horizontalGroup).pad(10);
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 1));

        return table;
    }

    public Actor createTopBar(final TestGame game, Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        Table labelTable = new Table();

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getMenuScreen());
            }
        });

        Label currentLevel = new Label(game.getCurrentLevel().getLevel() + " level", skin, "button");
        labelTable.add(currentLevel).padLeft(20).padRight(20);
        labelTable.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.7f));

        CountLabel countLabel = new CountLabel(skin, "button", 0, game.getCurrentLevel().getEnemyNumber());
        Image enemyImage = new Image(textureManager.getTexture("enemy"));

        ScoreLabel scoreLabel = new ScoreLabel(skin, "button", 0);
        Table scoreTable = new Table();
        scoreTable.add(scoreLabel);
        scoreTable.row();
        scoreTable.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.7f));

        Table countTable = new Table();
        countTable.add(countLabel).padLeft(20).padRight(20);
        countTable.add(enemyImage).width(countLabel.getHeight()).height(countLabel.getHeight());
        countTable.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.7f));

        game.getGameStatisticGui().setEnemyCountLabel(countLabel);
        game.getGameStatisticGui().setScoreLabel(scoreLabel);

        table.add(labelTable).padRight(50);
        table.add(countTable).padLeft(50).padRight(20);
        table.add(scoreTable).padLeft(50).padRight(20);
        table.add(exitButton).expand().right();
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.5f));

        return table;
    }

    public Actor createScrollableLevels(final TestGame game, final Skin skin, final String styleName, TextureManager textureManager) {
        final Table table = new Table();

        for (int level = 1; level <= game.getMaxLevel(); level++) {
            LevelButton levelButton = new LevelButton(level <= game.getCurrentMaxLevel(), game, level, skin, textureManager);

            table.add(levelButton).width(100).height(100).pad(30);

            if (level % 5 == 0) {
                table.row();
            }
        }

        ScrollPane scrollPane = new ScrollPane(table, skin);

        scrollPane.addListener(getScrollOnHover(scrollPane));

        Table container = new Table();

        container.add(scrollPane).height(700);

        return container;
    }

    public Actor createLevelCustomizer(final TestGame game, Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();
        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 1f));

        final OptionSlider widthSlider = new OptionSlider("width", 10, 50, 1, 20, skin);
        final OptionSlider heightSlider = new OptionSlider("height", 10, 50, 1, 20, skin);
        final OptionSlider enemyNumberSlider = new OptionSlider("enemy number", 1, 100, 1, 5, skin);
        final OptionSlider killSquare = new OptionSlider("kill square", 1, 20, 1, 4, skin);

        table.add(widthSlider);
        table.row();
        table.add(heightSlider);
        table.row();
        table.add(enemyNumberSlider);
        table.row();
        table.add(killSquare);
        table.row();

        TextButton textButton = new TextButton("create game", skin);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.newGameScreen(new LevelConfiguration(10,
                        enemyNumberSlider.getValue(),
                        widthSlider.getValue(),
                        heightSlider.getValue(),
                        killSquare.getValue()));

                game.setScreen(game.getGameScreen());
            }
        });
        table.add(textButton).pad(30);
        table.row();

        return table;
    }

    public Actor createChooseGameType(final TestGame game, Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        TextButton levelButton = new TextButton("play levels", skin);
        levelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getLevelScreen());
            }
        });

        TextButton customizerLevelButton = new TextButton("customize level", skin);
        customizerLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getCustomizerScreen());
            }
        });

        table.add(levelButton).pad(30).padLeft(70).padRight(70);
        table.row();
        table.add(customizerLevelButton).pad(30).padLeft(70).padRight(70);
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 1));

        return table;
    }

    private static EventListener getScrollOnHover(final Actor scrollPane) {
        return new EventListener() {
            @Override
            public boolean handle(Event event) {
                if(event instanceof InputEvent)
                    if(((InputEvent)event).getType() == InputEvent.Type.enter)
                        event.getStage().setScrollFocus(scrollPane);
                return false;
            }
        };
    }
}
