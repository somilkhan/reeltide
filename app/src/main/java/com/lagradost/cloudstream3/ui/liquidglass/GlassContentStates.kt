package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GlassLoadingState(
    modifier: Modifier = Modifier,
    message: String? = null,
) {
    val tokens = LocalReelTideGlassTokens.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CircularProgressIndicator(color = tokens.textPrimary)
        message?.let {
            Text(text = it, color = tokens.textSecondary)
        }
    }
}

@Composable
fun GlassEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val tokens = LocalReelTideGlassTokens.current
    GlassCard(modifier = modifier.fillMaxWidth(), level = GlassLevel.Glass) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                color = tokens.textPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            )
            message?.let {
                Text(text = it, color = tokens.textSecondary)
            }
            if (actionText != null && onAction != null) {
                GlassButton(text = actionText, onClick = onAction)
            }
        }
    }
}

@Composable
fun GlassErrorState(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val tokens = LocalReelTideGlassTokens.current
    GlassCard(modifier = modifier.fillMaxWidth(), level = GlassLevel.Elevated) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                color = tokens.textPrimary,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
            )
            message?.let {
                Text(text = it, color = tokens.textSecondary)
            }
            if (actionText != null && onAction != null) {
                GlassButton(text = actionText, onClick = onAction)
            }
        }
    }
}
