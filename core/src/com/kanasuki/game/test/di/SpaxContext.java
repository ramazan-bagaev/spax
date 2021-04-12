package com.kanasuki.game.test.di;

import com.kanasuki.game.test.event.EventManager;
import com.kanasuki.game.test.gui.GameStatisticGui;
import com.kanasuki.game.test.management.DisposingManager;
import com.kanasuki.game.test.management.ScreenManager;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = SpaxModule.class)
public interface SpaxContext {

    DisposingManager disposingManager();

    @Singleton
    ScreenManager screenManager();

    @Singleton
    EventManager eventManager();

    @Singleton
    GameStatisticGui gameStatisticGui();

    @Singleton
    SpaxContext spaxContext();
}
