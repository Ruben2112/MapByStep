package com.heveamobile.mapbystep.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.domain.model.Rarity
import com.heveamobile.mapbystep.theme.RarityCommon
import com.heveamobile.mapbystep.theme.RarityEpic
import com.heveamobile.mapbystep.theme.RarityLegendary
import com.heveamobile.mapbystep.theme.RarityRare
import com.heveamobile.mapbystep.theme.RarityUncommon
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.expand_icon_description
import mapbystep.composeapp.generated.resources.map_statistics_destinations_discovered
import mapbystep.composeapp.generated.resources.rarity_common
import mapbystep.composeapp.generated.resources.rarity_epic
import mapbystep.composeapp.generated.resources.rarity_legendary
import mapbystep.composeapp.generated.resources.rarity_rare
import mapbystep.composeapp.generated.resources.rarity_uncommon
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapStatisticsList(
    modifier: Modifier = Modifier,
    map: Map,
    isExpanded: Boolean = true,
    showExpandIcon: Boolean = true,
    showProgress: Boolean = true,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.map_statistics_destinations_discovered),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = map.formatProgress(null),
                style = MaterialTheme.typography.bodyMedium,
            )
            if (showExpandIcon) {
                Spacer(
                    modifier = Modifier.width(MaterialTheme.spacing.small),
                )
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = if (isExpanded) Icons.Rounded.ArrowDropUp else Icons.Rounded.ArrowDropDown,
                    contentDescription = stringResource(Res.string.expand_icon_description),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier.height(MaterialTheme.spacing.small),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${stringResource(Res.string.rarity_common)}:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = RarityCommon),
                    )
                    Text(
                        text = if (showProgress) map.formatProgress(Rarity.Common) else map.destinations
                            .count { it.rarity == Rarity.Common }
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (showExpandIcon) {
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.large),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${stringResource(Res.string.rarity_uncommon)}:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = RarityUncommon),
                    )
                    Text(
                        text = if (showProgress) map.formatProgress(Rarity.Uncommon) else map.destinations
                            .count { it.rarity == Rarity.Uncommon }
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (showExpandIcon) {
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.large),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${stringResource(Res.string.rarity_rare)}:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = RarityRare),
                    )
                    Text(
                        text = if (showProgress) map.formatProgress(Rarity.Rare) else map.destinations
                            .count { it.rarity == Rarity.Rare }
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (showExpandIcon) {
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.large),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${stringResource(Res.string.rarity_epic)}:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = RarityEpic),
                    )
                    Text(
                        text = if (showProgress) map.formatProgress(Rarity.Epic) else map.destinations
                            .count { it.rarity == Rarity.Epic }
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (showExpandIcon) {
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.large),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "${stringResource(Res.string.rarity_legendary)}:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = RarityLegendary),
                    )
                    Text(
                        text = if (showProgress) map.formatProgress(Rarity.Legendary) else map.destinations
                            .count { it.rarity == Rarity.Legendary }
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    if (showExpandIcon) {
                        Spacer(
                            modifier = Modifier.width(MaterialTheme.spacing.large),
                        )
                    }
                }
            }
        }
    }
}