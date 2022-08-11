package com.cren90.android.common.inject.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class CoreModule {

    @Binds
    @Singleton
    abstract fun provideContext(@ApplicationContext context: Context): Context
}