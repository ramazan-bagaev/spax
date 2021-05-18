package com.kanasuki.game.test.di;

import com.kanasuki.game.test.GameManager;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.GameActorField;
import com.kanasuki.game.test.actor.GameActorManager;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.management.ActManager;
import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.TextureManager;
import com.kanasuki.game.test.utils.WinningConditionChecker;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module(includes = SpaxModule.class)
public class GameModule {

    private LevelConfiguration levelConfiguration;
    private final SpaxContext spaxContext;

    public GameModule(LevelConfiguration levelConfiguration, SpaxContext spaxContext) {
        this.levelConfiguration = levelConfiguration;
        this.spaxContext = spaxContext;
    }

    @Singleton
    @Provides
    public LevelConfiguration levelConfiguration() {
        return levelConfiguration;
    }

    @Singleton
    @Provides
    public Environment environment() {
        return new Environment(levelConfiguration);
    }

    @Singleton
    @Provides
    public GameActorManager gameActorManager() {
        return new GameActorManager();
    }

    @Singleton
    @Provides
    public GameManager gameManager(TextureManager textureManager, AnimationManager animationManager, LevelConfiguration levelConfiguration,
                                   Environment environment, GameActorManager gameActorManager, WinningConditionChecker winningConditionChecker,
                                   ActManager actManager) {
        return new GameManager(textureManager, animationManager, levelConfiguration, spaxContext.gameStatisticGui(),
                environment, gameActorManager, winningConditionChecker, actManager);
    }

    @Singleton
    @Provides
    public ActManager actManager(GameActorManager gameActorManager, GameActorField gameActorField, Environment environment) {
        return new ActManager(gameActorField, gameActorManager, environment);
    }
}
