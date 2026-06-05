package com.heveamobile.mapbystep.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SliderDefaults.CenteredTrack
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.InfoCard
import mapbystep.shared.generated.resources.Res
import mapbystep.shared.generated.resources.settings_distance_multiplier
import mapbystep.shared.generated.resources.settings_distance_multiplier_explanation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(
    KoinExperimentalAPI::class,
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {
    val sliderState = rememberSliderState(
        value = state.distanceMultiplier.toFloat() - 1F,
        valueRange = -1F..1F,
        steps = 19,
    )
    sliderState.onValueChangeFinished = {
        onAction(SettingsAction.UpdateDistanceMultiplier(sliderState.value + 1F))
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
    ) {
        Card {
            Column {
                InfoCard(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .padding(top = MaterialTheme.spacing.medium),
                    text = stringResource(
                        Res.string.settings_distance_multiplier_explanation,
                    ),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
                ) {
                    Text(
                        text = stringResource(Res.string.settings_distance_multiplier),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Row(
                        modifier = Modifier.weight(1F),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Slider(
                            modifier = Modifier.weight(1F),
                            state = sliderState,
                            thumb = {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.colorScheme.onSurface),
                                    contentAlignment = Alignment.Center,
                                ) {}
                            },
                            track = { state ->
                                CenteredTrack(
                                    modifier = Modifier.height(8.dp),
                                    colors = SliderDefaults
                                        .colors()
                                        .copy(
                                            activeTrackColor = MaterialTheme.colorScheme.onSurface,
                                            inactiveTrackColor = MaterialTheme.colorScheme.onTertiaryContainer,
                                            activeTickColor = Color.Transparent,
                                            inactiveTickColor = Color.Transparent,
                                        ),
                                    drawStopIndicator = {},
                                    thumbTrackGapSize = 0.dp,
                                    sliderState = state,
                                )
                            },
                        )
                        Text(
                            modifier = Modifier.width(40.dp),
                            text = "${state.distanceMultiplier}x",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}