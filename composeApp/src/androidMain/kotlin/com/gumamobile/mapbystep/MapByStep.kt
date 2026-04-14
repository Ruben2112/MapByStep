package com.gumamobile.mapbystep

import android.app.Application
import com.gumamobile.mapbystep.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class MapByStep : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@MapByStep)
            },
        )
    }
}