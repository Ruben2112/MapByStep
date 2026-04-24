package com.heveamobile.mapbystep.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import mapbystep.composeapp.generated.resources.home_sync_icon_description
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
                }
            }
        },
        elements = arrayOf<NavKey>(
            Route.Profile,
        ),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                onDrawerItemClicked = { route ->
                    onAction(HomeAction.CloseNavigationDrawer)
                    when (route) {
                        NavigationDrawerRoute.Profile -> backStack.add(Route.Profile)
                        NavigationDrawerRoute.Maps -> backStack.add(Route.Maps)
                        NavigationDrawerRoute.MapProgress -> TODO()
                        NavigationDrawerRoute.Directions -> TODO()
                        NavigationDrawerRoute.Destinations -> TODO()
                        NavigationDrawerRoute.DestinationDetails -> TODO()
                        NavigationDrawerRoute.Settings -> TODO()
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
                        IconButton(onClick = { onAction(HomeAction.SyncSteps) }) {
                            Icon(
                                imageVector = Icons.Filled.Sync,
                                contentDescription = stringResource(Res.string.home_sync_icon_description),
                                tint = OnSurface,
                            )
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
            )
        }
    }
}