package com.gumamobile.mapbystep.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.gumamobile.mapbystep.navigation.Route
import com.gumamobile.mapbystep.theme.SurfaceContainerHigh
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.navigation3.koinEntryProvider
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
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
                    scope.launch {
                        drawerState.close()
                    }
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
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Navigation drawer icon",
                            )
                        }
                    },
                    actions = {},
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