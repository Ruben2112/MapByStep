package com.heveamobile.mapbystep.ui.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.domain.rememberHealthPermissionLauncher
import com.heveamobile.mapbystep.formatStepAmount
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.ErrorCard
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.step_data_previous_seven_days
import mapbystep.composeapp.generated.resources.step_data_previous_thirty_days
import mapbystep.composeapp.generated.resources.step_data_previous_twenty_four_hours
import mapbystep.composeapp.generated.resources.step_data_seven_day_record
import mapbystep.composeapp.generated.resources.step_data_thirty_day_record
import mapbystep.composeapp.generated.resources.step_data_title
import mapbystep.composeapp.generated.resources.step_data_total_steps
import mapbystep.composeapp.generated.resources.step_data_twenty_four_hour_record
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val permissionManager = viewModel.healthPermissionManager

    val launcher = rememberHealthPermissionLauncher(
        manager = permissionManager,
        onResult = {
            viewModel.onAction(ProfileAction.UpdatePermissionState)
        },
    )

    ProfileContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
        onPermissionRequest = { launcher() },
    )
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    onPermissionRequest: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        AnimatedVisibility(state.isLoading) {
            Card {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        strokeWidth = 2.dp,
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    Text(
                        text = "Loading step data...",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
        AnimatedContent(targetState = state.healthPermissionState) { permissionState ->
            when (permissionState) {
                HealthPermissionStatus.Loading -> {}
                HealthPermissionStatus.Granted -> {}
                HealthPermissionStatus.NotGranted -> ErrorCard(
                    errorMessage = "Permissions not granted",
                    actionLabel = "Request permissions",
                    onAction = onPermissionRequest,
                )

                HealthPermissionStatus.NotInstalled -> ErrorCard(errorMessage = "Required Health Connect app not installed")
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Card(
            title = stringResource(Res.string.step_data_title),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_total_steps),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.totalSteps,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_previous_twenty_four_hours),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.previousTwentyFourHours,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_twenty_four_hour_record),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.twentyFourHourRecord,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_previous_seven_days),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.previousSevenDays,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_seven_day_record),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.sevenDayRecord,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_previous_thirty_days),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.previousThirtyDays,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        stringResource(Res.string.step_data_thirty_day_record),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                    Text(
                        modifier = Modifier.weight(1F),
                        text = formatStepAmount(
                            state.thirtyDayRecord,
                            FormatMode.Long,
                        ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}