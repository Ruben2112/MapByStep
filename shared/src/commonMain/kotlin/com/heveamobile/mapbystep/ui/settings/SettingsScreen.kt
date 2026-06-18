package com.heveamobile.mapbystep.ui.settings

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.AlertDialog
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.FilePickerHandlerEffect
import com.heveamobile.mapbystep.ui.common.InfoCard
import com.heveamobile.mapbystep.ui.common.PrimaryButton
import com.heveamobile.mapbystep.ui.home.LocalSnackbarHostState
import kotlinx.coroutines.flow.collectLatest
import mapbystep.shared.generated.resources.Res
import mapbystep.shared.generated.resources.cancel
import mapbystep.shared.generated.resources.settings_distance_multiplier
import mapbystep.shared.generated.resources.settings_distance_multiplier_explanation
import mapbystep.shared.generated.resources.settings_export
import mapbystep.shared.generated.resources.settings_export_failed
import mapbystep.shared.generated.resources.settings_export_import_explanation
import mapbystep.shared.generated.resources.settings_export_successful
import mapbystep.shared.generated.resources.settings_import
import mapbystep.shared.generated.resources.settings_import_confirmation_dialog_body
import mapbystep.shared.generated.resources.settings_import_failed
import mapbystep.shared.generated.resources.settings_import_successful
import mapbystep.shared.generated.resources.settings_import_successful_dialog_body
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {

    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                SettingsEvent.ExportSuccessful -> snackbarHostState.showSnackbar(
                    getString(Res.string.settings_export_successful),
                )

                SettingsEvent.ExportFailed -> snackbarHostState.showSnackbar(
                    getString(Res.string.settings_export_failed),
                )

                SettingsEvent.ImportSuccessful -> snackbarHostState.showSnackbar(
                    getString(Res.string.settings_import_successful),
                )

                SettingsEvent.ImportFailed -> snackbarHostState.showSnackbar(
                    getString(Res.string.settings_import_failed),
                )

            }
        }
    }

    FilePickerHandlerEffect(viewModel.filePickerHandler)

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

    LaunchedEffect(state.distanceMultiplier) {
        sliderState.value = state.distanceMultiplier.toFloat() - 1F
    }

    sliderState.onValueChangeFinished = {
        onAction(SettingsAction.UpdateDistanceMultiplier(sliderState.value + 1F))
    }

    AnimatedVisibility(visible = state.showImportConfirmationAlert) {
        ImportConfirmationDialog(onAction = onAction)
    }

    AnimatedVisibility(visible = state.showImportSuccessfulAlert) {
        ImportSuccessfulDialog(onAction = onAction)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
    ) {
        DistanceMultiplierCard(
            sliderState = sliderState,
            state = state,
        )

        ExportImportProgressCard(onAction = onAction)
    }
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
private fun DistanceMultiplierCard(
    sliderState: SliderState,
    state: SettingsState,
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

@Composable
private fun ExportImportProgressCard(
    onAction: (SettingsAction) -> Unit,
) {
    Card {
        Column(horizontalAlignment = Alignment.End) {
            InfoCard(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .padding(top = MaterialTheme.spacing.medium),
                text = stringResource(
                    Res.string.settings_export_import_explanation,
                ),
            )
            Row(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
            ) {
                PrimaryButton(label = stringResource(Res.string.settings_export)) {
                    onAction(SettingsAction.ExportProgress)
                }
                PrimaryButton(label = stringResource(Res.string.settings_import)) {
                    onAction(SettingsAction.ImportProgress)
                }
            }
        }
    }
}

@Composable
private fun ImportConfirmationDialog(onAction: (SettingsAction) -> Unit) {
    AlertDialog(
        title = stringResource(Res.string.settings_import),
        body = stringResource(Res.string.settings_import_confirmation_dialog_body),
        primaryActionLabel = stringResource(Res.string.settings_import),
        primaryAction = {
            onAction(SettingsAction.ConfirmImport)
        },
        isPrimaryActionDestructive = true,
        secondaryActionLabel = stringResource(Res.string.cancel),
        secondaryAction = {
            onAction(SettingsAction.CancelImport)
        },
        onDismissRequest = {
            onAction(SettingsAction.CancelImport)
        },
    )
}

@Composable
private fun ImportSuccessfulDialog(onAction: (SettingsAction) -> Unit) {
    AlertDialog(
        title = stringResource(Res.string.settings_import),
        body = stringResource(Res.string.settings_import_successful_dialog_body),
        primaryActionLabel = stringResource(Res.string.settings_import),
        primaryAction = {
            onAction(SettingsAction.CloseImportSuccessfulDialog)
        },
        onDismissRequest = {
            onAction(SettingsAction.CloseImportSuccessfulDialog)
        },
    )
}