package com.kanasuki.game.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kanasuki.game.test.di.DaggerSpaxContext;
import com.kanasuki.game.test.di.SpaxContext;
import com.kanasuki.game.test.event.EventManager;
import com.kanasuki.game.test.management.DisposingManager;
import com.kanasuki.game.test.management.ScreenManager;

public class TestGame extends Game {

	private static final TestGame INSTANCE = new TestGame();

	private BitmapFont font;

	private DisposingManager disposingManager;

	@Override
	public void create() {
		//this.font = new BitmapFont(Gdx.files.internal("default2.fnt"), false);

		SpaxContext spaxContext = DaggerSpaxContext.create();

		//skin.getFont("font").getData().setScale(1.5f, 1.5f);

		//skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
		//skin.add("default-font", font);

		this.disposingManager = spaxContext.disposingManager();
		ScreenManager screenManager = spaxContext.screenManager();
		EventManager eventManager = spaxContext.eventManager();

		eventManager.addListener(screenManager);

		screenManager.init();

		setScreen(screenManager.getMenuScreen());
	}

	@Override
	public void dispose() {
		//font.dispose();

		disposingManager.dispose();
	}

	@Override
    public void render() {
	    super.render();
    }

    public static TestGame instance() {
		return INSTANCE;
	}
}