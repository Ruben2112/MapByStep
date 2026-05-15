package com.heveamobile.mapbystep.ui.directions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity
import com.heveamobile.mapbystep.domain.usecase.GetCountOfDirectionsInStockUseCase
import com.heveamobile.mapbystep.domain.usecase.GetMapsWithProgressUseCase
import com.heveamobile.mapbystep.domain.usecase.GetUserUseCase
import com.heveamobile.mapbystep.domain.usecase.PurchaseDirectionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DirectionsViewModel(
    private val getMapsWithProgressUseCase: GetMapsWithProgressUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getCountOfDirectionsInStock: GetCountOfDirectionsInStockUseCase,
    private val purchaseDirectionsUseCase: PurchaseDirectionsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DirectionsState())
    val state: StateFlow<DirectionsState> = _state.asStateFlow()

    init {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            getUserUseCase().collect { user ->
                _state.update { it.copy(user = user) }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getMapsWithProgressUseCase().collect { maps ->
                val selected = maps.firstOrNull { it.isActive }
                _state.update { state ->
                    state.copy(
                        maps = maps,
                        selectedMap = selected,
                        amountInStock = Rarity.entries.associateWith {
                            if (selected != null) getCountOfDirectionsInStock(
                                selected,
                                it,
                            ) else 0
                        },
                    )
                }
            }

            viewModelScope.launch(Dispatchers.IO) {
                getUserUseCase().first()
                getMapsWithProgressUseCase().first()

                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onAction(action: DirectionsAction) {
        when (action) {
            is DirectionsAction.AutofillCart -> {
                _state.update { state ->
                    val map = state.selectedMap
                        ?: return@update state

                    val newCart = Rarity.entries.associateWith { rarity ->
                        getCountOfDirectionsInStock(
                            map,
                            rarity,
                        )
                    }

                    state.copy(
                        cart = newCart,
                        totalCost = calculateTotalCost(
                            map,
                            newCart,
                        ),
                    )
                }
            }

            is DirectionsAction.Purchase -> {
                val currentState = _state.value
                val map = currentState.selectedMap

                if (map == null || currentState.totalCost == 0 || map.currentMapPoints < currentState.totalCost) return

                viewModelScope.launch(Dispatchers.IO) {
                    _state.value.selectedMap?.let { map ->
                        purchaseDirectionsUseCase(
                            map = map,
                            cart = currentState.cart,
                            cost = currentState.totalCost,
                        )
                    }
                }

                _state.update { state ->
                    state.copy(
                        cart = emptyCart,
                        totalCost = 0,
                    )
                }
            }

            is DirectionsAction.ResetCart -> {
                _state.update { state ->
                    state.copy(
                        cart = emptyCart,
                        totalCost = 0,
                    )
                }
            }

            is DirectionsAction.SelectMap -> TODO()
            is DirectionsAction.ToggleMapSelector -> TODO()
            is DirectionsAction.UpdateCartAmount -> {
                _state.update { state ->
                    val correctedAmount = coerceAmount(
                        action.amount,
                        action.rarity,
                    )
                    val newCart = state.cart
                        .toMutableMap()
                        .apply {
                            this[action.rarity] = correctedAmount
                        }

                    state.copy(
                        cart = newCart,
                        totalCost = calculateTotalCost(
                            state.selectedMap,
                            newCart,
                        ),
                    )
                }
            }
        }
    }

    private fun calculateTotalCost(
        selectedMap: Map?,
        cart: kotlin.collections.Map<Rarity, Int>,
    ): Int {
        if (selectedMap == null) return 0
        return cart.entries.sumOf { (rarity, amount) ->
            amount * selectedMap.storePrice(rarity)
        }
    }

    private fun coerceAmount(
        amount: Int,
        rarity: Rarity,
    ): Int {
        val map = _state.value.selectedMap
            ?: return 0
        val maxAllowed = getCountOfDirectionsInStock(
            map,
            rarity,
        )
        return amount.coerceIn(
            0,
            maxAllowed,
        )
    }
}