package com.heveamobile.mapbystep.ui.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.heveamobile.mapbystep.domain.model.Destination
import com.heveamobile.mapbystep.theme.Outline
import com.heveamobile.mapbystep.theme.PrimaryContainer
import com.heveamobile.mapbystep.theme.SurfaceContainer
import com.heveamobile.mapbystep.theme.color
import com.heveamobile.mapbystep.theme.spacing
import mapbystep.composeapp.generated.resources.Res
import mapbystep.composeapp.generated.resources.ic_question_mark
import mapbystep.composeapp.generated.resources.ic_steps
import org.jetbrains.compose.resources.painterResource


@Composable
fun DestinationCard(
    modifier: Modifier = Modifier,
    destination: Destination,
    isRevealed: Boolean,
    isNew: Boolean = false,
    raritySpoiler: Boolean = false,
    onClick: () -> Unit,
    isLarge: Boolean = false,
) {
    val rotation = animateFloatAsState(
        targetValue = if (isRevealed) 0F else 180F,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
    )
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 16F * density
            }
            .clip(if (isLarge) MaterialTheme.shapes.large else MaterialTheme.shapes.medium)
            .clickable {
                onClick()
            },
    ) {
        if (rotation.value <= 90F) {
            Box {
                CardFront(
                    isLarge = isLarge,
                    isNew = isNew,
                    destination = destination,
                )
            }
        } else {
            Box(
                Modifier.graphicsLayer {
                    rotationY = 180F
                },
            ) {
                CardBack(
                    isLarge = isLarge,
                    raritySpoiler = raritySpoiler,
                    destination = destination,
                )
            }
        }
    }
}

@Composable
private fun CardFront(
    modifier: Modifier = Modifier,
    isLarge: Boolean,
    isNew: Boolean = false,
    destination: Destination,
) {
    Box(
        modifier = modifier.aspectRatio(
            5F / 7F,
            matchHeightConstraintsFirst = true,
        ),
        contentAlignment = Alignment.TopEnd,
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .clip(if (isLarge) MaterialTheme.shapes.large else MaterialTheme.shapes.medium)
                .border(
                    width = if (isLarge) 4.dp else 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = if (isLarge) MaterialTheme.shapes.large else MaterialTheme.shapes.medium,
                )
                .background(if (destination.visits > 0) SurfaceContainer else PrimaryContainer),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F),
            ) {
                //TODO: Add image of country
//                Icon(
//                    modifier = Modifier.fillMaxSize(),
//                    painter = painterResource(resource = Res.drawable.ic_question_mark),
//                    contentDescription = "Placeholder card art",
//                    tint = destination.rarity.color,
//                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLarge) 4.dp else 1.dp)
                    .background(Outline),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = if (isLarge) MaterialTheme.spacing.large else MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.small,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = destination.name,
                    textAlign = TextAlign.Center,
                    style = if (isLarge) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isLarge) MaterialTheme.spacing.large else MaterialTheme.spacing.medium),
            contentAlignment = Alignment.TopStart,
        ) {
            Icon(
                modifier = Modifier.size(if (isLarge) 32.dp else 24.dp),
                painter = painterResource(Res.drawable.ic_steps),
                contentDescription = "Rarity ${destination.rarity.name} icon",
                tint = destination.rarity.color,
            )
        }
        if (isNew) {
            Box(
                modifier = Modifier.padding(
                    if (isLarge) MaterialTheme.spacing.medium else MaterialTheme.spacing.small,
                ),
            ) {
                Badge(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh) {
                    if (isLarge) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = MaterialTheme.spacing.medium,
                                vertical = MaterialTheme.spacing.small,
                            ),
                            text = if (isLarge) "New!" else "",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    } else {
                        Icon(
                            Icons.Default.Star,
                            modifier = Modifier.size(8.dp),
                            contentDescription = "New!",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CardBack(
    modifier: Modifier = Modifier,
    isLarge: Boolean,
    raritySpoiler: Boolean = false,
    destination: Destination,
) {
    Column(
        modifier = modifier
            .aspectRatio(
                5F / 7F,
                matchHeightConstraintsFirst = true,
            )
            .clip(if (isLarge) MaterialTheme.shapes.large else MaterialTheme.shapes.medium)
            .border(
                width = if (isLarge) 4.dp else 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = if (isLarge) MaterialTheme.shapes.large else MaterialTheme.shapes.medium,
            )
            .background(if (destination.visits > 0) SurfaceContainer else PrimaryContainer),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F),
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(resource = Res.drawable.ic_question_mark),
                contentDescription = "Hidden card icon",
                tint = if (raritySpoiler) destination.rarity.color else MaterialTheme.colorScheme.onSurface,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isLarge) 4.dp else 1.dp)
                .background(Outline),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (isLarge) MaterialTheme.spacing.large else MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.small,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "???",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}