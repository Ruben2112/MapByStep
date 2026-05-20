package com.heveamobile.mapbystep.ui.maps

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.formatAmount
import com.heveamobile.mapbystep.theme.RarityCommon
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.MapStatisticsList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MapsScreen(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<MapsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MapsContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
private fun MapsContent(
    modifier: Modifier = Modifier,
    state: MapsState,
    onAction: (MapsAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        if (state.maps.isNotEmpty()) {
            state.maps.forEach { map ->
                Card(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            onAction(
                                MapsAction.ExpandProgress(map),
                            )
                        },
                    title = map.name,
                    subtitle = "Level ${map.currentLevel}",
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.medium),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = "Steps per visit:",
                                style = MaterialTheme.typography.bodyMedium.copy(color = RarityCommon),
                            )
                            Text(
                                text = formatAmount(
                                    map.calculatedDistance,
                                    FormatMode.Long,
                                ),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Spacer(
                                modifier = Modifier.width(MaterialTheme.spacing.large),
                            )
                        }
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                        MapStatisticsList(
                            map = map,
                            isExpanded = state.expandedMapId == map.id,
                        )
                    }
                }
            }
        }
    }
}