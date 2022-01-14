package com.aivicevic.randomusers

import android.app.Application
import com.aivicevic.randomusers.di.module.AppModules
import com.aivicevic.randomusers.di.module.DataModules
import com.aivicevic.randomusers.util.ListUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class RandomUsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val loggingLevel =
            if (BuildConfig.DEBUG) Level.ERROR else Level.NONE  // Workaround for https://github.com/InsertKoinIO/koin/issues/1188
        val moduleList = ListUtil.concatenate(AppModules, DataModules)
        startKoin {
            androidContext(this@RandomUsersApplication)
            androidLogger(loggingLevel)
            modules(moduleList)
        }
    }
}
