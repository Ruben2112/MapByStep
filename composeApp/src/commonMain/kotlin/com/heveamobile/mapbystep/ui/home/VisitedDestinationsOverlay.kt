package com.heveamobile.mapbystep.ui.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Info
import com.heveamobile.mapbystep.theme.OnTertiaryContainer
import com.heveamobile.mapbystep.theme.color
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.CountryInfoCard
import com.heveamobile.mapbystep.ui.common.DestinationCard
import com.heveamobile.mapbystep.ui.common.PrimaryButton
import kotlinx.coroutines.launch
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource

@Composable
fun VisitedDestinationsOverlay(
    modifier: Modifier = Modifier,
    state: VisitedDestinationsState,
    onAction: (HomeAction) -> Unit,
) {
    val destinations = state.destinations
    val revealedDestinations = state.destinations.filter { it.isRevealed }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { destinations.size },
    )
    val currentDestination = destinations.getOrNull(pagerState.currentPage)
    val currentRarityColor =
        if (state.isSingleCardLayout && currentDestination?.isRevealed == true) {
            currentDestination.rarity.color.copy(alpha = 0.25F)
        } else {
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.75F)
        }

    val backgroundColor = remember { Animatable(currentRarityColor) }

    LaunchedEffect(
        currentDestination,
        revealedDestinations,
        state.isSingleCardLayout,
    ) {
        backgroundColor.animateTo(
            currentRarityColor,
            tween(300),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor.value)
            .pointerInput(Unit) {
                detectTapGestures {
                    /* Consumes tap events so they don't reach the UI behind */
                }
            },
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxHeight(),
            targetState = state.isSingleCardLayout,
            transitionSpec = {
                // Smooth fade when opening/closing the detail view
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
        ) { isSingleCardLayout ->
            if (isSingleCardLayout && currentDestination != null) {
                SingleCardLayout(
                    pagerState = pagerState,
                    destinations = destinations,
                    showInfo = state.showInfo,
                    onAction = onAction,
                )
            } else {
                GridLayout(
                    pagerState = pagerState,
                    destinations = destinations,
                    onAction = onAction,
                )
            }
        }

        val showRevealAllButton = !state.isSingleCardLayout && destinations.any { !it.isRevealed }

        AnimatedVisibility(
            visible = !showRevealAllButton,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = MaterialTheme.spacing.large,
                    ),
                contentAlignment = Alignment.BottomCenter,
            ) {
                FloatingActionButton(
                    onClick = {
                        if (state.showInfo) {
                            onAction(HomeAction.ToggleDestinationInfo)
                        } else if (state.isSingleCardLayout) {
                            onAction(HomeAction.CloseSingleCardLayout)
                        } else {
                            onAction(HomeAction.CloseVisitedDestinations)
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close screen",
                        modifier = Modifier.size(MaterialTheme.spacing.large),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = showRevealAllButton,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = MaterialTheme.spacing.large,
                    ),
                contentAlignment = Alignment.BottomCenter,
            ) {
                PrimaryButton(
                    label = "Reveal all",
                    onClick = { onAction(HomeAction.RevealAllDestinations) },
                )
            }
        }
    }
}

@Composable
private fun SingleCardLayout(
    pagerState: PagerState,
    destinations: List<Destination>,
    showInfo: Boolean,
    onAction: (HomeAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = !showInfo,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = !showInfo,
                    beyondViewportPageCount = 1,
                ) { index ->
                    val destination = destinations[index]

                    Box {
                        DestinationCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = MaterialTheme.spacing.extraLarge,
                                ),
                            destination = destination,
                            isRevealed = destination.isRevealed,
                            isNew = destination.isNew,
                            mapPointsGained = destination.mapPointsGained,
                            onClick = {
                                if (!destination.isRevealed) {
                                    onAction(HomeAction.RevealDestination(destination))
                                } else {
                                    onAction(HomeAction.ToggleDestinationInfo)
                                }
                            },
                            isLarge = true,
                        )
                    }
                }

                // Navigation Overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .rotate(90F)
                            .graphicsLayer {
                                rotationX = 180F
                            }
                            .alpha(if (pagerState.currentPage == 0) 0F else 1F),
                        onClick = {
                            if (pagerState.currentPage > 0) scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage - 1,
                                )
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_steps),
                            contentDescription = "Previous",
                            modifier = Modifier.size(MaterialTheme.spacing.large),
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .rotate(90F)
                            .alpha(if (pagerState.currentPage == destinations.size - 1) 0F else 1F),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                )
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_steps),
                            contentDescription = "Next",
                            modifier = Modifier.size(MaterialTheme.spacing.large),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            AnimatedContent(targetState = destinations[pagerState.currentPage].isRevealed) { isRevealed ->
                Box(contentAlignment = Alignment.Center) {
                    PrimaryButton(
                        modifier = Modifier.alpha(
                            if (isRevealed) 1F else 0F,
                        ),
                        label = "Show info",
                        onClick = {
                            scope.launch {
                                onAction(HomeAction.ToggleDestinationInfo)
                            }
                        },
                    )
                    Text(
                        modifier = Modifier.alpha(
                            if (!isRevealed) 1F else 0F,
                        ),
                        text = "Tap card to reveal",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = OnTertiaryContainer,
                        ),
                    )
                }
            }
        }
    }
    AnimatedVisibility(
        visible = showInfo,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val destination = destinations[pagerState.currentPage]
            if (destination.info is Info.CountryInfo) {
                CountryInfoCard(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    destination = destination,
                )
            }
        }
    }
}

@Composable
private fun GridLayout(
    pagerState: PagerState,
    destinations: List<Destination>,
    onAction: (HomeAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
            ),
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(MaterialTheme.spacing.large),
            )
        }
        items(destinations) { destination ->
            DestinationCard(
                destination = destination,
                isRevealed = destination.isRevealed,
                isNew = destination.isNew,
                onClick = {
                    if (destination.isRevealed) {
                        scope.launch {
                            pagerState.scrollToPage(destinations.indexOf(destination))
                        }
                        onAction(HomeAction.OpenSingleCardLayout(destination))

                    } else {
                        onAction(HomeAction.RevealDestination(destination))
                    }
                },
            )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            PrimaryButton(
                modifier = Modifier
                    .padding(
                        bottom = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.medium,
                    )
                    .alpha(0F),
                label = "",
                onClick = { },
            )
        }
    }
}