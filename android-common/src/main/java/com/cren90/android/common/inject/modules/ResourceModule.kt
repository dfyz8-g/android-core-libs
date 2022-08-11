package com.cren90.android.common.inject.modules

import com.cren90.android.common.providers.resources.*
import com.cren90.android.common.providers.system.AndroidSystemProvider
import com.cren90.android.common.providers.system.DateFormatProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module(includes = [CoreModule::class])
@InstallIn(SingletonComponent::class)
abstract class ResourceModule {

    @Binds
    @Singleton
    abstract fun provideColorProvider(provider: AndroidResourceProvider): ColorProvider

    @Binds
    @Singleton
    abstract fun provideDrawableProvider(provider: AndroidResourceProvider): DrawableProvider

    @Binds
    @Singleton
    abstract fun provideDimenProvider(provider: AndroidResourceProvider): DimenProvider

    @Binds
    @Singleton
    abstract fun provideIntegerProvider(provider: AndroidResourceProvider): IntegerProvider

    @Binds
    @Singleton
    abstract fun provideLayoutManagerProvider(provider: AndroidResourceProvider): LayoutManagerProvider

    @Binds
    @Singleton
    abstract fun provideStringProvider(provider: AndroidResourceProvider): StringProvider

    @Binds
    @Singleton
    abstract fun provideBooleanProvider(provider: AndroidResourceProvider): BooleanProvider

    @Binds
    @Singleton
    abstract fun provideDateProvider(provider: AndroidSystemProvider): DateFormatProvider

}