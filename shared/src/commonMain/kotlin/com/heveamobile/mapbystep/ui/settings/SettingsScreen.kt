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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults.CenteredTrack
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberSliderState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.formatTime
import com.heveamobile.mapbystep.theme.sliderColors
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.theme.switchColors
import com.heveamobile.mapbystep.theme.timePickerColors
import com.heveamobile.mapbystep.ui.common.AlertDialog
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.FilePickerHandlerEffect
import com.heveamobile.mapbystep.ui.common.InfoCard
import com.heveamobile.mapbystep.ui.common.PrimaryButton
import com.heveamobile.mapbystep.ui.common.SecondaryButton
import com.heveamobile.mapbystep.ui.home.LocalSnackbarHostState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalTime
import mapbystep.shared.generated.resources.Res
import mapbystep.shared.generated.resources.cancel
import mapbystep.shared.generated.resources.save
import mapbystep.shared.generated.resources.settings_daily_reminder_change_time
import mapbystep.shared.generated.resources.settings_daily_reminder_enable_daily_reminder
import mapbystep.shared.generated.resources.settings_daily_reminder_explanation
import mapbystep.shared.generated.resources.settings_daily_reminder_reminder_time
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
private fun SettingsContent(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
) {

    AnimatedVisibility(visible = state.showImportConfirmationAlert) {
        ImportConfirmationDialog(onAction = onAction)
    }

    AnimatedVisibility(visible = state.showTimePickerAlertDialog) {
        TimePickerDialog(
            initialTime = state.reminderTime,
            onAction = onAction,
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        ReminderCard(
            reminderIsEnabled = state.reminderIsEnabled,
            reminderTime = state.reminderTime,
            onAction = onAction,
        )
        DistanceMultiplierCard(
            distanceMultiplier = state.distanceMultiplier,
            onAction = onAction,
        )
        ExportImportDataCard(onAction = onAction)
    }
}

@Composable
private fun ReminderCard(
    reminderIsEnabled: Boolean,
    reminderTime: LocalTime,
    onAction: (SettingsAction) -> Unit,
) {
    Card {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            horizontalAlignment = Alignment.End,
        ) {
            InfoCard(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .padding(top = MaterialTheme.spacing.medium),
                text = stringResource(Res.string.settings_daily_reminder_explanation),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(Res.string.settings_daily_reminder_enable_daily_reminder),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Switch(
                    checked = reminderIsEnabled,
                    onCheckedChange = { onAction(SettingsAction.UpdateReminderIsEnabled(it)) },
                    colors = switchColors(),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .padding(bottom = MaterialTheme.spacing.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            ) {
                Text(
                    modifier = Modifier.weight(1F),
                    text = stringResource(Res.string.settings_daily_reminder_reminder_time),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = formatTime(
                        localTime = reminderTime,
                        formatMode = FormatMode.Short,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            PrimaryButton(
                modifier = Modifier.padding(
                    bottom = MaterialTheme.spacing.medium,
                    end = MaterialTheme.spacing.medium,
                ),
                label = stringResource(Res.string.settings_daily_reminder_change_time),
            ) {
                onAction(SettingsAction.ToggleTimePickerAlertDialog)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    onAction: (SettingsAction) -> Unit,
) {
    val state = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
    )

    AlertDialog(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        onDismissRequest = { onAction(SettingsAction.ToggleTimePickerAlertDialog) },
        confirmButton = {
            PrimaryButton(label = stringResource(Res.string.save)) {
                onAction(
                    SettingsAction.UpdateReminderTime(
                        LocalTime(
                            state.hour,
                            state.minute,
                        ),
                    ),
                )
                onAction(SettingsAction.ToggleTimePickerAlertDialog)
            }
        },
        dismissButton = {
            SecondaryButton(label = stringResource(Res.string.cancel)) {
                onAction(SettingsAction.ToggleTimePickerAlertDialog)
            }
        },
        text = {
            TimePicker(
                state = state,
                colors = timePickerColors(),
            )
        },
    )
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class,
)
private fun DistanceMultiplierCard(
    distanceMultiplier: Double,
    onAction: (SettingsAction) -> Unit,
) {
    val minValue = -1F
    val maxValue = 1F

    val sliderState = rememberSliderState(
        value = distanceMultiplier.toFloat() - 1F,
        valueRange = minValue..maxValue,
        // steps = all valid floats with a .1 decimal precision
        // (difference between maxValue times 10 and minValue times 10) (20)
        // plus the center point (0.0F) (1)
        // minus the slider's endpoints (-2)
        // which adds up to 19 steps
        steps = ((((maxValue.toInt() * 10) - (minValue.toInt() * 10)) + 1) - 2),
    )

    LaunchedEffect(distanceMultiplier) {
        sliderState.value = distanceMultiplier.toFloat() - 1F
    }

    sliderState.onValueChangeFinished = {
        onAction(SettingsAction.UpdateDistanceMultiplier(sliderState.value + 1F))
    }

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
                                colors = sliderColors(),
                                drawStopIndicator = {},
                                thumbTrackGapSize = 0.dp,
                                sliderState = state,
                            )
                        },
                    )
                    Text(
                        modifier = Modifier.width(40.dp),
                        text = "${distanceMultiplier}x",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Composable
private fun ExportImportDataCard(
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