package com.heveamobile.mapbystep.ui.directions

import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity
import com.heveamobile.mapbystep.domain.model.User

data class DirectionsState(
    val maps: List<Map> = emptyList(),
    val selectedMap: Map? = null,
    val user: User? = null,
    val cart: kotlin.collections.Map<Rarity, Int> = emptyCart,
    val totalCost: Int = 0,
    val amountInStock: kotlin.collections.Map<Rarity, Int> = emptyMap(),

    val isLoading: Boolean = false,
)

val emptyCart: kotlin.collections.Map<Rarity, Int>
    get() = mapOf(
        Pair(
            Rarity.Common,
            0,
        ),
        Pair(
            Rarity.Uncommon,
            0,
        ),
        Pair(
            Rarity.Rare,
            0,
        ),
        Pair(
            Rarity.Epic,
            0,
        ),
        Pair(
            Rarity.Legendary,
            0,
        ),
    )

sealed interface DirectionsAction {
    data object ToggleMapSelector : DirectionsAction
    data class SelectMap(val map: Map) : DirectionsAction
    data class UpdateCartAmount(
        val rarity: Rarity,
        val amount: Int,
    ) : DirectionsAction

    data object AutofillCart : DirectionsAction
    data object ResetCart : DirectionsAction
    data object Purchase : DirectionsAction
}