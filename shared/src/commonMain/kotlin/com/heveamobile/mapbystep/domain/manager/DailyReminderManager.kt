package com.heveamobile.mapbystep.domain.manager

import kotlinx.datetime.LocalTime

interface DailyReminderManager {
    fun scheduleDailyReminderNotification(time: LocalTime)
    fun cancelDailyReminderNotification()
}