package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.SongsRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.SongsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SongsModule {

    @Provides
    @Singleton
    fun provideSongsRepository(
        @ApplicationContext context: Context
    ): SongsRepository {
        return SongsRepositoryImpl(context)
    }

}