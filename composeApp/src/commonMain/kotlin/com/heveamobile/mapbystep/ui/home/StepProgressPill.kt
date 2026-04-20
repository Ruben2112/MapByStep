package com.heveamobile.mapbystep.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.formatStepAmount
import com.heveamobile.mapbystep.theme.OnSurface
import com.heveamobile.mapbystep.theme.SecondaryContainer
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource

@Composable
fun StepProgressPill(
    modifier: Modifier = Modifier,
    availableSteps: Long,
    requiredSteps: Long,
) {
    Row(
        modifier = modifier
            .padding(horizontal = MaterialTheme.spacing.small)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(SecondaryContainer)
            .padding(
                MaterialTheme.spacing.small,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(Res.drawable.ic_steps),
            contentDescription = "Steps icon",
            tint = OnSurface,
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.extraSmall))
        Text(
            text = "${
                formatStepAmount(
                    availableSteps,
                    FormatMode.Medium,
                )
            } / ${
                formatStepAmount(
                    requiredSteps,
                    FormatMode.Medium,
                )
            }",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}