package com.example.base.di

import com.example.base.DispatchersProvider
import com.example.base.Dispatcherss
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@InstallIn(SingletonComponent::class)
//@Module
/*class LocaleModules {

  //  @Provides
  //  @Singleton
    fun provideDispatchers(): DispatchersProvider = Dispatcherss()
}*/

fun provideDispatchers(): DispatchersProvider = Dispatcherss()
