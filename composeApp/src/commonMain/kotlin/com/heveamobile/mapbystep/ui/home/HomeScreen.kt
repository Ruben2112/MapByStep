package com.heveamobile.mapbystep.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.heveamobile.mapbystep.navigation.Route
import com.heveamobile.mapbystep.theme.OnSurface
import com.heveamobile.mapbystep.theme.SurfaceContainerHigh
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.app_name
import mapbystep.composeapp.generated.resources.home_navigation_icon_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val observer = remember {
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onAction(HomeAction.SyncSteps)
            }
        }
    }
    LocalLifecycleOwner.current.lifecycle.addObserver(observer)

    LaunchedEffect(state.isDrawerOpen) {
        if (state.isDrawerOpen) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }

    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.isClosed && state.isDrawerOpen) {
            onAction(HomeAction.CloseNavigationDrawer)
        } else if (drawerState.isOpen && !state.isDrawerOpen) {
            onAction(HomeAction.OpenNavigationDrawer)
        }
    }

    val entryProvider = koinEntryProvider<Any>()
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(
                        Route.Profile::class,
                        Route.Profile.serializer(),
                    )
                    subclass(
                        Route.Maps::class,
                        Route.Maps.serializer(),
                    )
                    subclass(
                        Route.Destinations::class,
                        Route.Destinations.serializer(),
                    )
                    subclass(
                        Route.DestinationDetails::class,
                        Route.DestinationDetails.serializer(),
                    )
                    subclass(
                        Route.Directions::class,
                        Route.Directions.serializer(),
                    )
                    subclass(
                        Route.Settings::class,
                        Route.Settings.serializer(),
                    )
                }
            }
        },
        elements = arrayOf<NavKey>(
            Route.Profile,
        ),
    )
    ModalNavigationDrawer(
        modifier = Modifier.blur(
            if (state.visitedDestinationsState.destinations.isEmpty()) 0.dp else 8.dp,
        ),
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                onDrawerItemClicked = { route ->
                    onAction(HomeAction.CloseNavigationDrawer)
                    when (route) {
                        NavigationDrawerRoute.Profile -> backStack.add(Route.Profile)
                        NavigationDrawerRoute.Maps -> backStack.add(Route.Maps)
                        NavigationDrawerRoute.Destinations -> backStack.add(Route.Destinations)
                        NavigationDrawerRoute.DestinationDetails -> backStack.add(
                            Route.DestinationDetails(destinationId = null),
                        )
                        NavigationDrawerRoute.Directions -> backStack.add(Route.Directions)
                        NavigationDrawerRoute.Settings -> backStack.add(Route.Settings)
                    }
                },
            )
        },
        scrimColor = Color.Transparent,
    ) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.app_name),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onAction(HomeAction.OpenNavigationDrawer)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = stringResource(Res.string.home_navigation_icon_description),
                                tint = OnSurface,
                            )
                        }
                    },
                    actions = {
                        StepProgressPill(
                            isLoading = state.isLoadingSteps,
                            availableSteps = state.availableSteps,
                            requiredSteps = state.requiredSteps,
                            onTap = {
                                onAction(HomeAction.SpendSteps)
                            },
                        )
                        val currentBackStackEntry = backStack.lastOrNull()
                        if (currentBackStackEntry is Route.Destinations) {
                            IconButton(onClick = { onAction(HomeAction.ToggleDropdownMenu) }) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    contentDescription = "Show actions button",
                                )
                            }
                            DropdownMenu(
                                expanded = state.sharedDestinationsState.showDropdownMenu,
                                onDismissRequest = {
                                    onAction(HomeAction.ToggleDropdownMenu)
                                },
                            ) {
                                DropdownMenuItem(
                                    leadingIcon = {
                                        if (state.sharedDestinationsState.hideUndiscovered) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Checkmark icon",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            "Hide undiscovered",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    },
                                    onClick = {
                                        onAction(HomeAction.ToggleDropdownMenu)
                                        onAction(HomeAction.ToggleHideUndiscovered)
                                    },
                                )
                                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
                                DropdownMenuItem(
                                    leadingIcon = {
                                        if (state.sharedDestinationsState.sortingOrder == SortingOrder.Rarity) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Checkmark icon",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            "Sort by rarity",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    },
                                    onClick = {
                                        onAction(HomeAction.ToggleDropdownMenu)
                                        onAction(HomeAction.UpdateSortOrder(SortingOrder.Rarity))
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = {
                                        if (state.sharedDestinationsState.sortingOrder == SortingOrder.Alphabetical) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Checkmark icon",
                                                tint = MaterialTheme.colorScheme.onSurface,
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            "Sort alphabetically",
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    },
                                    onClick = {
                                        onAction(HomeAction.ToggleDropdownMenu)
                                        onAction(HomeAction.UpdateSortOrder(SortingOrder.Alphabetical))
                                    },
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = SurfaceContainerHigh,
                    ),
                )
            },
        ) { paddingValues ->
            NavDisplay(
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                ),
                backStack = backStack,
                entryProvider = entryProvider,
                onBack = {
                    backStack.removeLastOrNull()
                },
                transitionSpec = {
                    fadeIn(tween(400)) togetherWith fadeOut(tween(400))
                },
            )
        }
    }

    AnimatedVisibility(
        visible = state.visitedDestinationsState.destinations.isNotEmpty(),
        enter = fadeIn(tween(300)),
        exit = fadeOut(tween(300)),
    ) {
        VisitedDestinationsOverlay(
            state = state.visitedDestinationsState,
            onAction = onAction,
        )
    }
}