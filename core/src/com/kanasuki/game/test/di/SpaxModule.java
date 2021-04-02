package com.kanasuki.game.test.di;

import com.kanasuki.game.test.TestGame;
import dagger.Module;
import dagger.Provides;

@Module
public class SpaxModule {

    @Provides
    public TestGame testGame() {
        return TestGame.instance();
    }
}
