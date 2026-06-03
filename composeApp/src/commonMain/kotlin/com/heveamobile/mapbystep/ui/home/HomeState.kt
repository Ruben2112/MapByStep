package com.heveamobile.mapbystep.ui.home

import com.heveamobile.mapbystep.domain.model.Destination

data class HomeState(
    val isLoadingSteps: Boolean = false,

    val isDrawerOpen: Boolean = false,
    val availableSteps: Long = 0L,
    val requiredSteps: Long = 0L,

    val visitedDestinationsState: VisitedDestinationsState = VisitedDestinationsState(),

    val sharedDestinationsState: SharedDestinationsState = SharedDestinationsState(),
)

data class VisitedDestinationsState(
    val destinations: List<Destination> = emptyList(),
    val destinationShown: Destination? = null,
    val revealingAll: Boolean = false,
    val mapPointsGained: Int = 0,
    val showResultSummary: Boolean = false,
)

enum class SortingOrder {
    Rarity,
    Alphabetical,
    VisitCount,
}

data class SharedDestinationsState(
    val showDropdownMenu: Boolean = false,
    val hideUndiscovered: Boolean = false,
    val sortingOrder: SortingOrder = SortingOrder.Rarity,
)

sealed interface HomeAction {
    data object OpenNavigationDrawer : HomeAction
    data object CloseNavigationDrawer : HomeAction
    data object SyncSteps : HomeAction
    data object SpendSteps : HomeAction

    data class RevealDestination(val destination: Destination) : HomeAction
    data object RevealAllDestinations : HomeAction
    data object CloseVisitedDestinations : HomeAction
    data class ToggleDestinationInfo(val destination: Destination?) : HomeAction

    data object ToggleDropdownMenu : HomeAction
    data object ToggleHideUndiscovered : HomeAction
    data class UpdateSortOrder(val sortOrder: SortingOrder) : HomeAction
}
