package com.heveamobile.mapbystep.ui.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.DestinationCard
import com.heveamobile.mapbystep.ui.common.DropDownMenu
import com.heveamobile.mapbystep.ui.common.MapStatisticsList
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DestinationsScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<DestinationsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    DestinationsContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun DestinationsContent(
    modifier: Modifier = Modifier,
    state: DestinationsState,
    onAction: (DestinationsAction) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        if (state.maps.isNotEmpty()) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                DropDownMenu(
                    items = state.maps,
                    selectedItem = state.selectedMap
                        ?: state.maps.first(),
                    onItemSelected = { map -> onAction(DestinationsAction.SelectMap(map)) },
                    itemContent = { map ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.alpha(if (map.isActive) 1F else 0F),
                                painter = painterResource(Res.drawable.ic_steps),
                                contentDescription = "Map is Active",
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                            Text(
                                modifier = Modifier.weight(1F),
                                text = map.name,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    },
                )
            }
        }

        if (state.selectedMap != null) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            onAction(DestinationsAction.ToggleProgressDisplay)
                        },
                    bottomContent = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = MaterialTheme.spacing.small,
                                ),
                        ) {
                            Text(
                                "Total visits",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Text(
                                text = state.selectedMap.destinations
                                    .sumOf { it.visits }
                                    .toString(),
                                modifier = Modifier.weight(1F),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium),
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Current level:",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                            Text(
                                text = state.selectedMap.currentLevel.toString(),
                                modifier = Modifier.weight(1F),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        MapStatisticsList(
                            map = state.selectedMap,
                            isExpanded = state.isProgressExpanded,
                        )
                    }
                }
            }

            items(
                state.selectedMap.destinations.sortedWith(
                    compareBy(
                        { it.rarity.intValue },
                        { it.name },
                    ),
                ),
                key = { it.id },
            ) { destination ->
                DestinationCard(
                    destination = destination,
                    isRevealed = destination.isRevealed,
                    raritySpoiler = true,
                    onClick = {
                        // TODO: Show destination details
                    },
                )
            }
        }
    }
}