package com.kanasuki.game.test.di;

import com.kanasuki.game.test.TestGame;
import com.kanasuki.game.test.gui.GameStatisticGui;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SpaxModule {

    @Provides
    public TestGame testGame() {
        return TestGame.instance();
    }

    @Singleton
    @Provides
    public GameStatisticGui gameStatisticGui() {
        return new GameStatisticGui();
    }
}
