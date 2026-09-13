package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

/** Content-first poster surface. Material surrounds the artwork instead of replacing it. */
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
    val description = buildString {
        append(title)
        subtitle?.let { append(", $it") }
    }

    GlassCard(
        modifier = modifier
            .clip(cardShape)
            .then(
                if (onClick != null) {
                    Modifier
                        .clickable(
                            role = Role.Button,
                            onClickLabel = "Open $title",
                            onClick = onClick,
                        )
                        .semantics {
                            contentDescription = description
                            role = Role.Button
                        }
                } else Modifier.semantics { contentDescription = description }
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
                    GlassMediaPlaceholder()
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
                Text(text = title, color = tokens.textPrimary, fontSize = 14.sp, maxLines = 2)
                subtitle?.let {
                    Text(text = it, color = tokens.textSecondary, fontSize = 12.sp, maxLines = 1)
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
private fun GlassMediaPlaceholder() {
    val tokens = LocalReelTideGlassTokens.current
    Box(contentAlignment = Alignment.Center) {
        Text(text = "—", color = tokens.textTertiary, fontSize = 20.sp)
    }
}
