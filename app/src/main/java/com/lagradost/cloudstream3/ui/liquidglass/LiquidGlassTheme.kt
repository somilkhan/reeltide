package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class ReelTideGlassTokens(
    val background: Color = Color(0xFF070708),
    val elevatedBackground: Color = Color(0xFF101012),
    val glass: Color = Color(0xD90B0B0D),
    val glassElevated: Color = Color(0xE0141416),
    val glassStrong: Color = Color(0xE81A1A1D),
    val glassHighlight: Color = Color(0x16FFFFFF),
    val glassBorder: Color = Color(0x20FFFFFF),
    val textPrimary: Color = Color(0xFFF7F7F8),
    val textSecondary: Color = Color(0xFFB0B0B6),
    val textTertiary: Color = Color(0xFF77777E),
    val textDisabled: Color = Color(0xFF505056),
    val scrim: Color = Color(0x99000000),
    val destructive: Color = Color(0xFFE4E4E7),
    val screenHorizontalPadding: Int = 16,
    val contentGap: Int = 12,
    val controlHeight: Int = 52,
    val smallRadius: Int = 14,
    val mediumRadius: Int = 20,
    val largeRadius: Int = 28,
)

val LocalReelTideGlassTokens = staticCompositionLocalOf { ReelTideGlassTokens() }

private val ReelTideDarkColors: ColorScheme = darkColorScheme(
    primary = Color(0xFFF7F7F8),
    onPrimary = Color(0xFF080809),
    secondary = Color(0xFFB0B0B6),
    onSecondary = Color(0xFF080809),
    tertiary = Color(0xFF8C8C93),
    onTertiary = Color(0xFF080809),
    background = Color(0xFF070708),
    onBackground = Color(0xFFF7F7F8),
    surface = Color(0xFF0D0D0F),
    onSurface = Color(0xFFF7F7F8),
    surfaceVariant = Color(0xFF171719),
    onSurfaceVariant = Color(0xFFB0B0B6),
    outline = Color(0xFF303035),
    outlineVariant = Color(0xFF202024),
)

private val ReelTideTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 30.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.4).sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        lineHeight = 29.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = (-0.2).sp,
    ),
    titleLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = TextStyle(
        fontSize = 11.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Medium,
    ),
)

@Composable
fun ReelTideTheme(
    content: @Composable () -> Unit,
) {
    androidx.compose.runtime.CompositionLocalProvider(
        LocalReelTideGlassTokens provides ReelTideGlassTokens(),
    ) {
        MaterialTheme(
            colorScheme = ReelTideDarkColors,
            typography = ReelTideTypography,
            content = content,
        )
    }
}

internal val GlassControlHeight = 52.dp
internal val GlassPillRadius = 28.dp
