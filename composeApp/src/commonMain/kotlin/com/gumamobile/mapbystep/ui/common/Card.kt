package com.gumamobile.mapbystep.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.gumamobile.mapbystep.theme.Outline
import com.gumamobile.mapbystep.theme.SurfaceContainer
import com.gumamobile.mapbystep.theme.spacing

@Composable
fun Card(
    modifier: Modifier = Modifier,
    title: String? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    mainContent: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium,
            )
            .clip(MaterialTheme.shapes.medium)
            .background(color = SurfaceContainer),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (title != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.spacing.medium),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .padding(horizontal = MaterialTheme.spacing.medium)
                        .background(Outline),
                )
            }
            Box(modifier = Modifier.padding(MaterialTheme.spacing.medium)) {
                mainContent.invoke()
            }
            if (bottomContent != null) {
                TODO("Bottom content is not implemented yet for Card composable")
            }
        }
    }
}
