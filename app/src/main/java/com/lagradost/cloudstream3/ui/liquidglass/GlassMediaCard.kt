package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

/**
 * Content-first poster surface. Material surrounds the artwork instead of replacing it.
 */
@Composable
fun GlassMediaCard(
    title: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    progress: Float? = null,
    onClick: (() -> Unit)? = null,
) {
    val tokens = LocalReelTideGlassTokens.current
    val cardShape = RoundedCornerShape(tokens.mediumRadius.dp)
    val safeProgress = progress?.coerceIn(0f, 1f)

    GlassCard(
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.semantics {
                    contentDescription = buildString {
                        append(title)
                        subtitle?.let { append(", $it") }
                    }
                    role = Role.Button
                }
            } else Modifier
        ),
        level = GlassLevel.Elevated,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(cardShape),
                contentAlignment = Alignment.Center,
            ) {
                if (imageUrl.isNullOrBlank()) {
                    GlassMediaPlaceholder(modifier = Modifier.fillMaxWidth())
                } else {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Text(
                    text = title,
                    color = tokens.textPrimary,
                    fontSize = 14.sp,
                    maxLines = 2,
                )
                subtitle?.let {
                    Text(
                        text = it,
                        color = tokens.textSecondary,
                        fontSize = 12.sp,
                        maxLines = 1,
                    )
                }
                safeProgress?.let {
                    LinearProgressIndicator(
                        progress = { it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp),
                        color = tokens.textPrimary,
                        trackColor = tokens.glassHighlight,
                    )
                }
            }
        }
    }
}

@Composable
private fun GlassMediaPlaceholder(modifier: Modifier = Modifier) {
    val tokens = LocalReelTideGlassTokens.current
    GlassSurface(
        modifier = modifier,
        level = GlassLevel.Glass,
        shape = RoundedCornerShape(0.dp),
    ) {
        Row(
            modifier = Modifier.size(48.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "—",
                color = tokens.textTertiary,
                fontSize = 20.sp,
            )
        }
    }
}
