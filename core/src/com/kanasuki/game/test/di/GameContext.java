package com.kanasuki.game.test.di;

import com.kanasuki.game.test.GameManager;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = GameModule.class)
public interface GameContext {

    GameManager gameManager();
}
