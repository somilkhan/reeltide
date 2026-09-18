package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

enum class GlassLevel {
    Glass,
    Elevated,
    Strong,
    Sheet,
}

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.Glass,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    val tokens = LocalReelTideGlassTokens.current
    val fill = when (level) {
        GlassLevel.Glass -> tokens.glass
        GlassLevel.Elevated -> tokens.glassElevated
        GlassLevel.Strong -> tokens.glassStrong
        GlassLevel.Sheet -> tokens.glassStrong
    }
    val borderAlpha = when (level) {
        GlassLevel.Glass -> 0.70f
        GlassLevel.Elevated -> 0.90f
        GlassLevel.Strong -> 1.00f
        GlassLevel.Sheet -> 1.00f
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(fill)
            .border(1.dp, tokens.glassBorder.copy(alpha = borderAlpha), shape),
        content = content,
    )
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.Elevated,
    content: @Composable BoxScope.() -> Unit,
) {
    GlassSurface(
        modifier = modifier,
        level = level,
        shape = RoundedCornerShape(20.dp),
        content = content,
    )
}

@Composable
fun GlassPill(
    modifier: Modifier = Modifier,
    level: GlassLevel = GlassLevel.Strong,
    content: @Composable BoxScope.() -> Unit,
) {
    GlassSurface(
        modifier = modifier,
        level = level,
        shape = RoundedCornerShape(999.dp),
        content = content,
    )
}
