package com.heveamobile.mapbystep.ui.common

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
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.domain.model.Info
import com.heveamobile.mapbystep.formatPopulation
import com.heveamobile.mapbystep.theme.color
import com.heveamobile.mapbystep.theme.spacing
import com.heveamobile.mapbystep.toTitleCase
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource

@Composable
fun CountryInfoCard(
    modifier: Modifier = Modifier,
    destination: Destination,
    visitCountOverride: Int? = null,
) {
    val info = destination.info as Info.CountryInfo
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
                    MapboxMap(
                        modifier = Modifier.fillMaxSize(),
                        boundingBox = destination.info.boundingBox,
                    )
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
                            text = info.flag,
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
                            text = visitCountOverride?.toString()
                                ?: destination.totalVisits.toString(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Text(
                        text = if (destination.totalVisits == 1) "Visit" else "Visits",
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
                    text = formatPopulation(info.population),
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
                val capitals = info.capitals.split(",")
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
                val continents = info.continents.split(",")
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
                val languages = info.languages.split(",")
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
                            text = language.toTitleCase(),
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
                val currencies = info.currencies.split(",")
                Text(
                    if (currencies.size == 1) "Currency:" else "Currencies:",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraLarge))
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.End,
                ) {
                    currencies.forEach { currency ->
                        Text(
                            text = currency.toTitleCase(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        }
    }
}