package com.heveamobile.mapbystep.data.source.local

import androidx.room.TypeConverter
import com.heveamobile.mapbystep.data.entity.Rarity

class RarityConverters {
    @TypeConverter
    fun intToRarity(value: Int): Rarity {
        return when (value) {
            1 -> Rarity.Common
            2 -> Rarity.Uncommon
            3 -> Rarity.Rare
            4 -> Rarity.Epic
            5 -> Rarity.Legendary
            else -> throw IllegalArgumentException("Invalid Rarity value: $value")
        }
    }

    @TypeConverter
    fun rarityToInt(rarity: Rarity): Int {
        return when (rarity) {
            Rarity.Common -> 1
            Rarity.Uncommon -> 2
            Rarity.Rare -> 3
            Rarity.Epic -> 4
            Rarity.Legendary -> 5
        }
    }
}