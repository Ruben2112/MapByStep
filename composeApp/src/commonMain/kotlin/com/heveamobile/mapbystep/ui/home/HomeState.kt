package com.heveamobile.mapbystep.ui.home

import com.heveamobile.mapbystep.domain.model.Destination

data class HomeState(
    val isDrawerOpen: Boolean = false,
    val availableSteps: Long = 0L,
    val requiredSteps: Long = 0L,

    val isLoadingSteps: Boolean = false,
    val visitedDestinationsState: VisitedDestinationsState = VisitedDestinationsState(),
)

data class VisitedDestinationsState(
    // TODO: Prepopulated for testing purposes
//    val destinations: List<Destination> = listOf(
//        Destination(
//            id = "1",
//            mapId = "1",
//            name = "Common destination 1",
//            rarity = Rarity.Common,
//        ),
//        Destination(
//            id = "2",
//            mapId = "1",
//            name = "Rare destination 2",
//            rarity = Rarity.Rare,
//        ),
//        Destination(
//            id = "3",
//            mapId = "1",
//            name = "Legendary destination 2",
//            rarity = Rarity.Legendary,
//        ),
//    ),
    val destinations: List<Destination> = emptyList(),
    val revealedDestinations: List<Destination> = emptyList(),
    val isSingleCardLayout: Boolean = true,
    val revealingAll: Boolean = false,
)

sealed interface HomeAction {
    data object OpenNavigationDrawer : HomeAction
    data object CloseNavigationDrawer : HomeAction
    data object SyncSteps : HomeAction
    data object SpendSteps : HomeAction

    data class RevealDestination(val destination: Destination) : HomeAction
    data object RevealAllDestinations : HomeAction
    data object CloseVisitedDestinations : HomeAction
    data object CloseSingleCardLayout : HomeAction
    data class OpenSingleCardLayout(val destination: Destination) : HomeAction
}
