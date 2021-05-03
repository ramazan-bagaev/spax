package com.kanasuki.game.test.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kanasuki.game.test.GameProgress;
import com.kanasuki.game.test.event.EventManager;
import com.kanasuki.game.test.event.EventType;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.level.LevelMap;
import com.kanasuki.game.test.management.LevelManager;
import com.kanasuki.game.test.texture.TextureDrawable;
import com.kanasuki.game.test.texture.TextureManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiFactory {

    private LevelMap levelMap;
    private GameStatisticGui gameStatisticGui;
    private GameProgress gameProgress;
    private final LevelManager levelManager;
    private final EventManager eventManager;

    @Inject
    public GuiFactory(LevelMap levelMap, GameProgress gameProgress, GameStatisticGui gameStatisticGui,
                      LevelManager levelManager, EventManager eventManager) {
        this.levelMap = levelMap;
        this.gameProgress = gameProgress;
        this.gameStatisticGui = gameStatisticGui;
        this.levelManager = levelManager;
        this.eventManager = eventManager;
    }

    public GameScreenLayout createGameScreenLayout() {
        Table topBar = new Table();
        Table middlePlace = new Table();

        return new GameScreenLayout(topBar, middlePlace);
    }

    public Actor createMenu(Skin skin, String name, TextureManager textureManager) {
        Table table = new Table();

        TextButton playButton = new TextButton("play", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventManager.fire(EventType.CHOOSE_GAME_TYPE_SCREEN_ON);
                //screenManager.setScreen(screenManager.getChooseGameTypeScreen());
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

    public Actor createGameOverWindow(Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        Label gameOverLabel = new Label("game over", skin, "title");
        TextButton againButton = new TextButton("again", skin);
        againButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getGameScreen());
                eventManager.fire(EventType.GAME_SCREEN_ON);
            }
        });

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getMenuScreen());
                eventManager.fire(EventType.MENU_SCREEN_ON);
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

    public Actor createWinWindow(Skin skin, String styleName, TextureManager textureManager, final int score) {
        Table table = new Table();

        Label gameOverLabel = new Label("you win", skin, "title");
        TextButton againButton = new TextButton("again", skin);
        againButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getGameScreen());
                eventManager.fire(EventType.GAME_SCREEN_ON);
            }
        });

        StarAwardTable starAwardTable = new StarAwardTable(textureManager, levelManager.getCurrentLevelConfiguration().getStarAmount(score),
                (int) gameOverLabel.getHeight());

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getMenuScreen());
                eventManager.fire(EventType.MENU_SCREEN_ON);
            }
        });

        TextButton nextLevel = new TextButton("next", skin);
        nextLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                eventManager.fire(EventType.NEXT_LEVEL);
                //screenManager.nextMaxLevel();
                //screenManager.setScreen(screenManager.getGameScreen());
                //LevelConfiguration levelConfiguration = levelMap.getLevel(game.getCurrentLevel().getLevel() + 1);
                //game.newGameScreen(levelConfiguration);
                //game.setScreen(game.getGameScreen());
            }
        });

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.addActor(againButton);
        horizontalGroup.space(10);
        horizontalGroup.addActor(exitButton);

        if (levelManager.getCurrentLevel() != 10) {
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

    public Actor createTopBar(Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        Table labelTable = new Table();

        TextButton exitButton = new TextButton("menu", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getMenuScreen());
                eventManager.fire(EventType.MENU_SCREEN_ON);
            }
        });

        Label currentLevel = new Label(levelManager.getCurrentLevel() + " level", skin, "button");
        labelTable.add(currentLevel).padLeft(20).padRight(20);
        labelTable.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.7f));

        CountLabel countLabel = new CountLabel(skin, "button", 0, levelManager.getCurrentLevelConfiguration().getEnemyNumber());
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

        gameStatisticGui.setEnemyCountLabel(countLabel);
        gameStatisticGui.setScoreLabel(scoreLabel);

        table.add(labelTable).padRight(50);
        table.add(countTable).padLeft(50).padRight(20);
        table.add(scoreTable).padLeft(50).padRight(20);
        table.add(exitButton).expand().right();
        table.row();

        table.setBackground(new TextureDrawable(textureManager.getTexture("window"), 0.5f));

        return table;
    }

    public Actor createScrollableLevels(final Skin skin, final String styleName, TextureManager textureManager) {
        final Table table = new Table();

        for (int level = 1; level <= levelMap.getLevelNumbers(); level++) {
            LevelButton levelButton = new LevelButton(level <= levelManager.getCurrentMaxLevel(), level, skin,
                    textureManager, levelMap, gameProgress, levelManager, eventManager);

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

    public Actor createLevelCustomizer(Skin skin, String styleName, TextureManager textureManager) {
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
                levelManager.setCustomizableLevel(new LevelConfiguration(10,
                        enemyNumberSlider.getValue(),
                        widthSlider.getValue(),
                        heightSlider.getValue(),
                        killSquare.getValue()));

                //GameScreen gameScreen = screenManager.getGameScreen();
                //screenManager.setScreen(gameScreen);

                eventManager.fire(EventType.CUSTOMIZABLE_GAME_ON);
            }
        });
        table.add(textButton).pad(30);
        table.row();

        return table;
    }

    public Actor createChooseGameType(Skin skin, String styleName, TextureManager textureManager) {
        Table table = new Table();

        TextButton levelButton = new TextButton("play levels", skin);
        levelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getLevelScreen());
                eventManager.fire(EventType.LEVEL_SCREEN_ON);
            }
        });

        TextButton customizerLevelButton = new TextButton("customize level", skin);
        customizerLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //screenManager.setScreen(screenManager.getCustomizerScreen());
                eventManager.fire(EventType.CUSTOMIZER_SCREEN_ON);
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
