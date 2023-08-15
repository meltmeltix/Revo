package com.meltix.revo.di

import android.content.Context
import com.meltix.revo.data.repository.AlbumViewRepositoryImpl
import com.meltix.revo.domain.repository.AlbumViewRepository
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