package com.kanasuki.game.test.di;

import com.kanasuki.game.test.texture.TextureManager;
import dagger.Module;
import dagger.Provides;

@Module
public class SpaxModule {

    @Provides
    public TextureManager textureManager() {
        return new TextureManager();
    }
}
