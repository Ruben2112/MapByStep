package com.heveamobile.mapbystep.ui.profile

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.domain.rememberHealthPermissionLauncher
import com.heveamobile.mapbystep.formatAmount
import com.heveamobile.mapbystep.formatDate
import com.heveamobile.mapbystep.formatDateTime
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.ErrorCard
import com.heveamobile.mapbystep.ui.common.HealthPermissionStatus
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.Scroll
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.compose.cartesian.data.columnSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.ColumnCartesianLayer.ColumnProvider
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.Fill
import com.patrykandpatrick.vico.compose.common.component.TextComponent
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.historic_step_data_start_time
import mapbystep.composeapp.generated.resources.historic_step_data_title
import mapbystep.composeapp.generated.resources.historic_step_data_total_steps
import mapbystep.composeapp.generated.resources.personal_records_seven_days
import mapbystep.composeapp.generated.resources.personal_records_subtitle
import mapbystep.composeapp.generated.resources.personal_records_thirty_days
import mapbystep.composeapp.generated.resources.personal_records_title
import mapbystep.composeapp.generated.resources.personal_records_twenty_four_hours
import mapbystep.composeapp.generated.resources.profile_error_action_request_permissions
import mapbystep.composeapp.generated.resources.profile_error_health_connect_not_installed
import mapbystep.composeapp.generated.resources.profile_error_permissions_not_granted
import mapbystep.composeapp.generated.resources.profile_loading_step_data
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

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
            Column {
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
                            text = stringResource(Res.string.profile_loading_step_data),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }
        }
        AnimatedContent(targetState = state.healthPermissionState) { permissionState ->
            when (permissionState) {
                HealthPermissionStatus.Loading -> {}
                HealthPermissionStatus.Granted -> {}
                HealthPermissionStatus.NotGranted -> Column(modifier = Modifier.fillMaxWidth()) {
                    ErrorCard(
                        errorMessage = stringResource(Res.string.profile_error_permissions_not_granted),
                        actionLabel = stringResource(Res.string.profile_error_action_request_permissions),
                        onAction = onPermissionRequest,
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }

                HealthPermissionStatus.NotInstalled -> Column(modifier = Modifier.fillMaxWidth()) {
                    ErrorCard(errorMessage = stringResource(Res.string.profile_error_health_connect_not_installed))
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }
            }
        }
        HistoricDataCard(state = state)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        PersonalRecordsDataCard(state = state)
    }
}

@Composable
private fun HistoricDataCard(state: ProfileState) {
    Card(
        title = stringResource(Res.string.historic_step_data_title),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            Card(modifier = Modifier.height(160.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.small),
                    contentAlignment = Alignment.Center,
                ) {
                    if (state.dailyStepData.isNotEmpty()) {
                        DailyStepsChart(state.dailyStepData)
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.historic_step_data_start_time),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = formatDateTime(
                        state.startTime,
                        FormatMode.Medium,
                    ),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.historic_step_data_total_steps),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = formatAmount(
                        state.totalSteps,
                        FormatMode.Long,
                    ),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
private fun DailyStepsChart(dailyStepData: Map<Instant, Long>) {
    val modelProducer = remember { CartesianChartModelProducer() }

    val horizontalAxisValueFormatter = CartesianValueFormatter { _, x, _ ->
        val instant = Instant.fromEpochSeconds(x.toLong())
        formatDate(
            instant,
            FormatMode.Short,
        )
    }

    val verticalAxisValueFormatter = CartesianValueFormatter { _, y, _ ->
        formatAmount(
            y.toLong(),
            FormatMode.Medium,
        )
    }

    LaunchedEffect(dailyStepData) {
        modelProducer.runTransaction {
            columnSeries {
                series(
                    x = dailyStepData.keys.map { it.epochSeconds },
                    y = dailyStepData.values,
                )
            }
        }
    }

    CartesianChartHost(
        modifier = Modifier,
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = ColumnProvider.series(
                    rememberLineComponent(
                        fill = Fill(MaterialTheme.colorScheme.onSurface),
                        thickness = 8.dp,
                        shape = MaterialTheme.shapes.small.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp),
                        ),
                    ),
                ),
                columnCollectionSpacing = MaterialTheme.spacing.small,
                dataLabel = rememberTextComponent(MaterialTheme.typography.bodySmall),
            ),
            startAxis = VerticalAxis.rememberStart(
                valueFormatter = verticalAxisValueFormatter,
                label = TextComponent(MaterialTheme.typography.bodySmall),
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = horizontalAxisValueFormatter,
                guideline = null,
                label = TextComponent(MaterialTheme.typography.bodySmall),
            ),
        ),
        modelProducer = modelProducer,
        scrollState = rememberVicoScrollState(initialScroll = Scroll.Absolute.End),
    )
}

@Composable
private fun PersonalRecordsDataCard(state: ProfileState) {
    Card(
        title = stringResource(Res.string.personal_records_title),
        subtitle = stringResource(Res.string.personal_records_subtitle),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.personal_records_twenty_four_hours),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = "${
                        formatAmount(
                            state.previousTwentyFourHours,
                            FormatMode.Long,
                        )
                    } / ${
                        formatAmount(
                            state.twentyFourHourRecord,
                            FormatMode.Long,
                        )
                    } steps",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.personal_records_seven_days),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = "${
                        formatAmount(
                            state.previousSevenDays,
                            FormatMode.Long,
                        )
                    } / ${
                        formatAmount(
                            state.sevenDayRecord,
                            FormatMode.Long,
                        )
                    } steps",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.personal_records_thirty_days),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = "${
                        formatAmount(
                            state.previousThirtyDays,
                            FormatMode.Long,
                        )
                    } / ${
                        formatAmount(
                            state.thirtyDayRecord,
                            FormatMode.Long,
                        )
                    } steps",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}