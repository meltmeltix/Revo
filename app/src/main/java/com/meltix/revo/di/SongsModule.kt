package com.meltix.revo.di

import android.content.Context
import com.meltix.revo.data.repository.SongsRepositoryImpl
import com.meltix.revo.domain.repository.SongsRepository
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