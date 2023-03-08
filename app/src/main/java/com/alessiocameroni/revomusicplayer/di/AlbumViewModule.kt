package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.AlbumViewRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumViewRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlbumViewModule {

    @Provides
    @Singleton
    fun provideAlbumViewRepository(
        @ApplicationContext context: Context
    ): AlbumViewRepository {
        return AlbumViewRepositoryImpl(context)
    }

}