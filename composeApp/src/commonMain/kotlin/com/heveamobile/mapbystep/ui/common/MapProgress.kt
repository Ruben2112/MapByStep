package com.heveamobile.mapbystep.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.theme.RarityCommon
import com.heveamobile.mapbystep.theme.RarityEpic
import com.heveamobile.mapbystep.theme.RarityLegendary
import com.heveamobile.mapbystep.theme.RarityRare
import com.heveamobile.mapbystep.theme.RarityUncommon
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.map_progress_destinations_discovered
import mapbystep.composeapp.generated.resources.rarity_common
import mapbystep.composeapp.generated.resources.rarity_epic
import mapbystep.composeapp.generated.resources.rarity_legendary
import mapbystep.composeapp.generated.resources.rarity_rare
import mapbystep.composeapp.generated.resources.rarity_uncommon
import org.jetbrains.compose.resources.stringResource

@Composable
fun MapProgress(
    modifier: Modifier = Modifier,
    map: Map,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(Res.string.map_progress_destinations_discovered),
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "113 / 226 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                text = "54 / 109 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                text = "31 / 61 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                text = "17 / 34 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                text = "9 / 17 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
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
                text = "2 / 5 (50%)",
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier.width(MaterialTheme.spacing.large),
            )
        }
    }
}