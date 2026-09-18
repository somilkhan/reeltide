package com.lagradost.cloudstream3.ui.liquidglass

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

/** Shared modal material. Callers own visibility and business actions. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!visible) return

    val tokens = LocalReelTideGlassTokens.current
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
        shape = RoundedCornerShape(topStart = tokens.largeRadius.dp, topEnd = tokens.largeRadius.dp),
        containerColor = tokens.glassStrong,
        contentColor = tokens.textPrimary,
        scrimColor = tokens.scrim,
    ) {
        GlassSurface(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            level = GlassLevel.Sheet,
            shape = RoundedCornerShape(tokens.mediumRadius.dp),
        ) {
            Column(content = content)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GlassDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    text: String? = null,
    confirmButton: @Composable () -> Unit,
    dismissButton: @Composable (() -> Unit)? = null,
) {
    if (!visible) return

    val tokens = LocalReelTideGlassTokens.current
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    ) {
        GlassSurface(
            modifier = modifier.fillMaxWidth(),
            level = GlassLevel.Sheet,
            shape = RoundedCornerShape(tokens.largeRadius.dp),
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                title?.let {
                    Text(
                        text = it,
                        color = tokens.textPrimary,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
                text?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(top = 8.dp),
                        color = tokens.textSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    dismissButton?.invoke()
                    confirmButton()
                }
            }
        }
    }
}
