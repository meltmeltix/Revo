package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.AlbumsRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.AlbumsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlbumsModule {

    @Provides
    @Singleton
    fun provideAlbumsRepository(
        @ApplicationContext context: Context
    ): AlbumsRepository {
        return AlbumsRepositoryImpl(context)
    }

}