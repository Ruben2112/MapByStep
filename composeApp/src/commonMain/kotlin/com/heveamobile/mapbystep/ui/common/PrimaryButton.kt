package com.heveamobile.mapbystep.ui.common

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.heveamobile.mapbystep.theme.SurfaceContainer

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = SurfaceContainer,
        ),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}