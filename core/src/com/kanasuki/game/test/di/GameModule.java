package com.kanasuki.game.test.di;

import com.kanasuki.game.test.GameManager;
import com.kanasuki.game.test.actor.Environment;
import com.kanasuki.game.test.actor.GameActorField;
import com.kanasuki.game.test.actor.GameActorManager;
import com.kanasuki.game.test.level.LevelConfiguration;
import com.kanasuki.game.test.management.action.ActionManager;
import com.kanasuki.game.test.management.action.EnemyActionManager;
import com.kanasuki.game.test.management.action.TemporaryAnimatedGameActorActionManager;
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
                                   ActionManager actionManager) {
        return new GameManager(textureManager, animationManager, levelConfiguration, spaxContext.gameStatisticGui(),
                environment, gameActorManager, winningConditionChecker, actionManager);
    }

    @Singleton
    @Provides
    public ActionManager actManager(GameActorManager gameActorManager, EnemyActionManager enemyActionManager,
                                    TemporaryAnimatedGameActorActionManager temporaryAnimatedGameActorActionManager) {
        ActionManager actionManager = new ActionManager(gameActorManager);

        actionManager.addGameActorActionManager(enemyActionManager);
        actionManager.addGameActorActionManager(temporaryAnimatedGameActorActionManager);

        return actionManager;
    }

    @Singleton
    @Provides
    public EnemyActionManager enemyActionManager(Environment environment, GameActorField gameActorField) {
        return new EnemyActionManager(environment, gameActorField);
    }

    @Singleton
    @Provides
    public TemporaryAnimatedGameActorActionManager temporaryAnimatedGameActorActionManager(GameActorManager gameActorManager) {
        return new TemporaryAnimatedGameActorActionManager(gameActorManager);
    }
}
