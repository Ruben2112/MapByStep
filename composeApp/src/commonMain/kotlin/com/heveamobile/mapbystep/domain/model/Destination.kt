package com.heveamobile.mapbystep.domain.model

enum class Rarity(
    val intValue: Int,
) {
    Common(1),
    Uncommon(2),
    Rare(3),
    Epic(4),
    Legendary(5);

    companion object {
        fun fromInt(value: Int): Rarity {
            return entries.find { it.intValue == value }
                ?: Common
        }
    }
}

data class Destination(
    val id: String,
    val mapId: String,
    val name: String,
    val rarity: Rarity,
    var isDiscovered: Boolean = false,
    var isNew: Boolean = false,
    var visits: Int = 0,
    var isRevealed: Boolean = false,
)