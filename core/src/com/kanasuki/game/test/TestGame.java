package com.kanasuki.game.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kanasuki.game.test.gui.GameStatisticGui;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.level.LevelMap;
import com.kanasuki.game.test.screen.ChooseGameTypeScreen;
import com.kanasuki.game.test.screen.CustomizerScreen;
import com.kanasuki.game.test.screen.GameScreen;
import com.kanasuki.game.test.screen.LevelScreen;
import com.kanasuki.game.test.screen.MenuScreen;
import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.TextureManager;

public class TestGame extends Game {

	private static final String DEFAULT_STYLE = "default";

	private Skin skin;
	private BitmapFont font;

	private MenuScreen menuScreen;
	private GameScreen gameScreen;
	private LevelScreen levelScreen;
	private CustomizerScreen customizerScreen;
	private ChooseGameTypeScreen chooseGameTypeScreen;

	private TextureManager textureManager;
	private AnimationManager animationManager;

	private LevelConfiguration currentLevel;
	private LevelMap levelMap;
	private int currentMaxLevel = 1;
	private GameStatisticGui gameStatisticGui;

	private GameProgress gameProgress;

	@Override
	public void create() {
		//this.font = new BitmapFont(Gdx.files.internal("default2.fnt"), false);


		this.skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
		//skin.getFont("font").getData().setScale(1.5f, 1.5f);

		this.textureManager = new TextureManager();
		this.animationManager = new AnimationManager();
		this.levelMap = new LevelMap();
		this.gameStatisticGui = new GameStatisticGui();
		this.gameProgress = new GameProgress();

		//skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
		//skin.add("default-font", font);

		this.menuScreen = new MenuScreen(this, textureManager);
		this.levelScreen = new LevelScreen(this, textureManager);
		this.customizerScreen = new CustomizerScreen(this, textureManager);
		this.chooseGameTypeScreen = new ChooseGameTypeScreen(this, textureManager);

		setScreen(menuScreen);
	}

	@Override
	public void dispose() {
		//font.dispose();
		skin.dispose();

		menuScreen.dispose();

		if (gameScreen != null) {
            gameScreen.dispose();
        }

		textureManager.dispose();
		animationManager.dispose();
	}

	@Override
    public void render() {
	    super.render();

    }

    public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void newGameScreen(LevelConfiguration levelConfiguration) {
		if (gameScreen != null) {
			gameScreen.dispose();
		}
		this.currentLevel = levelConfiguration;
		this.gameScreen = new GameScreen(this, textureManager, animationManager, levelConfiguration);
	}

	public String getStyle() {
		return DEFAULT_STYLE;
	}

	public Skin getSkin() {
		return skin;
	}

	public LevelScreen getLevelScreen() {
		return levelScreen;
	}

    public LevelConfiguration getCurrentLevel() {
        return currentLevel;
    }

    public LevelMap getLevelMap() {
        return levelMap;
    }

	public int getCurrentMaxLevel() {
		return currentMaxLevel;
	}

	public int getMaxLevel() {
		return levelMap.getLevelNumbers();
	}

	public void nextLevel() {
		currentMaxLevel++;
		levelScreen.dispose();
		this.levelScreen = new LevelScreen(this, textureManager);
	}

	public GameStatisticGui getGameStatisticGui() {
		return gameStatisticGui;
	}

    public CustomizerScreen getCustomizerScreen() {
        return customizerScreen;
    }

    public ChooseGameTypeScreen getChooseGameTypeScreen() {
        return chooseGameTypeScreen;
    }

    public GameProgress getGameProgress() {
        return gameProgress;
    }
}