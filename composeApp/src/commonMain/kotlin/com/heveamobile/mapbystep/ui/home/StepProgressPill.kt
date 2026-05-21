package com.heveamobile.mapbystep.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.FormatMode
import com.heveamobile.mapbystep.formatAmount
import com.heveamobile.mapbystep.theme.OnSurface
import com.heveamobile.mapbystep.theme.SecondaryContainer
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_footstep
import mapbystep.composeapp.generated.resources.ic_steps
import mapbystep.composeapp.generated.resources.step_progress_pill_step_progress_icon_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StepProgressPill(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    availableSteps: Long,
    requiredSteps: Long,
    onTap: () -> Unit,
) {
    AnimatedContent(targetState = isLoading) {
        Row(
            modifier = modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .clip(shape = MaterialTheme.shapes.medium)
                .background(SecondaryContainer)
                .clickable {
                    onTap()
                }
                .padding(
                    vertical = MaterialTheme.spacing.small,
                    horizontal = MaterialTheme.spacing.medium,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onSurface,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = formatAmount(
                        availableSteps,
                        FormatMode.Medium,
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (availableSteps >= requiredSteps) {
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
                    WalkingFootsteps()
                } else {
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.ic_steps),
                        contentDescription = stringResource(Res.string.step_progress_pill_step_progress_icon_description),
                        tint = OnSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun WalkingFootsteps() {
    val infiniteTransition = rememberInfiniteTransition(label = "WalkingTransition")

    @Composable
    fun AnimatedFootstep(
        delayMillis: Int,
        isTop: Boolean,
    ) {
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = TweenSpec(
                    durationMillis = 2000,
                    delay = delayMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "FootstepProgress",
        )

        // Calculate visual properties based on progress
        val normalisedProgress = if (isTop && progress < 1F) {
            progress
        } else if (!isTop && progress > 1F) {
            progress - 1F
        } else {
            0F
        }
        val offset = (1F - normalisedProgress * 20F)
        val translucency =
            if (normalisedProgress < 0.2f) normalisedProgress * 5F else (1F - normalisedProgress) * 1.2F

        Icon(
            painter = painterResource(Res.drawable.ic_footstep),
            contentDescription = null,
            tint = OnSurface,
            modifier = Modifier
                .size(16.dp)
                .graphicsLayer {
                    translationX = offset
                    translationY = if (isTop) (-5).dp.toPx() else 5.dp.toPx()
                    alpha = translucency.coerceIn(
                        0f,
                        1f,
                    )
                    rotationZ = 90f
                },
        )
    }

    Box(
        contentAlignment = Alignment.Center, // Start from the left
    ) {
        AnimatedFootstep(
            delayMillis = 0,
            isTop = true,
        )
        AnimatedFootstep(
            delayMillis = 0,
            isTop = false,
        )
    }
}