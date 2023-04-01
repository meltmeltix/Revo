package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.PlaylistsRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.PlaylistsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaylistsModule {

    @Provides
    @Singleton
    fun providePlaylistRepository(
        @ApplicationContext context: Context
    ): PlaylistsRepository {
        return PlaylistsRepositoryImpl(context)
    }

}