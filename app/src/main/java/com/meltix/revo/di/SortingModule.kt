package com.meltix.revo.di

import android.content.Context
import com.meltix.revo.data.repository.SortingRepositoryImpl
import com.meltix.revo.domain.repository.SortingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SortingModule {

    @Provides
    @Singleton
    fun provideSortingRepository(
        @ApplicationContext context: Context
    ): SortingRepository {
        return SortingRepositoryImpl(context)
    }

}