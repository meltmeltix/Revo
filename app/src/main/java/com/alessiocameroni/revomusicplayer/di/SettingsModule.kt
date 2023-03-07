package com.alessiocameroni.revomusicplayer.di

import android.content.Context
import com.alessiocameroni.revomusicplayer.data.repository.SettingsRepositoryImpl
import com.alessiocameroni.revomusicplayer.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext context: Context
    ): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }

}