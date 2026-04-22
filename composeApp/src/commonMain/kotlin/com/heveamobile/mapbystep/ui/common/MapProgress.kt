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
                text = "Destinations discovered:",
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
                text = "Common:",
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
                text = "Uncommon:",
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
                text = "Rare:",
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
                text = "Epic:",
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
                text = "Legendary:",
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