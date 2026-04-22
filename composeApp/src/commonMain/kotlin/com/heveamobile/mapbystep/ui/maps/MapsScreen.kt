package com.heveamobile.mapbystep.ui.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.formatStepAmount
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.MapProgress
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import mapbystep.composeapp.generated.resources.maps_active_map
import mapbystep.composeapp.generated.resources.maps_expand_progress_icon_description
import mapbystep.composeapp.generated.resources.maps_step_progress_icon_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
fun MapsContent(
    modifier: Modifier = Modifier,
    state: MapsState,
    onAction: (MapsAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        if (state.activeMap != null) {
            Card(title = stringResource(Res.string.maps_active_map)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(
                                MapsAction.ExpandProgress(
                                    state.activeMap,
                                ),
                            )
                        }
                        .padding(MaterialTheme.spacing.medium),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = state.activeMap.name,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(Res.drawable.ic_steps),
                                contentDescription = stringResource(Res.string.maps_step_progress_icon_description),
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
                            Text(
                                text = "${
                                    formatStepAmount(
                                        state.availableSteps,
                                        FormatMode.Long,
                                    )
                                } / ${
                                    formatStepAmount(
                                        state.activeMap.calculatedDistance,
                                        formatMode = FormatMode.Medium,
                                    )
                                }",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = if (state.expandedMapId == state.activeMap.id) Icons.Rounded.ArrowDropUp else Icons.Rounded.ArrowDropDown,
                            contentDescription = stringResource(Res.string.maps_expand_progress_icon_description),
                        )
                    }
                    AnimatedVisibility(visible = state.expandedMapId == state.activeMap.id) {
                        Column {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                            MapProgress(map = state.activeMap)
                        }
                    }
                }
            }
        }
    }
}