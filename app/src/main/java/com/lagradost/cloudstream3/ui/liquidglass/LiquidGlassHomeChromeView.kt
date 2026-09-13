package com.lagradost.cloudstream3.ui.liquidglass

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.lagradost.cloudstream3.R
import com.lagradost.cloudstream3.ui.settings.Globals.PHONE
import com.lagradost.cloudstream3.ui.settings.Globals.isLayout

/**
 * Phone-only Home chrome. It delegates actions to the existing Home controls so
 * provider selection, search, account and random playback retain their original
 * business/navigation implementation.
 */
class LiquidGlassHomeChromeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private var providerName by mutableStateOf("")
    private var controls: HomeControls? = null
    private var providerTextWatcher: android.text.TextWatcher? = null

    init {
        isClickable = true
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isLayout(PHONE)) {
            visibility = GONE
            return
        }
        post { bindControls() }
    }

    override fun onDetachedFromWindow() {
        providerTextWatcher?.let { watcher ->
            controls?.provider?.removeTextChangedListener(watcher)
        }
        providerTextWatcher = null
        controls = null
        super.onDetachedFromWindow()
    }

    private fun bindControls() {
        val root = findRootView(this) ?: return
        val provider = root.findViewById<ExtendedFloatingActionButton>(R.id.home_api_fab)
            ?: return
        val search = root.findViewById<View>(R.id.home_preview_search_button)
        val account = root.findViewById<View>(R.id.home_switch_account)
        val random = root.findViewById<View>(R.id.home_random)

        controls = HomeControls(provider, search, account, random)
        providerName = provider.text?.toString().orEmpty()
        providerTextWatcher?.let { provider.removeTextChangedListener(it) }
        providerTextWatcher = object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                providerName = s?.toString().orEmpty()
            }
            override fun afterTextChanged(s: android.text.Editable?) = Unit
        }.also(provider::addTextChangedListener)
        invalidate()
    }

    private fun findRootView(view: View): ViewGroup? {
        var current: View? = view
        while (current != null) {
            if (current is ViewGroup && current.findViewById<View>(R.id.home_api_fab) != null) {
                return current
            }
            current = current.parent as? View
        }
        return null
    }

    @Composable
    override fun Content() {
        ReelTideTheme {
            val current = controls
            if (current == null) {
                Spacer(modifier = Modifier.height(1.dp))
            } else {
                HomeChrome(
                    providerName = providerName.ifBlank { "Sources" },
                    showRandom = current.random?.isVisible == true,
                    onProviderClick = { current.provider.performClick() },
                    onProviderLongClick = { current.provider.performLongClick() },
                    onSearchClick = { current.search?.performClick() },
                    onAccountClick = { current.account?.performClick() },
                    onRandomClick = { current.random?.performClick() },
                )
            }
        }
    }

    private data class HomeControls(
        val provider: ExtendedFloatingActionButton,
        val search: View?,
        val account: View?,
        val random: View?,
    )
}

@Composable
private fun HomeChrome(
    providerName: String,
    showRandom: Boolean,
    onProviderClick: () -> Unit,
    onProviderLongClick: () -> Unit,
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit,
    onRandomClick: () -> Unit,
) {
    val tokens = LocalReelTideGlassTokens.current
    val providerShape = RoundedCornerShape(999.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GlassPill(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(providerShape)
                .combinedClickable(
                    role = Role.Button,
                    onClick = onProviderClick,
                    onLongClick = onProviderLongClick,
                )
                .semantics {
                    contentDescription = "Content source: $providerName"
                    role = Role.Button
                },
            level = GlassLevel.Strong,
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_filter_list_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = tokens.textSecondary,
                )
                Spacer(Modifier.width(10.dp))
                AnimatedContent(
                    targetState = providerName,
                    transitionSpec = {
                        (fadeIn() + scaleIn(initialScale = 0.96f)) togetherWith
                            (fadeOut() + scaleOut(targetScale = 1.04f))
                    },
                    label = "home-provider-label",
                ) { name ->
                    Text(
                        text = name,
                        color = tokens.textPrimary,
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 1,
                    )
                }
            }
        }

        HomeChromeIconButton(
            icon = R.drawable.search_icon,
            description = "Search",
            onClick = onSearchClick,
        )
        HomeChromeIconButton(
            icon = R.drawable.ic_outline_account_circle_24,
            description = "Account",
            onClick = onAccountClick,
        )
        if (showRandom) {
            HomeChromeIconButton(
                icon = R.drawable.ic_baseline_play_arrow_24,
                description = "Play random",
                onClick = onRandomClick,
            )
        }
    }
}

@Composable
private fun HomeChromeIconButton(
    icon: Int,
    description: String,
    onClick: () -> Unit,
) {
    val tokens = LocalReelTideGlassTokens.current
    GlassPill(
        modifier = Modifier
            .size(48.dp)
            .combinedClickable(role = Role.Button, onClick = onClick)
            .semantics {
                contentDescription = description
                role = Role.Button
            },
        level = GlassLevel.Elevated,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(21.dp),
                tint = tokens.textPrimary,
            )
        }
    }
}
