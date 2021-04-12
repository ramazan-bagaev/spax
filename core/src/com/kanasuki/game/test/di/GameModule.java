package com.kanasuki.game.test.di;

import com.kanasuki.game.test.GameManager;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.GameActorManager;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.texture.AnimationManager;
import com.kanasuki.game.test.texture.TextureManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = SpaxModule.class)
public class GameModule {

    private LevelConfiguration levelConfiguration;
    private final SpaxContext spaxContext;

    public GameModule(LevelConfiguration levelConfiguration, SpaxContext spaxContext) {
        this.levelConfiguration = levelConfiguration;
        this.spaxContext = spaxContext;
    }

    @Provides
    public LevelConfiguration levelConfiguration() {
        return levelConfiguration;
    }

    @Provides
    public Environment environment() {
        return new Environment(levelConfiguration);
    }

    @Provides
    public GameActorManager gameActorManager() {
        return new GameActorManager();
    }
    
    @Provides
    public GameManager gameManager(TextureManager textureManager, AnimationManager animationManager, LevelConfiguration levelConfiguration,
                                   Environment environment, GameActorManager gameActorManager) {
        return new GameManager(textureManager, animationManager, levelConfiguration, spaxContext.gameStatisticGui(), environment, gameActorManager);
    }
}
