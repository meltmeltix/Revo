package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.ArtistViewRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.ArtistViewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArtistViewModule {

    @Provides
    @Singleton
    fun provideArtistViewRepository(
        @ApplicationContext context: Context
    ): ArtistViewRepository {
        return ArtistViewRepositoryImpl(context)
    }

}