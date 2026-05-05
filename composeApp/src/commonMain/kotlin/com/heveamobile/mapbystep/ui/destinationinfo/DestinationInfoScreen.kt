package com.heveamobile.mapbystep.ui.destinationinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.heveamobile.mapbystep.data.entity.InfoType
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Info
import com.heveamobile.mapbystep.formatPopulation
import com.heveamobile.mapbystep.navigation.Route
import com.heveamobile.mapbystep.theme.color
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.toTitleCase
import com.heveamobile.mapbystep.ui.common.Card
import com.heveamobile.mapbystep.ui.common.DropDownMenu
import com.heveamobile.mapbystep.ui.common.MapDropDownMenu
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DestinationInfoScreen(
    modifier: Modifier = Modifier,
    route: Route.DestinationInfo,
) {
    val viewModel: DestinationInfoViewModel = koinViewModel { parametersOf(route) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    DestinationInfoContent(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun DestinationInfoContent(
    modifier: Modifier = Modifier,
    state: DestinationInfoState,
    onAction: (DestinationInfoAction) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(MaterialTheme.spacing.medium),
    ) {
        if (state.maps.size > 1) {
            item {
                MapDropDownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    maps = state.maps,
                    selectedMap = state.selectedMap
                        ?: state.maps.first(),
                    onItemSelected = { map -> onAction(DestinationInfoAction.SelectMap(map)) },
                )
            }
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            }
        }
        if (state.destinations.isNotEmpty()) {
            item {
                DropDownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    items = state.destinations,
                    selectedItem = state.selectedDestination
                        ?: state.selectedMap?.destinations?.first()
                        ?: state.maps.first().destinations.first(),
                    onItemSelected = { destination ->
                        onAction(DestinationInfoAction.SelectDestination(destination))
                    },
                ) { destination ->
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = destination.name,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            }
        }
        if (state.selectedMap != null && state.selectedDestination != null && state.info != null) {
            item {
                when (state.selectedMap.infoType) {
                    InfoType.Country -> CountryInfoCard(
                        modifier = Modifier.fillMaxWidth(),
                        destination = state.selectedDestination,
                        destinationInfo = state.info as Info.CountryInfo,
                    )
                }
            }
        }
    }
}

@Composable
fun CountryInfoCard(
    modifier: Modifier = Modifier,
    destination: Destination,
    destinationInfo: Info.CountryInfo,
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                text = destination.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = destination.rarity.color,
                ),
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline,
            )
            Box(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                Card(modifier = Modifier.height(160.dp)) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "*insert map here*",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        modifier = Modifier
                            .weight(1F)
                            .padding(MaterialTheme.spacing.small)
                            .size(48.dp),
                        painter = painterResource(Res.drawable.ic_steps),
                        tint = destination.rarity.color,
                        contentDescription = "Rarity ${destination.rarity.name} icon",
                    )
                    Text(
                        text = destination.rarity.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = destination.rarity.color,
                        ),
                    )
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaterialTheme.colorScheme.outline,
                )
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier.weight(1F),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier.padding(MaterialTheme.spacing.small),
                            text = destinationInfo.flag,
                            textAlign = TextAlign.Center,
                            autoSize = TextAutoSize.StepBased(),
                        )
                    }
                    Text(
                        text = "Flag",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                VerticalDivider(
                    modifier = Modifier.fillMaxHeight(),
                    color = MaterialTheme.colorScheme.outline,
                )
                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(MaterialTheme.spacing.medium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier.weight(1F),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = destination.visits.toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Text(
                        text = if (destination.visits == 1) "Visit" else "Visits",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) {
                Text(
                    "Population:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Text(
                    modifier = Modifier.weight(1F),
                    text = formatPopulation(destinationInfo.population),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) {
                val capitals = destinationInfo.capitals.split(",")
                Text(
                    if (capitals.size == 1) "Capital:" else "Capitals:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.End,
                ) {
                    capitals.forEach { capital ->
                        Text(
                            text = capital,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) {
                val continents = destinationInfo.continents.split(",")
                Text(
                    if (continents.size == 1) "Continent:" else "Continents:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.End,
                ) {
                    continents.forEach { continent ->
                        Text(
                            text = continent,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) {
                val languages = destinationInfo.languages.split(",")
                Text(
                    if (languages.size == 1) "Language:" else "Languages:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.End,
                ) {
                    languages.forEach { language ->
                        Text(
                            text = language,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            ) {
                Text(
                    "Currency:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Text(
                    modifier = Modifier.weight(1F),
                    text = destinationInfo.currencies.toTitleCase(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}