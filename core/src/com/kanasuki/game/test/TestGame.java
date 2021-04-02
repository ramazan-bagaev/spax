package com.kanasuki.game.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kanasuki.game.test.di.DaggerSpaxComponent;
import com.kanasuki.game.test.di.SpaxComponent;
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

		SpaxComponent spaxComponent = DaggerSpaxComponent.create();

		//skin.getFont("font").getData().setScale(1.5f, 1.5f);

		//skin.addRegions(new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas")));
		//skin.add("default-font", font);

		this.disposingManager = spaxComponent.disposingManager();
		ScreenManager screenManager = spaxComponent.screenManager();
		EventManager eventManager = spaxComponent.eventManager();

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