package com.heveamobile.mapbystep.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.heveamobile.mapbystep.domain.model.Map
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource

@Composable
fun MapDropDownMenu(
    modifier: Modifier = Modifier,
    maps: List<Map>,
    selectedMap: Map,
    onItemSelected: (Map) -> Unit,
) {
    DropDownMenu(
        modifier = modifier,
        items = maps,
        selectedItem = selectedMap,
        onItemSelected = { map -> onItemSelected(map) },
        itemContent = { map ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.alpha(if (map.isActive) 1F else 0F),
                    painter = painterResource(Res.drawable.ic_steps),
                    contentDescription = "Map is Active",
                    tint = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                Text(
                    modifier = Modifier.weight(1F),
                    text = map.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
    )
}