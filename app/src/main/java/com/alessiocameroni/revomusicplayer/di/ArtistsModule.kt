package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.ArtistsRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtistsModule {

    @Provides
    @Singleton
    fun provideArtistsRepository(
        @ApplicationContext context: Context
    ): ArtistsRepository {
        return ArtistsRepositoryImpl(context)
    }

}