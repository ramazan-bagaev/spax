package com.kanasuki.game.test.di;

import com.kanasuki.game.test.TestGame;
import dagger.Component;

@Component(modules = SpaxModule.class)
public interface SpaxComponent {

    void inject(TestGame testGame);
}
