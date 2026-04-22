package com.heveamobile.mapbystep.data.source.local

import androidx.room.TypeConverter
import com.heveamobile.mapbystep.data.entity.InfoType

class InfoTypeConverters {
    @TypeConverter
    fun infoTypeToString(value: InfoType): String {
        return value.name
    }

    @TypeConverter
    fun stringToInfoType(value: String): InfoType {
        return InfoType.valueOf(value)
    }
}