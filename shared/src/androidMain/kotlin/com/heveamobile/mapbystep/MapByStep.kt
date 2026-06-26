package com.heveamobile.mapbystep

import android.app.Application
import com.heveamobile.mapbystep.di.initializeKoin
import com.heveamobile.mapbystep.platform.manager.NotificationManager
import org.koin.android.ext.koin.androidContext

class MapByStep : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@MapByStep)
            },
        )
        NotificationManager.createDailyReminderNotificationChannel(this)
    }
}