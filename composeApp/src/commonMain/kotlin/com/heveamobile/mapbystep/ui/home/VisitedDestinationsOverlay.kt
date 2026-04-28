package com.heveamobile.mapbystep.ui.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
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
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.theme.OnTertiaryContainer
import com.heveamobile.mapbystep.theme.color
import com.heveamobile.mapbystep.theme.spacing
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
    val revealedDestinations = state.revealedDestinations

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { destinations.size },
    )
    val currentDestination = destinations.getOrNull(pagerState.currentPage)
    val currentRarityColor =
        if (state.isSingleCardLayout && currentDestination?.isRevealed(revealedDestinations) == true) {
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
            .background(backgroundColor.value),
    ) {
        // Unified AnimatedContent to handle Grid <-> Detail transitions
        AnimatedContent(
            modifier = Modifier.fillMaxHeight(),
            targetState = state.isSingleCardLayout,
            transitionSpec = {
                // Smooth fade when opening/closing the detail view
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
        ) { targetIndex ->
            if (state.isSingleCardLayout && currentDestination != null) {
                SingleCardLayout(
                    pagerState = pagerState,
                    destinations = destinations,
                    revealedDestinations = revealedDestinations,
                    onAction = onAction,
                )
            } else {
                GridLayout(
                    pagerState = pagerState,
                    destinations = destinations,
                    revealedDestinations = revealedDestinations,
                    onAction = onAction,
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            if (state.isSingleCardLayout) {
                PrimaryButton(
                    modifier = Modifier.padding(MaterialTheme.spacing.large),
                    label = "Close",
                    onClick = { onAction(HomeAction.CloseSingleCardLayout) },
                )
            } else {
                if (destinations.size != revealedDestinations.size) {
                    PrimaryButton(
                        modifier = Modifier.padding(MaterialTheme.spacing.large),
                        label = "Reveal all",
                        onClick = { onAction(HomeAction.RevealAllDestinations) },
                    )
                } else {
                    PrimaryButton(
                        modifier = Modifier.padding(MaterialTheme.spacing.large),
                        label = "Close",
                        onClick = { onAction(HomeAction.CloseVisitedDestinations) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SingleCardLayout(
    pagerState: PagerState,
    destinations: List<Destination>,
    revealedDestinations: List<Destination>,
    onAction: (HomeAction) -> Unit,
) {
    val scope = rememberCoroutineScope()

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
                beyondViewportPageCount = 1,
            ) { index ->
                val destination = destinations[index]
                val isRevealed = destination.isRevealed(revealedDestinations)

                DestinationCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = MaterialTheme.spacing.extraLarge,
                        ),
                    destination = destination,
                    isRevealed = isRevealed,
                    isNew = destination.isNew,
                    onClick = {
                        onAction(HomeAction.RevealDestination(destination))
                    },
                    isLarge = true,
                )
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

                if (pagerState.currentPage < destinations.size - 1) {
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
                } else {
                    // Spacer to maintain Row symmetry when Next button is hidden
                    Box(
                        modifier = Modifier.size(
                            MaterialTheme.spacing.large + MaterialTheme.spacing.medium + MaterialTheme.spacing.small,
                        ),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(
            modifier = Modifier.alpha(
                if (destinations[pagerState.currentPage].isRevealed(revealedDestinations)) 0F else 1F,
            ),
            text = "Tap card to reveal",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = OnTertiaryContainer,
            ),
        )
    }
}

@Composable
private fun GridLayout(
    pagerState: PagerState,
    destinations: List<Destination>,
    revealedDestinations: List<Destination>,
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
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
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
                isRevealed = revealedDestinations.contains(destination),
                isNew = destination.isNew,
                onClick = {
                    if (revealedDestinations.contains(destination)) {
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

private fun Destination.isRevealed(revealedDestinations: List<Destination>): Boolean {
    return revealedDestinations.contains(this)
}