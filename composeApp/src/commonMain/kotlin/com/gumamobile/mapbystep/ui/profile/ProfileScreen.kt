package com.gumamobile.mapbystep.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gumamobile.mapbystep.theme.spacing
import com.gumamobile.mapbystep.ui.common.Card
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.step_data_one_month_record
import mapbystep.composeapp.generated.resources.step_data_one_week_record
import mapbystep.composeapp.generated.resources.step_data_title
import mapbystep.composeapp.generated.resources.step_data_total_steps
import mapbystep.composeapp.generated.resources.step_data_twenty_four_hour_record
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileContent(
        state = state,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(MaterialTheme.spacing.medium),
        title = stringResource(Res.string.step_data_title),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.step_data_total_steps),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = state.totalSteps?.toString()
                        ?: "--",
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
                    text = state.twentyFourHourRecord?.toString()
                        ?: "--",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.step_data_one_week_record),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = state.oneWeekRecord?.toString()
                        ?: "--",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    stringResource(Res.string.step_data_one_month_record),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Text(
                    modifier = Modifier.weight(1F),
                    text = state.oneMonthRecord?.toString()
                        ?: "--",
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}