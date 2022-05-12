package com.example.baseapp

import android.app.Application
import com.example.baseapp.presentation.vm.useCaseModule
import com.example.baseapp.presentation.vm.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

//@HiltAndroidApp
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = listOf(useCaseModule,viewModelModule)
        startKoin {
            androidContext(this@BaseApp)
            modules(appModules)
        }
    }
}
