package com.heveamobile.mapbystep.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.theme.spacing

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    errorMessage: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Card(
        bottomContent = {
            if (actionLabel != null && onAction != null) {
                PrimaryButton(
                    label = actionLabel,
                    onClick = onAction,
                )
            }
        },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.Warning,
                contentDescription = "Error icon",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.error,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
            Text(
                errorMessage,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.error),
            )
        }
    }
}